package com.vlantion.travelexperts8.Package;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Package implements Serializable {

    private int packageId;

    private BigDecimal pkgAgencyCommission;

    private BigDecimal pkgBasePrice;

    private String pkgDesc;

    private Date pkgEndDate;

    private String pkgName;

    private Date pkgStartDate;

    //Constructor
    public Package(int packageId, String pkgName, Date pkgEndDate,  Date pkgStartDate, BigDecimal pkgAgencyCommission, BigDecimal pkgBasePrice, String pkgDesc
                   ) {
        super();
        this.packageId = packageId;
        this.pkgName = pkgName;
        this.pkgAgencyCommission = pkgAgencyCommission;
        this.pkgBasePrice = pkgBasePrice;
        this.pkgDesc = pkgDesc;
        this.pkgEndDate = pkgEndDate;
        this.pkgStartDate = pkgStartDate;
    }

    public Package() {
    }

    public int getPackageId() {
        return this.packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public BigDecimal getPkgAgencyCommission() {
        return this.pkgAgencyCommission;
    }

    public void setPkgAgencyCommission(BigDecimal pkgAgencyCommission) {
        this.pkgAgencyCommission = pkgAgencyCommission;
    }

    public BigDecimal getPkgBasePrice() {
        return this.pkgBasePrice;
    }

    public void setPkgBasePrice(BigDecimal pkgBasePrice) {
        this.pkgBasePrice = pkgBasePrice;
    }

    public String getPkgDesc() {
        return this.pkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc = pkgDesc;
    }

    public Date getPkgEndDate() {
        return this.pkgEndDate;
    }

    public void setPkgEndDate(Date pkgEndDate) {
        this.pkgEndDate = pkgEndDate;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Date getPkgStartDate() {
        return this.pkgStartDate;
    }

    public void setPkgStartDate(Date pkgStartDate) {
        this.pkgStartDate = pkgStartDate;
    }


}