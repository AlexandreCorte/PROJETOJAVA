package ggc.app.partners;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.*;
import ggc.app.exception.UnknownPartnerKeyException;
import java.util.*;
//FIXME import classes

/**
 * Show partner.
 */
class DoShowPartner extends Command<WarehouseManager> {

  DoShowPartner(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER, receiver);
    addStringField("id", "Identificador do parceiro: ");
  }

  @Override
  public void execute() throws CommandException {
    String id = stringField("id");
    try{
    Partner partner = _receiver.printPartner(id);
    Collection<Notification> notifications = new ArrayList<Notification>(_receiver.showPartnerNotifications(id));
    _display.popup(partner);
    for (Notification n: notifications){
      _display.addLine(n);
    }
    _display.display();
    _receiver.clearNotifications(id);
    }catch(UnknownPartnerKey ii){
      throw new UnknownPartnerKeyException(id);
    }
  }

}
