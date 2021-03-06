package org.example.test.cache_mybatis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/28 15:37
 */
public class RelativeCacheContext {

    /**
     * 存储全量缓存的映射关系
     */
    public static final Map<Class<?>, MyRelativeCache> MAPPER_CACHE_MAP = new ConcurrentHashMap<>();
    /**
     * 存储 Mapper 对应缓存 需要to更新缓存，但是此时 Mapper 对应缓存还未加载
     * 也就是 Class<?> 对应的缓存更新时，需要更新 List<RelativeCache> 中的缓存
     */
    public static final Map<Class<?>, List<MyRelativeCache>> UN_LOAD_TO_RELATIVE_CACHES_MAP = new ConcurrentHashMap<>();
    /**
     * 存储 Mapper 对应缓存 需要from更新缓存，但是在 加载 Mapper 缓存时，这些缓存还未加载
     * 也就是 List<RelativeCache> 中的缓存更新时，需要更新 Class<?> 对应的缓存
     */
    public static final Map<Class<?>, List<MyRelativeCache>> UN_LOAD_FROM_RELATIVE_CACHES_MAP = new ConcurrentHashMap<>();

    public static void putCache(Class<?> clazz, MyRelativeCache cache) {
        MAPPER_CACHE_MAP.put(clazz, cache);
    }

    public static void getCache(Class<?> clazz) {
        MAPPER_CACHE_MAP.get(clazz);
    }

}
