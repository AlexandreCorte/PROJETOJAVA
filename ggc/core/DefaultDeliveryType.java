package ggc.core;

import java.io.Serializable;

public class DefaultDeliveryType implements Serializable, DeliveryType{
    private static DefaultDeliveryType _defaultDeliveryType;

    private DefaultDeliveryType(){

    }

    public static DefaultDeliveryType getDefaultDeliveryType(){
        if (_defaultDeliveryType==null){
            _defaultDeliveryType=new DefaultDeliveryType();
        }
        return _defaultDeliveryType;
    }

    public void sendNotification(Observer observer, Notification notification){
        observer.addNotification(notification);
    }

}
