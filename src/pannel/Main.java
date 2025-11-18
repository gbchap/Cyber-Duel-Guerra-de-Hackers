package src.pannel;

import java.util.ArrayList;
import java.util.Scanner;

import src.data.CartaP;
import src.data.CartaSup;
import src.hackers.Jogador;

public class Main {
    public static void main(String[] args){

       
        //CARREGAR CARTAS DO CSV
        System.out.println("Carregando banco de cartas...");

        ArrayList<CartaP> ataques =
            LeitorCSV.lerAtaque("src/data/ataque.csv");

        ArrayList<CartaP> defesas =
            LeitorCSV.lerDefesa("src/data/defesa.csv");

        ArrayList<CartaSup> suportes =
            LeitorCSV.lerSuporte("src/data/suporte.csv");

        System.out.println("Cartas carregadas:");
        System.out.println("Ataques: " + ataques.size());
        System.out.println("Defesas: " + defesas.size());
        System.out.println("Suportes: " + suportes.size());
        // TESTE impressão da leitura
                System.out.println("\nPrimeiro ataque:");
                ataques.get(0).get();

                System.out.println("\nPrimeira defesa:");
                defesas.get(0).get();

                System.out.println("\nPrimeiro suporte:");
                suportes.get(0).get();

        System.out.println("\nBem-vindo, jogador. Digite suas informa��es.");

        Scanner entrada = new Scanner(System.in);

        Jogador hacker1 = new Jogador();
        Jogador hacker2;
        hacker1.coletarInfo(entrada);

        System.out.print("Seu competidor será um robô (0) ou um humano? (1): ");

        String robo_humano = entrada.nextLine();
        while (!robo_humano.equals("0") && !robo_humano.equals("1")){
            System.out.print("Opção Inválida! Digite Opção Válida: ");
            robo_humano = entrada.nextLine();
        }

        if (robo_humano.equals("0")){
            hacker2 = new Jogador("BOT", "202565001");
        }
        else{
            hacker2 = new Jogador();
            hacker2.coletarInfo(entrada);
        }

        entrada.close(); 
    }
}