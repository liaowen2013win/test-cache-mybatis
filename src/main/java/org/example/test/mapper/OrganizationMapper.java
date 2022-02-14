package org.example.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.example.test.cache_mybatis.MyRelativeCache;
import org.example.test.model.entity.Organization;
import org.example.test.reflect.BaseTestMapper;
import org.springframework.stereotype.Repository;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/27 18:46
 */
@Repository
@CacheNamespace(implementation = MyRelativeCache.class, eviction = MyRelativeCache.class, flushInterval = 30 * 60 * 1000)
public interface OrganizationMapper extends BaseTestMapper<Organization> {

    Organization selectOneById(@Param("orgId") Long orgId);

    Integer updateOrgById(Organization organization);
}
