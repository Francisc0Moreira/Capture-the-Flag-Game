package Classes;

/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Graphs.Network;
import Listas.ArrayOrderedList;
import exceptions.EmptyCollectionException;
import exceptions.NonComparableElementException;
import exceptions.UnknownPathException;

/**
 * Esta classe corresponde ao mapa onde os jogadores irão jogar
 */
public class Mapa {

    /**
     * Atributo que corresponde ao mapa
     */
    protected Network mapa;

    /**
     * Construtor do mapa
     */
    public Mapa() {
        this.mapa = new Network<>();
    }

    /**
     * Este metodo permite criar um mapa para os jogadores
     * 
     * @param numLocations     Numero de locais que irão fazer parte do mapa
     * @param chooseLocalNames Decisão se quer escolher um nome para cada local
     * @param bidirectional    Decisão se é bidirecional
     * @param density          Escolha da densidade das arestas no mapa
     */
    public void createMapa(int numLocations, boolean chooseLocalNames, boolean bidirectional, double density) {
        Scanner scanner = new Scanner(System.in);
        List<Local> locations = new ArrayList<>();

        if (chooseLocalNames) {
            for (int i = 0; i < numLocations; i++) {

                System.out.println("Escolha o nome do local " + i + " :");
                String nome = scanner.nextLine();

                Local local = new Local(nome);
                locations.add(local);
                mapa.addVertex(local);
            }
        } else {
            for (int i = 0; i < numLocations; i++) {
                Local local = new Local("Local " + (i + 1));
                locations.add(local);
                mapa.addVertex(local);
            }
        }

        Random random = new Random();
        int maxEdges = (int) ((numLocations * (numLocations - 1)) * density);
        int numEdges = 0;

        for (int i = 0; i < numLocations - 1; i++) {
            Local source = locations.get(i);
            Local destination = locations.get(i + 1);

            mapa.addEdge(source, destination, getRandom());

            if (!bidirectional)
                numEdges++;

            if (bidirectional) {
                mapa.addEdge(destination, source, getRandom());
                numEdges++;
            }
        }

        while (numEdges < maxEdges) {
            Local source = locations.get(random.nextInt(numLocations));
            Local destination;
            do {
                destination = locations.get(random.nextInt(numLocations - 1));
            } while (source == destination);

            if (source != destination) {
                mapa.addEdge(source, destination, getRandom());

                if (!bidirectional)
                    numEdges++;

                if (bidirectional) {
                    mapa.addEdge(destination, source, getRandom());
                    numEdges++;
                }
            }
        }
    }

    /**
     * Retorna um número aleatório no intervalo de 1 a 15 (inclusive)
     * 
     * @return um inteiro
     */
    public static int getRandom() {

        Random random = new Random();
        int randomNumber = random.nextInt(15) + 1;

        return randomNumber;
    }

    /**
     * Este metodo permite validar um inteiro
     * 
     * @param mensagem Mensagem a ser mostrada
     * @return o numero inteiro
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
     * Este metodo permite atribuir o bot ao local inicial
     * 
     * @return o local inicial
     */
    public Local atribuirBotAoLocalInicial() {
        Object[] vertices = this.mapa.getVertices();

        for (Object objTmp : vertices) {
            if (objTmp instanceof Local) {
                Local localTmp = (Local) objTmp;
                return localTmp;
            }
        }
        System.out.println("Não há locais no mapa.");
        return null;
    }

