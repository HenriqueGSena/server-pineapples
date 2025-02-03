package com.server.pineapples.entities;

import java.util.Date;

public class Airbnb {

    private Date dataPagamento;
    private String descricao;
    private String valor;

    public Airbnb() {
    }

    public Airbnb(Date dataPagamento, String descricao, String valor) {
        this.dataPagamento = dataPagamento;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
