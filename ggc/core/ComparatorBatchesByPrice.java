package ggc.core;

import java.util.Comparator;

public class ComparatorBatchesByPrice implements Comparator<Batch>{
    public int compare(Batch a, Batch b){
        if (a.getPrice()<b.getPrice()){
            return -1;
        }
        else{
            return 1;
        }
    }
}

