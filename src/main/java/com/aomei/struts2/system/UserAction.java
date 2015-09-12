package com.aomei.struts2.system;

import com.aomei.dao.RoleResourcesMapper;
import com.aomei.dao.UserMapper;
import com.aomei.dao.UserRoleMapper;
import com.aomei.model.PageResult;
import com.aomei.model.User;
import com.aomei.model.UserRole;
import com.aomei.query.UserQuery;
import com.aomei.query.UserRoleQuery;
import com.aomei.struts2.manager.BaseAction;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ParentPackage("common")
@Namespace("/system/user")
@Component
public class UserAction extends BaseAction<User> {
    @Autowired
    private UserMapper userMapper;
    @Getter @Setter
    private UserQuery userQuery;
    @Getter
    private User user;
    @Autowired
    RoleResourcesMapper roleResourcesMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Getter @Setter
    UserRole userRole;
    @Action(value = "getListJson", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String  getBaseListJson(){
        try{
            if(userQuery==null){
                userQuery=new UserQuery();
            }
            userQuery.setPage(page);
            userQuery.setRows(rows);
            if(baseDao==null){
                baseDao=getDao();
            }
            initDataMap();
            PageResult<User> pageResult= userMapper.getPageResult(userQuery);
            log.info("获取第【{}】页数据，每页显示【{}】条数据",page,rows);

            dataMap.put("rows",pageResult.getList());
            dataMap.put("total", pageResult.getTotalCount());
        }catch (Exception e){
            log.error("查询数据异常{}",e);
        }
        return SUCCESS;
    }

    @Action(value = "getUserById", results = { @Result(type = "json",params = {"root","user"})})
    public String getUserById(){
        user=userMapper.selectByPrimaryKey(user.getId());
        return SUCCESS;
    }
    @Action(value = "saveAuthorize", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String saveAuthorize(){
        try{
            initDataMap();
            if(userRole.getRoleIds()!=null){
                //第一步获取当前用户的角色信息
                UserRoleQuery query=new UserRoleQuery();
                query.setUserid(userRole.getUserid());
                List<UserRole> list=userRoleMapper.selectByExample(query);
                //现在的角色
                List<UserRole> addList=new ArrayList<UserRole>();
                //需要删除的角色
                List<UserRole> removeList=new ArrayList<UserRole>();
                //需要恢复的角色 (之前已经入库但是isDelete=Y)
                List<UserRole> resetList=new ArrayList<UserRole>();
                String [] roleIds=userRole.getRoleIds().split(",");
                if(StringUtils.isEmpty(userRole.getRoleIds())){
                    roleIds=new String[0];
                }
                if(list!=null){
                    //已数据库为准 获取removeList
                    for(UserRole curUserRole:list){
                        boolean isExists=false;
                        for(String roleId:roleIds){
                            if(Integer.parseInt(roleId)==curUserRole.getRoleid()){
                                //说明还在
                                isExists=true;
                                if(StringUtils.equals(curUserRole.getIsDelete(),"Y")){
                                    //虽然存在但是之前已经被删除
                                    resetList.add(curUserRole);
                                }
                                break;
                            }
                        }
                        if(!isExists){
                            removeList.add(curUserRole);
                        }
                    }

                    //已页面传递的值为准 获取将要新增的角色关联信息
                    for(String roleId:roleIds){
                        boolean isExists=false;
                        for(UserRole curUserRole:list){
                            if(Integer.parseInt(roleId)==curUserRole.getRoleid()){
                                isExists=true;
                                break;
                            }
                        }
                        if(!isExists){
                            addList.add(new UserRole(userRole.getUserid(),Integer.parseInt(roleId)));
                        }
                    }
                }

                for(UserRole curUserRole:removeList){
                    userRoleMapper.deleteByPrimaryKey(curUserRole.getId());
                }
                for(UserRole curUserRole:resetList){
                    curUserRole.setIsDelete("N");
                    userRoleMapper.updateByPrimaryKey(curUserRole);
                }
                for(UserRole curUserRole:addList){
                    userRoleMapper.insertSelective(curUserRole);
                }


            }
        }catch (Exception e){
            dataMap.put("errorMsg","角色授权失败");
            log.info("角色授权失败{}",e);
        }
        return SUCCESS;
    }
    public UserMapper getDao(){
        return userMapper;
    }

    public void setUser(User user){
        this.user=user;
        super.modelData=user;
    }
}
