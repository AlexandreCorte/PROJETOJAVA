package ggc.core;

public class SimpleProduct extends Product{

    public SimpleProduct(String id){
        super(id);
    }

    Recipe getRecipe(){
        return null;
    }

    @Override
    public String toString() {
        return "" + _id + "|" + (int)Math.round(_maxPrice) + "|" + (int)Math.round(_totalStock);
    }

    int getLimitDays(){
        return 5;
    }
}
