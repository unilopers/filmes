package com.unilopers.cinema.controller;

import com.unilopers.cinema.dto.request.ComprarIngressoDTO;
import com.unilopers.cinema.dto.response.IngressoDTO;
import com.unilopers.cinema.mapper.IngressoMapper;
import com.unilopers.cinema.model.*;
import com.unilopers.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;

    @Autowired
    private SessaoAssentoRepository sessaoAssentoRepository;

    @Autowired
    private IngressoMapper ingressoMapper;

    @GetMapping
    public List<IngressoDTO> list() {
        List<Ingresso> ingressos = ingressoRepository.findAll();
        return ingressoMapper.toDTOList(ingressos);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ComprarIngressoDTO dto) {
        try {
            Optional<Usuario> usuario = usuarioRepository.findById(dto.getIdUsuario());
            Optional<Sessao> sessao = sessaoRepository.findById(dto.getIdSessao());
            Optional<TipoIngresso> tipoIngresso = tipoIngressoRepository.findById(dto.getIdTipoIngresso());

            if (usuario.isEmpty() || sessao.isEmpty() || tipoIngresso.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Dados inválidos"));
            }

            // Valida compatibilidade técnica
            String categoriaSessao = sessao.get().getTipoExibicao();
            String categoriaIngresso = tipoIngresso.get().getCategoriaTecnica();

            if (!categoriaSessao.equalsIgnoreCase(categoriaIngresso)) {
                return ResponseEntity.badRequest().body(Map.of(
                        "erro", "Tipo de ingresso (" + categoriaIngresso + ") incompatível com a sessão (" + categoriaSessao + ")"
                ));
            }

            // Verifica disponibilidade do assento
            Optional<SessaoAssento> assento = sessaoAssentoRepository.findBySessaoAndIdAssento(sessao.get(), dto.getNumeroAssento());
            if (assento.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Assento não existe"));
            }
            if (assento.get().getReservado()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Assento já ocupado"));
            }

            // Reserva assento
            assento.get().setReservado(true);
            sessaoAssentoRepository.save(assento.get());

            // Calcula valor final
            BigDecimal valorFinal = sessao.get().getPrecoBase().multiply(tipoIngresso.get().getFatorPreco());

            // Cria ingresso com status PENDENTE
            Ingresso ingresso = new Ingresso(
                    usuario.get(),
                    sessao.get(),
                    tipoIngresso.get(),
                    dto.getNumeroAssento(),
                    valorFinal,
                    "PENDENTE"
            );

            Ingresso saved = ingressoRepository.save(ingresso);
            IngressoDTO responseDTO = ingressoMapper.toDTO(saved);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(saved.getId())
                    .toUri();

            return ResponseEntity.created(location).body(responseDTO);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }
}