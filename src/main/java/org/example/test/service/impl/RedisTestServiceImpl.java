package org.example.test.service.impl;

import org.example.test.common.Constant;
import org.example.test.service.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/2/11 9:58
 */
@Service
public class RedisTestServiceImpl implements RedisTestService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String deductStock2() {
        // 库存校验，基于redis本身的原子性来保证
        Long realStock = stringRedisTemplate.opsForHash().increment(Constant.REDIS_KEY_STOCK_HASH, Constant.REDIS_KEY_STOCK, -1);
        if (realStock > 0) {
            System.out.println("扣减成功，剩余库存：" + realStock + "");
        } else {
            System.out.println("扣减失败，库存不足");
        }
        return "扣减成功，剩余库存：" + realStock;
    }
}
