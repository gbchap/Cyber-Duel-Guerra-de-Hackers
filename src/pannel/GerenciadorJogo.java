package pannel;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import data.CartaP;
import hackers.Jogador;
import java.util.Scanner;

public class GerenciadorJogo {
    Jogador j1;
    Jogador j2;

    GerenciadorJogo(Jogador j1, Jogador j2){
        this.j1 = j1;
        this.j2 = j2;
    }

    // Verificador de Desistencia
    public int verificaDesistencia(Jogador hacker, Scanner entrada){
        System.out.println("\nSua vez, " + hacker.getNome() + "(" + hacker.getMatricula() + ")!");
        System.out.println("\nVida: " + hacker.getVida() + " Energia: " + hacker.getEnergia());
        String selecaoOpcao;

        while(true){
            System.out.print("\nJogar(0) Passar a vez(1) Desistir(2): ");
            selecaoOpcao = entrada.nextLine();

            while (!selecaoOpcao.equals("0") && !selecaoOpcao.equals("1") && !selecaoOpcao.equals("2")){     // Verificacao de escolha válida pelo usuário
                System.out.print("Opção Inválida! Digite Opção Válida: ");
                selecaoOpcao = entrada.nextLine();
            }

            //Confirmacao da desistencia
            System.out.print("\nConfirma opção? (Y/N) "); 
            String confirmaOpcao = entrada.nextLine();
            
            while(!confirmaOpcao.toLowerCase().equals("n") && !confirmaOpcao.toLowerCase().equals("y")){ //verificacao de escolha de opcao CONFIRMA correta
                System.out.print("Opção Inválida! Escolha uma opção válida: ");
                confirmaOpcao = entrada.nextLine();
            }
            
            if (confirmaOpcao.toLowerCase().equals("n")){
                System.out.println("Escolha sua opção novamente");
            }
            else{
                break; 
            }

        }


        if (selecaoOpcao.equals("2")){
            return 2;
        }
        else if (selecaoOpcao.equals("1")){
            return 1;
        }
        else{
            return 0;
        }
    }

