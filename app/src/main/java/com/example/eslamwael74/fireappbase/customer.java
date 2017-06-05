package com.example.eslamwael74.fireappbase;

/**
 * Created by Eslam Wael on 4/15/2017.
 */

public class customer {

    private String name;
    private String custId;
    private String pNum;

    public customer(String name, String custId, String pNum) {
        this.name = name;
        this.custId = custId;
        this.pNum = pNum;
    }
    public customer(){

    }

    public String getName() {
        return name;
    }

    public String getCustId() {
        return custId;
    }

    public String getpNum() {
        return pNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }
}
