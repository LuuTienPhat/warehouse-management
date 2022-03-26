package com.example.warehousemanagement.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Receipt {
    private int id;
    private LocalDate date;
    private ArrayList<ReceiptDetail> receiptDetails = new ArrayList<>();
    private String warehouseId;

    public Receipt() {
    }

    public Receipt(int id, LocalDate date, String warehouseId, ArrayList<ReceiptDetail> receiptDetails) {
        this.id = id;
        this.date = date;
        this.receiptDetails = receiptDetails;
        this.warehouseId = warehouseId;
    }

    public int countNumberOfProductTypes() {
        int numberOfTypes = 0;

        return numberOfTypes;
    }

    public ArrayList<ReceiptDetail> getReceiptDetails() {
        return receiptDetails;
    }

    public void setReceiptDetails(ArrayList<ReceiptDetail> receiptDetails) {
        this.receiptDetails = receiptDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
}
