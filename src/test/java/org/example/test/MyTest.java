package org.example.test;

import lombok.extern.slf4j.Slf4j;
import org.example.test.service.RedisTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/2/11 15:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyTestApplication.class)
@Slf4j
public class MyTest {

    @Autowired
    private RedisTestService redisTestService;

    @Test
    public void test01() {
        MyThread myThread01 = new MyThread();
        myThread01.setName("myThread01");
        MyThread myThread02 = new MyThread();
        myThread02.setName("myThread02");
        MyThread myThread03 = new MyThread();
        myThread03.setName("myThread03");
        myThread01.start();
        myThread02.start();
        myThread03.start();

    }


    class MyThread extends Thread {
        @Override
        public void run() {
            redisTestService.deductStock2();
        }
    }
}
