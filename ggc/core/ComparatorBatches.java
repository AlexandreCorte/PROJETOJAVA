package ggc.core;

import java.util.Comparator;

public class ComparatorBatches implements Comparator<Batch> {
    public int compare(Batch a, Batch b) {
        if (a.getProductId().toLowerCase().compareTo(b.getProductId().toLowerCase()) < 0)
            return -1;
        else if (a.getProductId().toLowerCase().compareTo(b.getProductId().toLowerCase()) > 0)
            return 1;
        else {
            if (a.getPartnerId().toLowerCase().compareTo(b.getPartnerId().toLowerCase()) < 0)
                return -1;
            else if (a.getPartnerId().toLowerCase().compareTo(b.getPartnerId().toLowerCase()) > 0)
                return 1;
            else {
                if (a.getPrice() < b.getPrice())
                    return -1;
                else if (a.getPrice() > b.getPrice())
                    return 1;
                else {
                    if (a.getQuantity() < b.getQuantity())
                        return -1;
                    else
                        return 1;
                }
            }
        }
    }
}