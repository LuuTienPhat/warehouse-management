package com.example.warehousemanagement.model;

public class ProductQuantityDetail {
    String wareHouseId;
    String wareHouseName;
    int quantity;
    String unit;
    String productId;

    public ProductQuantityDetail(String wareHouseId, String wareHouseName, int quantity, String unit, String productId) {
        this.wareHouseId = wareHouseId;
        this.wareHouseName = wareHouseName;
        this.quantity = quantity;
        this.unit = unit;
        this.productId = productId;
    }

    public ProductQuantityDetail() {
    }

    public String getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
