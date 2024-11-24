package gift.global.exception1.wish;

public class WishCanNotModifyException extends RuntimeException {
    public WishCanNotModifyException() {
        super("Wish cannot be modified.");
    }
}
