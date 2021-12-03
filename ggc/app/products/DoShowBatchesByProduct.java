package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import java.util.*;
import ggc.core.Batch;
import ggc.core.UnknownProductKey;
import ggc.app.exception.UnknownProductKeyException;

//FIXME import classes

/**
 * Show all products.
 */
class DoShowBatchesByProduct extends Command<WarehouseManager> {

  DoShowBatchesByProduct(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_BY_PRODUCT, receiver);
    addStringField("id", Message.requestProductKey());
  }

  @Override
  public final void execute() throws CommandException {
    String id = stringField("id");
    try {
      List<Batch> batches = new ArrayList<Batch>(_receiver.printBatchesByProduct(id));
      for (Batch b : batches) {
        _display.addLine(b);
      }
      _display.display();
    } catch (UnknownProductKey ii) {
      throw new UnknownProductKeyException(id);
    }
  }
}
