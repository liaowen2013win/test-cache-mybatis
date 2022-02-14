package org.example.test.service;

import org.example.test.model.entity.Organization;
import org.example.test.reflect.BaseCacheService;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/28 13:44
 */
public interface OrgService extends BaseCacheService<Organization> {

    Organization updateOrgById(Long orgId, String orgName);
}
