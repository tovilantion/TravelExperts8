package com.vlantion.travelexperts8.Booking;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {

    private int bookingId;
    private Date bookingDate;
    private String bookingNo;
    private int travelerCount;
    private int customerId;
    private String tripTypeId;
    private int packageId;

    public Booking(int bookingId, Date bookingDate, String bookingNo, int travelerCount, int customerId, String tripTypeId, int packageId) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingNo = bookingNo;
        this.travelerCount = travelerCount;
        this.customerId = customerId;
        this.tripTypeId = tripTypeId;
        this.packageId = packageId;
    }

    public int getBookingId() {
        return this.bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookingDate() {
        return this.bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingNo() {
        return this.bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public int getTravelerCount() {
        return this.travelerCount;
    }

    public void setTravelerCount(int travelerCount) {
        this.travelerCount = travelerCount;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTripTypeId() {
        return this.tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

    public int getPackageId() {
        return this.packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    @Override
    public String toString(){
        return this.getBookingId() + " " + this.getBookingDate() + " " + this.getBookingNo();
    }
}
