package ggc.core;

public class SelectionStatus extends Status{
    private static SelectionStatus _self;

    private SelectionStatus(){
        super("SELECTION");
    }

    static Status getStatus(){
        if (_self==null)
            _self = new SelectionStatus();
        return _self;
    }

    double PaymentP1(Date currentDate, Date deadLine, double price){
        return 0.9*price;
    }
    
    double PaymentP2(Date currentDate, Date deadLine, double price){
        double amountPaid=0;
        if( deadLine.difference(currentDate) >= 2){
            amountPaid = 0.95 * price;
        }
        else{
            amountPaid=price;
        }
        return amountPaid;
    }
    
    double PaymentP3(Date currentDate, Date deadLine, double price){
        double amountPaid = 0;
        if(currentDate.difference(deadLine) <= 1){
            amountPaid=price;
        }
        else{
            double baseFine = 0.02 * price;
            double fine = currentDate.difference(deadLine) * baseFine;
            amountPaid = price + fine;
        }
        return amountPaid;
    }
    
    double PaymentP4(Date currentDate, Date deadLine, double price){
        double baseFine = 0.05 * price;
        double fine = currentDate.difference(deadLine) * baseFine;
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
        if (currentDate.difference(deadLine) > 2){
            demote(partner);
            return points*0.10;
        }
        else{
            return points;
        }

    }

    double updatePointsP4(double amountToPay, Date currentDate, Date deadLine, double points, Partner partner){
        demote(partner);
        return points*0.10;
    }

    void demote(Partner partner){
        partner.setStatus(NormalStatus.getStatus());
    }
}