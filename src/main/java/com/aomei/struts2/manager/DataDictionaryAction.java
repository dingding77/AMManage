package com.aomei.struts2.manager;

import com.aomei.dao.DataDictionaryDao;
import com.aomei.dao.MBaseDao;
import com.aomei.model.DataDictionary;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2015/7/26.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/manager")
@Component
public class DataDictionaryAction extends BaseAction<DataDictionary> {
    @Autowired
    DataDictionaryDao dataDictionaryDao;
    @Getter @Setter
    String type;
    @Action(value = "getDataDictionaryListJson", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String getListJson(){
        if(super.modelData==null){
            modelData=new DataDictionary();
        }
        return super.getBaseListJson();
    }

    @Action(value = "getListJsonByTypeJson", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String getListJsonByType(){
        try{
            List<DataDictionary> list=dataDictionaryDao.selectByType(type);
            initDataMap();
            dataMap.put("list",list);
        }catch (Exception e){
            log.info("根据类型查询数据字典信息异常{}",e);
        }
        return SUCCESS;
    }

    @Override
    public MBaseDao<DataDictionary> getDao() {
        return dataDictionaryDao;
    }
}
