package com.aomei.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by 丁江磊 on 2015/7/8.
 */
public interface MBaseDao <T>{
    /**
     * 根据主键删除信息
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入实体信息
     * @param record
     * @return
     */
    int insert(T record);

    /**
     * 根据实体对象具体值，插入具体字段信息
     * @param record
     * @return
     */
    int insertSelective(T record);

    /**
     * 根据主机获取实体对象
     * @param id
     * @return
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 更新不为空的自动信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * 更新实体对象
     * @param record
     * @return
     */
    int updateByPrimaryKey(T record);

    /**
     * 批量删除数据
     * @param ids
     * @return
     */
    int deleteByPrimaryKeyArray(Integer[] ids);

    /**
     * 分页查询
     * @param map limitStart limitEnd
     * @return
     */
    List<T> selectPages(Map<String,Object> map);
}
