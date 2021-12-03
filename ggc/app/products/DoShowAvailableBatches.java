package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Batch;
import java.util.*;
//FIXME import classes

/**
 * Show available batches.
 */
class DoShowAvailableBatches extends Command<WarehouseManager> {

  DoShowAvailableBatches(WarehouseManager receiver) {
    super(Label.SHOW_AVAILABLE_BATCHES, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    Collection<Batch> batches = new ArrayList<Batch>(_receiver.printBatches());
    for (Batch b: batches){
      _display.addLine(b);
    }
    _display.display();
  }

}
