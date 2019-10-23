package com.vlantion.travelexperts8.Supplier;

public class Supplier {
    private int supplierId;
    private String supName;

    public Supplier () {
    }

    public Supplier(int supplierId, String supName) {
        super();
        this.supplierId = supplierId;
        this.supName = supName;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }
}
