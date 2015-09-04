package com.aomei.system.dao;

import com.aomei.dao.UserMapper;
import com.aomei.dao.impl.BaseMapperImpl;
import com.aomei.model.User;
import com.aomei.query.UserQuery;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2015/6/21.
 */
@Repository("userMapper")
public class UserMapperImpl extends BaseMapperImpl<User,UserQuery> implements UserMapper {
    @Override
    public User findUser(User user){
        return (User)getSqlSession().selectOne(getClassName()+".findUser",user);
    }
}
