package com.actec.ms.vo;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)
@Documented
public @interface Authority {  
    // 默认验证
    AuthorityType value() default AuthorityType.VALIDATE;

}  