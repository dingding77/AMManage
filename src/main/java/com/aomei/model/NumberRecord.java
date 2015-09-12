package com.aomei.model;

import java.io.Serializable;

public class NumberRecord implements Serializable {
    private Integer id=1;

    private Integer ap;

    private Integer ampo;

    private Integer am;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAp() {
        return ap;
    }

    public void setAp(Integer ap) {
        this.ap = ap;
    }

    public Integer getAmpo() {
        return ampo;
    }

    public void setAmpo(Integer ampo) {
        this.ampo = ampo;
    }

    public Integer getAm() {
        return am;
    }

    public void setAm(Integer am) {
        this.am = am;
    }
}