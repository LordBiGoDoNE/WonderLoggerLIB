package wonderlogger.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;
import wonderlogger.model.entity.WonderConfig;
import wonderlogger.model.entity.WonderException;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WonderLoggerFactory {

    private String software;
    private String version;
    private String apiAdress;
    private String dbPath;
    private JdbcPooledConnectionSource jdbc;
    private Dao<WonderConfig, Long> wonderConfigDaoManager;
    private Dao<WonderException, Long> wonderExceptionDaoManager;
    private List<WonderException> wonderExceptions;

    public WonderLoggerFactory(String pSoftware, String pVersion, String pAPIAdress, String pDBPath) {
        this.software = pSoftware;
        this.version = pVersion;
        this.apiAdress = pAPIAdress;
        this.dbPath = pDBPath;

        String data = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        try {
            String sqlitePath = new StringBuilder("jdbc:sqlite:")
                    .append(dbPath)
                    .append(software.trim())
                    .append("-")
                    .append(version.trim())
                    .append("-")
                    .append(data)
                    .append(".db").toString();

            jdbc = new JdbcPooledConnectionSource(sqlitePath);

            TableUtils.createTableIfNotExists(jdbc, WonderConfig.class);
            TableUtils.createTableIfNotExists(jdbc, WonderException.class);

            wonderConfigDaoManager = DaoManager.createDao(jdbc, WonderConfig.class);
            wonderExceptionDaoManager = DaoManager.createDao(jdbc, WonderException.class);
        } catch (SQLException ex) {
            Logger.getLogger(WonderLoggerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    List<WonderException> getWonderExceptions() {
        return wonderExceptions;
    }
}
