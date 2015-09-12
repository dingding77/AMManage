package com.aomei.system.dao;

import com.aomei.dao.UserRoleMapper;
import com.aomei.dao.impl.BaseMapperImpl;
import com.aomei.model.UserRole;
import com.aomei.query.UserRoleQuery;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2015/9/10.
 */
@Repository("userRoleMapper")
public class UserRoleMapperImpl extends BaseMapperImpl<UserRole,UserRoleQuery> implements UserRoleMapper {

}
