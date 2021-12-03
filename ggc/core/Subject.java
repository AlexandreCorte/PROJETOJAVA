package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

abstract class Subject implements Serializable {
    protected List<Observer> _observers = new ArrayList<Observer>();

    abstract void update(String event, Double price);
    void addObserver(Observer obs) {
        _observers.add(obs);
    }
    void removeObserver(Observer obs) {
        _observers.remove(obs);
    }
}