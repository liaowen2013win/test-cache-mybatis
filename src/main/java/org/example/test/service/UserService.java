package org.example.test.service;

import org.example.test.model.vo.UserInfoVO;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/27 18:50
 */
public interface UserService {

    UserInfoVO getUserById(Long userId);

    UserInfoVO getUserUpdateById(Long userId);
}
