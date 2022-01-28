package org.example.test.service.impl;

import org.example.test.mapper.OrganizationMapper;
import org.example.test.model.entity.Organization;
import org.example.test.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/28 13:55
 */
@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public Organization updateOrgById(Long orgId, String orgName) {
        Organization organization = organizationMapper.selectOneById(orgId);
        organization.setOrgName(orgName);
        organizationMapper.updateOrgById(organization);
        return organization;
    }
}
