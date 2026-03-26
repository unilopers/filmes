package com.unilopers.cinema.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessoes")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sessao")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_filme")
    @JsonIgnoreProperties({"generos", "createdAt", "updatedAt"})
    private Filme filme;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sala")
    private Sala sala;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "preco_base")
    private BigDecimal precoBase;

    @Column(name = "tipo_exibicao")
    private String tipoExibicao;

    @Column(name = "status")
    private String status = "PENDENTE";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Sessao() {}

    // Getters e Setters
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        this.precoBase = precoBase;
    }

    public String getTipoExibicao() {
        return tipoExibicao;
    }

    public void setTipoExibicao(String tipoExibicao) {
        this.tipoExibicao = tipoExibicao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
