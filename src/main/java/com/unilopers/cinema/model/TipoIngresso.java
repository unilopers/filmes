package com.unilopers.cinema.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tipos_ingresso")
public class TipoIngresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_ingresso")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "fator_preco")
    private BigDecimal fatorPreco;

    // NOVO CAMPO: Para dar match com a sessão (Ex: "3D", "2D")
    @Column(name = "categoria_tecnica")
    private String categoriaTecnica;

    public TipoIngresso() {}

    public TipoIngresso(String descricao, BigDecimal fatorPreco, String categoriaTecnica) {
        this.descricao = descricao;
        this.fatorPreco = fatorPreco;
        this.categoriaTecnica = categoriaTecnica;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getFatorPreco() { return fatorPreco; }
    public void setFatorPreco(BigDecimal fatorPreco) { this.fatorPreco = fatorPreco; }
    public String getCategoriaTecnica() { return categoriaTecnica; }
    public void setCategoriaTecnica(String categoriaTecnica) { this.categoriaTecnica = categoriaTecnica; }
}