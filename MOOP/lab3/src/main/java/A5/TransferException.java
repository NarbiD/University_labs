package A5;

public class TransferException extends Exception {
    public TransferException() {
    }

    public TransferException(String s) {
        super(s);
    }

    public TransferException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TransferException(Throwable throwable) {
        super(throwable);
    }
}
