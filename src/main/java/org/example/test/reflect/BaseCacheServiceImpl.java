package org.example.test.reflect;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.example.test.annotations.CacheField;
import org.example.test.annotations.CacheKey;
import org.example.test.utils.LocalCacheUtil;
import org.example.test.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 缓存实现
 *
 * @author liaowen
 * @date 2022/2/14 15:40
 */
public abstract class BaseCacheServiceImpl<M extends BaseTestMapper<T>, T> extends BaseServiceImpl implements BaseCacheService<T> {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    protected M baseMapper;

    @Override
    public T findFromCacheByBizId(Long bizId, Long scrollId) throws Exception {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Map<Object, T> data = getData(scrollId, tClass);
        return data.get(String.valueOf(bizId));
    }

    /**
     * 缓存的处理（特定线程走二级缓存）
     *
     * @param scrollId
     * @param tClass
     * @return
     * @throws Exception
     */
    private Map<Object, T> getData(Long scrollId, Class<T> tClass) throws Exception {
        Map<Object, T> hmget = (Map<Object, T>) LocalCacheUtil.get(tClass.getSimpleName() + scrollId);
        if (hmget == null) {
            long l = System.currentTimeMillis();
            checkCache(scrollId);
            log.debug(tClass.getSimpleName() + scrollId + "检测时间: " + (System.currentTimeMillis() - l));
            long l1 = System.currentTimeMillis();
            hmget = redisUtil.hmget(getCachePrefixKey(scrollId), tClass);
            log.debug(tClass.getSimpleName() + scrollId + "hash时间: " + (System.currentTimeMillis() - l1));
            LocalCacheUtil.put(tClass.getSimpleName() + scrollId, hmget);
        }
        return hmget;
    }

    @Override
    public Map<Object, T> findAllMapFromCacheByTestId(Long scrollId) throws Exception {
        Long l = System.currentTimeMillis();
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Map<Object, T> hmget = getData(scrollId, tClass);
        return hmget;
    }

    @Override
    public List<T> findAllListFromCacheByTestId(Long scrollId) throws Exception {
        List<T> list = Lists.newArrayList();
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Map<Object, T> hmget = getData(scrollId, tClass);
        if (CollectionUtil.isNotEmpty(hmget)) {
            list = new ArrayList<>(hmget.values());
        }
        return list;
    }

    @Override
    public void checkCache(Long scrollId) throws Exception {
        if (!redisUtil.hasKey(getCachePrefixKey(scrollId))) {
            // redis中不存在key,从数据库中重新加载
            List<T> allByTestId = getAllByTestIdWithCache(scrollId);
            writeBatchToRedis(allByTestId);
        }
        // todo 如果上一步判断缓存存在,这个时候整个缓存都被删除了,会出问题
    }

    private void writeBatchToRedis(List<T> list) throws Exception {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        T tmp = list.get(0);
        Class<?> tClass = tmp.getClass();
        CacheKey cacheKey = AnnotationUtils.getAnnotation(tClass, CacheKey.class);
        Field keyField = ReflectionUtils.findField(tClass, cacheKey.name());
        ReflectionUtils.makeAccessible(keyField);
        Object key = ReflectionUtils.getField(keyField, tmp);
        CacheField cacheField = AnnotationUtils.getAnnotation(tClass, CacheField.class);
        Field itemField = ReflectionUtils.findField(tClass, cacheField.name());
        ReflectionUtils.makeAccessible(itemField);
        Map<String, Object> listMap = Maps.newHashMap();
        for (T t1 : list) {
            Object field = ReflectionUtils.getField(itemField, t1);
            listMap.put(String.valueOf(field), t1);
        }
        boolean result = false;
        try {
            result = redisUtil.hmset(getCachePrefixKey((Long) key), listMap, 24 * 3600);
        } catch (Exception e) {
            log.error("设置缓存失败!", e);
        }
        if (!result) {
            throw new Exception("初始化缓存失败,请重试");
        }

    }

    public abstract List<T> getAllByTestId(Long scrollId);

    private List<T> getAllByTestIdWithCache(Long scrollId) {
        List<T> all = getAllByTestId(scrollId);

        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        CacheField cacheField = AnnotationUtils.getAnnotation(tClass, CacheField.class);
        Field itemField = ReflectionUtils.findField(tClass, cacheField.name());
        ReflectionUtils.makeAccessible(itemField);
        Map<String, Object> listMap = Maps.newHashMap();
        for (T t1 : all) {
            Object field = ReflectionUtils.getField(itemField, t1);
            listMap.put(String.valueOf(field), t1);
        }
        LocalCacheUtil.put(tClass.getSimpleName() + scrollId, listMap);
        return all;
    }

    @Override
    public Callable<List<T>> getAllByTestId(Long scrollId, CountDownLatch countDownLatch) {
        return new Callable<List<T>>() {
            @Override
            public List<T> call() {
                long l = System.currentTimeMillis();
                try {
                    return getAllByTestIdWithCache(scrollId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    log.debug("执行耗时间：" + (System.currentTimeMillis() - l));
                    countDownLatch.countDown();
                }
                return new ArrayList<>();
            }
        };
    }

    @Override
    public Callable<Map<Object, T>> getAllFromCacheByTestId(Long scrollId, CountDownLatch countDownLatch) {
        return new Callable<Map<Object, T>>() {
            @Override
            public Map<Object, T> call() {
                long l = System.currentTimeMillis();
                try {
                    return findAllMapFromCacheByTestId(scrollId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    log.debug("执行耗时间----：" + (System.currentTimeMillis() - l));
                    countDownLatch.countDown();
                }
                return new HashMap<>();
            }
        };
    }

    public abstract String getCachePrefixKey(Long scrollId);

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    @Override
    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

}
