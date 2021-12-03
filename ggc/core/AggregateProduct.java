package ggc.core;

//Tambem tem uma receita
public class AggregateProduct extends Product{
    private Recipe _recipe;


    AggregateProduct(String id, Recipe recipe){
        super(id);
        _recipe = recipe;
    }

    @Override
    public String toString() {
        return "" + _id + "|" + (int)Math.round(_maxPrice) + "|" + (int)Math.round(_totalStock) + "|" + _recipe;
    }

    Recipe getRecipe(){
        return _recipe;
    }

    int getLimitDays(){
        return 3;
    }
}
