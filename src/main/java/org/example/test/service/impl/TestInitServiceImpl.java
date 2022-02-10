package org.example.test.service.impl;

import org.example.test.service.TestInitService;
import org.springframework.stereotype.Service;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/2/10 11:47
 */
@Service
public class TestInitServiceImpl implements TestInitService {
    @Override
    public void initOut() {
        System.out.println("MyTestInitThread run ===========");
    }
}
