package wonderlogger.model.vo;

import wonderlogger.model.entity.WonderConfig;
import wonderlogger.model.entity.WonderException;

import java.util.List;

public class APIRequest {
    private String software;
    private String version;
    private List<WonderException> wonderExceptions;

    public APIRequest(WonderConfig pWonderConfig, List<WonderException> pWonderExceptions) {
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
