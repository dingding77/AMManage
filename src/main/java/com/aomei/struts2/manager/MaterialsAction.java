package com.aomei.struts2.manager;

import com.aomei.dao.MBaseDao;
import com.aomei.dao.MaterialsDao;
import com.aomei.model.Materials;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2015/8/9.
 */
public class MaterialsAction extends BaseAction<Materials>{

    @Autowired
    MaterialsDao materialsDao;

    @Getter
    Materials materials;

    public void setMaterials(Materials materials){
        this.materials=materials;
        this.modelData=materials;
    }
    public MBaseDao<Materials> getDao(){
        return materialsDao;
    }
}
