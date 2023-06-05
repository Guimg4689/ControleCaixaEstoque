package com.estoque.gerenciamento.service;

import com.estoque.gerenciamento.model.Pedido;
import com.estoque.gerenciamento.model.Produto;
import com.estoque.gerenciamento.model.Token;
import com.estoque.gerenciamento.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
@NoArgsConstructor
@Getter
@Setter
public class Authorization {

    final String secret = "pipocasazuisbomdia10";
    private ArrayList<Usuario> users;
    private HashMap<String,Usuario>usuarioHashMap;

    public boolean autenticaUser(Usuario a){
        boolean retorno = false;
       if(usuarioHashMap.containsKey(a.getEmail())){
         if ((a.getSenha()+secret).equals(usuarioHashMap.get(a.getEmail()).getSenha()))
             retorno = true;
        }
        return retorno;
    }

    public boolean verificarAdm(Usuario a){
        boolean retorno = false;
        if(autenticaUser(a))
            if (usuarioHashMap.get(a.getEmail()).isAdm()) retorno = true;
        return  retorno;
    }

    public Token geraToken(Usuario a) throws Exception {
        if(autenticaUser(a)){
            Token b = new Token(a.getEmail(), verificarAdm(a));
            return b;
        }else throw new Exception("Acesso negado");
    }

    public void cadastraUsuario(String email, String senha) throws Exception {
        if(usuarioHashMap.containsKey(email))throw new Exception("Email invalido");
        Usuario a = new Usuario(email,senha+secret,false);
        users.add(a);
        usuarioHashMap.put(a.getEmail(),a);
      //  escreverUsuario("usuarios.dat",a);
    }

    public void cadastraAdm(String email, String senha) throws Exception {
        if(usuarioHashMap.containsKey(email))throw new Exception("Email invalido");
        Usuario a = new Usuario(email,senha+secret,true);
        users.add(a);
        usuarioHashMap.put(a.getEmail(),a);
        //escreverUsuario("usuarios.dat",a);
    }

    public String imprimeUsers(Token admin){
        if (admin.isAcesso_total()){
            for (Usuario b : this.users) {
                return b.toString();
            }
        }
        return "Acesso Negado";
    }



    public Usuario escreverUsuario(String s,ArrayList<Usuario> a) {
        FileOutputStream escritorArquivo = null;
        ObjectOutputStream escritorObj;
        try {
            escritorArquivo = new FileOutputStream(s);
            escritorObj = new ObjectOutputStream(escritorArquivo);

            a.forEach(element-> {
                try {
                    escritorObj.writeObject(element);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException i) {
            System.out.println(i.getMessage());
        } finally {
            try {
                if (escritorArquivo != null) escritorArquivo.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public void lerUsers(String arquivo) throws IOException {
        ObjectInputStream leitorObj = null;
        FileInputStream leitorArquivo = null ;

        try {
            leitorArquivo = new FileInputStream(arquivo);
            leitorObj = new ObjectInputStream(leitorArquivo);
            while (arquivo!= null) {
                Usuario b = (Usuario) leitorObj.readObject();
                users.add(b);
                usuarioHashMap.put(b.getEmail(),b);
            }
        } catch (EOFException e) {
            try {
                leitorArquivo.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (Exception nex) {
            System.out.println(nex.getMessage());
        } finally {
            try {
                if (leitorArquivo != null) leitorArquivo.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
