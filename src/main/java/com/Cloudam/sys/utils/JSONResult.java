package com.Cloudam.sys.utils;

/**
 * @Author: Cloudam
 * @Description:
 * @Date:14:18星期六
 */
public class JSONResult {
    /**
     * 用来封装结果的是否成功
     */

    private Boolean success;

    /**
     * 执行后的返回值
     */

    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONResult() {
    }

    public JSONResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
