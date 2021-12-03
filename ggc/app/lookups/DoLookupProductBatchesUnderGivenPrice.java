package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Batch;
import java.util.*;

//FIXME import classes

/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductBatchesUnderGivenPrice extends Command<WarehouseManager> {

  public DoLookupProductBatchesUnderGivenPrice(WarehouseManager receiver) {
    super(Label.PRODUCTS_UNDER_PRICE, receiver);
    addRealField("price", Message.requestPriceLimit());
    //FIXME add command fields
  }

  @Override
  public void execute() throws CommandException {
    double price = realField("price");
    Collection<Batch> batches = new ArrayList<Batch>(_receiver.getBatchesUnderPrice(price));
    for (Batch b: batches){
      if (b.getPrice()<price){
      _display.addLine(b);
      }
    }
    _display.display();
  }
}
