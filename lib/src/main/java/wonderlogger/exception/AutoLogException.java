package wonderlogger.exception;

import wonderlogger.lib.WonderLogger;
import wonderlogger.lib.WonderLoggerFactory;

public class AutoLogException extends Exception {
    public AutoLogException(String message, Throwable cause, Class javaClass) {
        super(message, cause);
        WonderLoggerFactory.getDefaultWonderLogger(javaClass).logException(cause);
    }

    public AutoLogException(String message, Throwable cause, WonderLogger wonderLogger) {
        super(message, cause);
        wonderLogger.logException(cause);
    }

    public AutoLogException(Throwable cause, Class javaClass) {
        super(cause);
    }

    public AutoLogException(Throwable cause, WonderLogger wonderLogger) {
        super(cause);
        wonderLogger.logException(cause);
    }
}
