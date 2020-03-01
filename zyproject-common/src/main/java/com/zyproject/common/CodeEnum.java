package com.zyproject.common;

/**
 *  状态码枚举
 */
public enum CodeEnum {
    SUCCESS(1000,"成功！"),
    FAIL(1001,"未知错误"),
    LOGINFAIL(1002,"用户名或密码错误，登录失败"),
    USERNOTEXIST(1003,"用户信息不存在"),
    TIMEOUT(1004,"会话超时"),
    NORIGHT(1005,"无权限"),
    NOTREE(1006,"未查找到对应的菜单"),
    NORECORDS(1007,"无记录"),
    USERTYPERROR(1008,"用户类型转换失败"),
    LOGINCODEREPEAT(1009,"登录名已存在"),
    FORMATJSONERROR(1010,"json字符格式化为实体时出错"),
    ARTICLETITLEISNULL(1011,"文章标题不能为空"),
    ARTICLECONTENTISNULL(1012,"文章内容不能为空"),
    ARTICLEKINDIDISNULL(1013,"文章分类不能为空"),
    SUPARTICLEKINDIDISNULL(1014,"文章上级分类不能为空，顶级分类请传0"),
    ARTICLEKINDNAMEISNULL(1015,"文章分类名称不能为空"),
    USERLOGINCODEISNULL(1016,"用户登录名不能为空"),
    USERNAMEISNULL(1017,"用户姓名不能为空"),
    USERPASSWORDISNULL(1018,"用户密码不能为空"),
    ROLENAMEISNULL(1019,"角色名称不能为空"),
    ;


    private int code; //状态码
    private String msg; //响应内容

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return  code;
    }

    public String getMsg(){
        return msg;
    }

    public static String getEnumDesc(Integer value) {
        CodeEnum[] businessModeEnums = values();
        for (CodeEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode() == value) {
                return businessModeEnum.getMsg();
            }
        }
        return null;
    }

    public static Integer getEnumCode(String value) {
        CodeEnum[] codeEnums = values();
        for (CodeEnum codeEnum : codeEnums) {
            if (codeEnum.getMsg() == value) {
                return codeEnum.getCode();
            }
        }
        return null;
    }

}
