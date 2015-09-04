package com.aomei.system.dao;

import com.aomei.dao.RoleMapper;
import com.aomei.dao.impl.BaseMapperImpl;
import com.aomei.model.Role;
import com.aomei.query.RoleQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/8/30.
 */
@Repository("roleMapper")
public class RoleMapperImpl extends BaseMapperImpl<Role,RoleQuery> implements RoleMapper{
    public List<Role> getRolesByUserId(Integer userId){
        return getSqlSession().selectList(getClassName() + ".getRolesByUserId", userId);
    }

    @Override
    public int deleteByPrimaryKeyArray(Integer[] ids) {
        return getSqlSession().update(getClassName()+".deleteByPrimaryKeyArray",ids);
    }
}
