package com.example.janche.common.restResult;

/**
 * @author lirong
 * @ClassName: ResultCode
 * @Description: 返回的状态码
 * @date 2018-12-03 9:26
 */

public enum ResultCode {

    /**
     * HTTP通信使用
     */
    SUCCESS(200, "SUCCESS"),

    ERROR(500, "服务器内部错误"),

    NOT_FOUND(404, "接口不存在"),

    FAIL(400, "接口异常,请联系管理员"),

    UNAUTHORIZED(401, "未认证（签名错误）"),

    /**
     * security
     */
    LOGIN_ERROR(-990, "登录失败"),

    OVER_MAX_LOGIN_NUM(-991, "当前在线人数过多，请稍后登录"),

    REFRESH_TOKEN_EXPIRED(-997, "用户 刷新令牌过期"),

    LIMITED_AUTHORITY(-1000, "权限不够"),

    UNLOGIN(-999, "用户未登录或登录已失效"),

    TOKEN_ILLEGAL(-996, "用户令牌不合法"),

    TOKEN_EXPIRED(-998, "用户 普通令牌过期"),

    IP_NOT_ALLOW(-980, "登录的IP不被允许"),

    /**
     * oauth2
     */
    MISSING_CLIENT_INFO(-1009, "客户端注册信息缺失"),

    CLIENT_ID_ALREADY_EXIST(-1010, "client_id已存在"),

    /**
     * 导入导出
     */
    DATA_IS_NULL(4901, "请选择数据导出"),

    IMPORT_FAIL(4902, "未知错误，导入失败"),


//===========  系统管理
    /**
     * 用户管理
     */

    OLD_PASSWORD_ERROR(2002, "原密码错误"),

    PASSWORD_ERROR(2003, "密码错误"),

    RESET_PASSWORD_ERROR(2004, "重置密码失败"),

    USER_NOT_EXIST(2005, "用户不存在"),

    ;



    public int code;

    public String message;

    ResultCode (int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
