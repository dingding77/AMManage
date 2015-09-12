package com.aomei.query;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/10.
 */
public class UserRoleQuery extends BaseQuery{
        private Integer id;

        private Integer userid;

        private Integer roleid;

        private String isDelete;

        private Date createTime;

        private Date modifyTime;

        private static final long serialVersionUID = 1L;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getRoleid() {
            return roleid;
        }

        public void setRoleid(Integer roleid) {
            this.roleid = roleid;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete == null ? null : isDelete.trim();
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
        }
}
