package wonderlogger.vo;

public class PostResponse {
    private boolean sucess;
    private String response;
    private Throwable exception;

    public PostResponse(boolean sucess, String response) {
        this.sucess = sucess;
        this.response = response;
    }

    public PostResponse(boolean sucess, Throwable exception) {
        this.sucess = sucess;
        this.exception = exception;
    }

    public boolean isSucess() {
        return sucess;
    }

    public String getResponse() {
        return response;
    }

    public Throwable getException() {
        return exception;
    }
}
