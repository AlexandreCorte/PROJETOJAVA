package ggc.core;
import java.util.Comparator;

public class ComparatorById implements Comparator<Partner>{
    public int compare(Partner a, Partner b){
      if (a.getId().toLowerCase().compareTo(b.getId().toLowerCase())<0)
        return -1;
      else
        return 1;
    }
  }