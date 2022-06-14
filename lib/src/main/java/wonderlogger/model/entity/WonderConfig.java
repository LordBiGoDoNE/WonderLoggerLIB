package wonderlogger.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "wonderconfig")
public class WonderConfig {

    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField()
    private String software;
    
    @DatabaseField()
    private String versao;
    
    public WonderConfig(String pSoftware, String pVersao) {
        this.software = pSoftware;
        this.versao = pVersao;
    }

    public WonderConfig() {
    }

    public String getSoftware() {
        return software;
    }

    public String getVersao() {
        return versao;
    }

}
