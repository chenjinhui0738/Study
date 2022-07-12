package annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用户账号
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface UserAccount {
    /**
     * 用户id
     */
    public int id() default -1;

    /**
     * 账号
     */
    public String account() default "";

    /**
     * 密码
     */
    public String password() default "";
}
