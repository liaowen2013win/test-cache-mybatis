package org.example.test.service;

import org.example.test.model.entity.Organization;
import org.example.test.reflect.BaseCacheService;

/**
 * @author liaowen
 * @date 2022/1/28 13:44
 */
public interface OrgService extends BaseCacheService<Organization> {

    /**
     * 更新
     *
     * @param orgId   组织id
     * @param orgName 组织名称
     * @return 新的对象
     */
    Organization updateOrgById(Long orgId, String orgName);
}
