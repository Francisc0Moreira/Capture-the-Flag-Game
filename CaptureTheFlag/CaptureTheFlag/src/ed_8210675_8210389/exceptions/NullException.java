/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

package exceptions;

/**
 * Esta  exceção é geralmente lançada quando um método espera um valor não nulo
 * mas recebe uma referência nula, resultando em um erro.
 *
 */
public class NullException extends Exception {

    /**
     * Controi uma nova {@code NullException} com uma mensagem em especifico.
     *
     * @param message mensagem detalhada
     */
    public NullException(String message) {
        super("Erro " + message);
    }
}
