package hifit.be.user.exception;

public class TokenExpiredException extends RuntimeException{

    public TokenExpiredException(String message) {

        super(message);
    }
}
