package com.aomei.struts2.system;

import com.aomei.dao.DataDictionaryDao;
import com.aomei.dao.MBaseDao;
import com.aomei.model.DataDictionary;
import com.aomei.struts2.manager.BaseAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2015/9/13.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/system/dictionary")
@Component
public class DataDictionaryAction extends BaseAction<DataDictionary> {
    @Autowired
    private DataDictionaryDao dataDictionaryDao;
    DataDictionary dataDictionary;
    List<DataDictionary> dictionaryList;
    @Override
    public MBaseDao<DataDictionary> getDao() {
        return dataDictionaryDao;
    }

    public DataDictionary getDataDictionary() {
        return dataDictionary;
    }
    @Action(value = "getPstpList", results = { @Result(type = "json",params = {"root","dictionaryList"})})
    public String getPstpList(){
        try{
            if(dataDictionary!=null){
                dictionaryList=dataDictionaryDao.selectByType(dataDictionary.getType());
            }
        }catch (Exception e){
            log.error("查询后工序数据字典信息异常{}",e);
        }
        return SUCCESS;
    }
    public void setDataDictionary(DataDictionary dataDictionary) {
        if(dataDictionary!=null){
            super.modelData=dataDictionary;
        }
        this.dataDictionary = dataDictionary;
    }

    public List<DataDictionary> getDictionaryList() {
        return dictionaryList;
    }

    public void setDictionaryList(List<DataDictionary> dictionaryList) {
        this.dictionaryList = dictionaryList;
    }
}
