package com.luongnv.practice.annotation;

import java.lang.annotation.Documented;

@Documented
public @interface MostUsed {

    String value() default "java";
}
