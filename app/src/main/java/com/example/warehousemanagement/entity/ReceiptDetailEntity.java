package com.example.warehousemanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "receipt_detail", primaryKeys = {"receipt_id", "product_id"})
public class ReceiptDetailEntity {

    @ColumnInfo(name = "receipt_id")
    private String receiptId;

    @ColumnInfo(name = "product_id")
    private String productId;

    @ColumnInfo(name = "unit")
    private String unit;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public ReceiptDetailEntity() {
    }

    public ReceiptDetailEntity(String receiptId, String productId, String unit, int quantity) {
        this.receiptId = receiptId;
        this.productId = productId;
        this.unit = unit;
        this.quantity = quantity;
    }


    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
