package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import java.util.*;
import ggc.core.Acquisition;
//FIXME import classes
import ggc.core.UnknownPartnerKey;
import ggc.app.exception.UnknownPartnerKeyException;

/**
 * Show all transactions for a specific partner.
 */
class DoShowPartnerAcquisitions extends Command<WarehouseManager> {

  DoShowPartnerAcquisitions(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_ACQUISITIONS, receiver);
    addStringField("PartnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("PartnerId");
    try{
    Collection<Acquisition> acquisitions = new ArrayList<Acquisition>(_receiver.printAcquisitions(partnerId));
    for (Acquisition a: acquisitions){
      _display.addLine(a);
    }
    _display.display();
    }catch (UnknownPartnerKey upke){
      throw new UnknownPartnerKeyException(partnerId);
    }
  }
}
