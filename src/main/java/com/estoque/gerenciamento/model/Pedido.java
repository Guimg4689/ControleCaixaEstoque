package com.estoque.gerenciamento.model;

import com.estoque.gerenciamento.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class Pedido implements Serializable {
    private static int cont = 0;
    private int id;
    private String emailUser;
    private ArrayList<Produto> produtos;
    private double total;

    public Pedido(String idUsuario, ArrayList<Produto> produtos,double total) {
        this.emailUser = idUsuario;
        this.produtos = produtos;
        this.cont = cont;
        this.total = total;
        cont++;

    }

    private String printaProdutos(){
        String listString = getProdutos().stream().map(Produto::MostraCliente)
                .collect(Collectors.joining("\n"));
        return listString;
    }



    @Override
    public String toString() {
        return "----------------------------------" + "\n" +"Pedido:" + "\n" +

                "Email Usuario: " + emailUser + "\n" +
                "Produtos :\n" + printaProdutos() + "\n" +
                "Total= " + total + "\n" +
                "----------------------------------";
    }
}