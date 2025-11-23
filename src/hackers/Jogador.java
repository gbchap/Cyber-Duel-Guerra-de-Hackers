package hackers;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import data.CartaP;
import pannel.Espera;

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
        System.out.print("\nDigite seu identificador (número de matrícula): ");
        this.matricula = entrada.nextLine();
    }

    public String getNome(){ //retorna nome
        return nome;
    }

    public String getMatricula(){
        return matricula;
    }

    public int getEnergia(){
        return pontosDeEnergia;
    }

    public void adicionaNoDeck(ArrayList<CartaP> conjunto, int index){ //adicionar carta no deck
        deck.add(conjunto.get(index - 1));
    }

    public void imprimirCartasDeck() { 
        System.out.println("\n\u001B[3;4mCARTAS NO DECK:\u001B[0m");
        
        for (int i = 0; i < deckManipulavel.size(); i++){
            System.out.println("\nCarta " + (i+1) + ": ");
            deckManipulavel.get(i).imprime();
            Espera.esperar(0.05);
        }
    }


    public ArrayList<CartaP> getDeck() {
        return deck;
    }

    public ArrayList<CartaP> getDeckManipulavel() {
        return deckManipulavel;
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
        return pontosDeVida;
    }

    public void preencherDeckManipulavel(){ // preenche o deck 
        deckManipulavel.clear();
        deckManipulavel.addAll(deck);
    }

    public int deckManipulavelsize(){
        return deckManipulavel.size();
    }

    public String acessaNomeCartasDeckManipulavel(int i){
        return deckManipulavel.get(i).getNome();
    }

    public void imprimeCartaDeckManipulavel(int i){
        deckManipulavel.get(i).imprime();
        System.out.print("\n");
    }

    public int custoCartaDeckManipulavel(int i){
        return deckManipulavel.get(i).getCusto();
    }

    public String tipoCartaDeckManipulavel(int i){
        return deckManipulavel.get(i).getTipo();
    }

    public String efeitoCartaDeckManipulavel(int i){
        return deckManipulavel.get(i).getEfeito();
    }

    public double poderCartaDeckManipulavel(int i){
        return deckManipulavel.get(i).getPoder();
    }

    public void zeraVida(){
        pontosDeVida = 0;
    }

    public void aumentaVida(double maisVida){
        pontosDeVida += maisVida;
    }

    public void aumentaAtaque(double maisAtaque){
        qtdAtaque += maisAtaque;
    }

    public void aumentaDefesa(double maisDefesa){
        qtdDefesa += maisDefesa;
    }

    public void reinicioTurnoPontosAtqDef(){
        qtdAtaque = 0;
        qtdDefesa = 0;
    }

    public void diminuiAtaque(double porcentagem){
        qtdAtaque = qtdAtaque * porcentagem;
    }

    public void atualizaVida(double perdaVida){
        if (perdaVida >= 0){
            if (pontosDeVida - perdaVida < 0){
                pontosDeVida = 0;
            }
            else{
                pontosDeVida -= perdaVida;
            }
        }
    }

    public void arredondaVida(){
        pontosDeVida = (Math.round(pontosDeVida / 10.0) * 10.0);
    }

    public double getAtaque(){
        return qtdAtaque;
    }

    public double getDefesa(){
        return qtdDefesa;
    }


    public void deletaCartasDeckManipulavel(ArrayList<Integer> armazena){
        // ordena o vetor armazena em ordem decrescente
        for (int i = 0; i < armazena.size() - 1; i++) {
            for (int j = i + 1; j < armazena.size(); j++) {
                if (armazena.get(i) < armazena.get(j)) {
                    // troca
                    int temp = armazena.get(i);
                    armazena.set(i, armazena.get(j));
                    armazena.set(j, temp);
                }
            }
        }

        // remove os indices sem causar problema de mudanca nos indices
        for (int i = 0; i < armazena.size(); i++) {
            int indice = armazena.get(i);
            deckManipulavel.remove(indice);
        }
    }



    public boolean deckManipulavelEhVazio(){
        if (deckManipulavel.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
}