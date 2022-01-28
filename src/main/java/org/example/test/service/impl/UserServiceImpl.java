package org.example.test.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.test.mapper.OrganizationMapper;
import org.example.test.mapper.UserMapper;
import org.example.test.model.entity.Organization;
import org.example.test.model.vo.UserInfoVO;
import org.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/27 18:51
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public UserInfoVO getUserById(Long userId) {
        UserInfoVO userInfoVO = userMapper.queryUserInfo(userId);
        return userInfoVO;
    }

    @Override
    public UserInfoVO getUserUpdateById(Long userId) {
        UserInfoVO userInfoVO = userMapper.queryUserInfo(userId);
        Organization organization = organizationMapper.selectOneById(userInfoVO.getOrgId());
        organization.setOrgName(organization.getOrgName() + "-1");
        organizationMapper.updateOrgById(organization);
        userInfoVO = userMapper.queryUserInfo(userId);
        return userInfoVO;
    }
}
