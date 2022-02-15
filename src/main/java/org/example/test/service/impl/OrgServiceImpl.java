package org.example.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.example.test.common.Constant;
import org.example.test.mapper.OrganizationMapper;
import org.example.test.model.entity.Organization;
import org.example.test.reflect.BaseCacheServiceImpl;
import org.example.test.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织服务
 *
 * @author liaowen
 * @date 2022/1/28 13:55
 */
@Service
public class OrgServiceImpl extends BaseCacheServiceImpl<OrganizationMapper, Organization> implements OrgService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public Organization updateOrgById(Long orgId, String orgName) {
        Organization organization = organizationMapper.selectOneById(orgId);
        organization.setOrgName(orgName);
        organizationMapper.updateOrgById(organization);
        return organization;
    }

    @Override
    public List<Organization> getAllByScrollId(Long orgId) {
        LambdaQueryWrapper<Organization> organizationLambdaQueryWrapper = Wrappers.lambdaQuery();
        organizationLambdaQueryWrapper.eq(Organization::getOrgId, orgId);
        return baseMapper.selectList(organizationLambdaQueryWrapper);
    }

    @Override
    public String getCachePrefixKey(Long orgId) {
        return Constant.TEST_ORG_INFO_PREFIX_KEY + orgId;
    }

}
