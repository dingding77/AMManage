package com.aomei.system.dao;

import com.aomei.dao.RoleResourcesMapper;
import com.aomei.dao.impl.BaseMapperImpl;
import com.aomei.model.RoleResources;
import com.aomei.query.RoleResourcesQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("roleResourcesMapper")
public class RoleResourcesMapperImpl extends BaseMapperImpl<RoleResources,RoleResourcesQuery> implements RoleResourcesMapper {
    public int batchInsert(List<RoleResources> list){
        return getSqlSession().insert(getClassName()+".batchInsert",list);
    }
    public int updateDeleteFlag(Map<String,Object> map){
        return getSqlSession().update(getClassName()+".updateDeleteFlag",map);
    }
}