package org.example.test.service;

import org.example.test.model.entity.Organization;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/28 13:44
 */
public interface OrgService {

    Organization updateOrgById(Long orgId, String orgName);
}
