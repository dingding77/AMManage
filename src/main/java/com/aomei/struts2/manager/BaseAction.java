package com.aomei.struts2.manager;

import com.aomei.dao.MBaseDao;
import com.aomei.util.ColumnToPropertyUtil;
import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/7/26.
 */
@Slf4j
public abstract   class BaseAction<T> extends ActionSupport {
    @Getter
    @Setter
    public Map<String,Object> dataMap;     //查询条件
    @Getter @Setter
    public int page;               //分页参数(页数)
    @Getter @Setter
    public int rows;               //分页参数(行数)
    @Getter @Setter
    private String sort;
    @Getter @Setter
    private String order;
    @Getter @Setter
    public String ids;             //操作参数 主键
    @Getter @Setter
    public Integer id;             //操作参数 单一主键
    @Getter @Setter
    public T modelData;                //实体对象
    public MBaseDao<T> baseDao;
    private  String  simpleName;
    {
        simpleName=(getSimpleName());
    }

    @Action(value = "getListJson", results = { @Result(type = "json",params = {"root","dataMap"})})
    public String  getBaseListJson(){
        try{
            if(baseDao==null){
                baseDao=getDao();
            }
            initDataMap();
            dataMap.put("limitStart",(page-1)*rows);
            dataMap.put("limitEnd",rows);
            if(StringUtils.isNotEmpty(sort)){
                dataMap.put("sortName", ColumnToPropertyUtil.propertyToColumn(sort));
                dataMap.put("sortOrder",order);
            }
            dealDataMap();
            List<T> list=baseDao.selectPages(dataMap);
            int total=baseDao.selectCount(dataMap);
            log.info("获取第【{}】页数据，每页显示【{}】条数据,共查询到"+total+"条数据",page,rows);
            dataMap.put("rows",list);
            dataMap.put("total", total);
        }catch (Exception e){
            log.error("查询列表异常={}",e);
        }
        return SUCCESS;
    }

    public void dealDataMap(){
        dataMap.put(simpleName.toString(),modelData);
    }

    public void initDataMap(){
        if(dataMap==null){
            dataMap=new HashMap<String,Object>();
        }
    }

    @Action(value = "addSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String addBaseSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        if(baseDao==null){
            baseDao=getDao();
        }
        try{
            int result=baseDao.insertSelective(modelData);
            if(result!=1){
                dataMap.put("errorMsg","添加失败");
            }
        }catch (Exception e){
            dataMap.put("errorMsg","添加失败");
            log.error("addBaseSave()异常={}",e);
        }
        return SUCCESS;
    }
    @Action("edit")
    public String toBaseEditPage(){
        if(baseDao==null){
            baseDao=getDao();
        }
        if(id!=null){
            modelData=(T)baseDao.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }

    @Action("show")
    public String toBaseShowPage(){
        if(baseDao==null){
            baseDao=getDao();
        }
        if(id!=null){
            modelData=(T)baseDao.selectByPrimaryKey(id);
        }
        return SUCCESS;
    }
    @Action(value = "editSave", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String editBaseSave(){
        if(dataMap==null){
            dataMap=new HashMap<String, Object>();
        }
        if(baseDao==null){
            baseDao=getDao();
        }
        try{
            int result=baseDao.updateByPrimaryKeySelective(modelData);
            if(result!=1){
                dataMap.put("errorMsg","修改失败");
            }
        }catch (Exception e){
            dataMap.put("errorMsg","修改失败");
            e.printStackTrace();
        }
        return SUCCESS;
    }
    @Action(value = "delete", results = { @Result(name = "success", type = "json", params = {
            "root", "dataMap" }) })
    public String deleteBaseInfo(){
        try{
            if(dataMap==null){
                dataMap=new HashMap<String, Object>();
            }
            if(baseDao==null){
                baseDao=getDao();
            }
            String [] strs=ids.split(",");
            Integer [] ids=new Integer[strs.length];
            for(int i=0;i<strs.length;i++){
                ids[i]=Integer.parseInt(strs[i]);
            }

            if(ids!=null&&ids.length>0){
                int result=baseDao.deleteByPrimaryKeyArray(ids);
                if(result<=0){
                    dataMap.put("errorMsg","删除失败");
                }else{
                    dataMap.put("success",SUCCESS);
                }
            }else{
                dataMap.put("errorMsg","删除失败");
            }
        }catch (Exception e){
            dataMap.put("errorMsg","删除失败");
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String getSimpleName(){
        String str=null;
        try{
            //在父类中得到子类声明的父类的泛型信息
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<T> clazz = (Class) pt.getActualTypeArguments()[0];
            //clazz.getSimpleName().toString().toLowerCase(); 这里是获取实体类的简单名称，再把类名转为小写
            str= clazz.getSimpleName().toString().replace("Action","");
            StringBuilder sb = new StringBuilder(str);
            sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
            str = sb.toString();
        }catch (Exception e){
            log.error("获取类 simpleName异常{}",e);
        }
        return str;
    }

    public abstract   MBaseDao<T> getDao();
}
