package com.unilopers.cinema.controller;

import com.unilopers.cinema.dto.request.CreateSessaoDTO;
import com.unilopers.cinema.dto.response.SessaoDTO;
import com.unilopers.cinema.mapper.SessaoMapper;
import com.unilopers.cinema.model.*;
import com.unilopers.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.unilopers.cinema.service.async.SessaoAsyncService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private SessaoAssentoRepository sessaoAssentoRepository;

    @Autowired
    private HomologacaoRepository homologacaoRepository;

    @Autowired
    private SessaoAsyncService sessaoAsyncService;

    @Autowired
    private SessaoMapper sessaoMapper;

    @GetMapping
    public List<SessaoDTO> list() {
        List<Sessao> sessoes = sessaoRepository.findAll();
        return sessaoMapper.toDTOList(sessoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoDTO> read(@PathVariable Long id) {
        Optional<Sessao> sessao = sessaoRepository.findById(id);
        return sessao
                .map(sessaoMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateSessaoDTO dto) {
        try {
            Optional<Filme> filme = filmeRepository.findById(dto.getIdFilme());
            Optional<Sala> sala = salaRepository.findById(dto.getIdSala());

            if (filme.isEmpty() || sala.isEmpty()) {
                return ResponseEntity.badRequest().body("Filme ou Sala inválidos");
            }

            // Valida homologação
            boolean isHomologado = homologacaoRepository.existsByFilmeAndSalaAndRequisitoTecnicoAndStatusValidacao(
                    filme.get(), sala.get(), dto.getTipoExibicao(), "Aprovado"
            );

            if (!isHomologado) {
                return ResponseEntity.badRequest().body(
                        "Sala não homologada para exibir este filme em " + dto.getTipoExibicao()
                );
            }

            Sessao sessao = new Sessao();
            sessao.setFilme(filme.get());
            sessao.setSala(sala.get());
            sessao.setDataHora(dto.getDataHora());
            sessao.setPrecoBase(dto.getPrecoBase());
            sessao.setTipoExibicao(dto.getTipoExibicao());
            sessao.setStatus("PENDENTE");

            Sessao saved = sessaoRepository.save(sessao);

            sessaoAsyncService.gerarAssentos(saved.getId(), sala.get().getCapacidade());

            SessaoDTO responseDTO = sessaoMapper.toDTO(saved);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();

            return ResponseEntity.created(location).body(responseDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (sessaoRepository.existsById(id)) {
            sessaoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}