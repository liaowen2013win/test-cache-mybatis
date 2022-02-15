package org.example.test.utils;

import com.google.common.collect.Lists;
import org.example.test.common.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地缓存工具
 *
 * @author liaowen
 * @date 2022/2/14 15:48
 */
public class LocalCacheUtil {


    private static ThreadLocal<Map> localCache = new ThreadLocal<>();

    /**
     * 支持二级缓存的线程
     */
    private static List<String> threadNames = Lists.newArrayList("import-pool", "http-nio", "open-pool");

    /**
     * 检测是否支持二级缓存
     *
     * @return
     */
    private static boolean check() {
        boolean flag = false;
        String threadName = Thread.currentThread().getName();
        for (String name : threadNames) {
            if (threadName.contains(name)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 清空二级缓存
     */
    public static void clear() {
        localCache.remove();
    }

    /**
     * 获取缓存值
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        Object result = null;
        if (check()) {
            Map map = localCache.get();
            if (map != null) {
                result = map.get(key);
            }
        }
        return result;
    }

    /**
     * 移出缓存值
     *
     * @param key
     */
    public static void remove(String key) {
        if (check()) {
            Map map = localCache.get();
            if (map != null) {
                map.remove(key);
                localCache.set(map);
            }
        }
    }

    /**
     * 设置缓存值
     *
     * @param key
     * @param obj
     * @return
     */
    public static Object put(String key, Object obj) {
        if (check()) {
            Map map = localCache.get();
            if (map == null) {
                map = new HashMap();
            }
            map.put(key, obj);
            localCache.set(map);
        }
        return obj;
    }

    public static Map getAll() {
        if (check()) {
            return localCache.get();
        }
        return null;
    }

    public static void setAll(Map map) {
        if (check()) {
            localCache.set(map);
        }
    }

    public static void removeBizCache(Object testId, Object languageId) {
        LocalCacheUtil.remove(Constant.LOCAL_ITEM_ALL_MAP + testId);
    }

    public static void addCacheThread(String threadName) {
        threadNames.add(threadName);
    }
}
