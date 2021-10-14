package testeexceptionapp;

import throwable.WonderLogger;

public class TesteExceptionApp {

    public static void main(String[] args) throws Exception {
        WonderLogger a = new WonderLogger(TesteExceptionApp.class);
        a.loggarException(new Exception("heuehueheuhu"));
    }
    
}
