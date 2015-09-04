package com.aomei.struts2.common;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/21.
 */
@Namespace("/common")
public class LogOutAction extends ActionSupport{

    @Action(value="login-out", results = {@Result(name = "success",type = "redirect",location = "/common/login-form.htm")})
    public String logout(){
        ActionContext actionContext = ActionContext.getContext();
        Map session = actionContext.getSession();
        session.clear();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        return SUCCESS;
    }
}
