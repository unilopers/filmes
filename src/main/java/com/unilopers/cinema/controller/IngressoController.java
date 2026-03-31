package com.unilopers.cinema.controller;

import com.unilopers.cinema.dto.request.ComprarIngressoDTO;
import com.unilopers.cinema.dto.response.IngressoDTO;
import com.unilopers.cinema.mapper.IngressoMapper;
import com.unilopers.cinema.model.*;
import com.unilopers.cinema.repository.*;
import com.unilopers.cinema.service.async.IngressoAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Autowired
    private IngressoAsyncService ingressoAsyncService;

    @GetMapping
    public ResponseEntity<?> list(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (isAdmin) {
            return ResponseEntity.ok(ingressoMapper.toDTOList(ingressoRepository.findAll()));
        }

        Optional<Usuario> usuario = usuarioRepository.findByEmail(authentication.getName());
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(ingressoMapper.toDTOList(ingressoRepository.findByUsuario(usuario.get())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable Long id, Authentication authentication) {
        Optional<Ingresso> ingresso = ingressoRepository.findById(id);
        if (ingresso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean isAdmin = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isDono = ingresso.get().getUsuario().getEmail().equals(authentication.getName());

        if (!isAdmin && !isDono) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(ingressoMapper.toDTO(ingresso.get()));
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

            if (dto.getNumeroAssento() > sessao.get().getSala().getCapacidade() || dto.getNumeroAssento() <= 0) {
                return ResponseEntity.badRequest().body(Map.of(
                        "erro", "Assento fora do limite da sala (1-" + sessao.get().getSala().getCapacidade() + ")"
                ));
            }

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

            // Cria ingresso
            Ingresso ingresso = new Ingresso(
                    usuario.get(),
                    sessao.get(),
                    tipoIngresso.get(),
                    dto.getNumeroAssento(),
                    valorFinal,
                    "PENDENTE"
            );

            Ingresso saved = ingressoRepository.save(ingresso);

            ingressoAsyncService.processarConfirmacaoPagamento(saved.getId());

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