    // Método de Turnos 
    public void turnosPVP(Jogador hacker1, Jogador hacker2, ArrayList<CartaP> conjunto1, ArrayList<CartaP> conjunto2, 
        ArrayList<CartaP> conjunto3, int qtdAtqDef, int qtdSup, Scanner entrada){ // implementar parametros

        // selecionar cartas ambos os jogadores
        selecionar(hacker1, hacker2, conjunto1, conjunto2, conjunto3, qtdAtqDef, qtdSup, entrada);


        System.out.println("\nComeçando o jogo...");
        int contadorTurnos = 1; // contar os turnos
        // Mostrando as regras
        System.out.println("\nInformações: "); 
        System.out.println("\n1: Você tem 100 pontos de vida e 10 pontos de energia no início do jogo! Se sua vida zerar, você perde!");
        System.out.println("\n2: A cada turno, escolha quais cartas do seu deck você vai jogar, uma de cada vez! Lembre-se que só será permitido a combinação de cartas se não ultrapassar seus pontos de energia!");
        System.out.println("\n3: A cada novo turno, você ganha +1 de energia, mas nunca ulrapassa o limite de 10"); 
        System.out.println("\n4: Se seu deck esvaziar completamente, você receberá seu deck inicial de novo!");
        System.out.println("\n5: Você pode escolher jogar, passar a vez ou desistir a cada turno!");
        System.out.println("\nBom jogo!");

        // while (verificar vida)
        while (hacker1.getVida() != 0 && hacker2.getVida() != 0){
            ArrayList<Integer> armazenaJogador1 = new ArrayList<>(); // vetor que armazena selecao das cartas da mao jogada JOGADOR 1
            ArrayList<Integer> armazenaJogador2 = new ArrayList<>(); // vetor que armazena selecao das cartas da mao jogada JOGADOR 2
            
            // Mensagem Turno 
            System.out.println("\nTurno " + contadorTurnos);

            // // verificador de desistencia jogador 1 - inicio turno  TRANSFORMAR EM MÉTODO
            // int opcaoJogarPassarDesistir1 = verificaDesistencia(hacker1, entrada);
            // if (opcaoJogarPassarDesistir1 == 2){
            //     // se desistir, coloca a vida em zero e break
            //     hacker1.diminuiVida(100);
            //     break;
            // }
            // else if (opcaoJogarPassarDesistir == 1){
            //     // se passar, imprime mensagem
            //     System.out.println("\n" + hacker1.getNome() + " passou a vez!");

            // }
            // else{
            //     // se jogar, chama o turno
            //     selecaoMaoJogador(hacker1, armazenaJogador1, entrada);
            //     // turno jogador 1   
            // }

            if (contadorTurnos % 2 == 0){
                // verifica desistencia jogador 2
                int opcaoJogarPassarDesistir2 = verificaDesistencia(hacker2, entrada);

                turnoJogador(hacker2, armazenaJogador2, opcaoJogarPassarDesistir2, entrada);
                if (opcaoJogarPassarDesistir2 == 2){
                    break;
                }

                // verifica desistencia jogador 1
                int opcaoJogarPassarDesistir1 = verificaDesistencia(hacker1, entrada);
                turnoJogador(hacker1, armazenaJogador1, opcaoJogarPassarDesistir1, entrada);
                if (opcaoJogarPassarDesistir1 == 2){
                    break;
                }
            }
            else{
                // verifica desistencia jogador 1
                int opcaoJogarPassarDesistir1 = verificaDesistencia(hacker1, entrada);
                turnoJogador(hacker1, armazenaJogador1, opcaoJogarPassarDesistir1, entrada);
                if (opcaoJogarPassarDesistir1 == 2){
                    break;
                }

                // verifica desistencia jogador 2
                int opcaoJogarPassarDesistir2 = verificaDesistencia(hacker2, entrada);
                turnoJogador(hacker2, armazenaJogador2, opcaoJogarPassarDesistir2, entrada);
                if (opcaoJogarPassarDesistir2 == 2){
                    break;
                }
            }

            // // verificador de desistencia jogador 2 -  TRANSFORMAR EM MÉTODO
            // int opcaoJogarPassarDesistir2 = verificaDesistencia(hacker2, entrada);
            // if (opcaoJogarPassarDesistir2 == 2){
            //     // se desistir, coloca a vida em zero e break
            //     hacker2.diminuiVida(100);
            //     break;
            // }
            // else if (opcaoJogarPassarDesistir2 == 1){
            //     // se passar, imprime mensagem
            //     System.out.println("\n" + hacker2.getNome() + " passou a vez!");

            // }
            // else{
            //     // se jogar, chama o turno
            //     selecaoMaoJogador(hacker2, armazenaJogador2, entrada);

            //     // turno jogador 2   
            // }

            // display cartas "Painel Turno ... " ================ (colocar em cima e em baixo)
            System.out.println("\nPainel do Turno:" + "\n===========================================");
            System.out.println("\n" + hacker1.getNome() + "(" + hacker1.getMatricula() + ") jogou:");
            // imprimir mao jogada; se o jogador tiver passado a vez verificar tam do vetor == 0, e imprime "passou a vez"

            System.out.println("\n" + hacker2.getNome() + "(" + hacker2.getMatricula() + ") jogou:");
            // imprimir mao jogada; se o jogador tiver passado a vez verificar tam do vetor == 0, e imprime "passou a vez"

            System.out.println("\n===========================================");

            // IMPLEMENTAR PONTOS DE ATAQUE, DEFESA E SUPORTE

            // mostrar pontos de vida e energia 
            System.out.println("\nDados " + hacker1.getNome() + "(" + hacker1.getMatricula() + "):\n" + "Vida: " + hacker1.getVida() + " Energia: " + hacker1.getEnergia());
            System.out.println("\nDados " + hacker2.getNome() + "(" + hacker2.getMatricula() + "):\n" + "Vida: " + hacker2.getVida() + " Energia: " + hacker2.getEnergia());
            
            // deletar as cartas correspondentes ao vetor
            hacker1.deletaCartasDeckManipulavel(armazenaJogador1);
            hacker2.deletaCartasDeckManipulavel(armazenaJogador2);
            
            // verificar se o deck esta vazio, se estiver, preenche-lo com o deck salvo no jogador
            if (hacker1.deckManipulavelEhVazio()){
                hacker1.preencherDeckManipulavel();
            }

            if (hacker2.deckManipulavelEhVazio()){
                hacker2.preencherDeckManipulavel();
            }
            
            // adicionar 1 de energia
            hacker1.aumentaEnergia();
            hacker2.aumentaEnergia();

            // muda o turno
            contadorTurnos++;
        }
        // imprime vencedor com base em quem ficou com zero 
        if (hacker1.getVida() == 0 && hacker2.getVida() != 0){
            // imprimir hacker2 ganhou
            System.out.println("\n" + hacker2.getNome() + "(" + hacker2.getMatricula() + ") VENCEU!");
        }
        else if (hacker1.getVida() != 0 && hacker2.getVida() == 0){
            // imprimir hacker1 ganhou
            System.out.println("\n" + hacker1.getNome() + "(" + hacker1.getMatricula() + ") VENCEU!");
        }
        else{
            // imprimir empate
            System.out.println("\nOs jogadores EMPATARAM!");
        }

        // OPCAO REPLAY ENTRA AQUI!!!!!
    }


