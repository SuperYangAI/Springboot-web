package com.actec.ms.vo;

public enum AuthorityType {

    // 需要验证权限 默认
    VALIDATE,

    // 不验证
    NO_VALIDATE,

    //admin用户可用
    ADMIN;

    public static boolean requireAdmin(Authority authority) {
        return authority != null && authority.value() == AuthorityType.ADMIN;
    }

}