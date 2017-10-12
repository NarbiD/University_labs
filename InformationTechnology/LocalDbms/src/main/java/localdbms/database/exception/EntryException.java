package localdbms.database.exception;

public class EntryException extends StorageException {
    public EntryException() {
    }

    public EntryException(String message) {
        super(message);
    }

    public EntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntryException(Throwable cause) {
        super(cause);
    }

    public EntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
