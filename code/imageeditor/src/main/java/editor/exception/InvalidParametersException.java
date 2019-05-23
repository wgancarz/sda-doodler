package editor.exception;

public class InvalidParametersException extends Exception {

    public InvalidParametersException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidParametersException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
