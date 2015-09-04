package com.aomei.struts2.system;

import com.aomei.dao.MenuMapper;
import com.aomei.model.Menu;
import com.aomei.model.PageResult;
import com.aomei.query.MenuQuery;
import com.aomei.struts2.manager.BaseAction;
import com.opensymphony.xwork2.ActionContext;
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
    @Getter
    Menu menu;
    @Getter @Setter
    MenuQuery menuQuery;
    @Getter @Setter
    private String menuParentId;
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
    public MenuMapper getDao(){
        return menuMapper;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        this.modelData=menu;
    }
}
