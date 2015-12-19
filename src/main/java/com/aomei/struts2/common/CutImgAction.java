package com.aomei.struts2.common;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/10/11.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/common")
@Component
public class CutImgAction extends ActionSupport {
    @Getter @Setter
    private String imgPath;
    @Getter @Setter
    private String inputId;
    @Action(value = "cutImg")
    public String showCustomer(){

        return SUCCESS;
    }
}
