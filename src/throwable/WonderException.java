package throwable;

import com.google.gson.Gson;

import java.util.Date;

public class WonderException {
    
    private String classe;
    private Date dataHora;
    private String causa;
    private String message;

    public WonderException(String classe, Date dataHora, String causa, String message) {
        this.classe = classe;
        this.dataHora = dataHora;
        this.causa = causa;
        this.message = message;
    }

    public WonderException() {
    }
    
    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson(){
        return new Gson().toJson(this);
    }
    
}
