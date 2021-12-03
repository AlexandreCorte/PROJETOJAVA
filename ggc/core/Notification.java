package ggc.core;

import java.io.Serializable;

public class Notification implements Serializable{
    String _type;
    String _productId;
    double _price;

    protected Notification(String type, String productId, double price) {
        _type = type;
        _productId = productId;
        _price = price;

    }

    String getType() {
        return _type;
    }

    String getProductId() {
        return _productId;
    }

    Double getPrice() {
        return _price;
    }

    public String toString(){
        return "" + _type + "|" + _productId + "|" + (int)Math.round(_price);
    }
}
