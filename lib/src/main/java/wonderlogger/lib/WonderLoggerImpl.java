package wonderlogger.lib;

import wonderlogger.model.entity.WonderException;

class WonderLoggerImpl implements WonderLogger {

    private WonderLoggerFactory wonderLoggerFactory;
    private Class<?> javaClass;

    public WonderLoggerImpl(WonderLoggerFactory wonderLoggerFactory, Class<?> javaClass) {
        this.wonderLoggerFactory = wonderLoggerFactory;
        this.javaClass = javaClass;
    }

    @Override
    public void logException(Throwable ex) {
        wonderLoggerFactory.saveExceptionToDatabase(new WonderException(javaClass, ex));
    }
}
