package com.aomei.intercept;

import com.aomei.model.Menu;
import com.aomei.model.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 权限拦截器
 * Created by Administrator on 2015/6/22.
 */
@Slf4j
public class AuthorityInterceptor extends AbstractInterceptor{

    // 拦截Action处理的拦截方法
    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        // 取出名为user的session属性
        User user = (User) session.getAttribute("user");
        String url=request.getServletPath();
        int index=url.indexOf("/login");
        //index>-1 说明是登录或者注销 不做拦截
        if(index==-1) {//若访问后台资源 过滤到login
            if(user!=null){
                //如果不是Ajax
                if(!isAjaxRequest(request)){
                    // 判断该用户是否有访问改路径的权限
                    boolean flag = false;
                    List<Menu> listMenu=(List<Menu>)session.getAttribute("user_menus");
                    String address = request.getRequestURI().lastIndexOf("/")== request.getRequestURI().length()-1?request.getRequestURI().substring(0,
                            request.getRequestURI().length() - 1): request.getRequestURI();
                    log.info("访问的地址为:{}",address);

                    //获得请求连接地址，带参数
                    url = session.getAttribute("REDIRECT_URL") == null ?address : session.getAttribute(
                            "REDIRECT_URL").toString();
                    log.debug("-----url:"+url);
                    if (url.lastIndexOf("/") == (url.length() - 1)
                            || url.indexOf("/common/index") != -1) {
                        // 以/结尾的地址,一般为主页地址,不做拦截
                        flag = true;
                    } else {
                        for (Menu m : listMenu) {
                            if (m.getResources() != null) {
                                if (url.lastIndexOf(m.getResources()) > 0) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                    }
                    session.removeAttribute("REDIRECT_URL");
                    if (flag) {
                        return invocation.invoke();
                    }
//                    else {
//                        log.debug("当前用户无访问此菜单(" + url + ")的权限");
//                        return "noAuthority";
//                    }
                }

            }else{
                String address = request.getRequestURI().lastIndexOf("/")== request.getRequestURI().length()-1?request.getRequestURI().substring(0,
                        request.getRequestURI().length() - 1): request.getRequestURI();
                address = request.getQueryString() == null ? address
                        : address + "?" + request.getQueryString();
                log.debug("url02:"+address);
                session.setAttribute("REDIRECT_URL", address);

                // session.put("REDIRECT_URL", request.getRequestURL().toString());
                // log.info(request.getRequestURL().toString());
                log.debug("user is not login.");
                // 没有登陆，将服务器提示设置成一个HttpServletRequest属性
                // ctx.put("tip","您还没有登录，请登陆系统");
                return Action.LOGIN;
            }
        }
        return invocation.invoke();
    }
    /**
     * 验证是否是ajax请求
     * @param request
     * @return
     */
    public boolean isAjaxRequest(HttpServletRequest request){
        String header=request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }
}
