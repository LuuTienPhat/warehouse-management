package com.example.warehousemanagement.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Receipt {
    int id;
    LocalDate date;
    ArrayList<ReceiptDetail> receiptDetails = new ArrayList<>();

    public Receipt() {
    }

    public Receipt(int id, LocalDate date, ArrayList<ReceiptDetail> receiptDetails) {
        this.id = id;
        this.date = date;
        this.receiptDetails = receiptDetails;
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
}
