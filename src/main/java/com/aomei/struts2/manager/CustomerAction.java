package com.aomei.struts2.manager;

import com.aomei.dao.CustomerDao;
import com.aomei.dao.MBaseDao;
import com.aomei.model.Customer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/28.
 */
@Slf4j
@ParentPackage("common")
@Namespace("/manager/customer")
@Component
public class CustomerAction extends BaseAction<Customer>{
    @Getter
    @Setter
    List<Customer> customers;
    @Getter
    Customer customer;
    @Autowired
    CustomerDao customerDao;
    {
        baseDao=customerDao;
    }
    public void setCustomer(Customer customer){
        this.customer=customer;
        this.modelData=customer;
    }
    @Action(value = "showCustomer", results = { @Result(name = "success", type = "json", params = {
            "root", "customers" }) })
    public String showCustomer(){
        customers=new ArrayList<Customer>();
        for(int i=0;i<10;i++){
            Customer customer=new Customer();
            customer.setName("Name"+i);
            customer.setCode("0000"+i);
            customers.add(customer);
        }
        return SUCCESS;
    }

    @Action(value = "getCustomerInfoByCode", results = { @Result(name = "success", type = "json", params = {
            "root", "customer" }) })
    public String getCustomerInfoByCode(){
        if(customer!=null){
            customer.setAddress("上海市嘉定区");
            customer.setPhone("13621853980");
        }
        return SUCCESS;
    }

    public MBaseDao<Customer> getDao(){
        return customerDao;
    }

}
