package com.aomei.struts2.custom;


import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

@Namespace("/custom")
public class HelloWorld6 extends ActionSupport {
    @Action("/H6/url")
    public String execute() {
        return SUCCESS;
    }

    @Action(value = "url",results = {
            @Result(name="url",location = "/url"),
            @Result(name="h6url",location = "/custom/H6/url")})
    public String doSomething() {
        return "h6url";
    }
}