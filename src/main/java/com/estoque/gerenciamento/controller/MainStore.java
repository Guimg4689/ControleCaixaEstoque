package com.estoque.gerenciamento.controller;

import com.estoque.gerenciamento.model.Pedido;
import com.estoque.gerenciamento.model.Produto;
import com.estoque.gerenciamento.model.Token;
import com.estoque.gerenciamento.model.Usuario;
import com.estoque.gerenciamento.service.Authorization;
import com.estoque.gerenciamento.service.Loja;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MainStore {
    public static void main(String[] args) throws Exception {
        Loja loja = new Loja();

        Scanner scanner = new Scanner(System.in);
        String email = null, senha;
        Boolean op;
        int option = 0;

        ArrayList<Usuario> users = new ArrayList<>(); loja.setUsers(users);
        HashMap<String, Usuario> UserHashmap = new HashMap<>();
        loja.setUsuarioHashMap(UserHashmap);
        loja.produtos = new ArrayList<>();
        loja.pedidos = new ArrayList<>();
        ArrayList<Produto>myproducts = new ArrayList<>();
        ArrayList<Produto>estoque = new ArrayList<>();


        try {
            loja.lerUsers("usuarios.dat");
        }catch (IOException ex){
            System.out.println("Erro");
        }
        File arquivo = new File("produtos.dat");
        try {
            loja.lerProd(arquivo);
        }catch (IOException e){
            System.out.println("Erro ao Abrir arquivo");
        }

        try {
            loja.lerPedidos("pedidos.dat");
        }catch (Exception e){
            System.out.println("Erro ao Abrir arquivo");
        }
        //adm padrão

        //loja.cadastraAdm("gui","123");
        //cadastrandos os produtos
        //loja.produtos.add(0, new Produto( "Bola", "Feito para jogar futebol", 47, 15, 30));
        //loja.produtos.add(1, new Produto( "Relogio Curren", "Melhor Relogio Masculino", 104, 10, 55));
        //loja.produtos.add(2, new Produto( "Soja", "Graos de Soja da Melhor Qualidade", 45, 80, 27));
        //loja.escreverProduto(arquivo,loja.produtos);


        int choose = 0;
        while (option != 3) {
            System.out.println("Digite Uma das Opcoes Abaixo: ");
            System.out.println("1 - Cadastrar Usuario");
            System.out.println("2 - Login");
            System.out.println("3 - Sair");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                        Scanner sc = new Scanner(System.in);
                        while (true){
                            System.out.println("Email:");
                             email = sc.nextLine();
                            System.out.println("Senha:");
                            senha = sc.next();
                                try {
                                    loja.cadastraUsuario(email, senha);
                                    loja.setUsers(users);
                                    loja.escreverUsuario("usuarios.dat",users);
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Erro Cadastrar Usuario");
                                }
                    }
                    System.out.println("Digite uma opcao abaixo:");
                    System.out.println("1 - Mostrar Produtos");
                    System.out.println("2 - Realizar Pedido");
                    System.out.println("3 - Sair");
                    option = sc.nextInt();
                    switch (option){
                        case 1:
                            for (Produto prod: loja.produtos) {
                                System.out.println(prod.MostraCliente());
                            }
                            break;
                        case 2 :
                            for (Produto prod: loja.produtos) {
                                System.out.println(prod.MostraCliente());
                            }

                            while (choose!=2){
                                try {
                                    System.out.println("Digite o id do Produto");
                                    int id = sc.nextInt();


                                    System.out.println("Digite a Quantidade:");
                                    int quant = scanner.nextInt();
                                    loja.makeOrder(id, quant, myproducts);
                                    System.out.println(myproducts);
                                }catch (ArrayIndexOutOfBoundsException e){
                                    System.out.println("id invalido.");
                                }catch (Exception ei){
                                    System.out.println("Erro");
                                }

                                System.out.println("1 - Continuar Comprando");
                                System.out.println("2 - Finalizar Pedido");
                                 choose = scanner.nextInt();

                                 switch (choose){
                                     case 1:
                                       break;
                                     case 2:
                                         for (Produto prod: loja.produtos) {
                                             System.out.println(prod.MostraCliente());
                                         }
                                         try {
                                             if(loja.verificaEstoque(myproducts)) {
                                                 Pedido order = new Pedido(UserHashmap.get(email).getEmail(), myproducts, loja.calculaTotal(myproducts));
                                                 loja.pedidos.add(order);
                                                 loja.escreverPedido("pedidos.dat", loja.pedidos);
                                                 System.out.println(order.toString());
                                                 loja.updateStock(myproducts);
                                                 myproducts.removeAll(myproducts);
                                             }
                                                 myproducts.removeAll(myproducts);




                                         }catch (IllegalArgumentException e) {
                                             System.out.println("Quantidade Insufuciente em Estoque");
                                         }catch (Exception ei){
                                             System.out.println("Erro!");
                                         }

                                         break;


                                 }

                            }
                            choose = 0;
                    }

                    break;

                case 2:
                    Token user = null;
                    while (user == null) {
                        Scanner s = new Scanner(System.in);
                        System.out.println("-------Login-------\n");
                        System.out.println("Email:");
                        email = s.next();
                        System.out.println("Senha:");
                        senha = s.next();
                        Usuario a = new Usuario(email, senha);
                        try {
                            Token b = loja.geraToken(a);
                            user = b;
                        } catch (Exception e) {
                            System.out.println("Erro login");
                        }
                    }

                        if(user.isAcesso_total()){
                            System.out.println("1 - Painel Adm");
                            System.out.println("2 - Sair");

                            option = scanner.nextInt();
                            switch (option){
                                case 1:
                                    System.out.println("1 - Mostra Clientes");
                                    System.out.println("2 - Cadastrar Admin");
                                    System.out.println("3 - Cadastrar Usuario");
                                    System.out.println("4 - Relatorio de Vendas");
                                    System.out.println("5 - Cadastrar Produtos");
                                    System.out.println("6 - Adicionar Estoque");
                                    System.out.println("7 - Mostrar Pedidos");

                                    option =scanner.nextInt();
                                    switch (option){
                                        case 1:
                                            for(Usuario usuario: users){
                                                System.out.println(usuario.toString());
                                            }
                                           break;
                                        case 2:
                                            try {
                                                Scanner novo = new Scanner(System.in);
                                                System.out.println("Email:");
                                                email = novo.next();
                                                System.out.println("Senha:");
                                                senha = novo.next();
                                                loja.cadastraAdm(email,senha);
                                                loja.setUsers(users);
                                                loja.escreverUsuario("usuarios.dat",users);
                                            }catch (Exception e){
                                                System.out.println("Erro Ao Cadastrar");
                                            }
                                            break;


                                        case 3:
                                            Scanner sca = new Scanner(System.in);
                                            while (true){
                                                System.out.println("Email:");
                                                email = sca.next();
                                                System.out.println("Senha:");
                                                senha = sca.next();
                                                try {
                                                    loja.cadastraUsuario(email, senha);
                                                    loja.setUsers(users);
                                                    loja.escreverUsuario("usuarios.dat",users);
                                                    break;
                                                } catch (Exception e) {
                                                    System.out.println("Erro Cadastrar Usuario");
                                                }
                                            }
                                            break;

                                        case 4:
                                            System.out.println(loja.retornaCaixa());
                                            break;
                                        case 5:

                                            Scanner s = new Scanner(System.in);
                                            while (true){
                                                System.out.println("Nome:");
                                                String nome = s.nextLine();
                                                System.out.println("Descricao:");
                                                String desc = s.nextLine();
                                                System.out.println("Preco de Venda:");
                                                Double valor_un = s.nextDouble();
                                                System.out.println("Quantidade em Estoque:");
                                                int quant = s.nextInt();
                                                System.out.println("Preco de Custo Unitario:");
                                                Double custo = s.nextDouble();
                                               try {
                                                    Produto a = new Produto(nome,desc,valor_un,quant,custo);

                                                    loja.produtos.add(a);
                                                   int index  = loja.produtos.indexOf(a);
                                                    loja.produtos.get(index).setId(index);
                                                   arquivo = new File("produtos.dat");
                                                   loja.escreverProduto(arquivo,loja.produtos);

                                                    break;
                                                } catch (Exception e) {
                                                   System.out.println("Erro em Adicionar produto");
                                                }
                                            }
                                            break;

                                        case 6:
                                            int escolha = 0;
                                            Scanner s1 = new Scanner(System.in);

                                            while (escolha!=2){
                                                System.out.println("1 - Adicionar");
                                                System.out.println("2 - Sair");
                                                escolha = s1.nextInt();
                                                switch (escolha){
                                                    case 1 :
                                                        for (Produto prod: loja.produtos) {
                                                            System.out.println(prod);

                                                        }

                                                        System.out.println("Digite o id do Produto");
                                                        int id = s1.nextInt();
                                                        System.out.println("Digite a Quantidade:");
                                                        int quant= scanner.nextInt();
                                                        loja.makeOrder(id,quant,estoque);
                                                        loja.attEstoque(estoque);
                                                        arquivo = new File("produtos.dat");
                                                        loja.escreverProduto(arquivo,loja.produtos);
                                                        estoque.removeAll(estoque);
                                                        break;


                                                    case 2:

                                                        break;
                                                }


                                            }
                                            break;
                                        case 7:
                                            for (Pedido pedido : loja.pedidos) {
                                                System.out.println(pedido.toString());
                                            }
                                            break;

                                            }

                                case 2:
                                    break;
                            }



                        }else {
                            System.out.println("Digite uma opcao abaixo:");
                            System.out.println("1 - Mostrar Produtos");
                            System.out.println("2 - Realizar Pedido");
                            System.out.println("3 - Sair");
                            Scanner scan = new Scanner(System.in);
                            option = scan.nextInt();
                            switch (option){
                                case 1:
                                    for (Produto prod: loja.produtos) {
                                        System.out.println(prod.MostraCliente());

                                    }
                                    break;

                                case 2 :
                                    for (Produto prod: loja.produtos) {
                                        System.out.println(prod.MostraCliente());

                                    }

                                    while (choose!=2){

                                        System.out.println("Digite o id do Produto");
                                        int id = scan.nextInt();
                                        System.out.println("Digite a Quantidade:");
                                        int quant= scanner.nextInt();
                                        loja.makeOrder(id,quant,myproducts);
                                        System.out.println(myproducts);


                                        System.out.println("1 - Continuar Comprando");
                                        System.out.println("2 - Finalizar Pedido");
                                        choose = scanner.nextInt();

                                        switch (choose){
                                            case 1:
                                                break;
                                            case 2:

                                                for (Produto prod: loja.produtos) {
                                                    System.out.println(prod.MostraCliente());

                                                }

                                                try {
                                                    if(loja.verificaEstoque(myproducts)) {
                                                        Pedido order = new Pedido(UserHashmap.get(email).getEmail(), myproducts, loja.calculaTotal(myproducts));
                                                        loja.pedidos.add(order);
                                                        loja.escreverPedido("pedidos.dat", loja.pedidos);
                                                        System.out.println(order.toString());
                                                        loja.updateStock(myproducts);
                                                        myproducts.removeAll(myproducts);
                                                    }else {
                                                        myproducts.removeAll(myproducts);
                                                        System.out.println("Quantidade Insufuciente em Estoque");
                                                    }



                                                }catch (IllegalArgumentException e) {
                                                    System.out.println("Erro ao Cadastrar");
                                                }
                                                break;

                                        }
                                        if (choose == 3)break;
                                    }
                            }
                            choose = 0;
                        }

                    }


            }


    }
}
