package com.example.warehousemanagement.model;

public class ReceiptDetail {
    private int receiptId, quantity;
    private String productId, unit;

    public ReceiptDetail() {
    }

    public ReceiptDetail(int receiptId, String productId, String unit, int quantity) {
        this.receiptId = receiptId;
        this.quantity = quantity;
        this.productId = productId;
        this.unit = unit;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
