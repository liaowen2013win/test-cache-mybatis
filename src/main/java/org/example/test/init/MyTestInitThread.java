package org.example.test.init;

import org.example.test.service.TestInitService;
import org.example.test.utils.MySpringContextUtil;

/**
 * 启动初始化线程
 *
 * @author liaowen
 * @date 2022/2/10 11:35
 */
public class MyTestInitThread extends Thread {

    @Override
    public void run() {
        TestInitService testInitService = (TestInitService) MySpringContextUtil.getBean("testInitServiceImpl");
        testInitService.initOut();
    }
}
