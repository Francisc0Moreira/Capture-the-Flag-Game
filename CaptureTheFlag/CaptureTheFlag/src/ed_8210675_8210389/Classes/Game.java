package Classes;

/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

import Listas.ArrayOrderedList;
import exceptions.NonComparableElementException;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Game {

    
    /**
     * Este metodo cria o mapa
     * @param mapa mapa a ser criado
     */
    public void criarMapa(Mapa mapa) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n===== Map Creation =====\n");
        int numLocations = validateInt("Informe a quantidade de localizações no mapa: ");
        boolean bidirectional = getYesOrNo("O mapa será bidirecional? (S/N): ");
        double density = validateDouble("Informe a densidade das arestas (0.0 a 1.0): ");
        boolean LocalsNames = getYesOrNo(
                "Deseja escrever o nome para cada local? Caso N, os locais irão ser numerados! (S/N): ");

        mapa.createMapa(numLocations, LocalsNames, bidirectional, density);
        System.out.println("Mapa criado com sucesso!");

        boolean exportMap = getYesOrNo("Deseja exportar o mapa? (S/N): ");
        if (exportMap) {
            System.out.println("\n= Escolha o nome do seu mapa: ");
            String nomeDoMapa = scanner.nextLine();

            if (!nomeDoMapa.toLowerCase().endsWith(".txt")) {
                nomeDoMapa += ".txt";
            }

            mapa.exportMapa(nomeDoMapa);
            System.out.println("Mapa exportado com sucesso!");
        }
    }

    /**
     * Este metodo serve para dar inicio ao jogo
     * @param mapa mapa a ser jogado
     * @throws NonComparableElementException excecao
     */
    public void jogarJogo(Mapa mapa) throws NonComparableElementException {
        Scanner scanner = new Scanner(System.in);

        ArrayOrderedList<Player> jogadores = new ArrayOrderedList<Player>();

        System.out.println("\n========================================");
        System.out.println("======      CAPTURE THE FLAG      ======");
        System.out.println("========================================");
        System.out.println("\n|| Define o numero de bots para este jogo: ");
        int countBots = scanner.nextInt();

        for (int jogadorNum = 1; jogadorNum <= 2; jogadorNum++) {
            System.out.println("=  Escolha o nome do jogador: ");
            scanner.nextLine();
            String nome = scanner.nextLine();

            Player jogador = new Player<>(nome);
            criarBots(jogador, scanner, mapa, countBots);
            jogadores.add(jogador);
        }

        definirTargetLocationDosJogadores(jogadores);

        int numBots = jogadores.get(0).getBots().size();

        while (true) {
            for (int botIndex = 0; botIndex < numBots; botIndex++) {
                for (Player jogador : jogadores) {
                    System.out.println("\n== Jogador " + jogador.getName() + " ==");

                    Bots bot = (Bots) jogador.getBots().get(botIndex);

                    System.out.println("== Bot " + bot.getID() + " ==");
                    System.out.println(
                            "Local inicial antes de escolher o movimento: " + bot.getCurrentVertex().getLocalName());

                    mapa.chooseMove(bot);

                    System.out
                            .println("Local atual após escolher o movimento: " + bot.getCurrentVertex().getLocalName());

                    if (bot.getCurrentVertex().equals(bot.getTargetFlag())) {
                        System.out.println("\n ===== GAME-OVER ===== \n");
                        System.out.println(" -> Bot " + bot.getID() + "do jogador " + jogador.getName()
                                + " chegou à bandeira! (" + bot.getTargetFlag() + ")");
                        resetGame(jogadores, mapa);
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Este metodo serve para dar reset ao jogo
     * @param jogadores jogadores do utlimo jogo
     * @param mapa mapa do jogo
     */
    public void resetGame(ArrayOrderedList<Player> jogadores, Mapa mapa) {

        for (Player jogador : jogadores) {
            ArrayOrderedList<Bots> bots = jogador.getBots();
            for (Bots bot : bots) {
                bot.resetBot();
            }
        }

        for (Local local : mapa.getAllLocais()) {
            local.resetLocal();
        }
    }

    /**
     * Este metodo serve para criar os bots
     * @param jogador jogador onde os bots vao ser criados
     * @param scanner scanner
     * @param mapa mapa do jogo
     * @param numBots numero de bots a serem criados
     * @return retorna o jogador com os bots criados
     */
    private static Player criarBots(Player jogador, Scanner scanner, Mapa mapa, int numBots) {

        boolean swpchoice = true;
        boolean rpchoice = true;
        boolean spchoice = true;
        int startingPoint = chooseStartingPoint(mapa);
        Local localinicial = null;

        for (int i = 1; i <= numBots; i++) {

            Bots bot = new Bots(i);
            try {
                jogador.addBot(bot);
            } catch (NonComparableElementException e) {
                e.printStackTrace();
            }

            int choice = 0;
            do {

                System.out.println("\n===========================================");
                System.out.println("======= Escolha um algoritmo para o bot ===");
                System.out.println("=======---------------------------------===");
                if (swpchoice == true)
                    System.out.println("======= 1. Shorthest Weight Path        ===");
                if (rpchoice == true)
                    System.out.println("======= 2. Random Path                  ===");
                if (spchoice == true)
                    System.out.println("======= 3. Shortest Path                ===");
                System.out.println("===========================================\n");
                choice = scanner.nextInt();

                if (choice == 1){
                    swpchoice = false;}
                if (choice == 2){
                    rpchoice = false;}
                if (choice == 3){
                    spchoice = false;}

            } while (choice < 1 || choice > 3);

            bot.setAlgorithmChoice(choice);
            localinicial = ChooseBotsFlags(bot, startingPoint, mapa);

        }
        return jogador;
    }

    /**
     * Este metodo serve para definir a bandeira que os bots irão capturar
     * @param jogadores jogadores do jogo
     */
    public static void definirTargetLocationDosJogadores(ArrayOrderedList<Player> jogadores) {
        for (Player jogador : jogadores) {
            for (Player outroJogador : jogadores) {
                if (jogador != outroJogador) {
                    definirTargetLocationDoJogador(jogador, outroJogador);
                }
            }
        }
    }

    /**
     * Este metodo serve para definir a bandeira que os bots irão capturar
     * @param jogador jogador1
     * @param outroJogador jogador2
     */
    private static void definirTargetLocationDoJogador(Player jogador, Player outroJogador) {
        ArrayOrderedList<Bots> botsJogador = jogador.getBots();
        ArrayOrderedList<Bots> botsOutroJogador = outroJogador.getBots();

        Iterator<Bots> iteratorBotsJogador = botsJogador.iterator();

        while (iteratorBotsJogador.hasNext()) {
            Bots botJogador = iteratorBotsJogador.next();

            Iterator<Bots> iteratorBotsOutroJogador = botsOutroJogador.iterator();

            if (iteratorBotsOutroJogador.hasNext()) {
                Bots botOutroJogador = iteratorBotsOutroJogador.next();

                botJogador.setTargetFlag(botOutroJogador.getCurrentVertex());
            }
        }
    }

    /**
     * Este metodo serve para validar um inteiro
     * @param mensagem mensagem a ser mostrada
     * @return retorna o inteiro validado
     */
    public static int validateInt(String mensagem) {
        Scanner scanner = new Scanner(System.in);
        int numero = 0;
        boolean entradaValida = false;
        do {
            try {
                System.out.print(mensagem);
                String input = scanner.nextLine();
                numero = Integer.parseInt(input);
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número inteiro válido.");
            }
        } while (!entradaValida);
        return numero;
    }
     
    /**
     * Este metodo serve para validar um double
     * @param mensagem mensagem a ser mostrada
     * @return retorna o double validado 
     */
    private static double validateDouble(String mensagem) {
        Scanner scanner = new Scanner(System.in);
        double numero = 0.0;
        boolean entradaValida = false;
        do {
            try {
                System.out.print(mensagem);
                String input = scanner.nextLine();
                numero = Double.parseDouble(input);
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        } while (!entradaValida);
        return numero;
    }

    /**
     * Este metodo serve para validar um boolean
     * @param mensagem mensagem a ser mostrada
     * @return retorna o boolean validado
     */
    private static boolean getYesOrNo(String mensagem) {
        Scanner scanner = new Scanner(System.in);
        boolean respostaValida = false;
        do {
            System.out.print(mensagem);
            String resposta = scanner.nextLine().toUpperCase();
            if (resposta.equals("S") || resposta.equals("s")) {
                return true;
            } else if (resposta.equals("N") || resposta.equals("n")) {
                return false;
            } else {
                System.out.println("Resposta inválida. Por favor, digite 'S'/'s' para Sim ou 'N'/'n' para Não.");
            }
        } while (!respostaValida);
        return false;
    }

    /**
     * Este metodo serve para escolher o ponto de partida
     * @param mapa mapa do jogo
     * @return retorna a escolha para o ponto de partida
     */
    public static int chooseStartingPoint(Mapa mapa) {
        List<Local> allLocais = mapa.getAllLocais();

        if (allLocais.isEmpty()) {
            System.out.println("Não há locais no mapa.");
            return -1;
        }

        int choice = 0;
        do {
            System.out.println("\n========== Todos os locais deste mapa ========= ");
            for (int i = 0; i < allLocais.size(); i++) {
                Local local = allLocais.get(i);
                if (!local.isOccupied()) {
                    System.out.println("|| " + i + ". " + local.getLocalName());
                }
            }

            choice = validateInt("\n ==== Escolha onde irá ficar a bandeira : ");
        } while (choice < 0 || choice >= allLocais.size());

        return choice;
    }

    /**
     * Este metodo serve para escolher a bandeira de cada bot
     * @param bot1 bot a escolher a bandeira
     * @param choice escolha do bot
     * @param mapa mapa do jogo
     * @return retorna o local onde a bandeira foi colocada
     */
    public static Local ChooseBotsFlags(Bots bot1, int choice, Mapa mapa) {

        if (bot1 == null) {
            System.out.println("Não existe esse bot!");
            return null;
        }

        Local localInicial;

        do {

            localInicial = mapa.getLocalByChoice(choice);

            if (localInicial != null && !localInicial.isOccupied()) {
                bot1.setCurrentVertex(localInicial);

                localInicial.setHasFlag(true);
                System.out.println(
                        "Local inicial escolhido para o bot " + bot1.getID() + ": " + localInicial.getLocalName());
                return localInicial;
            } else if (localInicial != null) {
                System.out.println("Local já ocupado. Escolha outro local para o bot " + bot1.getID() + ".");
            } else {
                System.out.println("Local não encontrado. Escolha um local válido para o bot " + bot1.getID() + ".");
            }
        } while (!localInicial.isOccupied());

        return localInicial;
    }

}
