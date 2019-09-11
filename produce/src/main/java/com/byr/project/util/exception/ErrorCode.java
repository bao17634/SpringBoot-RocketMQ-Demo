
package com.byr.project.util.exception;

/**
 * @Author: yanrong
 * @Date: 2019/9/10 
 * @Version: 1.0
 */
public enum ErrorCode  implements IErrorCode{

    LOG_OUT(1000, "用户未登录或登录超时"),
    PARAMS_REQUIRED(1001, "必填参数不能为空"),
    RESULT_EMPTY(1002, "数据为空"),
    RESULT_ERROR(1003, "操作失败,请联系客服");

    private Integer errorCode;

    private String errorMsg;

    ErrorCode(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public Integer getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }
}
