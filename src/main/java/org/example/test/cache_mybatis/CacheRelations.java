package org.example.test.cache_mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/28 15:42
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRelations {

    // from中mapper class对应的缓存更新时，需要更新当前注解标注mapper的缓存
    Class<?>[] from() default {};

    // 当前注解标注mapper的缓存更新时，需要更新to中mapper class对应的缓存
    Class<?>[] to() default {};

}
