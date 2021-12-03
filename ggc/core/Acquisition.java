package ggc.core;


public class Acquisition extends Transaction{
    public Acquisition(int id, double baseValue, int quantity, Date paymentDate, String productId, String partnerId){
        super(id, baseValue, quantity, paymentDate, productId, partnerId);
    }

    @Override
    public String toString(){
        return "COMPRA" + "|" + _id + "|" + _partnerId + "|" + _productId + "|" + _quantity + "|" + (int)Math.round(_baseValue)
         + "|" + _paymentDate.getDays();
    }

    public boolean isPaid(){
        return true;
    }

    double updatePrice(){
        return -(_baseValue);
    }

    Date getDeadline(){
        return null;
    }

    void Pay(double price, Date currentDate){
        
    }

    void setAmountToPay(double amountToPay){

    }

}