    /**
     * Este metodo permite escolher o algoritmo para o bot executar
     * 
     * @param bot o bot
     */
    public void chooseMove(Bots bot) {
        switch (bot.getAlgorithmChoice()) {
            case 1:

                try {
                    shortestWeightPathMove(bot);
                } catch (NonComparableElementException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    randomPathMove(bot);
                } catch (NonComparableElementException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    shortestPath(bot);
                } catch (NonComparableElementException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Escolha do algoritmo não foi bem definida: BOT(" + bot.getID() + ")");
        }
    }

    /**
     * Este algoritmo permite mover o bot através dos vizinhos com menor pesos
     * 
     * @param bot o bot
     * @throws NonComparableElementException exceção
     */
    public void shortestWeightPathMove(Bots bot) throws NonComparableElementException {

        ArrayOrderedList<Local> neighbors = getNeighborLocais(bot);

        Local currentLocal = procurarLocalDoBot(bot.getCurrentVertex());

        if (currentLocal == null) {
            System.out.println("Bot (" + bot.getID() + ") não encontrado.");
            return;
        }

        Local previousBotVertex = bot.getCurrentVertex();
        Local shortestPath = null;
        Local targetLocal = null;
        double minWeight = Double.POSITIVE_INFINITY;

        for (Local destination : neighbors) {
            if (!destination.isOccupied() && !destination.equals(previousBotVertex)) {
                double edge = this.mapa.getEdgeWeight(currentLocal, destination);

                if (destination.equals(bot.getTargetFlag())) {
                    continue;
                }

                if (edge < minWeight) {
                    minWeight = edge;
                    targetLocal = destination;
                    currentLocal.setOccupied(false);
                }
            }
        }

        if (targetLocal != null) {
            if (!targetLocal.isHasFlag()) {
                bot.setCurrentVertex(targetLocal);
                System.out.println("Bot com ID " + bot.getID() + " movido para " + targetLocal.getLocalName());

                targetLocal.setOccupied(true);
            }

        } else {
            System.out.println("Não foi possível encontrar um caminho para mover o bot com ID " + bot.getID());
        }
    }

    /**
     * Este algoritmo permite mover o bot através de um caminho aleatório
     * 
     * @param bot o bot
     * @throws NonComparableElementException exceção
     */
    public void randomPathMove(Bots bot) throws NonComparableElementException {

        Local currentLocal = procurarLocalDoBot(bot.getCurrentVertex());

        if (currentLocal == null) {
            System.out.println("Bot (" + bot.getID() + ") não encontrado.");
            return;
        }

        Local targetLocal = null;
        ArrayOrderedList<Local> neighbors = getNeighborLocais(bot);

        List<Local> availableDestinations = new ArrayList<>();

        for (Local destination : neighbors) {
            if (!destination.isOccupied()) {
                availableDestinations.add(destination);
            }
        }

        if (!availableDestinations.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(availableDestinations.size());
            targetLocal = availableDestinations.get(randomIndex);

            bot.setCurrentVertex(targetLocal);
            System.out.println("Bot com ID " + bot.getID() + " movido para " + targetLocal.getLocalName());

        } else {
            System.out.println("Não foi possível encontrar um caminho para mover o bot com ID " + bot.getID());
        }
    }

    /**
     * Este algoritmo permite mover o bot através do caminho mais curto
     * 
     * @param bot o bot
     * @throws NonComparableElementException exceção
     */
    public void shortestPath(Bots bot) throws NonComparableElementException {
        Iterator<Local> shortestPathIterator = this.mapa.iteratorShortestPath(bot.getCurrentVertex(),
                bot.getTargetFlag());

        if (!shortestPathIterator.hasNext()) {
            System.out.println("Não foi possível encontrar um caminho mais curto para o Bot com ID " + bot.getID());
            return;
        }

        Local previousBotVertex = bot.getCurrentVertex();

        while (shortestPathIterator.hasNext()) {
            Local newTargetLocation = shortestPathIterator.next();

            if (newTargetLocation != null) {
                if (!newTargetLocation.equals(previousBotVertex)) {
                    bot.setCurrentVertex(newTargetLocation);
                    System.out
                            .println("Bot com ID " + bot.getID() + " movido para " + newTargetLocation.getLocalName());

                    break;
                }
            } else {
                System.out.println("Não foi possível encontrar um caminho para mover o bot com ID " + bot.getID());
                break;
            }
        }
    }

    /**
     * Este metodo permite obter os vizinhos do local onde o bot se encontra
     * 
     * @param bot o bot
     * @return os vizinhos
     */
    private ArrayOrderedList<Local> getNeighborLocais(Bots bot) {
        ArrayOrderedList<Local> neighbors = new ArrayOrderedList<>();
        Local currentLocal = procurarLocalDoBot(bot.getCurrentVertex());

        if (currentLocal == null) {
            System.out.println("Bot (" + bot.getID() + ") não encontrado.");
            return neighbors;
        }

        Object[] vertices = this.mapa.getVertices();

        for (Object objTmp : vertices) {
            if (objTmp instanceof Local) {
                Local localTmp = (Local) objTmp;
                if (this.mapa.getEdgeWeight(currentLocal, localTmp) > 0) {
                    try {
                        neighbors.add(localTmp);
                    } catch (NonComparableElementException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return neighbors;
    }

    /**
     * Este metodo permite procurar o local onde o bot se encontra
     * 
     * @param currentLocation o local onde o bot se encontra
     * @return o local
     */
    private Local procurarLocalDoBot(Local currentLocation) {

        Object[] vertices = this.mapa.getVertices();

        for (Object objTmp : vertices) {
            if (objTmp instanceof Local) {
                Local localTmp = (Local) objTmp;
                if (localTmp.equals(currentLocation)) {
                    return (Local) objTmp;
                }
            }
        }
        return null;
    }

    /**
     * Este metodo permite obter o local através da escolha do jogador
     * 
     * @param choice a escolha do jogador
     * @return o local
     */
    public Local getLocalByChoice(int choice) {
        Object[] vertices = this.mapa.getVertices();

        if (choice >= 0 && choice < vertices.length) {
            Object objTmp = vertices[choice];

            if (objTmp instanceof Local) {
                return (Local) objTmp;
            }
        }

        return null;
    }

    /**
     * Este metodo permite obter todos os locais do mapa
     * 
     * @return os locais
     */
    public List<Local> getAllLocais() {
        List<Local> allLocais = new ArrayList<>();
        Object[] vertices = this.mapa.getVertices();

        for (Object objTmp : vertices) {
            if (objTmp instanceof Local) {
                Local localTmp = (Local) objTmp;
                allLocais.add(localTmp);
            }
        }

        return allLocais;
    }

    /**
     * Este metodo permite obter o mapa
     * 
     * @return o mapa
     */
    public Network getMapa() {
        return mapa;
    }

    /**
     * Este metodo permite importar o mapa
     * 
     * @param filename o nome do ficheiro
     */
    public void importMapa(String filename) {
        List<Local> locais = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean readingLocais = false;
            boolean readingConexoes = false;
            int numVertices = 0;
            while ((line = reader.readLine()) != null) {
                if (line.equals("Numero_vertices:")) {
                    numVertices = Integer.parseInt(reader.readLine());
                } else if (line.equals("Local")) {
                    readingLocais = true;
                    readingConexoes = false;
                } else if (line.equals("Conexoes")) {
                    readingLocais = false;
                    readingConexoes = true;
                } else if (readingLocais) {
                    String localName = line.replace(";", "");
                    Local local = new Local(localName);
                    locais.add(local);
                    mapa.addVertex(local);
                } else if (readingConexoes) {
                    String[] parts = line.split(";");
                    String localName1 = parts[0];
                    String localName2 = parts[1];
                    double weight = Double.parseDouble(parts[2]);

                    Local local1 = getLocalByName(localName1, locais);
                    Local local2 = getLocalByName(localName2, locais);

                    if (local1 != null && local2 != null && weight > 0.0) {
                        mapa.addEdge(local1, local2, weight);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Este metodo permite obter o local pelo nome
     * 
     * @param name   o nome do local
     * @param locais os locais
     * @return o local
     */
    private Local getLocalByName(String name, List<Local> locais) {
        for (Local local : locais) {
            if (local.getLocalName().equals(name)) {
                return local;
            }
        }
        return null;
    }

    /**
     * Este metodo permite exportar o mapa
     * 
     * @param filename o nome do ficheiro
     */
    public void exportMapa(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Numero_vertices:");
            writer.println(this.mapa.getVertices().length);

            List<Local> locations = getAllLocais();

            writer.println("Local");
            for (Local local : locations) {

                writer.println(local.getLocalName() + ";");
            }
            writer.println("Conexoes");
            for (Local local1 : locations) {
                for (Local local2 : locations) {
                    double weight = mapa.getEdgeWeight(local1, local2);
                    writer.println(local1 + ";" + local2 + ";" + weight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Mapa [mapa=\n" + mapa.toString() + "]";
    }

}
