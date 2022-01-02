package wonderlogger.lib;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Calendar;
import wonderlogger.utils.HttpUtils;

public class WonderLogger {

    private static String softwareHouseToken;
    private static String software;
    private static String version;
    private static URL urlAPIImportacao;
    private static String fileLocation;

    private Class<?> classe;

    public WonderLogger(Class<?> classe) {
        this.classe = classe;
    }

    public static void initConfiguration(String pIdSoftwareHouse, String pSoftware, String pVersion, URL pUrl) {
        softwareHouseToken = pIdSoftwareHouse;
        software = pSoftware;
        version = pVersion;
        urlAPIImportacao = pUrl;
    }

    public void loggarException(Exception ex) throws Exception {
        WonderException wEX = new WonderException(classe, software, version, ex, Calendar.getInstance().getTime());
        
        if (!HttpUtils.sendPost(urlAPIImportacao, wEX.toJson())) {
            generateJsonFile(wEX);
        }
    }

    public void generateJsonFile(WonderException pWonderException) throws Exception {
        PrintWriter out = new PrintWriter(new FileWriter(String.valueOf(pWonderException.getClasse() + pWonderException.getDateHour().getTime()) + ".sql", true));
        out.println(pWonderException.toJson());
        out.flush();
        out.close();
    }

}
