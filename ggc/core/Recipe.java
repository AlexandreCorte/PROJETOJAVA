package ggc.core;

import java.io.Serializable;
import java.util.*;

public class Recipe implements Serializable {
    private double _alpha;
    List<Component> _components = new ArrayList<>();

    public Recipe(double alpha, List<Component> components){
        _alpha = alpha;
        _components.addAll(components);
    }

    double getAlpha() {
        return _alpha;
    }

    Collection<Component> getComponents() {
        return _components;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        int size = _components.size();
        for(int i=0; i<size; i++){
            Component component= _components.get(i);
            String productId = component.getProduct().getId();
            int productQuantity = component.getQuantity();
            if (i!=size-1){
            message.append(productId).append(":").append(productQuantity).append("#");
            }
            else{
            message.append(productId).append(":").append(productQuantity);
            }
        }
        return message.toString();
    }
}
