package pannel;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import data.CartaP;
import hackers.Jogador;
import java.util.Scanner;

public class GerenciadorJogo {
    Jogador hacker1;
    Jogador hacker2;

    GerenciadorJogo(Jogador hacker1, Jogador hacker2){
        this.hacker1 = hacker1;
        this.hacker1 = hacker2;
    }

    // Método Selecionar Cartas
    public void selecionarCartas(Jogador hacker, ArrayList<CartaP> conjunto, int qtdCartas, Scanner entrada){
        System.out.println("\n" + "Escolha " + qtdCartas + " cartas de " + conjunto.get(0).getTipo() + " da lista!" + "\n");
            
        for (int i = 0; i < conjunto.size(); i++){
            System.out.print(i+1 + " ");
            conjunto.get(i).imprime();
            System.out.print("\n");
        }
        System.out.println("\n" + "Digite o número correspondente das cartas e confirme a seleção: ");

        int[] verificarNumCartasRept = new int[4]; // vetor para verificar possiveis repeticoes 
                
        for (int i = 0; i < qtdCartas; i++){  // Loop para selecionar as cartas
            String escolhaDeCartas;
            int numeroDaCarta;   

            while (true){ // verificacao de escolha apropriada + verficacao se o numero foi correto
                escolhaDeCartas = entrada.nextLine();
                        
                try {
                    numeroDaCarta = Integer.parseInt(escolhaDeCartas);
                } catch (NumberFormatException e) {
                    System.out.println("Digite um número válido.");
                    continue; 
                }

                if (numeroDaCarta >= 1 && numeroDaCarta <= conjunto.size()){
                    int contador = 0; //para facilitar a verificacao
                    for (int j = 0 ; j < i; j++){
                        if (numeroDaCarta == verificarNumCartasRept[j]){
                            contador++;
                        }
                    }
                    if (contador == 0){
                        verificarNumCartasRept[i] = numeroDaCarta;
                        break;
                    }
                    else{
                        System.out.print("Carta Inválida! Escolha uma carta válida: ");
                    }
                }
                else{
                    System.out.print("Carta Inválida! Escolha uma carta válida: ");
                }
            }
                    
            System.out.print("Confirma carta " + conjunto.get(numeroDaCarta - 1).getNome() + "? (Y/N)"); // confirmacao da escolha da carta
            String confirma = entrada.nextLine();
            
            while(!confirma.toLowerCase().equals("n") && !confirma.toLowerCase().equals("y")){ //verificacao de escolha de opcao CONFIRMA correta
                System.out.print("Opção Inválida! Escolha uma opção válida: ");
                confirma = entrada.nextLine();
            }
            
            if (confirma.toLowerCase().equals("n")){
                System.out.println("Escolha novamente a sua carta");
                i--;
            }
            else{
                System.out.println("Carta Escolhida!");
                hacker.adicionaNoDeck(conjunto, numeroDaCarta); 
            }
        }
    }

    //Método geral Selecionador
    public void selecionarCartas(Jogador hacker, ArrayList<CartaP> conjunto1, ArrayList<CartaP> conjunto2, 
    ArrayList<CartaP> conjunto3, int qtdAtqDef, int qtdSup, Scanner entrada){
        selecionarCartas(hacker, conjunto1, qtdAtqDef, entrada); // ataque
        selecionarCartas(hacker, conjunto2, qtdAtqDef, entrada); // defesa
        selecionarCartas(hacker, conjunto3, qtdSup, entrada); // suporte
        
    }

    // Método Selecao Aleatoria
    public void selecaoAleatoria(Jogador hacker, ArrayList<CartaP> conjunto, int qtdCartas){
        int numeroCarta;
        
        //escolher número aleatório entre 1 e conjunto.size()+1
        Random gerador = new Random();
        for (int i = 0; i < qtdCartas; i++){
            numeroCarta = gerador.nextInt(conjunto.size()) + 1;
            hacker.adicionaNoDeck(conjunto, numeroCarta);
        }
    }

    // Método Geral Selecao Aleatoria
    public void selecaoAleatoria(Jogador hacker, ArrayList<CartaP> conjunto1, ArrayList<CartaP> conjunto2, 
    ArrayList<CartaP> conjunto3, int qtdAtqDef, int qtdSup){
        selecaoAleatoria(hacker, conjunto1, qtdAtqDef); // ataque
        selecaoAleatoria(hacker, conjunto2, qtdAtqDef); // defesa
        selecaoAleatoria(hacker, conjunto3, qtdSup); // suporte
    }

}
