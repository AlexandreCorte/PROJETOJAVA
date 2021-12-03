package ggc.core;

import java.io.Serializable;

public class Date implements Serializable {

    private int _days;

    public Date(){

        _days = 0;

    }

    public Date(int days){
        _days=days;
    }

    int getDays() {
        return _days;
    }

    int addDays(int days ){

        _days += days;

        return _days;
    }

    int difference( Date date ){

        return _days - date.getDays();
    }
}
