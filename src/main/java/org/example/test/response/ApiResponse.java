package org.example.test.response;

import lombok.Data;

/**
 * @author liaowen
 */
@Data
public class ApiResponse<T> implements java.io.Serializable {
    private int code;
    private String message;
    private T data;

    public ApiResponse() {

    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse ofFailed(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static ApiResponse ofFailed(ResponseStatus responseStatus) {
        return new ApiResponse(responseStatus.code, responseStatus.msg, null);
    }

    public static <T> ApiResponse ofSuccess(T data) {
        return new ApiResponse<T>(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), data);
    }

    public enum ResponseStatus {
        SUCCESS(200, "OK");

        private int code;
        private String msg;

        ResponseStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

    }
}
