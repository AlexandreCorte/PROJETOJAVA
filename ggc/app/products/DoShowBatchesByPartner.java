package ggc.app.products;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import java.util.*;

import ggc.core.UnknownPartnerKey;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.core.Batch;

//FIXME import classes

/**
 * Show batches supplied by partner.
 */
class DoShowBatchesByPartner extends Command<WarehouseManager> {

  DoShowBatchesByPartner(WarehouseManager receiver) {
    super(Label.SHOW_BATCHES_SUPPLIED_BY_PARTNER, receiver);
    addStringField("id", Message.requestPartnerKey());
  }

  @Override
  public final void execute() throws CommandException {
    String id = stringField("id");
    try {
      List<Batch> batches = new ArrayList<Batch>(_receiver.printBatchesByPartner(id));
      for (Batch b : batches) {
        _display.addLine(b);
      }
      _display.display();
    } catch (UnknownPartnerKey ii) {
      throw new UnknownPartnerKeyException(id);
    }
  }
}
