package org.example.test.annotations;

import java.lang.annotation.*;

/**
 * 缓存-key
 *
 * @author liaowen
 * @date 2022/2/14 15:57
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheKey {

    String name() default "";
}
