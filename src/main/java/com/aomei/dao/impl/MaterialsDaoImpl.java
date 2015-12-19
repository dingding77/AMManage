package com.aomei.dao.impl;

import com.aomei.dao.MaterialsDao;
import com.aomei.model.Materials;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2015/11/14.
 */
@Repository("materialsDao")
public class MaterialsDaoImpl extends MBaseDaoImpl<Materials> implements MaterialsDao{
}
