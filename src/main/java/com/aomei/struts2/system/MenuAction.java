package com.aomei.struts2.system;

import com.aomei.dao.MenuMapper;
import com.aomei.dao.RoleResourcesMapper;
import com.aomei.model.Menu;
import com.aomei.model.PageResult;
import com.aomei.model.RoleResources;
import com.aomei.query.MenuQuery;
import com.aomei.query.RoleResourcesQuery;
import com.aomei.struts2.manager.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
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
 * Created by Administrator on 2015/8/29.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/system/menu")
@Component
public class MenuAction extends BaseAction<Menu> {
    @Getter @Setter
    private List<Menu> listMenu=new ArrayList<Menu>();
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RoleResourcesMapper roleResourcesMapper;
    @Getter
    Menu menu;
    @Getter @Setter
    MenuQuery menuQuery;
    @Getter @Setter
    private String menuParentId;
    @Getter @Setter
    private JSON msg;
    @Getter @Setter
    private RoleResourcesQuery query;
    @Getter @Setter
    List<RoleResources> resourcesList;
    @Action(value="menu-show", results = { @Result(type = "json",params = {"root","listMenu"})})
    public String menuShow() throws Exception {
        log.debug("menu");
        List<Menu> list=(List<Menu>) ActionContext.getContext().getSession().get("userAllMenuList");
        if(list!=null & list.size()<0){
            log.debug("读取菜单失败");
        }else{
            log.debug("父级菜单id："+menuParentId);
            if(StringUtils.isNotEmpty(menuParentId)){
                for(Menu menu:list){
                    if(menu.getParentid()==Integer.parseInt(menuParentId)){
                        log.debug("menu.getParentid()={} menuParentId={}",menu.getParentid(),menuParentId);
                        listMenu.add(menu);
                    }
                }
            }

        }
        log.debug("读取的数量："+listMenu.size());
        return SUCCESS;
    }
    @Action(value = "getListJson", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String  getBaseListJson(){
        try{
            if(menuQuery==null){
                menuQuery=new MenuQuery();
            }
            menuQuery.setPage(page);
            menuQuery.setRows(rows);
            if(baseDao==null){
                baseDao=getDao();
            }
            initDataMap();
            PageResult<Menu> pageResult= menuMapper.getPageResult(menuQuery);
            log.info("获取第【{}】页数据，每页显示【{}】条数据",page,rows);

            dataMap.put("rows",pageResult.getList());
            dataMap.put("total", pageResult.getTotalCount());
        }catch (Exception e){
            log.error("查询数据异常{}",e);
        }
        return SUCCESS;
    }

    @Action(value="getParentMenu", results = { @Result(type = "json",params = {"root","listMenu"})})
    public String getParentMenu(){
        MenuQuery query=new MenuQuery();
        query.setIsDelete("N");
        query.setMenuType("0");
        query.setLimit(0);
        listMenu=menuMapper.selectByExample(query);
        return SUCCESS;
    }

    @Action(value = "menuTree", results = { @Result(name = SUCCESS, type = "json", params = { "root", "msg" }) })
    public String goodsTree(){
        query.setIsdelete("N");
        this.resourcesList=roleResourcesMapper.selectByExample(query);
        listMenu = menuMapper.selectMenu();
        this.msg=createTree(listMenu,0);
        return SUCCESS;
    }

    /**
     * 递归函数调用
     * @param goods  集合
     * @param pid    父节点ID
     * @return
     */
    private JSON createTree(List<Menu> goods,long pid){
        List<Map<String, Object>> lmjson =new ArrayList<Map<String,Object>>();//集合对象保存多个构造的map
        Map<String, Object>	jsonMap = null; //map对象模拟json键值对
        for(Menu gds:goods){
            if(pid==gds.getParentid()){//判断父节点是否相同，把相同的，载入一个组

                jsonMap = new HashMap<String, Object>();
                jsonMap.put("id", gds.getId());
                jsonMap.put("text", gds.getName());

                if(isHasSub(goods,gds.getId())){//=1表示有子节点
                    jsonMap.put("state", "closed");
                    jsonMap.put("children", createTree(goods,gds.getId()));//载入子节点

                }else{
                    if(isChecked(gds.getId())){
                        jsonMap.put("checked", true);
                    }
                }
                lmjson.add(jsonMap);
            }
        }
        return JSONArray.fromObject(lmjson);//map转为json
    }

    public boolean isChecked(Integer menuId){
        for(RoleResources resources:resourcesList){
            if(resources.getMenuid()==menuId){
                return true;
            }
        }
        return false;
    }
    public boolean isHasSub(List<Menu> allMenus,int menuId){
        for(Menu menu:allMenus){
            if(menu.getParentid()==menuId){
                return true;
            }
        }
        return false;
    }
    public MenuMapper getDao(){
        return menuMapper;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        this.modelData=menu;
    }
}
