package com.unilopers.cinema.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@Entity
@Table(name = "homologacoes")
public class Homologacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_homologacao")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_filme", nullable = false)
    @JsonIgnoreProperties({"generos", "createdAt", "updatedAt"})
    private Filme filme;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sala", nullable = false)
    private Sala sala;

    @Column(name = "requisito_tecnico")
    private String requisitoTecnico;

    @Column(name = "status_validacao")
    private String statusValidacao;

    @CreationTimestamp
    @Column(name = "data_analise", updatable = false)
    private LocalDateTime dataAnalise;

    public Homologacao() {
    }

    public Homologacao(Filme filme, Sala sala, String requisitoTecnico, String statusValidacao) {
        this.filme = filme;
        this.sala = sala;
        this.requisitoTecnico = requisitoTecnico;
        this.statusValidacao = statusValidacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getRequisitoTecnico() {
        return requisitoTecnico;
    }

    public void setRequisitoTecnico(String requisitoTecnico) {
        this.requisitoTecnico = requisitoTecnico;
    }

    public String getStatusValidacao() {
        return statusValidacao;
    }

    public void setStatusValidacao(String statusValidacao) {
        this.statusValidacao = statusValidacao;
    }

    public LocalDateTime getDataAnalise() {
        return dataAnalise;
    }
}