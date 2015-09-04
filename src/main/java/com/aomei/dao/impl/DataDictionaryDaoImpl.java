package com.aomei.dao.impl;

import com.aomei.dao.DataDictionaryDao;
import com.aomei.model.DataDictionary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/7/26.
 */
@Repository("dataDictionaryDao")
public class DataDictionaryDaoImpl extends MBaseDaoImpl<DataDictionary> implements DataDictionaryDao {
    public List<DataDictionary> selectByType(String type) throws Exception
    {
        return getSqlSession().selectList("DataDictionaryMapper.selectByType",type);
    }
}
