package com.example.carrentalcontract.entity.en;

public enum ResponseCode {
    // 系统模块
    SUCCESS(200, "操作成功"),
    ERROR(600, "操作失败"),

    //web
    WEB_400(400,"错误请求"),
    WEB_401(401,"访问未得到授权"),
    WEB_403(403,"拒绝访问"),
    WEB_404(404,"资源未找到"),
    WEB_500(500,"服务器内部错误"),
    WEB_UNKOWN(999,"未知错误"),

    //login && access
    LOGIN_SUCCESS(200, "登录成功"),
    LOGIN_FAIL(601, "账户或密码错误"),
    ACCESS_DENIED(602, "无权访问"),
    //parameter
    ARG_TYPE_MISMATCH(1000,"参数类型错误"),
    ARG_BIND_EXCEPTION(1001,"参数绑定错误"),
    ARG_VIOLATION(1002,"参数不符合要求"),
    ARG_MISSING(1003,"参数未找到"),

    //sign error
    SIGN_NO_APPID(10001, "appId不能为空"),
    SIGN_NO_TIMESTAMP(10002, "timestamp不能为空"),
    SIGN_NO_SIGN(10003, "sign不能为空"),
    SIGN_NO_NONCE(10004, "nonce不能为空"),
    SIGN_TIMESTAMP_INVALID(10005, "timestamp无效"),
    SIGN_DUPLICATION(10006, "重复的请求"),
    SIGN_VERIFY_FAIL(10007, "sign签名校验失败"),
    ;

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    private String msg;
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String toString(){
        return "code:"+code+";msg:"+msg;
    }
}
