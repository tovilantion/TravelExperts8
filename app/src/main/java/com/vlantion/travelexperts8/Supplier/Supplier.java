package com.vlantion.travelexperts8.Supplier;

import java.io.Serializable;

public class Supplier implements Serializable {
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


    @Override
    public String toString(){
        return this.supplierId + " " + this.supName;
    }
}
