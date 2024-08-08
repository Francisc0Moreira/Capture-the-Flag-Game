/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */
package exceptions;

/**
 * A classe {@code UnknownPathException} é uma exceção que indica
 * uma tentativa de lidar com um caminho desconhecido ou indefinido.
 * Essa exceção é geralmente lançada quando uma operação espera por um caminho válido,
 * mas o caminho fornecido não é reconhecido ou não existe, resultando em um erro.
 *
 */
public class UnknownPathException extends Exception {

    /**
     * Constrói uma nova {@code UnknownPathException} sem uma mensagem detalhada especificada.
     */
    public UnknownPathException() {
        super();
    }

    /**
     * Constrói uma nova {@code UnknownPathException} com a mensagem detalhada especificada.
     *
     * @param message a mensagem detalhada
     */
    public UnknownPathException(String message) {
        super(message);
    }
}
