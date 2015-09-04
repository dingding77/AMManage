package com.aomei.dao;

import com.aomei.model.Menu;
import com.aomei.model.Role;
import com.aomei.query.MenuQuery;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu,MenuQuery> {
    /**
     * 获取所有菜单信息
     * @return
     * @throws DataAccessException
     */
    public List<Menu> selectMenu()  throws DataAccessException;

    /**
     *根据角色获取菜单信息
     * @param roles
     * @return
     * @throws DataAccessException
     */
    public List<Menu> getMenusByRoles(List<Role> roles) throws DataAccessException;
}