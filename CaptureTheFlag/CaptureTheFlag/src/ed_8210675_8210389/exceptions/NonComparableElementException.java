/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

package exceptions;

/**
 * The {@code NonComparableElementException} class is an exception that indicates
 * an attempt to compare or operate on elements that are not comparable.
 * This exception is typically thrown when a comparison or operation is performed
 * on elements that do not implement the Comparable interface.
 *
 */
public class NonComparableElementException extends Exception {

    /**
     * Constructs a new {@code NonComparableElementException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public NonComparableElementException(String message) {
        super("Erro " + message);
    }
}
