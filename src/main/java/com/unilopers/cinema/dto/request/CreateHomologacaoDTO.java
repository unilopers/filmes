package com.unilopers.cinema.dto.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "homologacao")
public class CreateHomologacaoDTO {

    @JacksonXmlProperty(localName = "idFilme")
    private Long idFilme;

    @JacksonXmlProperty(localName = "idSala")
    private Long idSala;

    @JacksonXmlProperty(localName = "requisitoTecnico")
    private String requisitoTecnico;

    @JacksonXmlProperty(localName = "statusValidacao")
    private String statusValidacao;

    public CreateHomologacaoDTO() {}

    public CreateHomologacaoDTO(Long idFilme, Long idSala, String requisitoTecnico, String statusValidacao) {
        this.idFilme = idFilme;
        this.idSala = idSala;
        this.requisitoTecnico = requisitoTecnico;
        this.statusValidacao = statusValidacao;
    }

    // Getters e Setters
    public Long getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Long idFilme) {
        this.idFilme = idFilme;
    }

    public Long getIdSala() {
        return idSala;
    }

    public void setIdSala(Long idSala) {
        this.idSala = idSala;
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
}