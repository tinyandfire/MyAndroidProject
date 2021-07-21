package com.example.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * author : Cong
 * date   : 2021-07-01
 * time   : 17:50
 * whats the fst
 */
public class NetworkResponse<T> {
    public NetworkResponse() {
    }

    public NetworkResponse(T result) {
        this.success = true;
        this.result = result;
    }

    public NetworkResponse(String errorCode, String errorMsg) {
        this.success = false;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * {
     * "errorCode": "008013",
     * "errorMsg": "不可以给自己进行评价哦",
     * "errorTitle": "出错了",
     * }
     */

    @SerializedName("success")
    private boolean success;

    @SerializedName("result")
    private T result;

    @SerializedName("errorCode")
    private String errorCode;
    @SerializedName("errorMsg")
    private String errorMsg;
    @SerializedName("errorTitle")
    private String errorTitle;
    @SerializedName("timestamp")
    private long timestamp;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
