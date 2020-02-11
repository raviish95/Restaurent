package com.awizom.restaurent.Model;

public class FoodCategoryModel {
    public int FoodID;
    public String FCategory;

    public int getFoodID() {
        return FoodID;
    }

    public void setFoodID(int foodID) {
        FoodID = foodID;
    }

    public String getFCategory() {
        return FCategory;
    }

    public void setFCategory(String FCategory) {
        this.FCategory = FCategory;
    }
}
