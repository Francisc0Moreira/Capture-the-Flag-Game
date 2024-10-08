/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

package exceptions;

/**
 * The {@code ElementDoesntExistException} class is an exception that indicates
 * that an attempt to access or manipulate an element that doesn't exist has occurred.
 * This exception is typically thrown when an operation expects an element to be present,
 * but the element is not found.
 *

 */
public class ElementDoesntExistException extends Exception {

    /**
     * Constructs a new {@code ElementDoesntExistException} with no specified detail message.
     */
    public ElementDoesntExistException() {
        super();
    }

    /**
     * Constructs a new {@code ElementDoesntExistException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public ElementDoesntExistException(String message) {
        super(message);
    }
}
