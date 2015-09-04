package com.aomei.dao;

import com.aomei.model.Role;
import com.aomei.query.RoleQuery;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role,RoleQuery>{
    /**
     * 根据用户ID获取角色信息
     * @param userId
     * @return
     */
    public List<Role> getRolesByUserId(Integer userId);
}