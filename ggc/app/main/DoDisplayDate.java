package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;


class DoDisplayDate extends Command<WarehouseManager> {

  DoDisplayDate(WarehouseManager receiver) {
    super(Label.SHOW_DATE, receiver);
  }

  @Override
  public final void execute() throws CommandException {

    int days = _receiver.displayDate();

    _display.popup(Message.currentDate(days));

  }

}
