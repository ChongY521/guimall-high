package com.gulimall.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author aqiang9  2020-07-28
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {
    private final Set<Integer> validData = new HashSet<>();

    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.value();
        for (int value : values) {
            validData.add(value);
        }
    }
    /**
     * @param value 需要校验的值
     * @param context 上下文
     * @return 是否校验成功
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return validData.contains(value);
    }
}
