package com.aomei.dao;

import com.aomei.model.DataDictionary;

import java.util.List;

public interface DataDictionaryDao extends MBaseDao<DataDictionary>{
    public List<DataDictionary> selectByType(String type) throws Exception;
}