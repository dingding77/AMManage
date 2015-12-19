package com.aomei.system.dao;

import com.aomei.dao.ReportConfigMapper;
import com.aomei.dao.impl.BaseMapperImpl;
import com.aomei.model.ReportConfig;
import org.springframework.stereotype.Repository;

@Repository("reportConfigMapper")
public class ReportConfigMapperImpl extends BaseMapperImpl<ReportConfig,ReportConfig> implements ReportConfigMapper {

}