package com.aomei.struts2.system;

import com.aomei.dao.MBaseDao;
import com.aomei.dao.RoleMapper;
import com.aomei.dao.RoleResourcesMapper;
import com.aomei.model.PageResult;
import com.aomei.model.Role;
import com.aomei.model.RoleResources;
import com.aomei.model.User;
import com.aomei.query.RoleQuery;
import com.aomei.query.RoleResourcesQuery;
import com.aomei.struts2.manager.BaseAction;
import com.aomei.system.service.UserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    RoleResourcesMapper roleResourcesMapper;
    @Autowired
    UserService userService;
    @Getter
    Role role;
    @Getter @Setter
    RoleQuery roleQuery;
    @Getter @Setter
    String roleId;
    @Getter @Setter
    String menus;
    @Getter @Setter
    User user;

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
            List<Role> userRoleList=userService.getUserRoles(user);
            dataMap.put("allRoles",roleList);
            dataMap.put("userRoles",userRoleList);
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
    @Action(value = "saveAuthorize", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String saveAuthorize(){
        try{
            Role role=roleMapper.selectByPrimaryKey(Integer.parseInt(roleId));
            //获取当前角色对应的资源信息
            RoleResourcesQuery query=new RoleResourcesQuery();
            query.setRoleid(role.getId());
            //当前角色已入库的权限信息
            List<RoleResources> resourcesExistsList=roleResourcesMapper.selectByExample(query);
            //需要新增的菜单权限
            List<RoleResources> needAdd=new ArrayList<RoleResources>();
            //需要删除的菜单权限
            List<RoleResources> needRemove=new ArrayList<RoleResources>();
            //需要更新的菜单权限
            List<RoleResources> updateOk=new ArrayList<RoleResources>();
            initDataMap();
            List<RoleResources> resourcesList=new ArrayList<RoleResources>();
            if(role!=null){
                String [] menus=this.menus.split(",");
                for(String menuId:menus){
                    boolean isAdd=true;

                    for(RoleResources resources:resourcesExistsList){
                        //如果库中已存在
                        if(Integer.parseInt(menuId)==resources.getMenuid()){
                            if(resources.getIsdelete().equals("Y")){
                                updateOk.add(resources);
                            }
                            isAdd=false;
                            break;
                        }
                    }

                    if(isAdd){
                        RoleResources roleResources=new RoleResources();
                        roleResources.setRoleid(role.getId());
                        roleResources.setMenuid(Integer.parseInt(menuId));
                        roleResources.setIsdelete("N");
                        needAdd.add(roleResources);
                    }
                }

                //需要删除的
                for(RoleResources resources:resourcesExistsList){
                    boolean isRemove=true;
                    for(String menuId:menus){
                        if(resources.getMenuid()==Integer.parseInt(menuId)){
                            isRemove=false;
                            break;
                        }
                    }

                    if(StringUtils.equals(resources.getIsdelete(),"N")&&isRemove){
                        needRemove.add(resources);
                    }
                }
                if(needAdd.size()>0){
                    roleResourcesMapper.batchInsert(needAdd);
                }

                Map<String,Object> map=new HashMap<String, Object>();
                map.put("roleId",Integer.parseInt(roleId));
                if(updateOk.size()>0){
                    map.put("isDelete","N");
                    map.put("menuArray",updateOk);
                    roleResourcesMapper.updateDeleteFlag(map);
                }
                if(needRemove.size()>0){
                    map.put("isDelete","Y");
                    map.put("menuArray",needRemove);
                    roleResourcesMapper.updateDeleteFlag(map);
                }
            }else{
                log.info("role={}不存在",roleId);
            }
        }catch (Exception e){
            dataMap.put("errMsg","程序异常");
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
