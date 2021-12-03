package ggc.core;

import pt.tecnico.uilib.menus.CommandException;

public class UnknownProductKey extends CommandException {

  public UnknownProductKey() {
    super("Unknown Product");
  }

}