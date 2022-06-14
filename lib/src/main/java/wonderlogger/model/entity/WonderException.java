package wonderlogger.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.exception.ExceptionUtils;

@DatabaseTable(tableName = "wonderexception")
public class WonderException {

    @DatabaseField(generatedId = true)
    private long id;
    
    @DatabaseField()
    private String softwareClass;

    @DatabaseField()
    private Date dateHour;

    @DatabaseField()
    private String exceptionName;

    @DatabaseField()
    private String message;
    
    @DatabaseField()
    private String stacktrace;

    public WonderException(Class<?> pClass, Throwable pThrowable) {
        this.dateHour = Calendar.getInstance().getTime();
        this.softwareClass = pClass.getCanonicalName();
        this.exceptionName = pThrowable.getClass().getSimpleName();
        this.message = pThrowable.getMessage();
        this.stacktrace = ExceptionUtils.getStackTrace(pThrowable);
    }

    public WonderException() {
    }

    public long getId() {
        return id;
    }

    public String getSoftwareClass() {
        return softwareClass;
    }

    public Date getDateHour() {
        return dateHour;
    }

    public String getMessage() {
        return message;
    }

    public String getStacktrace() {
        return stacktrace;
    }
}
