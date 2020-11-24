package com.soft1851.validate;

import com.soft1851.common.utils.UrlUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName CheckUrlValidate.java
 * @Description TODO
 * @createTime 2020年11月24日 11:30:00
 */
public class CheckUrlValidate implements ConstraintValidator<com.soft1851.validate.CheckUrl, String> {

    @Override
    public boolean isValid(String url, ConstraintValidatorContext constraintValidatorContext) {
        return UrlUtil.verifyUrl(url.trim());
    }
}
