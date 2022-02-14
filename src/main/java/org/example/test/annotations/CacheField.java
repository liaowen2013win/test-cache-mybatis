package org.example.test.annotations;

import java.lang.annotation.*;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/2/14 15:57
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheField {

    String name() default "";
}
