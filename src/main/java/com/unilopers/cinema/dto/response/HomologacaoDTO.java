package com.unilopers.cinema.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalDateTime;

@JacksonXmlRootElement(localName = "homologacao")
public class HomologacaoDTO {

    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JacksonXmlProperty(localName = "filme")
    private FilmeSimplesDTO filme;

    @JacksonXmlProperty(localName = "sala")
    private SalaDTO sala;

    @JacksonXmlProperty(localName = "requisitoTecnico")
    private String requisitoTecnico;

    @JacksonXmlProperty(localName = "statusValidacao")
    private String statusValidacao;

    @JacksonXmlProperty(localName = "dataAnalise")
    private LocalDateTime dataAnalise;

    public HomologacaoDTO() {}

    public HomologacaoDTO(Long id, FilmeSimplesDTO filme, SalaDTO sala, String requisitoTecnico, String statusValidacao, LocalDateTime dataAnalise) {
        this.id = id;
        this.filme = filme;
        this.sala = sala;
        this.requisitoTecnico = requisitoTecnico;
        this.statusValidacao = statusValidacao;
        this.dataAnalise = dataAnalise;
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

    public void setDataAnalise(LocalDateTime dataAnalise) {
        this.dataAnalise = dataAnalise;
    }
}