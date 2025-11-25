package com.unilopers.cinema.controller;

import com.unilopers.cinema.model.*;
import com.unilopers.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/homologacoes")
public class HomologacaoController {

    @Autowired
    private HomologacaoRepository homologacaoRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private SalaRepository salaRepository;

    @GetMapping
    public List<Homologacao> list() {
        return homologacaoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody com.unilopers.cinema.dto.request.CreateHomologacaoDTO dto) {
        try {
            Optional<Filme> filme = filmeRepository.findById(dto.getIdFilme());
            Optional<Sala> sala = salaRepository.findById(dto.getIdSala());

            if (filme.isEmpty() || sala.isEmpty()) {
                return ResponseEntity.badRequest().body("Filme ou Sala não encontrados");
            }

            String requisito = dto.getRequisitoTecnico() != null ? dto.getRequisitoTecnico() : "2D";
            String status = dto.getStatusValidacao() != null ? dto.getStatusValidacao() : "Aprovado";

            Homologacao homologacao = new Homologacao(filme.get(), sala.get(), requisito, status);
            Homologacao saved = homologacaoRepository.save(homologacao);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();
            return ResponseEntity.created(location).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}