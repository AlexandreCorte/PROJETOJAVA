package ggc.core;
import java.util.Comparator;

public class ComparatorProducts implements Comparator<Product>{
    public int compare(Product a, Product b){
      if (a.getId().toLowerCase().compareTo(b.getId().toLowerCase())<0)
        return -1;
      else
        return 1;
    }
  }