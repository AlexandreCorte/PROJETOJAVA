package ggc.core;
public class EliteStatus extends Status {
    private static EliteStatus _self;

    private EliteStatus(){
        super("ELITE");
    }

    static Status getStatus(){
        if (_self==null)
            _self = new EliteStatus();
        return _self;
    }

    double PaymentP1(Date currentDate, Date deadLine, double price){
        return 0.9*price;
    }
     
    double PaymentP2(Date currentDate, Date deadLine, double price){
        return 0.9*price;
    }
     
    double PaymentP3(Date currentDate, Date deadLine, double price){
        return 0.95*price;

    }
    
    double PaymentP4(Date currentDate, Date deadLine, double price){
        return price;
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
        return points;
    }

    double updatePointsP4(double amountToPay, Date currentDate, Date deadLine, double points, Partner partner){
        if (currentDate.difference(deadLine) > 15){
            demote(partner);
            return points*0.25;
        }
        return points;
    }

    void demote(Partner partner){
        partner.setStatus(SelectionStatus.getStatus());
    }
}