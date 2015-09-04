package com.aomei.system.dao;

import com.aomei.dao.MenuMapper;
import com.aomei.dao.impl.BaseMapperImpl;
import com.aomei.model.Menu;
import com.aomei.model.Role;
import com.aomei.query.MenuQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单
 * @param
 */
@Repository("menuMapper")
public class MenuMapperImpl extends BaseMapperImpl<Menu,MenuQuery> implements MenuMapper{
    public List<Menu> selectMenu(){
        return getSqlSession().selectList(getClassName()+".queryAll");
    }

    /**
     * 根据角色获取当前角色拥有的菜单权限
     * @param roles
     * @return
     */
    public List<Menu> getMenusByRoles(List<Role> roles) {
        return getSqlSession().selectList(getClassName()+".getMenusByRoles", roles);
    }
}
