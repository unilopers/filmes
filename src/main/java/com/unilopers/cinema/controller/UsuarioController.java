package com.unilopers.cinema.controller;

import com.unilopers.cinema.dto.request.CreateUsuarioDTO;
import com.unilopers.cinema.dto.response.UsuarioDTO;
import com.unilopers.cinema.mapper.UsuarioMapper;
import com.unilopers.cinema.model.Usuario;
import com.unilopers.cinema.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @GetMapping
    public List<UsuarioDTO> list() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> read(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody CreateUsuarioDTO dto) {
        Optional<Usuario> existing = usuarioRepository.findByEmail(dto.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Usuario usuario = usuarioMapper.toEntity(dto);
        Usuario saved = usuarioRepository.save(usuario);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(usuarioMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @RequestBody CreateUsuarioDTO dto) {
        Optional<Usuario> opt = usuarioRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = opt.get();
        usuarioMapper.updateEntity(usuario, dto);
        Usuario saved = usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuarioMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}