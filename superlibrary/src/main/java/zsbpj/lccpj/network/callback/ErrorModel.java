package zsbpj.lccpj.network.callback;

public class ErrorModel {

    public int mStatusCode;
    public String mMessage;

    public ErrorModel() {

    }

    public ErrorModel(int statusCode, String msg) {
        this.mStatusCode = statusCode;
        this.mMessage = msg;
    }

    public String getMessage() {
        return mMessage == null ? "" : mMessage;
    }

    public int getStatusCode() {
        return mStatusCode;
    }
}
