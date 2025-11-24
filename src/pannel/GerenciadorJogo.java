package pannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import data.CartaP;
import hackers.Jogador;
import java.util.Scanner;

public class GerenciadorJogo {

    public GerenciadorJogo(){}

    // Método Turnos PLAYER x BOT; 
    public void turnoBOT(Jogador bot, ArrayList<Integer> armazenaJogador2, Replay replay, int contadorTurnos){ //bot será hacker2
        
        int energia = bot.getEnergia();
        ArrayList<Integer> escolhas = new ArrayList<>();

        boolean vidaBaixaRapido = bot.getVida() <= 30.0 && (contadorTurnos <= 2); //usar defesa e suporte DIMINUI_ATAQUE/AUMENTA_VIDA
        boolean vidaBaixa = bot.getVida() <= 40.0 && (contadorTurnos > 2);  // se 1, prioriza defesa; se 0, prioriza ataque e suporte AUMENTA_ATAQUE


        // Loop até gastar energia ou escolher 3 cartas
        while(energia > 0 && escolhas.size() < 3){
            Integer melhorIndice = null;
            CartaP melhorCarta = null;

            for(int i = 0; i < bot.deckManipulavelsize(); i++){
                if(escolhas.contains(i)) continue; // ignora cartas já escolhidas

                CartaP c = bot.getDeckManipulavel().get(i);
                int custo = c.getCusto();

                if(custo > energia) continue; // não pode gastar mais energia do que tem

                //Implementação de escolha de acordo com prioridade
                if(vidaBaixaRapido){
                    if((c.getTipo().equals("DEFESA") ||
                        (c.getTipo().equals("SUPORTE") && c.getEfeito().equals("DIMINUI_ATAQUE")) ||
                        (c.getTipo().equals("SUPORTE") && c.getEfeito().equals("AUMENTA_VIDA")))) {
                        if(melhorCarta == null || custo < melhorCarta.getCusto()){
                            melhorCarta = c;
                            melhorIndice = i;
                        }
                    }
                    
                }else if(vidaBaixa){
                    if(c.getTipo().equals("ATAQUE") || (c.getTipo().equals("SUPORTE") && c.getEfeito().equals("AUMENTA_ATAQUE")) ||
                    c.getTipo().equals("DEFESA")) {
                        if(melhorCarta == null || custo < melhorCarta.getCusto()){
                            melhorCarta = c;
                            melhorIndice = i;
                        }
                    }
                }else{ // situação normal
                    if(melhorCarta == null || custo < melhorCarta.getCusto()){
                        melhorCarta = c;
                        melhorIndice = i;
                    }
                }
            }

            if(melhorIndice == null) break; // nenhuma carta disponível
            escolhas.add(melhorIndice);
            energia -= bot.custoCartaDeckManipulavel(melhorIndice);

        }

        //se não escolheu nada/escolhas vazias por algum motivo
        if (escolhas.isEmpty()){
            System.out.println("BOT não conseguiu escolher cartas por falta de energia ou cartas válidas. Passa a vez.");
            replay.add("\n" + bot.getNome() + "(" + bot.getMatricula() + ") passou a vez.");
            return;
        }

        // registra no armazena (índices das cartas jogadas)
        armazenaJogador2.addAll(escolhas);

        replay.add("\n" + bot.getNome() + "(" + bot.getMatricula() + ") jogou:");
        System.out.println("\nBOT jogou:");
        for(int idx : escolhas){
            CartaP c = bot.getDeckManipulavel().get(idx);
            replay.add(" - " + c.getNome() + " (" + c.getTipo() + ")");
            System.out.println(" - " + c.getNome() + " (" + c.getTipo() + ")");
        }

        // Desconta energia
        int custoTotal = 0;
        for(int idx : escolhas) custoTotal += bot.custoCartaDeckManipulavel(idx);
        bot.diminuiEnergia(custoTotal);
    }

