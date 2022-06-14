package wonderlogger.utils;

import com.google.gson.GsonBuilder;

public class GsonUtils {
    public String toJson() {
        return new GsonBuilder().setDateFormat("dd/MM/yyyy hh:mm:ss").create().toJson(this);
    }

}