    // Método Turno Jogador 
    public void turnoJogador(Jogador hacker, ArrayList<Integer> armazena, int opcaoJogarPassarDesistir, Scanner entrada){
            if (opcaoJogarPassarDesistir == 2){
                // se desistir, coloca a vida em zero
                System.out.println("\n" + hacker.getNome() + "(" + hacker.getMatricula() + ") desistiu!");
                hacker.zeraVida();
            }
            else if (opcaoJogarPassarDesistir == 1){
                // se passar, imprime mensagem
                System.out.println("\n" + hacker.getNome() + "(" + hacker.getMatricula() + ") passou a vez!");
            }
            else{
                // se jogar, chama a selecao da mao
                selecaoMaoJogador(hacker, armazena, entrada);  
            }
    }


    // Método selecao da mao jogada de um jogador
    public void selecaoMaoJogador(Jogador hacker, ArrayList<Integer> armazena, Scanner entrada){
        System.out.println("\nJogue suas cartas, " + hacker.getNome() + "(" + hacker.getMatricula() + ")!");
        System.out.println("\nSeu deck: ");
        hacker.imprimirCartasDeck();
        System.out.println("\nEscolha as cartas pelo índice: ");

        int qtdCartasDeck = hacker.deckManipulavelsize();
        //int[] armazena = new int[qtdCartasDeck]; //vetor para armazenar as cartas escolhidas 
        // int tamArmazena; // para verificar o tamanho do armazena depois
        
        // fazer um while até ser escolhido uma mão válida
        while(true){
            // tamArmazena = 0;
            selecionarCartasManipulavel(hacker, armazena, qtdCartasDeck, entrada);
            // confirmar a mao jogada, mostrando as cartas selecionadas juntas
            System.out.println("\nSua mão escolhida: ");
            hacker.imprimirCartasDeck();
            // for (int i = 0; i < armazena.size(); i++){
            //     hacker.imprimeCartaDeckManipulavel(armazena.get(i));
            // }
            System.out.print("\nConfirma Seleção? (Y/N) ");

            String confirmaMao = entrada.nextLine();
            
            while(!confirmaMao.toLowerCase().equals("n") && !confirmaMao.toLowerCase().equals("y")){ //verificacao de escolha de opcao CONFIRMA correta
                System.out.print("Opção Inválida! Escolha uma opção válida: ");
                confirmaMao = entrada.nextLine();
            }

            if (confirmaMao.toLowerCase().equals("y")){
                // caso sim, verifica a quantidade de energia é válida
                int custoTotal = 0;
                for (int i = 0; i < armazena.size(); i++){
                    custoTotal += hacker.custoCartaDeckManipulavel(armazena.get(i));
                }
                
                if (custoTotal <= hacker.getEnergia()){
                    //caso sim, quebra o while para ir para o jogador 2
                    hacker.diminuiEnergia(custoTotal);
                    System.out.println("\nMão jogada!");
                    break;
                }
                else{
                    //caso nao, imprime mensagem e da continue para refazer o processo
                    System.out.println("\nMão selecionada ultrapassa seus pontos de energia! Selecione outra mão!");
                }   

            }
            else{
                //caso nao, imprime mensagem para selecionar de novo e da continue para refazer processo
                System.out.println("\nEntão selecione sua mão novamente!");

            }
        }

    }


    // Método GERAL SELECIONAR -> usando sobrecarga tb
    public void selecionar(Jogador hacker1, Jogador hacker2, ArrayList<CartaP> conjunto1, ArrayList<CartaP> conjunto2, 
    ArrayList<CartaP> conjunto3, int qtdAtqDef, int qtdSup, Scanner entrada){
        selecionar(hacker1, conjunto1, conjunto2, conjunto3, qtdAtqDef, qtdSup, entrada);
        selecionar(hacker2, conjunto1, conjunto2, conjunto3, qtdAtqDef, qtdSup, entrada);
    }

