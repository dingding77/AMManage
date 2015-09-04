package com.aomei.dao.impl;

import com.aomei.dao.CustomerDao;
import com.aomei.model.Customer;
import org.springframework.stereotype.Repository;

/**
 *
 * Created by Administrator on 2015/7/8.
 */
@Repository("customerDao")
public class CustomerDaoImpl extends MBaseDaoImpl<Customer> implements CustomerDao{

}
