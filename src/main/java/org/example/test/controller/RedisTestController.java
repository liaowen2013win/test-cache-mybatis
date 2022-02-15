package org.example.test.controller;

import org.apache.commons.lang3.StringUtils;
import org.example.test.model.entity.Organization;
import org.example.test.service.OrgService;
import org.example.test.service.RedisTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/2/10 14:59
 */
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTestService redisTestService;

    @Autowired
    private OrgService orgService;

    @GetMapping("/deduct_stock1")
    public String deductStock1() {
        // 相当于 jedis.get("stock")
        String stock1 = stringRedisTemplate.opsForValue().get("stock");
        if (StringUtils.isBlank(stock1)) {
            return "扣减失败，库存不足";
        }
        AtomicInteger stock = new AtomicInteger(Integer.parseInt(stock1));
        int realStock = 0;
        if (stock.get() > 0) {
            realStock = stock.decrementAndGet();
            // 相当于 jedis.set(key,value)
            stringRedisTemplate.opsForValue().set("stock", realStock + "");
            System.out.println("扣减成功，剩余库存：" + realStock + "");
        } else {
            System.out.println("扣减失败，库存不足");
        }
        return "扣减成功，剩余库存：" + realStock;
    }

    @GetMapping("/deduct_stock2")
    public String deductStock2() {
        return redisTestService.deductStock2();
    }

    @GetMapping("/getOrg")
    public Organization getOrg(Long orgId) {
        try {
            return orgService.findFromCacheByBizId(orgId, orgId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
