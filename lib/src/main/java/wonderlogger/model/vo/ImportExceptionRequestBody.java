package wonderlogger.model.vo;

import com.google.gson.annotations.SerializedName;
import wonderlogger.model.entity.WonderConfig;
import wonderlogger.model.entity.WonderException;

import java.util.List;

public class ImportExceptionRequestBody {
    private String software;
    private String version;
    @SerializedName("wonderExceptions")
    private List<WonderException> wonderExceptions;

    public ImportExceptionRequestBody(WonderConfig pWonderConfig, List<WonderException> pWonderExceptions) {
        this.software = pWonderConfig.getSoftware();
        this.version = pWonderConfig.getVersao();
        this.wonderExceptions = pWonderExceptions;
    }

    public String getSoftware() {
        return software;
    }

    public String getVersion() {
        return version;
    }

    public List<WonderException> getWonderExceptions() {
        return wonderExceptions;
    }
}
