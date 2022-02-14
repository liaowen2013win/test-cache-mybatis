package org.example.test.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 工具类：Redis操作
 * 基于spring和redis的redisTemplate工具类。hash都是以h开头的方法，set都是以s开头的方法，list都是以l开头的方法
 *
 * @author liaowen
 * @date 2022/2/14 15:11
 */
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 查询key
     *
     * @param pattern
     * @return
     */
    public Set<String> getKeys(String pattern) throws Exception {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) throws Exception {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param date 时间戳
     * @return
     */
    public boolean expireAt(String key, Date date) throws Exception {
        try {
            return redisTemplate.expireAt(key, date);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) throws Exception {
        try {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) throws Exception {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) throws Exception {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    redisTemplate.delete(key[0]);
                } else {
                    redisTemplate.delete(CollectionUtils.arrayToList(key));
                }
            }
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void delByKey(String... key) throws Exception {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    redisTemplate.delete(key[0]);
                } else {
                    redisTemplate.delete(CollectionUtils.arrayToList(key));
                }
            }
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }
    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) throws Exception {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return T
     */
    public <T> T get(String key, Class<T> tClass) throws Exception {
        try {
            return key == null ? null : (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) throws Exception {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) throws Exception {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 设置键的值，仅当键不存在时
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean setIfAbsent(String key, Object value, long time) throws Exception {
        boolean setIfAbsentRet = true;
        try {
            setIfAbsentRet = redisTemplate.opsForValue().setIfAbsent(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return setIfAbsentRet;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) throws Exception {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) throws Exception {
        try {
            return redisTemplate.opsForValue().increment(key, -delta);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) throws Exception {
        try {
            return redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return T
     */
    public <T> T hget(String key, String item, Class<T> tClass) throws Exception {
        try {
            return (T) redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) throws Exception {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public <T> Map<Object, T> hmget(String key, Class<T> tClass) throws Exception {
        Map<Object, T> resultMap = new HashMap<>();
        try {
            Optional.ofNullable(redisTemplate.opsForHash().entries(key))
                    .ifPresent(objectObjectMap -> {
                        objectObjectMap.forEach((o, o2) -> {
                            resultMap.put(o, (T) o2);
                        });
                    });
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
        return resultMap;
    }


    /**
     * 获取hashKey 和 field集合对应的所有键值
     * 注意：如果域的值为 null也会返回，
     *
     * @param key
     * @param fields 域集合
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> Map<Object, T> hmget(String key, List fields, Class<T> tClass) throws Exception {
        Map<Object, T> resultMap = new HashMap<>();
        try {
            List fieldStrs = (List) fields.stream().map(s -> s.toString()).collect(Collectors.toList());
            List<T> fieldValues = redisTemplate.opsForHash().multiGet(key, fieldStrs);
            Stream.iterate(0, i -> i + 1).limit(fields.size()).forEach(i -> {
                resultMap.put(fields.get(i), fieldValues.get(i));
            });
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
        return resultMap;
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) throws Exception {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) throws Exception {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) throws Exception {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) throws Exception {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) throws Exception {
        try {
            redisTemplate.opsForHash().delete(key, item);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) throws Exception {
        try {
            return redisTemplate.opsForHash().hasKey(key, item);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key        键
     * @param item       项
     * @param by(double) 要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) throws Exception {
        try {
            return redisTemplate.opsForHash().increment(key, item, by);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key      键
     * @param item     项
     * @param by(long) 要增加几(大于0)
     * @return
     */
    public long hincr(String key, String item, long by) throws Exception {
        try {
            return redisTemplate.opsForHash().increment(key, item, by);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) throws Exception {
        try {
            return redisTemplate.opsForHash().increment(key, item, -by);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) throws Exception {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) throws Exception {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) throws Exception {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) throws Exception {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) throws Exception {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) throws Exception {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) throws Exception {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) throws Exception {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1
     *              第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) throws Exception {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) throws Exception {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) throws Exception {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) throws Exception {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) throws Exception {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * Removes and returns first element in list
     *
     * @param key
     * @return
     */
    public Object lPop(String key) throws Exception {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) throws Exception {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) throws Exception {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * zset的add
     *
     * @param key   键
     * @param score 分数
     * @param value 值
     * @return
     */
    public boolean zSetAdd(String key, Object value, double score) throws Exception {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * zset的remove
     *
     * @param key    键
     * @param values 值
     * @return 移除的个数
     */
    public Long zSetRemove(String key, Object... values) throws Exception {
        try {
            return redisTemplate.opsForZSet().remove(key, values);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * zset的Range，查询从下标从start到end的元素  end 为-1 是最后一个值
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return
     */
    public Set<Object> zSetRange(String key, long start, long end) throws Exception {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * zset的RanK 查询某个元素的下标
     *
     * @param key   键
     * @param value
     * @return
     */
    public Long zSetRanK(String key, Object value) throws Exception {
        try {
            return redisTemplate.opsForZSet().rank(key, value);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * zset的score 查询某个元素的score
     *
     * @param key   键
     * @param value
     * @return
     */
    public Double zSetScore(String key, Object value) throws Exception {
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

    /**
     * zset的zincrby 增加score
     *
     * @param key   键
     * @param score 分数
     * @param value 值
     * @return
     */
    public Double zSetIncrSocre(String key, Object value, double score) throws Exception {
        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, score);
        } catch (Exception e) {
            throw new Exception("0004", e);
        }
    }

}