    // Método SELECIONAR 
    public void selecionar(Jogador hacker, ArrayList<CartaP> conjunto1, ArrayList<CartaP> conjunto2, 
    ArrayList<CartaP> conjunto3, int qtdAtqDef, int qtdSup, Scanner entrada){
        if (hacker.getNome().equals("BOT")){
            selecaoAleatoria(hacker, conjunto1, conjunto2, conjunto3, qtdAtqDef, qtdSup); // escolha aleatória BOT
        }
        else{
            System.out.println("\n" + hacker.getNome() + "(" + hacker.getMatricula() + "), selecione suas cartas!");
            System.out.print("Selecionar cartas (1)  Seleção Aleatória (2): ");  // Perguntando sobre escolha de selecao de cartas
            String selecao = entrada.nextLine();

            while (!selecao.equals("1") && !selecao.equals("2")){     // Verificacao de escolha válida pelo usuário
                System.out.print("Opção Inválida! Digite Opção Válida: ");
                selecao = entrada.nextLine();
            }
            if (selecao.equals("1")){ //selecionar na mao
                selecionarCartas(hacker, conjunto1, conjunto2, conjunto3, qtdAtqDef, qtdSup, entrada); // Jogador 1
            }
            else{   // Se a opcao for aleatorio
                selecaoAleatoria(hacker, conjunto1, conjunto2, conjunto3, qtdAtqDef, qtdSup);   // escolha aleatoria jogador 1
            }
        }

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
                        System.out.print("Carta já selecionada! Escolha uma carta válida: ");
                    }
                }
                else{
                    System.out.print("Carta Inválida! Escolha uma carta válida: ");
                }
            }
                    
            System.out.print("Confirma carta " + conjunto.get(numeroDaCarta - 1).getNome() + "? (Y/N) "); // confirmacao da escolha da carta
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

    //Método geral Selecionar Cartas
    public void selecionarCartas(Jogador hacker, ArrayList<CartaP> conjunto1, ArrayList<CartaP> conjunto2, 
    ArrayList<CartaP> conjunto3, int qtdAtqDef, int qtdSup, Scanner entrada){
        selecionarCartas(hacker, conjunto1, qtdAtqDef, entrada); // ataque
        selecionarCartas(hacker, conjunto2, qtdAtqDef, entrada); // defesa
        selecionarCartas(hacker, conjunto3, qtdSup, entrada); // suporte
        hacker.preencherDeckManipulavel();
        hacker.imprimirCartasDeck(); // mostrar cartas escolhidas
        
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
        System.out.println("\nSelecionando cartas Jogador " + hacker.getNome() + "(" + hacker.getMatricula() + ") aleatoriamente...");
        selecaoAleatoria(hacker, conjunto1, qtdAtqDef); // ataque
        selecaoAleatoria(hacker, conjunto2, qtdAtqDef); // defesa
        selecaoAleatoria(hacker, conjunto3, qtdSup); // suporte
        hacker.preencherDeckManipulavel();
        hacker.imprimirCartasDeck();

    }


    // Método selecionar cartas do deck manipulável
    public void selecionarCartasManipulavel(Jogador hacker, ArrayList<Integer> armazena, int qtdCartasDeck, Scanner entrada){
        int[] verifica = new int[qtdCartasDeck]; // vetor para verificar possiveis repeticoes 
                
        for (int i = 0; i < qtdCartasDeck; i++){  // Loop para selecionar as cartas com o limite de ser todas as cartas do deck
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

                if (numeroDaCarta >= 1 && numeroDaCarta <= qtdCartasDeck){
                    int contador = 0; //para facilitar a verificacao
                    for (int j = 0 ; j < i; j++){
                        if (numeroDaCarta == verifica[j]){
                            contador++;
                        }
                    }
                    if (contador == 0){
                        verifica[i] = numeroDaCarta;
                        break;
                    }
                    else{
                        System.out.print("Carta já selecionada! Escolha uma carta válida: ");
                    }
                }
                else{
                    System.out.print("Carta Inválida! Escolha uma carta válida: ");
                }
            }
                    
            System.out.print("Confirma carta " + hacker.acessaNomeCartasDeckManipulavel(numeroDaCarta -1) + "? (Y/N) "); // confirmacao da escolha da carta
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
                armazena.add(numeroDaCarta-1);
                if (armazena.size() == qtdCartasDeck){ // verifica se o vetor de cartas selecionadas está cheio
                    System.out.println("Todas as cartas do seu deck foram selecionadas!");
                }
                else{
                    System.out.print("\nDeseja escolher mais cartas? (Y/N) "); // caso o jogador escolha mais cartas
                    String confirmaMaisCartas = entrada.nextLine();
            
                    while(!confirmaMaisCartas.toLowerCase().equals("n") && !confirmaMaisCartas.toLowerCase().equals("y")){ //verificacao de escolha de opcao CONFIRMA correta
                        System.out.print("Opção Inválida! Escolha uma opção válida: ");
                        confirmaMaisCartas = entrada.nextLine();
                    }

                    if (confirmaMaisCartas.toLowerCase().equals("n")){
                        break;
                    }
                }
                
            }
        } 
    }
    
}
