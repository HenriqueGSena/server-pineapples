package com.server.pineapples.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class File {

    private Long id;
    private LocalDate dataPagamento;
    private String descricao;
    private Double valor;

    public File() {
    }

    public File(LocalDate dataPagamento, Long id, String descricao, Double valor) {
        this.dataPagamento = dataPagamento;
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
