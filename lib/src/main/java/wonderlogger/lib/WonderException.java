package wonderlogger.lib;

import com.google.gson.GsonBuilder;

import java.util.Date;

public class WonderException {

    private String software;
    private String versao;
    private String classe;
    private Date dateHour;
    private String message;
    private String stacktrace;

    public WonderException(Class<?> pClass, String pSoftware, String pVersao, Throwable pThrowable, Date pDateHour) {
        this.software = pSoftware;
        this.versao = pVersao;
        this.classe = pClass.getCanonicalName();
        this.dateHour = pDateHour;
        this.message = pThrowable.getMessage();
        this.stacktrace = pThrowable.getCause() != null ? pThrowable.getCause().getClass().getCanonicalName() : null;
    }

    public WonderException() {
    }

    public String getSoftware() {
        return software;
    }

    public WonderException setSoftware(String software) {
        this.software = software;
        return this;
    }

    public String getVersao() {
        return versao;
    }

    public WonderException setVersao(String versao) {
        this.versao = versao;
        return this;
    }

    public String getClasse() {
        return classe;
    }

    public WonderException setClasse(String classe) {
        this.classe = classe;
        return this;
    }

    public Date getDateHour() {
        return dateHour;
    }

    public WonderException setDateHour(Date dateHour) {
        this.dateHour = dateHour;
        return this;
    }

    public String getCausa() {
        return stacktrace;
    }

    public WonderException setCausa(String causa) {
        this.stacktrace = causa;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public WonderException setMessage(String message) {
        this.message = message;
        return this;
    }

    public String toJson() {
        return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss").create().toJson(this);
    }

}
