package ggc.core;

import java.util.*;

public class BreakDown extends Sale{
    double _amountPaid;
    List<Product> _components;
    List<Integer> _quantities;
    List<Double> _prices;
    public BreakDown(int id, double baseValue, int quantity, Date paymentDate, String productId, String partnerId, double amountPaid,
    List<Product> products, List<Integer> quantities, List<Double> prices){
        super(id, baseValue, quantity, paymentDate, productId, partnerId);
        _amountPaid=amountPaid;
        _components= products;
        _quantities=quantities;
        _prices = prices;
    }

    public String buyToString(){
        StringBuilder message = new StringBuilder();
        int size = _components.size();
        for (int i=0; i<size; i++){
            String product = _components.get(i).getId();
            int quantity = _quantities.get(i);
            double price = _prices.get(i);
            if (i!=size-1)
                message.append(product).append(":").append(quantity).append(":").append((int)Math.round(price)).append("#");
            else
            message.append(product).append(":").append(quantity).append(":").append((int)Math.round(price));
        }
        return message.toString();
    }

    public boolean isPaid(){
        return true;
    }

    public String toString(){
        return "DESAGREGAÇÃO" + "|" + _id + "|" + _partnerId + "|" + _productId + "|" + _quantity + "|" + (int)Math.round(_baseValue)
         + "|" + (int)Math.round(_amountPaid) + "|" + _paymentDate.getDays() + "|" + buyToString();
    }

    double updatePrice(){
        return _amountPaid;
    }

    Date getDeadline(){
        return null;
    }

    void Pay(double price, Date currentDate){
        
    }

    void setAmountToPay(double amountToPay){

    }
}
