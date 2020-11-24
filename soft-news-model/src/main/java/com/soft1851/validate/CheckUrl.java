package com.soft1851.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName CheckUrl.java
 * @Description 自定义链接检查注解
 * @createTime 2020年11月24日 11:25:00
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUrlValidate.class)
public @interface CheckUrl {
    String message() default "url不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
