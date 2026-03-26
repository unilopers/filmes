package com.unilopers.cinema.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JacksonXmlRootElement(localName = "ingresso")
public class IngressoDTO {

    @JacksonXmlProperty(localName = "id")
    private Long id;

    @JacksonXmlProperty(localName = "usuario")
    private UsuarioDTO usuario;

    @JacksonXmlProperty(localName = "sessao")
    private SessaoDTO sessao;

    @JacksonXmlProperty(localName = "tipoIngresso")
    private TipoIngressoDTO tipoIngresso;

    @JacksonXmlProperty(localName = "idAssento")
    private Integer idAssento;

    @JacksonXmlProperty(localName = "valorFinal")
    private BigDecimal valorFinal;

    @JacksonXmlProperty(localName = "status")
    private String status;

    @JacksonXmlProperty(localName = "createdAt")
    private LocalDateTime createdAt;

    public IngressoDTO() {}

    public IngressoDTO(Long id, UsuarioDTO usuario, SessaoDTO sessao, TipoIngressoDTO tipoIngresso,
                       Integer idAssento, BigDecimal valorFinal, String status, LocalDateTime createdAt) {
        this.id = id;
        this.usuario = usuario;
        this.sessao = sessao;
        this.tipoIngresso = tipoIngresso;
        this.idAssento = idAssento;
        this.valorFinal = valorFinal;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public SessaoDTO getSessao() {
        return sessao;
    }

    public void setSessao(SessaoDTO sessao) {
        this.sessao = sessao;
    }

    public TipoIngressoDTO getTipoIngresso() {
        return tipoIngresso;
    }

    public void setTipoIngresso(TipoIngressoDTO tipoIngresso) {
        this.tipoIngresso = tipoIngresso;
    }

    public Integer getIdAssento() {
        return idAssento;
    }

    public void setIdAssento(Integer idAssento) {
        this.idAssento = idAssento;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
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
}