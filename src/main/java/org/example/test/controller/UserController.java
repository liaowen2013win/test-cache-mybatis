package org.example.test.controller;

import org.example.test.model.vo.UserInfoVO;
import org.example.test.response.ApiResponse;
import org.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/28 10:30
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/getUserById")
    public ApiResponse<UserInfoVO> getUserById(Long userId) {
        UserInfoVO userInfoVO = userService.getUserById(userId);
        return ApiResponse.ofSuccess(userInfoVO);
    }
}
