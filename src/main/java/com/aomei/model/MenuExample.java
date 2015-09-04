package com.aomei.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MenuExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("level not between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andParentidIsNull() {
            addCriterion("parentId is null");
            return (Criteria) this;
        }

        public Criteria andParentidIsNotNull() {
            addCriterion("parentId is not null");
            return (Criteria) this;
        }

        public Criteria andParentidEqualTo(Integer value) {
            addCriterion("parentId =", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotEqualTo(Integer value) {
            addCriterion("parentId <>", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidGreaterThan(Integer value) {
            addCriterion("parentId >", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidGreaterThanOrEqualTo(Integer value) {
            addCriterion("parentId >=", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidLessThan(Integer value) {
            addCriterion("parentId <", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidLessThanOrEqualTo(Integer value) {
            addCriterion("parentId <=", value, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidIn(List<Integer> values) {
            addCriterion("parentId in", values, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotIn(List<Integer> values) {
            addCriterion("parentId not in", values, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidBetween(Integer value1, Integer value2) {
            addCriterion("parentId between", value1, value2, "parentid");
            return (Criteria) this;
        }

        public Criteria andParentidNotBetween(Integer value1, Integer value2) {
            addCriterion("parentId not between", value1, value2, "parentid");
            return (Criteria) this;
        }

        public Criteria andResourcesIsNull() {
            addCriterion("resources is null");
            return (Criteria) this;
        }

        public Criteria andResourcesIsNotNull() {
            addCriterion("resources is not null");
            return (Criteria) this;
        }

        public Criteria andResourcesEqualTo(String value) {
            addCriterion("resources =", value, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesNotEqualTo(String value) {
            addCriterion("resources <>", value, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesGreaterThan(String value) {
            addCriterion("resources >", value, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesGreaterThanOrEqualTo(String value) {
            addCriterion("resources >=", value, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesLessThan(String value) {
            addCriterion("resources <", value, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesLessThanOrEqualTo(String value) {
            addCriterion("resources <=", value, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesLike(String value) {
            addCriterion("resources like", value, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesNotLike(String value) {
            addCriterion("resources not like", value, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesIn(List<String> values) {
            addCriterion("resources in", values, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesNotIn(List<String> values) {
            addCriterion("resources not in", values, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesBetween(String value1, String value2) {
            addCriterion("resources between", value1, value2, "resources");
            return (Criteria) this;
        }

        public Criteria andResourcesNotBetween(String value1, String value2) {
            addCriterion("resources not between", value1, value2, "resources");
            return (Criteria) this;
        }

        public Criteria andIconImgIsNull() {
            addCriterion("icon_img is null");
            return (Criteria) this;
        }

        public Criteria andIconImgIsNotNull() {
            addCriterion("icon_img is not null");
            return (Criteria) this;
        }

        public Criteria andIconImgEqualTo(String value) {
            addCriterion("icon_img =", value, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgNotEqualTo(String value) {
            addCriterion("icon_img <>", value, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgGreaterThan(String value) {
            addCriterion("icon_img >", value, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgGreaterThanOrEqualTo(String value) {
            addCriterion("icon_img >=", value, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgLessThan(String value) {
            addCriterion("icon_img <", value, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgLessThanOrEqualTo(String value) {
            addCriterion("icon_img <=", value, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgLike(String value) {
            addCriterion("icon_img like", value, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgNotLike(String value) {
            addCriterion("icon_img not like", value, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgIn(List<String> values) {
            addCriterion("icon_img in", values, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgNotIn(List<String> values) {
            addCriterion("icon_img not in", values, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgBetween(String value1, String value2) {
            addCriterion("icon_img between", value1, value2, "iconImg");
            return (Criteria) this;
        }

        public Criteria andIconImgNotBetween(String value1, String value2) {
            addCriterion("icon_img not between", value1, value2, "iconImg");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andMenuTyeIsNull() {
            addCriterion("menu_tye is null");
            return (Criteria) this;
        }

        public Criteria andMenuTyeIsNotNull() {
            addCriterion("menu_tye is not null");
            return (Criteria) this;
        }

        public Criteria andMenuTyeEqualTo(String value) {
            addCriterion("menu_tye =", value, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeNotEqualTo(String value) {
            addCriterion("menu_tye <>", value, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeGreaterThan(String value) {
            addCriterion("menu_tye >", value, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeGreaterThanOrEqualTo(String value) {
            addCriterion("menu_tye >=", value, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeLessThan(String value) {
            addCriterion("menu_tye <", value, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeLessThanOrEqualTo(String value) {
            addCriterion("menu_tye <=", value, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeLike(String value) {
            addCriterion("menu_tye like", value, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeNotLike(String value) {
            addCriterion("menu_tye not like", value, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeIn(List<String> values) {
            addCriterion("menu_tye in", values, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeNotIn(List<String> values) {
            addCriterion("menu_tye not in", values, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeBetween(String value1, String value2) {
            addCriterion("menu_tye between", value1, value2, "menuTye");
            return (Criteria) this;
        }

        public Criteria andMenuTyeNotBetween(String value1, String value2) {
            addCriterion("menu_tye not between", value1, value2, "menuTye");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}