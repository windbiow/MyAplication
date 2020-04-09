package com.example.myapplication.dto;

public class Tag {

    Integer machineId ;
    Integer count ;
    Integer commodityId ;

    public Tag() {
    }

    public Tag(Integer machineId, Integer count, Integer commodityId) {
        this.machineId = machineId;
        this.count = count;
        this.commodityId = commodityId;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }
}
