package com.aomei.dao.impl;

import com.aomei.dao.BaseMapper;
import com.aomei.model.PageResult;

import java.util.List;

/**
 * Created by Administrator on 2015/8/29.
 */
public class BaseMapperImpl<T,Query> extends MBaseDaoImpl<T> implements BaseMapper<T,Query>{
    public List<T> selectByExample(Query query){
        List<T> list=getSqlSession().selectList(getClassName()+".selectByExample",query);
        return list;
    }
    public int countByExample(Query query){
        int count=(Integer)getSqlSession().selectOne(getClassName()+".countByExample",query);
        return count;
    }
    public PageResult<T> getPageResult(Query query){
        PageResult<T> result=new PageResult<T>();
        List<T> list= selectByExample(query);
        int count= countByExample(query);
        result.setList(list);
        result.setTotalCount(count);
        return result;
    }

}
