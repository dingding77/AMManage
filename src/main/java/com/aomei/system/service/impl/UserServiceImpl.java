package com.aomei.system.service.impl;

import com.aomei.dao.MenuMapper;
import com.aomei.dao.RoleMapper;
import com.aomei.model.Menu;
import com.aomei.model.Role;
import com.aomei.model.User;
import com.aomei.system.dao.UserMapperImpl;
import com.aomei.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/21.
 */
@Service("userService")
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapperImpl userMapper;
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RoleMapper roleMapper;
    @Override
    public User login(User user) throws Exception {
        return userMapper.findUser(user);
    }

    @Override
    public List<Role> getUserRoles(User user) throws Exception {
        List<Role> list=null;
        //获取用户信息
        user= userMapper.selectByPrimaryKey(user.getId());
        if(user!=null){
        //获取用户角色
            list=roleMapper.getRolesByUserId(user.getId());
        }
        return list;
    }

    @Override
    public List<Menu> getUserMenus(User user) throws Exception {
        List<Role> roles=getUserRoles(user);
        if(roles.size()>0){
            return menuMapper.getMenusByRoles(roles);
        }else{
            return new ArrayList<Menu>();
        }
    }
}
