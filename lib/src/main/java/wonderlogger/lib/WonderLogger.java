package wonderlogger.lib;

public abstract class AbsWonderLogger {
    WonderLoggerFactory wonderLoggerFactory;
    Class<?> javaClass;

    public abstract void loggarException(Exception ex);
}
