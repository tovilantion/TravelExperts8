package com.vlantion.travelexperts8.Customer;

import java.io.Serializable;

public class Customer implements Serializable {

    private int customerId;
    private String custFirstName;
    private String custLastName;
    private String custAddress;
    private String custCity;
    private String custProv;
    private String custPostal;
    private String custCountry;
    private String custHomePhone;
    private String custBusPhone;
    private String custEmail;
    private int agentId;

    public Customer(int customerId, String custFirstName,
                    String custLastName, String custAddress,
                    String custCity, String custProv,
                    String custPostal, String custCountry,
                    String custHomePhone, String custBusPhone,
                    String custEmail, int agentId) {

        this.customerId = new Integer(customerId);
        this.custFirstName = custFirstName;
        this.custLastName = custLastName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProv = custProv;
        this.custPostal = custPostal;
        this.custCountry = custCountry;
        this.custHomePhone = custHomePhone;
        this.custBusPhone = custBusPhone;
        this.custEmail = custEmail;
        this.agentId = agentId;
    }

    public int getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }
    public String getCustFirstName() {
        return this.custFirstName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }
    public String getCustLastName() {
        return this.custLastName;
    }

    public String getCustAddress() {
        return this.custAddress;
    }
    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return this.custCity;
    }
    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProv() {
        return this.custProv;
    }
    public void setCustProv(String custProv) {
        this.custProv = custProv;
    }


    public String getCustPostal() {
        return this.custPostal;
    }
    public void setCustPostal(String custPostal) {
        this.custPostal = custPostal;
    }

    public String getCustCountry() {
        return this.custCountry;
    }
    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }

    public String getCustHomePhone() {
        return this.custHomePhone;
    }
    public void setCustHomePhone(String custHomePhone) {
        this.custHomePhone = custHomePhone;
    }

    public String getCustBusPhone() {
        return this.custBusPhone;
    }
    public void setCustBusPhone(String custBusPhone) {
        this.custBusPhone = custBusPhone;
    }

    public String getCustEmail() {
        return this.custEmail;
    }
    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public int getAgentId() {
        return this.agentId;
    }
    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    @Override
    public String toString(){
        return this.getCustFirstName() + " " + this.getCustLastName();
    }

}
