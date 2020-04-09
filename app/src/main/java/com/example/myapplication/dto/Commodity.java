package com.example.myapplication.dto;

public class Commodity {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 价格
     */
    private Double price;

    /**
     * 商品分类
     */
    private String categoryName;

    /**
     * 商品状态
     */
    private Integer commodityStatus;

    /**
     * 图片
     */
    private String picture;

    /**
     * 备注
     */
    private String comment;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 机器主键
     */
    private Integer machineId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCommodityStatus() {
        return commodityStatus;
    }

    public void setCommodityStatus(Integer commodityStatus) {
        this.commodityStatus = commodityStatus;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Commodity() {
    }

    public Commodity(Integer id, String commodityName, Double price, String categoryName, Integer commodityStatus, String picture, String comment, Integer count, Integer machineId) {
        this.id = id;
        this.commodityName = commodityName;
        this.price = price;
        this.categoryName = categoryName;
        this.commodityStatus = commodityStatus;
        this.picture = picture;
        this.comment = comment;
        this.count = count;
        this.machineId = machineId;
    }

}
