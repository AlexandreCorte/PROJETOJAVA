package ggc.core;

import pt.tecnico.uilib.menus.CommandException;

public class UnknownTransactionKey extends CommandException{
    public UnknownTransactionKey(){
        super("Unknown Transaction");
    }
}