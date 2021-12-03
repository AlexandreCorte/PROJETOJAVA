package ggc.core;

import java.io.Serializable;

public interface DeliveryType extends Serializable{
    void sendNotification(Observer observer, Notification notification);
}
