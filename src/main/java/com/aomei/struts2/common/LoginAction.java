package com.aomei.struts2.common;

import com.aomei.model.Menu;
import com.aomei.model.Role;
import com.aomei.model.User;
import com.aomei.system.service.UserService;
import com.aomei.util.TreeMenu;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ParentPackage("common")
@Namespace("/common")
@Component
public class LoginAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
    @Getter @Setter
    private User user;
    @Autowired UserService userService;
    @Action("login-form")
    public String loginForm(){
        ActionContext context=ActionContext.getContext();
        Map  parameterMap=context.getParameters();
        Object obj=parameterMap.get("type");
        if(obj!=null){
            HttpServletRequest request = ServletActionContext.getRequest();
            request.setAttribute("errorMsg", "用户名或密码错误");
        }
        return SUCCESS;
    }
    @Action(value="login", results = {
            @Result(name = LOGIN,   type = "redirect",location = "/common/login-form.htm")})
    public String login() throws Exception {
        return LOGIN;
    }

	@Action(value="goHome", results = {
    @Result(name = SUCCESS, type = "redirect",location = "/common/index.htm"),
    @Result(name = LOGIN,   type = "redirect",location = "/common/login-form.htm?type=01")})
	public String goHome() throws Exception {
        if(user==null){
            return LOGIN;
        }
		//根据用户名和密码获取用户对象
        User loginUser=userService.login(user);

        if(loginUser!=null){
            log.info("用户【{}】登录成功",loginUser.getUsername());
            List<Menu> menuList=userService.getUserMenus(loginUser);
            List<Role> userRoleList=userService.getUserRoles(loginUser);
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("userAllMenuList",new ArrayList<Menu>(menuList));
            menuList=parseMenus(menuList);
            TreeMenu tree=new TreeMenu(menuList);

            session.setAttribute("userRoleList",userRoleList);
            session.setAttribute("user",loginUser);
            if(menuList!=null&&menuList.size()>0){
                session.setAttribute("user_menus",menuList);
                String menus=tree.buildTree();
                log.info("menus:{}",menus);
                session.setAttribute("menus", menus);
            }

            return SUCCESS;
        }else{
            log.info("用户【{}】登录失败",user.getUsername());
            return LOGIN;
        }
	}
	
	private List<Menu> parseMenus(List<Menu> menuList){
		Map<Integer,List<Menu>> map=new HashMap<Integer,List<Menu>>();
        for(Menu menu:menuList){
        	if(map.get(menu.getLevel())==null){
                map.put(menu.getLevel(),new ArrayList<Menu>());
            }
            map.get(menu.getLevel()).add(menu);
        }
        //先获取顶级菜单
        List<Menu> parentMenus=map.get(0);
        for(int level=1;level<map.size();level++){
            List<Menu> sonMenus=map.get(level);
            for(Menu parent:parentMenus){
                List<Menu> mysonMenus=new ArrayList<Menu>();
                for(Menu son:sonMenus){
                    if(son.getParentid().equals(parent.getId())){
                        mysonMenus.add(son);
                    }
                }
                parent.setChildrens(mysonMenus);
            }
            parentMenus=sonMenus;
        }
        return map.get(0);
	}
	
}
