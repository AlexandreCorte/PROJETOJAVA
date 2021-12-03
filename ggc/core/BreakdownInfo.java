package ggc.core;

import java.io.Serializable;
import java.util.*;

public class BreakdownInfo implements Serializable{
    private List<Product> _products;
    private List<Integer> _quantities;
    private List<Double> _prices;
    private double _price;

    public BreakdownInfo(List<Product> products, List<Integer> quantities, List<Double> prices, double price){
        _products = products;
        _quantities = quantities;
        _prices = prices;
        _price = price;
    }

     List<Product> getProducts(){
        return _products;
    }

     List<Integer> getQuantities(){
        return _quantities;
    }
    List<Double> getPrices(){
        return _prices;
    }
    
    double getPrice(){
        return _price;
    }
}