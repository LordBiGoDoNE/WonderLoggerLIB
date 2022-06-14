package wonderlogger.lib;

import com.google.gson.GsonBuilder;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import wonderlogger.model.entity.WonderConfig;
import wonderlogger.model.entity.WonderException;
import wonderlogger.model.vo.ImportExceptionRequestBody;
import wonderlogger.model.vo.PostResponse;
import wonderlogger.utils.HttpUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class WonderLoggerFactory {

    private final String software;
    private final String version;
    private final String apiAdress;
    private final String dbPath;
    private JdbcPooledConnectionSource jdbc;
    private Dao<WonderConfig, Long> wonderConfigDaoManager;
    private Dao<WonderException, Long> wonderExceptionDaoManager;
    private List<WonderException> wonderExceptions;
    private GsonBuilder gsonBuilder = new GsonBuilder();

    private static WonderLoggerFactory DEFAULT_FACTORY;

    public static void setDefaultFactory(WonderLoggerFactory pWonderLoggerFactory) {
        DEFAULT_FACTORY = pWonderLoggerFactory;
    }

    public static WonderLogger getDefaultWonderLogger(Class<?> pJavaClass) {
        return new WonderLoggerImpl(DEFAULT_FACTORY, pJavaClass);
    }

    public WonderLoggerFactory(String software, String version, String apiAdress, String dbPath, int intervalSeconds) {
        this.software = software;
        this.version = version;
        this.apiAdress = apiAdress;
        this.dbPath = dbPath;

        initFactory(intervalSeconds);
    }

    public WonderLoggerFactory(String software, String version, String apiAdress, String dbPath, int intervalSeconds, boolean setAsDefault) {
        this.software = software;
        this.version = version;
        this.apiAdress = apiAdress;
        this.dbPath = dbPath;

        if (setAsDefault) {
            WonderLoggerFactory.DEFAULT_FACTORY = this;
        }

        initFactory(intervalSeconds);
    }

    private void initFactory(int intervalSeconds) throws RuntimeException {
        try {
            File directory = new File(dbPath);
            if (!directory.exists()) {
                directory.mkdir();
            }

            String sqlitePath = new StringBuilder("jdbc:sqlite:")
                    .append(dbPath)
                    .append(software.trim())
                    .append("-")
                    .append(version.trim())
                    .append(".db").toString();

            jdbc = new JdbcPooledConnectionSource(sqlitePath);

            TableUtils.createTableIfNotExists(jdbc, WonderConfig.class);
            TableUtils.createTableIfNotExists(jdbc, WonderException.class);

            wonderConfigDaoManager = DaoManager.createDao(jdbc, WonderConfig.class);
            wonderExceptionDaoManager = DaoManager.createDao(jdbc, WonderException.class);

            startThread(intervalSeconds);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public WonderLogger getWonderLogger(Class<?> pJavaClass) {
        return new WonderLoggerImpl(this, pJavaClass);
    }

    public void saveExceptionToDatabase(WonderException pWonderException) {
        try {
            wonderExceptionDaoManager.create(pWonderException);
        } catch (SQLException e) {
            if (!wonderExceptions.contains(pWonderException)) {
                wonderExceptions.add(pWonderException);

                try {
                    saveToLog(pWonderException);
                } catch (IOException ex) {
                    Logger.getLogger(WonderLoggerFactory.class.getName()).log(Level.SEVERE, "WonderException insert error!", e);
                    Logger.getLogger(WonderLoggerFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void startThread(int intervalSeconds) {
        ScheduledExecutorService scheduleService = Executors.newSingleThreadScheduledExecutor();

        scheduleService.scheduleAtFixedRate(() -> {
            try {
                WonderConfig wonderConfig = wonderConfigDaoManager.queryForFirst();

                if (wonderConfig == null) {
                    wonderConfig = new WonderConfig(software, version);
                }

                List<WonderException> exceptionsToSend = wonderExceptionDaoManager.queryBuilder().limit(100L).query();

                if (!exceptionsToSend.isEmpty()) {
                    ImportExceptionRequestBody importExceptionRequestBody = new ImportExceptionRequestBody(wonderConfig, exceptionsToSend);

                    sendToAPI(importExceptionRequestBody);
                }
            } catch (Exception e) {
                Logger.getLogger(WonderLoggerFactory.class.getName()).log(Level.SEVERE, null, e);
            }
        }, 0, intervalSeconds, TimeUnit.SECONDS);
    }

    private void sendToAPI(ImportExceptionRequestBody pImportExceptionRequestBody) {
        String json = new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss")
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create().toJson(pImportExceptionRequestBody);

        PostResponse response = HttpUtils.sendPost(apiAdress, json);

        if (response.isSucess()) {
            try {
                wonderExceptionDaoManager.deleteIds(pImportExceptionRequestBody.getWonderExceptions().stream().map(WonderException::getId).collect(Collectors.toList()));
                wonderConfigDaoManager.executeRaw("VACUUM");
            } catch (SQLException e) {
                Logger.getLogger(WonderLoggerFactory.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            if (response.getResponse() != null) {
                Logger.getLogger(WonderLoggerFactory.class.getName()).log(Level.SEVERE, response.getResponse());
            }

            if (response.getException() != null) {
                Logger.getLogger(WonderLoggerFactory.class.getName()).log(Level.SEVERE, null, response.getException());
            }
        }
    }

    private void saveToLog(WonderException pWonderException) throws IOException {
        String logPath = new StringBuilder(dbPath)
                .append(software.trim())
                .append("-")
                .append(version.trim())
                .append(".log").toString();

        PrintWriter out = new PrintWriter(new FileWriter(logPath, true));
        out.println(gsonBuilder.create().toJson(pWonderException));
        out.flush();
        out.close();
    }
}
