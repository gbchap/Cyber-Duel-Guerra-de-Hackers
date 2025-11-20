package hackers;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import data.CartaP;

public class Jogador{
    private String nome;
    private String matricula;
    private double pontosDeVida = 100;
    private int pontosDeEnergia = 10;
    private double qtdAtaque = 0;
    private double qtdDefesa = 0;
    private ArrayList<CartaP> deck = new ArrayList<>();
    private ArrayList<CartaP> deckManipulavel = new ArrayList<>();

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

    public int getEnergia(){
        return pontosDeEnergia;
    }

    public void adicionaNoDeck(ArrayList<CartaP> conjunto, int index){ //adicionar carta no deck
        deck.add(conjunto.get(index - 1));
    }

    public void imprimirCartasDeck(){ // imprime as cartas armazenadas no deck
        System.out.println("\nCartas no Deck: ");
        for (int i = 0; i < deckManipulavel.size(); i++){
            System.out.println("\nCarta " + (i+1) + ": ");
            deckManipulavel.get(i).imprime();
        }
    }

    public void aumentaEnergia(){ // energia ganha ao fim dos turnos
        if (pontosDeEnergia < 10){
            pontosDeEnergia++;
        }
    }

    public void diminuiEnergia(int qtd){
        pontosDeEnergia -= qtd;
    }

    public void ajustaVida(){ // verifica ao final do turno se a vida, que passou dos 100 pontos, ainda continua acima
        if (pontosDeVida > 100){
            pontosDeVida = 100;
        }
    }

    public double getVida(){ // retorna a vida
        return pontosDeEnergia;
    }

    public void preencherDeckManipulavel(){
        deckManipulavel = deck;
    }

    public int deckManipulavelsize(){
        return deckManipulavel.size();
    }

    public String acessaNomeCartasDeckManipulavel(int i){
        return deckManipulavel.get(i).getNome();
    }

    public void imprimeCartaDeckManipulavel(int i){
        System.out.println("\n" + deckManipulavel.get(i).imprime());
    }

    public int custoCartaDeckManipulavel(int i){
        return deckManipulavel.get(i).getCusto();
    }

    public void diminuiVida(int pontosDano){
        if (pontosDano <= pontosDeVida){
            pontosDeVida -= pontosDano;
        }
        else{
            pontosDeVida = 0;
        }
    }

    public void deletaCartasDeckManipulavel(int[] indices){
        //os indices mudam, então pensar numa funcao para isso
    }
}