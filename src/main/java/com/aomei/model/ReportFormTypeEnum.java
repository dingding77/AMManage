package com.aomei.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * Created by Administrator on 2015/12/3.
 */
public enum ReportFormTypeEnum {
    //1:生产单 2:采购单 3:商业发票 4:送货单
    REPORT_FORM_1("1","生产单"),
    REPORT_FORM_2("2","采购单"),
    REPORT_FORM_3("3","商业发票"),
    REPORT_FORM_4("4","送货单");
    @Getter @Setter
    private String code;//编码
    @Getter @Setter
    private String desc;//描述
    ReportFormTypeEnum(String code,String desc){
        this.code=code;
        this.desc=desc;
    }
}
