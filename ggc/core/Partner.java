package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

public class Partner implements Serializable , Observer{
    private String _name;
    private String _address;
    private String _id;
    private Status _status;
    private double _points;
    private double _buyValue; //acquisition price
    private double _sellValue; //sell price
    private double _paidValue; //sell price with fine
    private List<Batch> _batch = new ArrayList<>();
    private List<Acquisition> _acquisitions = new ArrayList<Acquisition>();
    private List<Sale> _sales = new ArrayList<Sale>();
    private List<Notification> _notifications = new ArrayList<>();
    private DeliveryType _deliveryType;

    public Partner(String name, String id, String address) {

        _name = name;
        _id = id;
        _address = address;
        _status = NormalStatus.getStatus();
        _deliveryType = DefaultDeliveryType.getDefaultDeliveryType();
    }


    Collection<Acquisition> getAcquisitions(){
        return _acquisitions;
    }

    Collection<Notification> getNotifications(){
        return _notifications;
    }

    void addBuyValue(double price){
        _buyValue+=price;
    }

    void addSellValue(double price){
        _sellValue+=price;
    }

    void addPaidValue(double price){
        _paidValue+=price;
    }

    Collection<Sale> getSales(){
        return _sales;
    }

    String getAddress() {
        return _address;
    }

    void addPoints(double points){
        _points += points;
    }

    void setStatus(Status status){
        _status = status;
    }

    void setPoints(double points){
        _points = points;

    }

    void updatePoints(double points){
        _points+=points;
    }


    void checkPromotion(){
        if(_points > 25000){
            setStatus(EliteStatus.getStatus());
        }

        else if(_points > 2000){
            setStatus(SelectionStatus.getStatus());
        }

        else{
            setStatus(NormalStatus.getStatus());

        }

    }

    void updatePaidValue(double paidValue){
        _paidValue += paidValue;
    }

    String getName() {
        return _name;

    }

    String getId() {
        return _id;
    }

    Double getPoints() {
        return _points;
    }

    Status getStatus() {
        return _status;
    }

    List<Batch> getBatches() {
        return _batch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Partner partner = (Partner) o;

        return _id != null ? _id.toUpperCase().equals(partner.getId().toUpperCase()) : partner.getId() == null;
    }

    @Override
    public int hashCode() {
        return _id != null ? _id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "" + _id + "|" + _name + "|" + _address + "|" + _status + "|" + (int)Math.round(_points) + "|" + 
        (int)Math.round(_buyValue) + "|" + (int)Math.round(_sellValue) + "|" + (int)Math.round(_paidValue);
    }

    @Override
    public void notify(Notification notification) {
        _notifications.add(notification);

    }

    public void addNotification(Notification notification){
        _notifications.add(notification);
    }

    public void receiveNotification(Notification notification){
        _deliveryType.sendNotification(this, notification);
    }
}