    // Método faz a atualizacao dos pontos de defesa e ataque do jogador, analisando o suporte
    public double atualizacaoPontos (Jogador hacker, ArrayList<Integer> armazena, double diminuicaoAtaque, double aumentoMaiorAtaque,
    double calculoIntermediarioAtaque, double maiorPoderAtaque){
        // verificar as cartas de ataque
        for (int i = 0; i < armazena.size(); i++){
            if (hacker.tipoCartaDeckManipulavel(armazena.get(i)).equals("ATAQUE")){
                if (hacker.poderCartaDeckManipulavel(armazena.get(i)) > maiorPoderAtaque){
                    maiorPoderAtaque = hacker.poderCartaDeckManipulavel(armazena.get(i));
                    calculoIntermediarioAtaque = maiorPoderAtaque;
                }
            }
        }

        // passar verificando os tipos de suporte
        for (int i = 0; i < armazena.size(); i++){

            if (hacker.tipoCartaDeckManipulavel(armazena.get(i)).equals("SUPORTE")){
                // se for aumenta ataque 
                if (hacker.efeitoCartaDeckManipulavel(armazena.get(i)).equals("AUMENTA_ATAQUE")){
                    // salvar o maior ataque, calculando o aumento
                    calculoIntermediarioAtaque *= (1.0 + hacker.poderCartaDeckManipulavel(armazena.get(i)));

                }
                else if (hacker.efeitoCartaDeckManipulavel(armazena.get(i)).equals("AUMENTA_VIDA")){ // se for aumenta vida
                    // aumenta a vida
                    hacker.aumentaVida(hacker.poderCartaDeckManipulavel(armazena.get(i)));
                }
                else {  // se for diminuir ataque 
                    diminuicaoAtaque *= (1.0 - hacker.poderCartaDeckManipulavel(armazena.get(i)));
                    // salva a info pra depois que consolidar todos os pontos do outro jogador
                }   
            }
        }
        // calculando o aumento do maior ataque
        aumentoMaiorAtaque = calculoIntermediarioAtaque - maiorPoderAtaque;
        
        // somar todas as cartas de ataque + dano extra calculado (inicializar com 0)
        for (int i = 0; i < armazena.size(); i++){
            if (hacker.tipoCartaDeckManipulavel(armazena.get(i)).equals("ATAQUE")){
                hacker.aumentaAtaque(hacker.poderCartaDeckManipulavel(armazena.get(i)));
            }
        }
        hacker.aumentaAtaque(aumentoMaiorAtaque);

        // somar as cartas de defesa
        for (int i = 0; i < armazena.size(); i++){
            if (hacker.tipoCartaDeckManipulavel(armazena.get(i)).equals("DEFESA")){
                hacker.aumentaDefesa(hacker.poderCartaDeckManipulavel(armazena.get(i))); 
            }
        }

        return diminuicaoAtaque; // retorna porque preciso usar depois e assim como em C++ o valor alterado dentro da funcao não vai pra fora
    }


    // Método que faz a consolidacao do turno
    public void consolidaTurno (Jogador hacker1, Jogador hacker2, ArrayList<Integer> armazenaJogador1, ArrayList<Integer> armazenaJogador2){
        double diminuicaoAtaque1 = 1.0; // usar decimal
        double diminuicaoAtaque2 = 1.0;
        double aumentoMaiorAtaque1 = 0;
        double aumentoMaiorAtaque2 = 0;
        double calculoIntermediarioAtaque1 = 0;
        double calculoIntermediarioAtaque2 = 0;
        double maiorPoderAtaque1 = 0;
        double maiorPoderAtaque2 = 0;

        diminuicaoAtaque1 = atualizacaoPontos(hacker1, armazenaJogador1, diminuicaoAtaque1, aumentoMaiorAtaque1, calculoIntermediarioAtaque1, maiorPoderAtaque1);
        diminuicaoAtaque2 = atualizacaoPontos(hacker2, armazenaJogador2, diminuicaoAtaque2, aumentoMaiorAtaque2, calculoIntermediarioAtaque2, maiorPoderAtaque2);

        // aplicar o diminuir ataque 
        hacker1.diminuiAtaque(diminuicaoAtaque2);
        hacker2.diminuiAtaque(diminuicaoAtaque1);

        // fazer o ataque menos defesa + verificar vida + arredondar vida
        hacker1.atualizaVida(hacker2.getAtaque() - hacker1.getDefesa());
        hacker1.ajustaVida();
        hacker1.arredondaVida();
        hacker2.atualizaVida(hacker1.getAtaque() - hacker2.getDefesa());
        hacker2.ajustaVida();
        hacker2.arredondaVida();

    }


    // Verificador de Desistencia/passar vez caso o jogador nao tenha energia
    public boolean verificaEnergiaParaJogo(Jogador hacker){
        boolean temEnergia = false;
        for (int i = 0; i < hacker.deckManipulavelsize(); i++){
            if (hacker.custoCartaDeckManipulavel(i) <= hacker.getEnergia()){
                temEnergia = true;
            }
        }

        return temEnergia;
    }

