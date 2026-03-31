package com.unilopers.cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sala")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "capacidade")
    private Integer capacidade;

    @Column(name = "disponivel")
    private Boolean disponivel;

    public Sala() {}

    public Sala(String nome, Integer capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.disponivel = true;
    }

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