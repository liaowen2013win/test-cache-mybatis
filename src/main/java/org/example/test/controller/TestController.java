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
 * @date 2022/1/28 15:13
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserById")
    public ApiResponse<UserInfoVO> getUserById(Long userId) {
        UserInfoVO userInfoVO = userService.getUserUpdateById(userId);
        return ApiResponse.ofSuccess(userInfoVO);
    }

}
