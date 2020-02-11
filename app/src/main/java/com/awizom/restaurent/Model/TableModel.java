package com.awizom.restaurent.Model;

public class TableModel {

    public int TabID;
    public String TabName;
    public String TabType;

    public int getTabID() {
        return TabID;
    }

    public void setTabID(int tabID) {
        TabID = tabID;
    }

    public String getTabName() {
        return TabName;
    }

    public void setTabName(String tabName) {
        TabName = tabName;
    }

    public String getTabType() {
        return TabType;
    }

    public void setTabType(String tabType) {
        TabType = tabType;
    }

    public int getSeatNum() {
        return SeatNum;
    }

    public void setSeatNum(int seatNum) {
        SeatNum = seatNum;
    }

    public int getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(int isBooked) {
        this.isBooked = isBooked;
    }

    public int SeatNum;
    public int isBooked;
}
