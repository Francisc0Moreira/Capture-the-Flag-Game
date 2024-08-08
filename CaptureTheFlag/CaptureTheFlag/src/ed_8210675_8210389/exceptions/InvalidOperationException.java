/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

package exceptions;

/**
 * The {@code InvalidOperationException} class is an exception that indicates
 * an attempt to perform an operation that is not allowed or invalid.
 * This exception is typically thrown when a method is called in an inappropriate state
 * or when an operation cannot be carried out for some reason.
 *
 */
public class InvalidOperationException extends Exception {

    /**
     * Constructs a new {@code InvalidOperationException} with no specified detail message.
     */
    public InvalidOperationException() {
        super();
    }

    /**
     * Constructs a new {@code InvalidOperationException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public InvalidOperationException(String message) {
        super(message);
    }
}
