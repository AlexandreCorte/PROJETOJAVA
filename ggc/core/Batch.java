package ggc.core;

import java.io.Serializable;

/**
 * Class that represents a batch of products 
 * Batches can be provided by a partner and we can also sell them to other partners
 * @author Alexandre Corte and Nuno Dendas
 */
public class Batch implements Serializable {
    /**
     * represents the cost of the product contained in the batch
     */
    private double _price;
    /**
     * represents the amount of product stored in the batch
     */
    private int _quantity;
    /**
     * represents the partner that provided the batch to us
     */
    private Partner _partner;
    /**
     * represents the product stored in the batch
     */
    private Product _product;


    /**
     * Constructor that receives 4 input values
     * @param price
     * @param quantity
     * @param partner
     * @param product
     */
    public Batch(double price, int quantity, Partner partner, Product product){
        _price = price;
        _quantity = quantity;
        _partner = partner;
        _product = product;
    }

    /**
     * Gets the price of the product
     * @return double price
     */
    public double getPrice(){
        return _price;
    }

    /**
     * Gets quantity of the product
     * @return int quantity
     */
    int getQuantity(){
        return _quantity;
    }

    void updateQuantity(int quantity){
        _quantity+=quantity;
    }

    void setQuantity(int quantity){
        _quantity=quantity;
    }

    /**
     * Gets the partner that provided the batch
     * @return Partner partner
     */
    Partner gePartner(){
        return _partner;
    }

    /**
     * Gets the product stored in the batch
     * @return Product product
     */
    Product getProduct(){
        return _product;
    }

    /**
     * Gets the id of the product
     * @return String id
     */
    String getProductId(){
        return _product.getId();
    }

    /**
     * Gets the partner id
     * @return String partner id
     */
    String getPartnerId(){
        return _partner.getId();
    }

    @Override
    /**
     * method used to print a batch in the terminal
     * @return String with all the informations about the batch
     */
    public String toString(){
        return "" + getProductId() + "|" + getPartnerId() + "|" + (int)Math.round(_price) + "|" + _quantity;
    }
}

