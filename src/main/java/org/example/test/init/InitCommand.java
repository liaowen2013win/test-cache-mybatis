package org.example.test.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 程序启动初始化
 *
 * @author liaowen
 * @date 2022/2/10 11:33
 */
@Component
@Order(value = 1)
public class InitCommand implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        MyTestInitThread myTestInitThread = new MyTestInitThread();
        myTestInitThread.start();
    }
}
