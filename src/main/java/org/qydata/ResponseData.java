package org.qydata;

/**
 * Created by jonhn on 2016/11/11.
 */

public class ResponseData {


    public class ResponseData_result {
        private int resultCode;

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }

    }
    private Integer code;
    private String message;
    private ResponseData_result result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseData_result getResult() {
        return result;
    }

    public void setResult(ResponseData_result result) {
        this.result = result;
    }
}
