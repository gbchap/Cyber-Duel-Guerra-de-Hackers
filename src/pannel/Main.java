package pannel;

import data.CartaP;
import pannel.GerenciadorJogo;
import hackers.Jogador;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        //CARREGAR CARTAS DO CSV
        System.out.println("Carregando banco de cartas...");

        ArrayList<CartaP> ataques =
            LeitorCSV.lerAtaqueEdefesa("src/data/ataque.csv");

        ArrayList<CartaP> defesas =
            LeitorCSV.lerAtaqueEdefesa("src/data/defesa.csv");

        ArrayList<CartaP> suportes =
            LeitorCSV.lerSuporte("src/data/suporte.csv");

        System.out.println("\nCartas carregadas:");
        System.out.println("Ataques: " + ataques.size());
        System.out.println("Defesas: " + defesas.size());
        System.out.println("Suportes: " + suportes.size());


        System.out.println("\nBem-vindo, jogador. Digite suas informações."); // Interacao inicial com o usuário
        
        Scanner entrada = new Scanner(System.in); //Scanner para interagir com o usuário

        Jogador hacker1 = new Jogador();
        Jogador hacker2;
        hacker1.coletarInfo(entrada);
        
        System.out.print("\nSeu competidor será um robô (0) ou um humano? (1): "); // Pergunta sobre como vai ser o jogo: contra bot ou outro jogador
        
        String robo_humano = entrada.nextLine();
        while (!robo_humano.equals("0") && !robo_humano.equals("1")){ //Verificacao de opcao válida!
            System.out.print("Opção Inválida! Digite Opção Válida: ");
            robo_humano = entrada.nextLine();
        }

        if (robo_humano.equals("0")){ //Caminho se a opcao bot for escolhida
            hacker2 = new Jogador("BOT", "202565001");
            GerenciadorJogo gerenciador = new GerenciadorJogo(hacker1, hacker2);
            
            System.out.println("\n" + hacker1.getNome() + ", selecione suas cartas!");
            System.out.print("Selecionar cartas (1)  Seleção Aleatória (2): ");  // Perguntando sobre escolha de selecao de cartas
            String selecao = entrada.nextLine();
            
            while (!selecao.equals("1") && !selecao.equals("2")){     // Verificacao de escolha válida pelo usuário
                System.out.print("Opção Inválida! Digite Opção Válida: ");
                selecao = entrada.nextLine();
            }

            if (selecao.equals("1")){ //selecionar na mao
                gerenciador.selecionarCartas(hacker1, ataques, defesas, suportes, 4, 2, entrada); // Jogador 1
                hacker1.imprimirCartasDeck(); // mostrar cartas escolhidas (teste)
            }
            else{   // Se a opcao for aleatorio
                System.out.println("\nSelecionando cartas Jogador " + hacker1.getNome() + " aleatoriamente...");
                gerenciador.selecaoAleatoria(hacker1, ataques, defesas, suportes, 4, 2);   // escolha aleatoria jogador 1
                hacker1.imprimirCartasDeck(); // mostrar cartas escolhidas (teste)
            }
            System.out.println("\nSelecionando cartas Jogador " + hacker2.getNome() + " aleatoriamente...");
            gerenciador.selecaoAleatoria(hacker2, ataques, defesas, suportes, 4, 2);     // Selecao aleatoria para o bot
            hacker2.imprimirCartasDeck();
        }
        else{ // Caminho se a opcao jogador for escolhida
            hacker2 = new Jogador();
            hacker2.coletarInfo(entrada);
        
            System.out.print("Selecionar cartas (1)  Seleção Aleatória (2): ");  // Perguntando sobre escolha de cartas
            String selecao = entrada.nextLine();
            while (!selecao.equals("1") && !selecao.equals("2")){     // Verificacao de escolha válida pelo usuário
                System.out.print("Opção Inválida! Digite Opção Válida: ");
                selecao = entrada.nextLine();
            }

            if (selecao.equals("1")){
                
                // Chamar para ATAQUE, DEFESA e chamar para SUPORTE
            }
            else{                                              // Se a opcao for aleatorio
                //Selecao aleatoria
            }
        }


        entrada.close(); 
    }
}
