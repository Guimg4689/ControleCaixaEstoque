package com.estoque.gerenciamento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Produto implements Serializable {
    static int cont = 0;
    private int id;
    private String nome;
    private String descricao;
    private double preco_unitario;
    private int quantStock;
    private double custoProd;

    public Produto(int id, int quantStock) {
        this.id = id;
        this.quantStock = quantStock;
    }


    public Produto(String nome, String descricao, double preco_unitario, int quantStock, double custoProd){
            this.id = cont;
            this.nome = nome;
            this.descricao = descricao;
            if (preco_unitario >= 0) this.preco_unitario = preco_unitario;
            else throw new IllegalArgumentException("Atributo deve ser maior ou igual que 0");
            if (quantStock >= 0) this.quantStock = quantStock;
            else throw new IllegalArgumentException("Atributo deve ser maior ou igual que 0");
            if (custoProd >= 0) this.custoProd = custoProd;
            else throw new IllegalArgumentException("Atributo deve ser maior ou igual que 0");
            cont++;
        }

    public Produto(int i, String relogioCurren, String melhorRelogioMasculino, int i1, int i2, int i3) {
    }


    public String toString () {
            return "Produto:" +
                    "Id:" + id +
                    ", nome='" + nome + '\'' +
                    ", descricao='" + descricao + '\'' +
                    ", preco_unitario=" + preco_unitario +
                    ", quantStock=" + quantStock +
                    ", custoProd=" + custoProd +
                    '}' + "\n";
        }


        public String MostraCliente () {
            return "Produto{" +
                    "id=" + id +
                    ", nome= '" + nome + '\'' +
                    ", descricao= '" + descricao + '\'' +
                    ", preco_unitario= " + preco_unitario +
                    ", quant= " + quantStock +
                    '}' + "\n";
        }
    }
