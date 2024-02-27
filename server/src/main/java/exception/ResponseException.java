package exception;

public class ResponseException extends Exception {
    final private int errorCode;

    public ResponseException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int errorCode() {
        return errorCode;
    }
}