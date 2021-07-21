package com.example.api.network.exception;

/**
 * author : Cong
 * date   : 2021-07-01
 * time   : 17:46
 * whats the fst
 */
public class ResponseException extends Exception {
    private static final long serialVersionUID = 6044933668797788698L;
    private String errorCode;

    public ResponseException() {
        super();
    }

    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseException(Throwable cause) {
        super(cause);
    }

    public ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String errorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
