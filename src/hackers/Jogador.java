package src.hackers;

import java.util.Scanner;

public class Jogador{
    private String nome;
    private String matricula;
    private int pontosDeVida = 100;
    private int pontosDeEnergia = 10;

    public Jogador(){}

    public Jogador(String nome, String matricula){
        this.nome = nome;
        this.matricula = matricula;
    }

    // pega as infos do usuário
    public void coletarInfo(Scanner entrada){ 
        System.out.print("Digite seu nome: ");
        this.nome = entrada.nextLine();
        System.out.print("Digite seu identificador (número de matrícula): ");
        this.matricula = entrada.nextLine();
    }

}