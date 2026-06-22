package mate.academy.exception;

public class NoCartItemInShoppingCartException extends RuntimeException {
    public NoCartItemInShoppingCartException(String message) {
        super(message);
    }
}
