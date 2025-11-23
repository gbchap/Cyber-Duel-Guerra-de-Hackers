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

        System.out.println("*----------------------------------------------------");
        System.out.println("\nBEM-VINDO AO JOGO!!! Jogador, digite suas informações!"); // Interacao inicial com o usuário
        
        Scanner entrada = new Scanner(System.in); //Scanner para interagir com o usuário

        Jogador hacker1 = new Jogador();
        Jogador hacker2;
        hacker1.coletarInfo(entrada);
        
        System.out.print("\n\nSeu competidor será um robô (0) ou um humano? (1): "); // Pergunta sobre como vai ser o jogo: contra bot ou outro jogador
        
        String robo_humano = entrada.nextLine();
        while (!robo_humano.equals("0") && !robo_humano.equals("1")){ //Verificacao de opcao válida!
            System.out.print("Opção Inválida! Digite Opção Válida: ");
            robo_humano = entrada.nextLine();
        }

        if (robo_humano.equals("0")){ //Caminho se a opcao BOT for escolhida -> TRANSFORMAR EM MÉTODO GERENCIADORJOGO
            hacker2 = new Jogador("BOT", "202565001");
            GerenciadorJogo gerenciador = new GerenciadorJogo();

            gerenciador.turnoBOT(hacker1, hacker2, ataques, defesas, suportes, 4, 2, entrada); // selecionar as cartas dos jogadores
        }
        else{ // Caminho se a opcao jogador for escolhida -> TRANSFORMAR EM MÉTODO GERENCIADORJOGO
            hacker2 = new Jogador();
            System.out.println("\n\nBEM-VINDO, JOGADOR 2. Digite suas informações!");
            hacker2.coletarInfo(entrada);
            GerenciadorJogo gerenciador = new GerenciadorJogo();

            gerenciador.turnosPVP(hacker1, hacker2, ataques, defesas, suportes, 4, 2, entrada); // selecionar as cartas dos jogadores
        }


        entrada.close(); 
    }
}
