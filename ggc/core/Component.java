package ggc.core;

import java.io.Serializable;

public class Component implements Serializable {
    private int _quantity;
    private Product _product;

    public Component(int quantity, Product product){
        _quantity = quantity;
        _product = product;
    }

    int getQuantity() {
        return _quantity;
    }

    Product getProduct() {
        return _product;
    }

}
