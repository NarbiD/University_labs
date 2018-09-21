package A;

public class EmptyRouteException extends Exception {
    public EmptyRouteException() {
    }

    public EmptyRouteException(String s) {
        super(s);
    }

    public EmptyRouteException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public EmptyRouteException(Throwable throwable) {
        super(throwable);
    }
}
