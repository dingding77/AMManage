package com.aomei.struts2.common;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.stereotype.Component;

/**
 * Created by dingjianglei on 2015/6/23.
 */
@ParentPackage("common")
@Namespace("/common")
@Component
public class IndexAction extends ActionSupport{

    @Action("index")
    public String index(){
        return SUCCESS;
    }
}
