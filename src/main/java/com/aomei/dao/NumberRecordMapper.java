package com.aomei.dao;

import com.aomei.model.NumberRecord;
import org.springframework.dao.DataAccessException;

public interface NumberRecordMapper {
    public NumberRecord getRecordById() throws DataAccessException;
    public int updateRecord(NumberRecord record) throws DataAccessException;
}