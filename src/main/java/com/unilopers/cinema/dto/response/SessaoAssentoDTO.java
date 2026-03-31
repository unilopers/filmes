package com.unilopers.cinema.dto.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JacksonXmlRootElement(localName = "assento")
public class SessaoAssentoDTO {

    @JacksonXmlProperty(localName = "idAssento")
    private Integer idAssento;

    @JacksonXmlProperty(localName = "reservado")
    private Boolean reservado;

    public SessaoAssentoDTO() {}

    public SessaoAssentoDTO(Integer idAssento, Boolean reservado) {
        this.idAssento = idAssento;
        this.reservado = reservado;
    }

    public Integer getIdAssento() { return idAssento; }
    public void setIdAssento(Integer idAssento) { this.idAssento = idAssento; }

    public Boolean getReservado() { return reservado; }
    public void setReservado(Boolean reservado) { this.reservado = reservado; }
}