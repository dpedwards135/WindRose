package com.davidparkeredwards.windrosetools.wForm;

/**
 * Created by davidedwards on 6/8/17.
 */

public class DBResponse {

    private DbBody dbBody;
    private String message;
    private int code;

    public DBResponse(int code, DbBody dbBody, String message) {
        this.code = code;
        this.dbBody = dbBody;
        this.message = message;
    }

    public DbBody getDbBody() {
        return dbBody;
    }
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
