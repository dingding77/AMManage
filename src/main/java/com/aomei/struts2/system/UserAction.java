package com.aomei.struts2.system;

import com.aomei.dao.UserMapper;
import com.aomei.model.PageResult;
import com.aomei.model.User;
import com.aomei.query.UserQuery;
import com.aomei.struts2.manager.BaseAction;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public UserMapper getDao(){
        return userMapper;
    }

    public void setUser(User user){
        this.user=user;
        super.modelData=user;
    }
}
