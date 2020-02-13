package com.awizom.restaurent.Model;

public class TaxModel {
    public int TaxID ;
    public String TName ;

    public int getTaxID() {
        return TaxID;
    }

    public void setTaxID(int taxID) {
        TaxID = taxID;
    }

    public String getTName() {
        return TName;
    }

    public void setTName(String TName) {
        this.TName = TName;
    }

    public float getTRate() {
        return TRate;
    }

    public void setTRate(float TRate) {
        this.TRate = TRate;
    }

    public float TRate ;
}
