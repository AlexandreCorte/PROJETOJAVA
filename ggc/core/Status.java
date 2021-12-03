package ggc.core;

import java.io.Serializable;

public abstract class Status implements Serializable{
    private String _status;

    protected Status(String status){
        _status=status;
    }

    public String toString(){
        return _status;
    }

    abstract double PaymentP1(Date curreDate, Date deadLine, double price);

    abstract double PaymentP2(Date curreDate, Date deadLine, double price);

    abstract double PaymentP3(Date curreDate, Date deadLine, double price);

    abstract double PaymentP4(Date curreDate, Date deadLine, double price);

    abstract double updatePointsP1(double amountToPay, double points, Partner partner);

    abstract double updatePointsP2(double amountToPay, double points, Partner partner);
    
    abstract double updatePointsP3(double amountToPay, Date currentDate, Date deadLine, double points, Partner partner);

    abstract double updatePointsP4(double amountToPay, Date currentDate, Date deadLine, double points, Partner partner);

    abstract void demote(Partner partner);

    void checkPromotion(double points, Partner partner){
        if(points > 25000){
            partner.setStatus(EliteStatus.getStatus());
        }

        else if(points > 2000){
            partner.setStatus(SelectionStatus.getStatus());
        }

        else{
            partner.setStatus(NormalStatus.getStatus());

        }
    }

}