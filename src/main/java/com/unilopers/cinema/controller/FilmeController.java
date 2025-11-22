package com.unilopers.cinema.controller;

import com.unilopers.cinema.model.Filme;
import com.unilopers.cinema.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping
    public List<Filme> list() {
        return filmeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> read(@PathVariable Long id) {
        Optional<Filme> filme = filmeRepository.findById(id);
        return filme.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Filme> create(@RequestBody Filme filme) {
        // Verifica se já existe filme com mesmo título
        Optional<Filme> existing = filmeRepository.findByTitulo(filme.getTitulo());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Filme saved = filmeRepository.save(filme);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> update(@PathVariable Long id, @RequestBody Filme details) {
        Optional<Filme> opt = filmeRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Filme filme = opt.get();

        if (details.getTitulo() != null) {
            filme.setTitulo(details.getTitulo());
        }
        if (details.getDuracaoMin() != null) {
            filme.setDuracaoMin(details.getDuracaoMin());
        }
        if (details.getAno() != null) {
            filme.setAno(details.getAno());
        }

        Filme saved = filmeRepository.save(filme);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Filme> opt = filmeRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        filmeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}