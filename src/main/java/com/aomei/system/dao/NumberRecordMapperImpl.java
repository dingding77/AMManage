package com.aomei.system.dao;

import com.aomei.dao.NumberRecordMapper;
import com.aomei.dao.impl.MBaseDaoImpl;
import com.aomei.model.NumberRecord;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2015/9/12.
 */
@Repository("numberRecordMapper")
public class NumberRecordMapperImpl extends MBaseDaoImpl<NumberRecord> implements NumberRecordMapper{
    @Override
    public NumberRecord getRecordById() throws DataAccessException {
        int id=1;
        return (NumberRecord)getSqlSession().selectOne(getClassName()+".getRecordById",id);
    }
    @Override
    public int updateRecord(NumberRecord record) throws DataAccessException{
        return getSqlSession().update(getClassName()+".updateRecord",record);
    }
}
