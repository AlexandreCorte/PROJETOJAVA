package ggc.core;

public class NormalStatus extends Status{
    private static NormalStatus _self;
    
    private NormalStatus(){
        super("NORMAL");
    }

    static Status getStatus(){
        if (_self==null)
            _self = new NormalStatus();
        return _self;
    }

    double PaymentP1(Date currentDate, Date deadLine, double price){
        return 0.9*price;
    }
    double PaymentP2(Date currentDate, Date deadLine, double price){
        return price;
    }
    double PaymentP3(Date currentDate, Date deadLine, double price){
        double baseFine = price*0.05;
        double fine = baseFine * currentDate.difference(deadLine);
        double amountPaid = price + fine;
        return amountPaid;
    }
    double PaymentP4(Date currentDate, Date deadLine, double price){
        double baseFine = price*0.10;
        double fine = baseFine * currentDate.difference(deadLine);
        double amountPaid = price + fine;
        return amountPaid;
    } 

    double updatePointsP1(double amountToPay, double points, Partner partner){
        double totalPoints = points + amountToPay*10;
        checkPromotion(totalPoints, partner);
        return totalPoints;
    }

    double updatePointsP2(double amountToPay, double points, Partner partner){
        double totalPoints = points + amountToPay*10;
        checkPromotion(totalPoints, partner);
        return totalPoints;
    }

    double updatePointsP3(double amountToPay, Date currentDate, Date deadLine, double points, Partner partner){
        return 0;
    }

    double updatePointsP4(double amountToPay, Date currentDate, Date deadLine, double points, Partner partner){
        return 0;
    }

    void demote(Partner partner){
        partner.setStatus(NormalStatus.getStatus());
    }
    
}