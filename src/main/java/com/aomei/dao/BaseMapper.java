package com.aomei.dao;

import com.aomei.model.PageResult;

import java.util.List;

/**
 * Created by Administrator on 2015/8/29.
 */
public interface BaseMapper<T,Query> extends MBaseDao<T>{
    List<T> selectByExample(Query query);
    int countByExample(Query query);
    PageResult<T> getPageResult(Query query);
}
