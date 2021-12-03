package ggc.app.partners;

import java.util.*;
import ggc.core.Partner;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Show all partners.
 */
class DoShowAllPartners extends Command<WarehouseManager> {

  DoShowAllPartners(WarehouseManager receiver) {
    super(Label.SHOW_ALL_PARTNERS, receiver);
  }

  public void execute() throws CommandException {
    Collection<Partner> partners = new ArrayList<Partner>(_receiver.printPartners());
    for (Partner p: partners){
      _display.addLine(p);
    }
    _display.display();
  }
}
