package com.rockstar.buspassvitran.model;

/**
 * Created by rockstar on 22/1/19.
 */

public class BusPassData {

    private String Bus_Stand;
    private String STD_Code;
    private String Telephone;

    public String getBus_Stand() {
        return Bus_Stand;
    }

    public void setBus_Stand(String bus_Stand) {
        Bus_Stand = bus_Stand;
    }

    public String getSTD_Code() {
        return STD_Code;
    }

    public void setSTD_Code(String STD_Code) {
        this.STD_Code = STD_Code;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }
}
