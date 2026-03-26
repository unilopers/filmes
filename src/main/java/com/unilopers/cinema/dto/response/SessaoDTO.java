package com.unilopers.cinema.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JacksonXmlRootElement(localName = "sessao")
public class SessaoDTO {

    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JacksonXmlProperty(localName = "filme")
    private FilmeSimplesDTO filme;

    @JacksonXmlProperty(localName = "sala")
    private SalaDTO sala;

    @JacksonXmlProperty(localName = "dataHora")
    private LocalDateTime dataHora;

    @JacksonXmlProperty(localName = "precoBase")
    private BigDecimal precoBase;

    @JacksonXmlProperty(localName = "tipoExibicao")
    private String tipoExibicao;

    @JacksonXmlProperty(localName = "status")
    private String status;

    @JacksonXmlProperty(localName = "createdAt")
    private LocalDateTime createdAt;

    @JacksonXmlProperty(localName = "updatedAt")
    private LocalDateTime updatedAt;

    public SessaoDTO() {}

    public SessaoDTO(Long id, FilmeSimplesDTO filme, SalaDTO sala, LocalDateTime dataHora,
                     BigDecimal precoBase, String tipoExibicao, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.filme = filme;
        this.sala = sala;
        this.dataHora = dataHora;
        this.precoBase = precoBase;
        this.tipoExibicao = tipoExibicao;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FilmeSimplesDTO getFilme() {
        return filme;
    }

    public void setFilme(FilmeSimplesDTO filme) {
        this.filme = filme;
    }

    public SalaDTO getSala() {
        return sala;
    }

    public void setSala(SalaDTO sala) {
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