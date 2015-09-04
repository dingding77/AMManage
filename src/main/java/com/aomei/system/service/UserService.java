package com.aomei.system.service;

import com.aomei.model.Menu;
import com.aomei.model.Role;
import com.aomei.model.User;

import java.util.List;

/**
 * Created by Administrator on 2015/6/21.
 */
public interface UserService {
    /**
     * 用户登录接口
     * @param user
     * @return
     * @throws Exception
     */
    public User login(User user) throws Exception;

    /**
     * 获取用户拥有的角色
     * @param user
     * @return
     * @throws Exception
     */
    public List<Role> getUserRoles(User user) throws Exception;

    /**
     * 获取用户拥有的权限菜单
     * @param user
     * @return
     * @throws Exception
     */
    public List<Menu> getUserMenus(User user) throws Exception;
}
