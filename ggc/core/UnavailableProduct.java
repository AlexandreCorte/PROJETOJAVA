package ggc.core;

import pt.tecnico.uilib.menus.CommandException;

public class UnavailableProduct extends CommandException{
    public UnavailableProduct(){
        super("UnavailableProduct");
    }
}