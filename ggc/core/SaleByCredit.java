package ggc.core;

public class SaleByCredit extends Sale {
    private Date _deadline;
    private double _amountPaid;
    private boolean _paid;

    public SaleByCredit(int id, double baseValue, int quantity, Date paymentDate, String productId, String partnerId,
            Date deadline, double amountPaid) {
        super(id, baseValue, quantity, paymentDate, productId, partnerId);
        _deadline = deadline;
        _amountPaid = amountPaid;
        _paid = false;
    }
    
    void setPaymentDate(Date paymentDate){
        _paymentDate = paymentDate;

    }

    public boolean isPaid(){
        return _paid;
    }

    void Pay(double amountPaid, Date paymentDate){
        _paid = true;
        _paymentDate = paymentDate;
        _amountPaid = amountPaid;
    }

    Date getDeadline() {
        return _deadline;
    }

    @Override
    public String toString() {
        return "VENDA" + "|" + _id + "|" + _partnerId + "|" + _productId + "|" + _quantity + "|" + (int)Math.round(_baseValue)
         + "|" + (int)Math.round(_amountPaid)  + "|" + _deadline.getDays() + "|" + _paymentDate.getDays();
    }

    public String toStringWithoutDate() {
        return "VENDA" + "|" + _id + "|" + _partnerId + "|" + _productId + "|" + _quantity + "|" + (int)Math.round(_baseValue)
         + "|" + (int)Math.round(_amountPaid) + "|" + _deadline.getDays();
    }

    double updatePrice(){
        if (this.isPaid()){
            return _amountPaid;
        }
        else{
            return _baseValue;
        }
    }

    void setAmountToPay(double amountToPay){
        _amountPaid = amountToPay;
    }
}