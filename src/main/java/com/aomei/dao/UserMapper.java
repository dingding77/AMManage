package com.aomei.dao;

import com.aomei.model.User;
import com.aomei.query.UserQuery;

public interface UserMapper extends BaseMapper<User,UserQuery> {
    public User findUser(User user);
}