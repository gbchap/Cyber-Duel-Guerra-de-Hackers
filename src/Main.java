import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // carregando as cartas:

        //GerenciadorCartas gerenciador = new GerenciadorCartas();
        //gerenciador.carregarCartas();
        
        Scanner entrada1 = new Scanner(System.in);

        System.out.println("Bem-vindo, jogador. Digite suas informações.");
        System.out.print("Digite seu nome: ");
        String nome1 = entrada1.nextLine(); 
        System.out.print("Digite seu identificador (número de matrícula): ");
        String id1 = entrada1.nextLine();

        entrada1.close();
    }
}
