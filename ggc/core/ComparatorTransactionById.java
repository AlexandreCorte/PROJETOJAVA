package ggc.core;
import java.util.Comparator;

public class ComparatorTransactionById implements Comparator<Transaction>{
    public int compare(Transaction a, Transaction b){
      if (a.getId()<b.getId())
        return -1;
      else
        return 1;
    }
  }