package org.example.test.reflect;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.function.Function;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/2/14 14:38
 */
public class BaseServiceImpl<M extends BaseTestMapper<T>, T> implements BaseService<T> {

    protected Log log = LogFactory.getLog(this.getClass());

    @Autowired
    protected M baseMapper;

    protected Class<?> entityClass = this.currentModelClass();

    @Override
    public BaseMapper<T> getBaseMapper() {
        return this.baseMapper;
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), 1);
    }

    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(this.entityClass).getSqlStatement(sqlMethod.getMethod());
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        return throwEx ? this.baseMapper.selectOne(queryWrapper) : SqlHelper.getObject(this.log, this.baseMapper.selectList(queryWrapper));
    }

    @Override
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(this.log, this.baseMapper.selectMaps(queryWrapper));
    }

    @Override
    public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return SqlHelper.getObject(this.log, this.listObjs(queryWrapper, mapper));
    }

}
