package com.aomei.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/7/8.
 */
public class MBaseDaoImpl<T> extends SqlSessionDaoSupport {

    /**
     *
     * 获取传过来的泛型类名字
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String getClassName(){
        //在父类中得到子类声明的父类的泛型信息
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> clazz = (Class) pt.getActualTypeArguments()[0];
        //clazz.getSimpleName().toString().toLowerCase(); 这里是获取实体类的简单名称，再把类名转为小写
        return clazz.getSimpleName().toString()+"Mapper";
    }

    /**
     * 根据主键删除信息
     * @param id
     * @return
     */
    public int deleteByPrimaryKey(Integer id){
        return getSqlSession().delete(getClassName()+".deleteByPrimaryKey",id);
    }

    /**
     * 插入实体信息
     * @param record
     * @return
     */
    public int insert(T record){
        return getSqlSession().insert(getClassName() + ".insert", record);
    }

    /**
     * 根据实体对象具体值，插入具体字段信息
     * @param record
     * @return
     */
    public int insertSelective(T record){
        return getSqlSession().insert(getClassName()+".insertSelective",record);
    }

    /**
     * 根据主机获取实体对象
     * @param id
     * @return
     */
    public T selectByPrimaryKey(Integer id){
        return (T)getSqlSession().selectOne(getClassName()+".selectByPrimaryKey",id);
    }

    /**
     * 更新不为空的自动信息
     * @param record
     * @return
     */
    public int updateByPrimaryKeySelective(T record){
        return getSqlSession().update(getClassName()+".updateByPrimaryKeySelective",record);
    }

    /**
     * 更新实体对象
     * @param record
     * @return
     */
    public int updateByPrimaryKey(T record){
        return getSqlSession().update(getClassName()+".updateByPrimaryKey",record);
    }

    /**
     * 分页查询
     * @param map
     * @return
     */
    public List<T> selectPages(Map<String,Object> map) {
        return getSqlSession().selectList(getClassName()+".selectPages",map);
    }

    public int deleteByPrimaryKeyArray(Integer[] ids) {
        return getSqlSession().delete(getClassName()+".deleteByPrimaryKeyArray",ids);
    }

    public  int selectCount(Map<String,Object> map){
        return (Integer)getSqlSession().selectOne(getClassName()+".selectCount",map);
    }
}
