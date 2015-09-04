package com.aomei.struts2.system;

import com.aomei.dao.MBaseDao;
import com.aomei.dao.RoleMapper;
import com.aomei.model.PageResult;
import com.aomei.model.Role;
import com.aomei.query.RoleQuery;
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

import java.util.List;

/**
 * Created by Administrator on 2015/8/30.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/system/role")
@Component
public class RoleAction extends BaseAction<Role> {
    @Autowired
    RoleMapper roleMapper;

    @Getter
    Role role;
    @Getter @Setter
    RoleQuery roleQuery;

    @Action(value = "getAllListJson", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String getAllRole(){
        try{
            if(roleQuery==null){
                roleQuery=new RoleQuery();
            }
            roleQuery.setIsDelete("N");
            roleQuery.setIsEnable("1");
            roleQuery.setLimit(0);
            initDataMap();
            List<Role> roleList=roleMapper.selectByExample(roleQuery);
            dataMap.put("allRoles",roleList);
        }catch (Exception e){
            log.error("加载角色异常{}",e);
            dataMap.put("errorMsg","程序异常");
        }
        return SUCCESS;
    }
    @Action(value = "getListJson", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String  getBaseListJson(){
        try{
            if(roleQuery==null){
                roleQuery=new RoleQuery();
            }
            roleQuery.setPage(page);
            roleQuery.setRows(rows);
            if(baseDao==null){
                baseDao=getDao();
            }
            initDataMap();
            PageResult<Role> pageResult= roleMapper.getPageResult(roleQuery);
            log.info("获取第【{}】页数据，每页显示【{}】条数据",page,rows);

            dataMap.put("rows",pageResult.getList());
            dataMap.put("total", pageResult.getTotalCount());
        }catch (Exception e){
            log.error("查询数据异常{}",e);
        }
        return SUCCESS;
    }

    @Override
    public MBaseDao<Role> getDao() {
        return roleMapper;
    }

    public void setRole(Role role){
        this.role=role;
        super.modelData=role;
    }
}
