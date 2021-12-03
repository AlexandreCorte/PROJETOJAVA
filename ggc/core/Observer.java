package ggc.core;

import java.io.Serializable;

interface Observer extends Serializable {
    public void notify(Notification notification);
    public void addNotification(Notification notification);
    public void receiveNotification(Notification notification);
}