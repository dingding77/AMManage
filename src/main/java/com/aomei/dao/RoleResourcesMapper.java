package com.aomei.dao;

import com.aomei.model.RoleResources;
import com.aomei.query.RoleResourcesQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/5.
 */
public interface RoleResourcesMapper extends BaseMapper<RoleResources,RoleResourcesQuery>{
    public int batchInsert(List<RoleResources> list);
    public int updateDeleteFlag(Map<String,Object> map);
}
