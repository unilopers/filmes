package com.unilopers.cinema.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sessao_assento")
public class SessaoAssento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sessao", nullable = false)
    private Sessao sessao;

    @Column(name = "id_assento", nullable = false)
    private Integer idAssento;

    @Column(name = "reservado", nullable = false)
    private Boolean reservado = false;

    public SessaoAssento() {}

    public SessaoAssento(Sessao sessao, Integer idAssento, Boolean reservado) {
        this.sessao = sessao;
        this.idAssento = idAssento;
        this.reservado = reservado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public Integer getIdAssento() {
        return idAssento;
    }

    public void setIdAssento(Integer idAssento) {
        this.idAssento = idAssento;
    }

    public Boolean getReservado() {
        return reservado;
    }

    public void setReservado(Boolean reservado) {
        this.reservado = reservado;
    }
}
