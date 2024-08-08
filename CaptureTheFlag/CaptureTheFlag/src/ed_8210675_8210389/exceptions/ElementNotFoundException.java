/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

package exceptions;

/**
 * The {@code ElementNotFoundException} class is a runtime exception that indicates
 * that an element could not be found or accessed.
 * This exception is typically thrown when an operation expects an element to be present,
 * but the element is not found, leading to a runtime error.
 *
 */
public class ElementNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code ElementNotFoundException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ElementNotFoundException(String message) {
        super("Erro " + message);
    }
}
