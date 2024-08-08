package Classes;

/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

import Listas.ArrayList;
import Listas.ArrayOrderedList;
import exceptions.EmptyCollectionException;
import exceptions.NonComparableElementException;

/**
 * Esta classe é extendida a partir de um ArraList e corresoponde
 * à classe dos jogadores que irão jogar
 */
public class Player<T> extends ArrayOrderedList<T> implements Comparable<Player> {

    /**
     * Nome do jogador
     */
    private String name;

    /**
     * A lista dos bots de cada jogador
     */
    private ArrayOrderedList<Bots> bots;

    /**
     * Construtor do jogador
     * 
     * @param name nome do jogador
     */
    public Player(String name) {
        this.name = name;
        this.bots = new ArrayOrderedList<>() {
        };
    }

    /**
     * Obtem o nome do jogador
     * 
     * @return o nome
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do jogador
     * 
     * @param name nome com que o jogador se identifica
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtem todos os bots do jogador
     * 
     * @return os bots
     */
    public ArrayOrderedList<Bots> getBots() {
        return bots;
    }

    /**
     * Compara o nome do jogador com o nome do outro jogador
     * @param otherPlayer o outro jogador
     */
    @Override
    public int compareTo(Player otherPlayer) {
        return this.name.compareTo(otherPlayer.name);
    }

    /**
     * Define os bots do jogador
     * 
     * @param bots São os bots do jogador
     */
    public void setBots(ArrayOrderedList<Bots> bots) {
        this.bots = (ArrayOrderedList<Bots>) bots;
    }

    /**
     * Adiciona um bot à lista de bots do jogador
     * 
     * @param bot o bot a ser adicionado
     * @throws NonComparableElementException se o bot não for comparável
     */
    public void addBot(Bots bot) throws NonComparableElementException {
        this.bots.add(bot);
        ;
    }

    /**
     * Remove um bot da lista de bots do jogador
     * 
     * @param bot o bot a ser removido
     */
    public void removeBots(Bots bot) {
        try {
            this.bots.remove(bot);
        } catch (EmptyCollectionException e) {

            e.printStackTrace();
        }
    }

}
