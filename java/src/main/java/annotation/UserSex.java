package annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用户性别注解
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface UserSex {
    /**
     * 性别枚举
     */
    public enum Sex {MAN, WOMAN}

    ;

    /**
     * 性别属性
     */
    Sex userSex() default Sex.MAN;
}