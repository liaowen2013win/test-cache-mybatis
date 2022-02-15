package org.example.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.example.test.cache_mybatis.CacheRelations;
import org.example.test.cache_mybatis.MyRelativeCache;
import org.example.test.model.entity.UserEntity;
import org.example.test.model.vo.UserInfoVO;
import org.example.test.reflect.BaseTestMapper;
import org.springframework.stereotype.Repository;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/27 18:39
 */
@Repository
//@CacheNamespace(implementation = MyRelativeCache.class, eviction = MyRelativeCache.class, flushInterval = 30 * 60 * 1000)
//@CacheRelations(from = OrganizationMapper.class)
public interface UserMapper extends BaseTestMapper<UserEntity> {

    UserInfoVO queryUserInfo(@Param("userId") Long userId);

    Integer updateUserById(UserEntity userEntity);

}
