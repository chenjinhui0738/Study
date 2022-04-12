package Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用户名称注解
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface UserName {
    String value() default "";
}
