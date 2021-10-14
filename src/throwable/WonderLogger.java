package throwable;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

public class WonderLogger {

    private Class<?> classe;

    public WonderLogger(Class<?> classe) {
        this.classe = classe;
    }

    public void loggarException(Exception ex) throws Exception {
        WonderException wException = new WonderException(classe.getCanonicalName(), Calendar.getInstance().getTime(), null, ex.getMessage());

        gerarLogFile(wException);
    }

    public void gerarLogFile(WonderException wException) throws Exception {
        PrintWriter out = new PrintWriter(new FileWriter(String.valueOf(wException.getDataHora().getTime()) + ".json", true));
        out.println(wException.toJson());
        out.flush();
        out.close();
    }

}
