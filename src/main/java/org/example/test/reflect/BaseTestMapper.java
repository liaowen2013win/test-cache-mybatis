package org.example.test.reflect;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/2/14 14:34
 */
public interface BaseTestMapper<T> extends BaseMapper<T> {
    /**
     * 批量插入，仅适用于MySQL
     *
     * @param list 实体集合
     * @return 影响的行数
     */
    Integer insertBatchSomeColumn(Collection<T> list);
}
