package com.estoque.gerenciamento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class Usuario implements Serializable{

    private String email;
    private String senha;
    private boolean isAdm;


    //somente utilizado temporário
    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
        this.isAdm = false;
    }

    @Override
    public String toString() {
        return "Usuario{" +

                "email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", isAdm=" + isAdm +
                '}';
    }
}
