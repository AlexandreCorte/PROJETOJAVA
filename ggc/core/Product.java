package ggc.core;

import java.io.Serializable;
import java.util.*;

/**
 * Class that represents the products that will be stored in the Warehouse
 * @author Alexandre Corte and Nuno Dendas
 */

public abstract class Product extends Subject implements Serializable {
    /**
     * _id is a String that represents the name of our product
     */
    protected String _id;
    /**
     * _batch is an array list that represents the set of batches that contains the product
     */
    protected List<Batch> _batch = new ArrayList<>();
    /**
     * The product has a different price in every batch, so _maxPrice represents the highest price  
     */
    protected double _maxPrice;
    /**
     * _totalStock represents the quantity of product stored in the Warehouse
     */
    protected int _totalStock;


    Notification notificationType(String event, Double price){
        switch(event){

            case "NEW":
                return new Notification("NEW", _id, price);

            case "BARGAIN":
                return new Notification("BARGAIN", _id, price);
        }
        return null;
    }

    @Override
    void update(String event, Double price){
        for(Observer obs: _observers){
            obs.receiveNotification(notificationType(event, price));
        }
    }

    abstract int getLimitDays();

    abstract Recipe getRecipe();

    /**
     * Constructor that receives 1 input which is the product id
     * @param id
     */
    public Product(String id){
        _id=id;
    }



    @Override
    /**
     * Check if two objects (in this case, products) are the same
     * @return true if the 2 products are the same and false if not
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return _id != null ? _id.equals(product.getId()) : product.getId() == null;
    }

    @Override
    /**
     * @return a hash code value for the object (int)
     */
    public int hashCode() {
        return _id != null ? _id.hashCode() : 0;
    }

    /**
     * Gets the id of the product
     * @return String id
     */
    String getId() {
        return _id;
    }

    Collection<Observer> getObservers(){
        return _observers;
    }

    /**
     * Gets the total stock of the product
     * @return double totalstock
     */
    int getTotalStock(){
        return _totalStock;
    }

    /**
     * Gets the max price of our product
     * @return double maxprice
     */
    double getMaxPrice() {
        return _maxPrice;
    }

    /**
     * add stock to the product
     * @param stock quantity of product to add
     */
    void addStock(int stock){
        _totalStock += stock;
    }

    /**
     * Gets the list of batches that contains the product
     * @return a list of batches
     */
    Collection<Batch> getBatches() {
        return _batch;
    }

    /**
     * Sets the max price
     * @param price product's max price
     */
    void setMaxPrice(double price){
        _maxPrice=price;
    }

}
