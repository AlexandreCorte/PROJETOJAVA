package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable{
    protected int _id;
    protected Date _paymentDate;
    protected double _baseValue;
    protected int _quantity;
    protected String _productId;
    protected String _partnerId;

    public Transaction(int id, double baseValue, int quantity, Date paymentDate, String productId, String partnerId){
        _id = id;
        _baseValue=baseValue;
        _quantity=quantity;
        _paymentDate = paymentDate;
        _productId = productId;
        _partnerId = partnerId;
    }

    String getPartnerId(){
        return _partnerId;
    }

    String getProductId(){
        return _productId;
    }

    int getId(){
        return _id;
    }

    Date getPaymentDate(){
        return _paymentDate;
    }

    double getBaseValue(){
        return _baseValue;
    }

    int getQuantity(){
        return _quantity;
    }

    public abstract boolean isPaid();

    abstract double updatePrice();

    abstract Date getDeadline();

    abstract void Pay(double amountToPay, Date currentDate);

    abstract void setAmountToPay(double amountToPay);
}