    // Verificador de Desistencia
    public int verificaDesistencia(Jogador hacker, Scanner entrada){
        System.out.println("\nVida: " + hacker.getVida() + " | Energia: " + hacker.getEnergia() + "\n");
        String selecaoOpcao;

        if (verificaEnergiaParaJogo(hacker) == false){
            while(true){
                System.out.print("\n=> Você não tem energia para jogar cartas! Passar a vez(1) Desistir(2): ");
                selecaoOpcao = entrada.nextLine();

                while (!selecaoOpcao.equals("1") && !selecaoOpcao.equals("2")){     // Verificacao de escolha válida pelo usuário
                    System.out.print("Opção Inválida! Digite Opção Válida: ");
                    selecaoOpcao = entrada.nextLine();
                }

                //Confirmacao da opcao
                System.out.print("Confirma opção? (Y/N) "); 
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
        }
        else{
            while(true){
                System.out.print("\n=> Jogar(0) Passar a vez(1) Desistir(2): ");
                selecaoOpcao = entrada.nextLine();

                while (!selecaoOpcao.equals("0") && !selecaoOpcao.equals("1") && !selecaoOpcao.equals("2")){     // Verificacao de escolha válida pelo usuário
                    System.out.print("Opção Inválida! Digite Opção Válida: ");
                    selecaoOpcao = entrada.nextLine();
                }

                //Confirmacao da opcao
                System.out.print("Confirma opção? (Y/N) "); 
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
        ArrayList<CartaP> conjunto3, int qtdAtqDef, int qtdSup, Scanner entrada){ 

        Replay replay = new Replay();
        // selecionar cartas ambos os jogadores
        selecionar(hacker1, hacker2, conjunto1, conjunto2, conjunto3, qtdAtqDef, qtdSup, entrada);

        replay.add("=== DECK INICIAL DO JOGADOR 1 ===");
        for (CartaP c : hacker1.getDeck()) {
            replay.add(c.getNome() + " | Tipo: " + c.getTipo() + " | Poder: " + c.getPoder() + " | Custo: " + c.getCusto());
        }

        replay.add("\n=== DECK INICIAL DO JOGADOR 2 ===");
        for (CartaP c : hacker2.getDeck()) {
            replay.add(c.getNome() + " | Tipo: " + c.getTipo() + " | Poder: " + c.getPoder() + " | Custo: " + c.getCusto());
        }

        Espera.esperar(1);
        System.out.println("\n\nComeçando o jogo...");
        int contadorTurnos = 1; // contar os turnos

        // Mostrando as regras
        System.out.println("\n\n\u001B[3;4mINFORMAÇÕES:\u001B[0m "); 
        System.out.println("\n1: Você tem 100 pontos de vida e 10 pontos de energia no início do jogo! Se sua vida zerar, você perde!");
        System.out.println("\n2: A cada turno, escolha quais cartas do seu deck você vai jogar, uma de cada vez! Lembre-se que só será permitido a combinação de cartas se não ultrapassar seus pontos de energia!");
        System.out.println("\n3: A cada novo turno, você ganha +1 de energia, mas nunca ulrapassa o limite de 10"); 
        System.out.println("\n4: Se seu deck esvaziar completamente, você receberá seu deck inicial de novo!");
        System.out.println("\n5: Você pode escolher jogar, passar a vez ou desistir a cada turno!");
        System.out.println("\n6: Se não tiver energia suficiente para jogar, você só poderá passar a vez ou desistir!");
        System.out.println("\n\nBOM JOGO!\n");

        System.out.println("Tecle 'Enter' para inciar o jogo!");
        entrada.nextLine();

        //while principal do jogo, que verifica vida etc
        while (hacker1.getVida() != 0 && hacker2.getVida() != 0){

            ArrayList<Integer> armazenaJogador1 = new ArrayList<>(); // vetor que armazena selecao das cartas da mao jogada JOGADOR 1
            ArrayList<Integer> armazenaJogador2 = new ArrayList<>(); // vetor que armazena selecao das cartas da mao jogada JOGADOR 2

            // Limpar no início do turno (precaução)
            armazenaJogador1.clear();
            armazenaJogador2.clear();

            replay.add("\n======================================");
            replay.add("TURNO " + contadorTurnos + "\n");
            replay.add("Vida P1: " + hacker1.getVida() + " | Energia P1: " + hacker1.getEnergia());
            replay.add("Vida P2: " + hacker2.getVida() + " | Energia P2: " + hacker2.getEnergia());
            
            // Mensagem Turno 
            System.out.println("\n##################################################################"); // separador turnos
            System.out.println("\nTURNO " + contadorTurnos);

            // reiniciando pontos de ataque e defesa dos jogadores
            hacker1.reinicioTurnoPontosAtqDef();
            hacker2.reinicioTurnoPontosAtqDef();

            if (contadorTurnos % 2 != 0) {

                // TURNOS ÍMPARES → P1 COMEÇA

                // --- Turno jogador 1 ---
                System.out.println("\n=> SUA VEZ, " + hacker1.getNome() + "(" + hacker1.getMatricula() + ")!");
                if(!hacker1.getNome().equals("BOT")) hacker1.imprimirCartasDeck();
                int opcao1 = hacker1.getNome().equals("BOT") ? 0 : verificaDesistencia(hacker1, entrada);
                if(hacker1.getNome().equals("BOT")) turnoBOT(hacker1, armazenaJogador1, replay, contadorTurnos);
                else turnoJogador(hacker1, armazenaJogador1, opcao1, entrada, replay);

                // --- Turno jogador 2 ---
                System.out.println("\n------------------------------------------------------------------");
                System.out.println("\n=> SUA VEZ, " + hacker2.getNome() + "(" + hacker2.getMatricula() + ")!");
                if(!hacker2.getNome().equals("BOT")) hacker2.imprimirCartasDeck();
                int opcao2 = hacker2.getNome().equals("BOT") ? 0 : verificaDesistencia(hacker2, entrada);
                if(hacker2.getNome().equals("BOT")) turnoBOT(hacker2, armazenaJogador2, replay, contadorTurnos);
                else turnoJogador(hacker2, armazenaJogador2, opcao2, entrada, replay);

            } else {
                // TURNOS PARES → P2 COMEÇA

                // --- Turno jogador 2 ---
                System.out.println("\n=> SUA VEZ, " + hacker2.getNome() + "(" + hacker2.getMatricula() + ")!");
                if(!hacker2.getNome().equals("BOT")) hacker2.imprimirCartasDeck();
                int opcao2 = hacker2.getNome().equals("BOT") ? 0 : verificaDesistencia(hacker2, entrada);
                if(hacker2.getNome().equals("BOT")) turnoBOT(hacker2, armazenaJogador2, replay, contadorTurnos);
                else turnoJogador(hacker2, armazenaJogador2, opcao2, entrada, replay);

                // --- Turno jogador 1 ---
                System.out.println("\n------------------------------------------------------------------");
                System.out.println("\n=> SUA VEZ, " + hacker1.getNome() + "(" + hacker1.getMatricula() + ")!");
                if(!hacker1.getNome().equals("BOT")) hacker1.imprimirCartasDeck();
                int opcao1 = hacker1.getNome().equals("BOT") ? 0 : verificaDesistencia(hacker1, entrada);
                if(hacker1.getNome().equals("BOT")) turnoBOT(hacker1, armazenaJogador1, replay, contadorTurnos);
                else turnoJogador(hacker1, armazenaJogador1, opcao1, entrada, replay);
            }

            // PAINEL do turno - depois que ambos jogaram
            System.out.println("\n\nPAINEL DO TURNO " + contadorTurnos + ":\n==================================================================");


            // JOGADOR 1
            System.out.println("\n" + hacker1.getNome() + "(" + hacker1.getMatricula() + ") jogou:\n");
            if (armazenaJogador1.isEmpty()) {
                System.out.println("Passou a vez!\n");
                replay.add("\n" + hacker1.getNome() + "(" + hacker1.getMatricula() + ") passou a vez.");
            } else {
                for (int idx : armazenaJogador1) hacker1.imprimeCartaDeckManipulavel(idx);
                replay.add("\n" + hacker1.getNome() + "(" + hacker1.getMatricula() + ") jogou:");
                for (int idx : armazenaJogador1) {
                    CartaP carta = hacker1.getDeckManipulavel().get(idx);
                    replay.add(" - " + carta.getNome() + " (" + carta.getTipo() + ")");
                    Espera.esperar(0.1);
                }
            }

            // JOGADOR 2
            System.out.println("\n" + hacker2.getNome() + "(" + hacker2.getMatricula() + ") jogou:\n");
            if (armazenaJogador2.isEmpty()) {
                System.out.println("Passou a vez!\n");
                replay.add("\n" + hacker2.getNome() + "(" + hacker2.getMatricula() + ") passou a vez.");
            } else {
                for (int idx : armazenaJogador2) hacker2.imprimeCartaDeckManipulavel(idx);
                replay.add("\n" + hacker2.getNome() + "(" + hacker2.getMatricula() + ") jogou:");
                for (int idx : armazenaJogador2) {
                    CartaP carta = hacker2.getDeckManipulavel().get(idx);
                    replay.add(" - " + carta.getNome() + " (" + carta.getTipo() + ")");
                    Espera.esperar(0.1);
                }
            }

            System.out.println("==================================================================\n");

            // Consolida turno
            consolidaTurno(hacker1, hacker2, armazenaJogador1, armazenaJogador2);

            replay.add("\nApós consolidação:");
            replay.add("Vida P1: " + hacker1.getVida() + " | Energia P1: " + hacker1.getEnergia());
            replay.add("Vida P2: " + hacker2.getVida() + " | Energia P2: " + hacker2.getEnergia());

            // Mostrar vida e energia
            System.out.println("\nDADOS " + hacker1.getNome() + "(" + hacker1.getMatricula() + "):\n" + "Vida: " + hacker1.getVida() + " | Energia: " + hacker1.getEnergia() + "\n");
            System.out.println("\nDADOS " + hacker2.getNome() + "(" + hacker2.getMatricula() + "):\n" + "Vida: " + hacker2.getVida() + " | Energia: " + hacker2.getEnergia() + "\n");

            // Deletar cartas jogadas
            hacker1.deletaCartasDeckManipulavel(armazenaJogador1);
            hacker2.deletaCartasDeckManipulavel(armazenaJogador2);

            // Reabastecer deck se estiver vazio
            if (hacker1.deckManipulavelEhVazio()) hacker1.preencherDeckManipulavel();
            if (hacker2.deckManipulavelEhVazio()) hacker2.preencherDeckManipulavel();

            // Aumentar energia
            hacker1.aumentaEnergia();
            hacker2.aumentaEnergia();

            // Mudar turno
            contadorTurnos++;
        }

        // FIM DO JOGO 

        if (hacker1.getVida() == 0 && hacker2.getVida() != 0){
            System.out.println("\n*---------------------------------------------------*");
            System.out.println("\n" + hacker2.getNome() + " VENCEU!");
            System.out.println("\n*---------------------------------------------------*");
            replay.add("\nVENCEDOR: " + hacker2.getNome());
        }else if (hacker1.getVida() != 0 && hacker2.getVida() == 0){
            System.out.println("\n*---------------------------------------------------*");
            System.out.println("\n" + hacker1.getNome() + " VENCEU!");
            System.out.println("\n*---------------------------------------------------*");
            replay.add("\nVENCEDOR: " + hacker1.getNome());
        }else{
            System.out.println("\n*---------------------------------------------------*");
            System.out.println("\nOs jogadores EMPATARAM!");
            System.out.println("\n*---------------------------------------------------*");
            replay.add("\nO jogo terminou EMPATADO.");
        }

        // OPÇÃO REPLAY
        String respReplay;
        while (true) {
            System.out.print("\n=> Deseja salvar o replay? (Y/N): ");
            respReplay = entrada.nextLine().trim().toLowerCase();
            if (respReplay.equals("y") || respReplay.equals("n")) break;
            else System.out.println("Opção Inválida! Digite apenas Y ou N.");
        }
        if (respReplay.equals("y")) replay.salvar("replay.txt");
    }


    // Método Turno Jogador 
    public void turnoJogador(Jogador hacker, ArrayList<Integer> armazena, int opcaoJogarPassarDesistir, Scanner entrada, Replay replay){

        if (opcaoJogarPassarDesistir == 2){
            System.out.println("\n" + hacker.getNome() + "(" + hacker.getMatricula() + ") DESISTIU!");
            replay.add("\n" + hacker.getNome() + "(" + hacker.getMatricula() + ") desistiu!");
            Espera.esperar(0.5);
            hacker.zeraVida();
        }
        else if (opcaoJogarPassarDesistir == 1){
            System.out.println("\n" + hacker.getNome() + "(" + hacker.getMatricula() + ") PASSOU A VEZ!");
            Espera.esperar(0.5);

        }
        else{
            selecaoMaoJogador(hacker, armazena, entrada);
        }
    }


    // Método selecao da mao jogada de um jogador
    public void selecaoMaoJogador(Jogador hacker, ArrayList<Integer> armazena, Scanner entrada){
        System.out.println("\nEscolha as cartas pelo índice: ");

        int qtdCartasDeck = hacker.deckManipulavelsize();
        
        // fazer um while até ser escolhido uma mão válida
        while(true){
            // limpando o ArrayList armazena
            armazena.clear();

            selecionarCartasManipulavel(hacker, armazena, qtdCartasDeck, entrada);
            // confirmar a mao jogada, mostrando as cartas selecionadas juntas
            System.out.println("\n*----------------------------------------------------");
            System.out.println("\n\u001B[3;4mSUA MÃO ESCOLHIDA:\u001B[0m\n");
            for (int i = 0; i < armazena.size(); i++){
                hacker.imprimeCartaDeckManipulavel(armazena.get(i));
                Espera.esperar(0.1);
            }
            System.out.println("*----------------------------------------------------\n");
            System.out.print("Confirma Seleção? (Y/N) ");

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
                    System.out.println("\nMÃO JOGADA!");
                    break;
                }
                else{
                    //caso nao, imprime mensagem e da continue para refazer o processo
                    System.out.println("\nMão selecionada ultrapassa seus pontos de energia! Selecione outra mão!");
                }   

            }
            else{
                //caso nao, imprime mensagem para selecionar de novo e da continue para refazer processo
                System.out.println("\nENTÃO SELECIONE SUA MÃO NOVAMENTE!");

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
            System.out.println("\n\n=> " + hacker.getNome() + "(" + hacker.getMatricula() + "), SELECIONE SUAS CARTAS!");
            System.out.print("\nSelecionar cartas (1)  Seleção Aleatória (2): ");  // Perguntando sobre escolha de selecao de cartas
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
        System.out.println("\n\n" + "Escolha " + qtdCartas + " cartas de " + conjunto.get(0).getTipo() + " da lista!" + "\n");
        System.out.println("################################################################################################\n");
        Espera.esperar(0.5);
            
        for (int i = 0; i < conjunto.size(); i++){
            System.out.print(i+1 + " ");
            conjunto.get(i).imprime();
            System.out.print("\n");
            Espera.esperar(0.1);
        }
        System.out.println("#################################################################################################");
        System.out.println("\n" + "Digite o número correspondente das cartas e confirme a seleção: ");
        Espera.esperar(0.5);

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
        System.out.println("\n##################################################################");
        hacker.imprimirCartasDeck(); // mostrar cartas escolhidas
        System.out.println("\n##################################################################");

        
    }

    // Método Selecao Aleatoria
    public void selecaoAleatoria(Jogador hacker, ArrayList<CartaP> conjunto, int qtdCartas){ 
        int numeroCarta;
        int[] verificarNumCartasRept = new int[qtdCartas];
        boolean numeroRept;
        
        //escolher número aleatório entre 1 e conjunto.size()+1
        Random gerador = new Random();
        for (int i = 0; i < qtdCartas; i++){
            numeroRept = false;
            numeroCarta = gerador.nextInt(conjunto.size()) + 1;
            for (int j = 0; j < i; j++){
                if (numeroCarta == verificarNumCartasRept[j]){
                    numeroRept = true;
                }
            }
            if (numeroRept){
                i--;
            }
            else{
                verificarNumCartasRept[i] = numeroCarta;
                hacker.adicionaNoDeck(conjunto, numeroCarta);
            }
        }
    }

    // Método Geral Selecao Aleatoria
    public void selecaoAleatoria(Jogador hacker, ArrayList<CartaP> conjunto1, ArrayList<CartaP> conjunto2, 
    ArrayList<CartaP> conjunto3, int qtdAtqDef, int qtdSup){
        System.out.println("\n\nSELECIONANDO CARTAS JOGADOR " + hacker.getNome() + "(" + hacker.getMatricula() + ") ALEATORIAMENTE...\n");
        selecaoAleatoria(hacker, conjunto1, qtdAtqDef); // ataque
        selecaoAleatoria(hacker, conjunto2, qtdAtqDef); // defesa
        selecaoAleatoria(hacker, conjunto3, qtdSup); // suporte
        hacker.preencherDeckManipulavel();
        System.out.println("\n##################################################################");
        hacker.imprimirCartasDeck();
        System.out.println("\n##################################################################");

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
