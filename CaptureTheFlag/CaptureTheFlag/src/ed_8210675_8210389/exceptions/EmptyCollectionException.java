/**
 * Francisco Moreira - 8210675
 * Paulo Gonçalves - 8210389
 */

package exceptions;

/**
 * The {@code EmptyCollectionException} class is an exception that indicates
 * an attempt to perform an operation on an empty collection.
 * This exception is typically thrown when a method expects the collection to contain elements,
 * but the collection is found to be empty, leading to an error.
 *
 */
public class EmptyCollectionException extends Exception {

    /**
     * Constructs a new {@code EmptyCollectionException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method)
     */
    public EmptyCollectionException(String message) {
        super("Erro " + message);
    }
}
