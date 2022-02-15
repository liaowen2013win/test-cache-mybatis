package org.example.test.reflect;

import org.example.test.utils.RedisUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/2/14 14:37
 */
public interface BaseCacheService<T> extends BaseService {

    T findFromCacheByBizId(Long bizId, Long testId) throws Exception;

    Map<Object, T> findAllMapFromCacheByTestId(Long testId) throws Exception;

    List<T> findAllListFromCacheByTestId(Long testId) throws Exception;

    void checkCache(Long testId) throws Exception;

    RedisUtil getRedisUtil();

    Callable<List<T>> getAllByTestId(Long testId, CountDownLatch countDownLatch);

    Callable<Map<Object, T>> getAllFromCacheByTestId(Long testId, CountDownLatch countDownLatch);


}
