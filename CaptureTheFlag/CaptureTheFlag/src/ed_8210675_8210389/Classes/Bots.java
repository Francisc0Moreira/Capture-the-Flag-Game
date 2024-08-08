package Classes;

import exceptions.NonComparableElementException;

/**
 * Esta classe corresponde ao bot que cada jogador possui
 */
public class Bots<T> implements Comparable<Bots> {

    /**
     * Id do bot correspondido
     */
    private int ID;

    /**
     * Escolha do algoritmo do bot
     */
    private int algorithmChoice;

    /**
     * Elemento que indica o vertice onde o bot se encontra
     */
    private Local currentVertex;

    /**
     * Elemento que indica o vertice onde o bot se encontra
     */
    private Local targetFlag;

    /**
     * Elemento que indica o vertice onde o bot estava anteriormente
     */
    private Local previousVertex;

    

    /**
     * Este metodo define a flag que o bot tem de apanhar
     * @param targetFlag flag que o bot tem de apanhar
     */
    public void setTargetFlag(Local targetFlag) {
        this.targetFlag = targetFlag;
    }

    /**
     * Este metodo obtem a flag que o bot tem de apanhar
     * @return flag que o bot tem de apanhar
     */
    public Local getTargetFlag() {
        return targetFlag;
    }

    /**
     * Este contrutor define o ID do bot que irá jogar
     * 
     * @param iD
     */
    public Bots(int iD) {
        ID = iD;
    }

    /**
     * Obtem o id do bot
     * 
     * @return o id do bot
     */
    public int getID() {
        return ID;
    }

    /**
     * Define o id do bot
     * 
     * @param iD id escolhido
     */
    public void setID(int iD) {
        ID = iD;
    }

    /**
     * Obtem a escolha do algoritmo do bot
     * 
     * @return a escolha
     */
    public int getAlgorithmChoice() {
        return algorithmChoice;
    }

    /**
     * Define a escolha do algoritmo para o bot
     * 
     * @param algorithmChoice recebe a escolha {1-3}
     */
    public void setAlgorithmChoice(int algorithmChoice) {
        this.algorithmChoice = algorithmChoice;
    }

    /**
     * Obtem o local anterior do bot
     * @return o local anterior
     */
    public Local getPreviousVertex() {
        return previousVertex;
    }
 
    /**
     * Define o local anterior do bot
     * @param previousVertex local anterior
     */
    public void setPreviousVertex(Local previousVertex) {
        this.previousVertex = previousVertex;
    }


    /**
     * Obtem o vertice onde o bot se encontra
     * 
     * @return o vertice
     */
    public Local getCurrentVertex() {
        return currentVertex;
    }

    /**
     * Define o vertice onde o bot se vai mudar
     * 
     * @param newVertex Vertice novo
     */
    public void setCurrentVertex(Local newVertex) {
        this.currentVertex = newVertex;
    }

    /**
     * Este metodo reseta o bot para o estado inicial
     */
    public void resetBot() {
        this.algorithmChoice = 0;
        this.currentVertex = null;
        this.targetFlag = null;
        this.previousVertex = null;
    }

    @Override
    public int compareTo(Bots otherBot) {
        return Integer.compare(this.getID(), otherBot.getID());
    }

}
