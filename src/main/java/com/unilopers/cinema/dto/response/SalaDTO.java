package com.unilopers.cinema.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "sala")
public class SalaDTO {

    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JacksonXmlProperty(localName = "nome")
    private String nome;

    @JacksonXmlProperty(localName = "capacidade")
    private Integer capacidade;

    @JacksonXmlProperty(localName = "disponivel")
    private Boolean disponivel;

    public SalaDTO() {}

    public SalaDTO(Long id, String nome, Integer capacidade, Boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.disponivel = disponivel;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getCapacidade() {
        return capacidade;
    }
    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }
    public Boolean getDisponivel() {
        return disponivel;
    }
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
}