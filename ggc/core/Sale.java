package ggc.core;

public abstract class Sale extends Transaction{
    public Sale(int id, double baseValue, int quantity, Date paymentDate, String productId, String partnerId){
        super(id, baseValue, quantity, paymentDate, productId, partnerId);

}
}