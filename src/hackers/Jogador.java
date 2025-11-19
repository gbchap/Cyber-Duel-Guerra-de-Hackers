package hackers;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import data.CartaP;

public class Jogador{
    private String nome;
    private String matricula;
    private double pontosDeVida = 100;
    private double pontosDeEnergia = 10;
    private double qtdAtaque = 0;
    private double qtdDefesa = 0;
    private ArrayList<CartaP> deck = new ArrayList<>();

    public Jogador(){}

    public Jogador(String nome, String matricula){
        this.nome = nome;
        this.matricula = matricula;
    }

    // pega as infos do usuário
    public void coletarInfo(Scanner entrada){ 
        System.out.print("\nDigite seu nome: ");
        this.nome = entrada.nextLine();
        System.out.print("Digite seu identificador (número de matrícula): ");
        this.matricula = entrada.nextLine();
    }

    public String getNome(){ //retorna nome
        return nome;
    }

    public void adicionaNoDeck(ArrayList<CartaP> conjunto, int index){ //adicionar carta no deck
        deck.add(conjunto.get(index - 1));
    }

    public void imprimirCartasDeck(){ // imprime as cartas armazenadas no deck
        System.out.println("\nCartas no Deck: ");
        for (int i = 0; i < deck.size(); i++){
            System.out.println("\nCarta " + (i+1) + ": " + deck.get(i).getNome() + " " + deck.get(i).getTipo());
        }
    }
}