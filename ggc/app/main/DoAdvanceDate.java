package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.app.exception.InvalidDateException;
import ggc.core.InvalidDate;

//FIXME import classes

/**
 * Advance current date.
 */
class DoAdvanceDate extends Command<WarehouseManager> {

  DoAdvanceDate(WarehouseManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    addIntegerField("advanceDate", Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws CommandException {
      Integer number = integerField("advanceDate");
      try {
        _receiver.advanceDate(number);
      } catch(InvalidDate ide){
        throw new InvalidDateException(number);
      }; 
  }
}
