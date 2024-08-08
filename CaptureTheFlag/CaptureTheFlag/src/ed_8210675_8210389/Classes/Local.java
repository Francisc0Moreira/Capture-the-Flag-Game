package Classes;
/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

public class Local implements Comparable<Local> {

    /**
     * Nome do local
     */
    private String localName;

    /**
     * Atributo que verifica que o local está ocupado ou não
     */
    private boolean occupied;

    /**
     * Atributo que verifica se o local tem a flag ou não
     */
    private boolean hasFlag;

    /**
     * 
     * @param botID
     */
    public Local(String name) {
        localName = name;
    }

    /**
     * Obtem o nome do local do mapa
     * 
     * @return o nome
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Define o nome do local do mapa
     * 
     * @param localName
     */
    public void setLocalName(String localName) {
        this.localName = localName;
    }

    /**
     * Verifica se o local está ocupado ou não
     * @return true se estiver ocupado, false caso contrário
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Define se o local está ocupado ou não
     * @param occupied
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * Verifica se o local tem a flag ou não
     * @return true se tiver a flag, false caso contrário
     */
    public boolean isHasFlag() {
        return hasFlag;
    }

    /**
     * Define se o local tem a flag ou não
     * @param hasFlag
     */
    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    /**
     * Este metodo permite resetar o local
     */
    public void resetLocal() {
        this.occupied = false;
        this.hasFlag = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((localName == null) ? 0 : localName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Local other = (Local) obj;
        if (localName == null) {
            if (other.localName != null)
                return false;
        } else if (!localName.equals(other.localName))
            return false;
        return true;
    }

    /**
     * Este metodo permite comparar locais no mapa
     * 
     * @param other novo local
     * @return
     */
    @Override
    public int compareTo(Local other) {
        return this.localName.compareTo(other.localName);
    }

    @Override
    public String toString() {
        return localName;
    }

}
