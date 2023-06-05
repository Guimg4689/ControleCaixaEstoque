package com.estoque.gerenciamento.service;

import com.estoque.gerenciamento.model.Pedido;
import com.estoque.gerenciamento.model.Produto;
import com.estoque.gerenciamento.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

@NoArgsConstructor
@Getter
public class Loja extends Authorization{

    public ArrayList<Produto> produtos;
    public ArrayList<Pedido>pedidos;


    public void makeOrder(int id, int quant, ArrayList<Produto> a){
        Produto b = produtos.get(id);
        Produto c = new Produto(b.getId(),b.getNome(),b.getDescricao(),b.getPreco_unitario(),quant, b.getCustoProd());
        a.add(c);
    }

    public void updateStock(ArrayList<Produto> a) throws Exception{
        for (Produto d:a){
            Produto b  = produtos.get((int) d.getId());
           if(b.getQuantStock()-d.getQuantStock()>=0)b.setQuantStock((b.getQuantStock()-d.getQuantStock()));
           else throw new IllegalArgumentException("Produto no estoque insuficiente");
        }
        File s = new File("produtos.dat");
        escreverProduto(s,produtos);
    }

    public boolean verificaEstoque(ArrayList<Produto> a){
        int cont = 0;
        for (Produto d:a){
            Produto b  = produtos.get((int) d.getId());
            if(b.getQuantStock()-d.getQuantStock()< 0)
               cont++;

        }
      if (cont == 0)return true;
      else return false;
    }



    public Double calculaTotal(ArrayList<Produto> x){
        Iterator<Produto> it = x.iterator();
        Double total = 0.0;
        while (it.hasNext()){
            Produto one = it.next();
            total+= one.getPreco_unitario() * one.getQuantStock();
        }
        return total;
    }


    public Double calculaCusto(ArrayList<Produto> x){
        Iterator<Produto> it = x.iterator();
        Double total = 0.0;
        while (it.hasNext()){
            Produto one = it.next();
            total = total + one.getCustoProd() * one.getQuantStock();

        }
        return total;
    }
    public String mostraProdutos(ArrayList<Produto> produtos){
        String word ="";
        for(Produto a:produtos){
            word += a.MostraCliente();
        }
        return word;
    }

    public String retornaCaixa(){
        pedidos.removeAll(pedidos);
        lerPedidos("pedidos.dat");

       double Faturamento,Custo_Vendidos,Lucro, Faturamento_Potencial, Lucro_Potencial,Custo_Total;
       ArrayList<Produto> comprados = new ArrayList<Produto>();
       pedidos.forEach(element-> element.getProdutos().forEach(produto -> comprados.add(produto)));
       Faturamento = calculaTotal(comprados);
       Custo_Vendidos = calculaCusto(comprados);
       Lucro = Faturamento - Custo_Vendidos;
       //potencial de faturamento dos produtos os quais ainda não foram vendidos
        Faturamento_Potencial = calculaTotal(this.produtos);
        Custo_Total = calculaCusto(this.produtos);
        Lucro_Potencial = Faturamento_Potencial - Custo_Total;
        return "---------------------------CAIXA------------------------------------"+ "\n" +
                "Atual: " + "\n" +
                "Faturamento: " + Faturamento + "\n" +
                "Custo: " + Custo_Vendidos  + "\n" +
                "Lucro:" + Lucro + "\n" +
                "-----------------------FUTURO------------------------" + "\n" +
                "Potencial de Vendas Futuras: " + "\n" +
                "Faturamento: " + Faturamento_Potencial + "\n" +
                "Custo: " + Custo_Total  + "\n" +
                "Lucro:" + Lucro_Potencial + "\n" +
                "-----------------------FIM DE FLUXO---------------------------------";
    }


    public void attEstoque(ArrayList<Produto> produtos1){
        for(Produto produto: produtos1){
            Produto novo = new Produto(produto.getId(),produto.getQuantStock());
            int quant = produtos.get(produto.getId()).getQuantStock();
            produtos.get(produto.getId()).setQuantStock(quant += produto.getQuantStock() );
        }
    }
    public void decreaseStock(ArrayList<Produto> produtos1){
        for(Produto produto: produtos1){
            Produto novo = new Produto(produto.getId(),produto.getQuantStock());
            int quant = produtos.get(produto.getId()).getQuantStock();
            produtos.get(produto.getId()).setQuantStock(quant -= produto.getQuantStock());
        }
    }

    public void lerProd (File arquivo) throws IOException {
        ObjectInputStream leitorObj = null;
        FileInputStream leitorArquivo = null;
        try {
            leitorArquivo = new FileInputStream(arquivo);
            leitorObj = new ObjectInputStream(leitorArquivo);
            while (arquivo!= null){
            Produto b = (Produto) leitorObj.readObject();
            produtos.add(b);
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

    public Produto escreverProduto(File s, ArrayList<Produto> a) {

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

    public void lerPedidos(String arquivo) {
        ObjectInputStream leitorObj = null;
        FileInputStream leitorArquivo = null;

        try {
            leitorArquivo = new FileInputStream(arquivo);
            leitorObj = new ObjectInputStream(leitorArquivo);
            while (arquivo!= null){
                Pedido b = (Pedido) leitorObj.readObject();

                pedidos.add(b);}

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

    public Produto escreverPedido(String s, ArrayList<Pedido> a) {
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

    public void reescreveLista(ArrayList<Produto> listaObjetos, String caminhoArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            oos.writeObject(listaObjetos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



        // Método para reescrever a lista de objetos no arquivo

    }

