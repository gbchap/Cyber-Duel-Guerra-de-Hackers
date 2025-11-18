package src.pannel;
import src.data.CartaP;
import src.pannel.GerenciadorCartas;
import hackers.Jogador;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        // carregando as cartas:


        GerenciadorCartas gc = new GerenciadorCartas();

        public CartaP getCarta(String nome){
            
            return cartas.get(nome);
        }

        System.out.println("Bem-vindo, jogador. Digite suas informações.");
        
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
