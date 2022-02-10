package org.example.test.service.impl;

import org.example.test.common.Constant;
import org.example.test.service.TestInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/2/10 11:47
 */
@Service
public class TestInitServiceImpl implements TestInitService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void initOut() {
        System.out.println("MyTestInitThread run ===========");
        stringRedisTemplate.opsForValue().set(Constant.REDIS_KEY_STOCK, 10 + "");
        stringRedisTemplate.opsForHash().put(Constant.REDIS_KEY_STOCK_HASH, Constant.REDIS_KEY_STOCK, 10 + "");
    }
}
