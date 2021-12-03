package ggc.app.lookups;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
import ggc.core.Sale;
import ggc.core.SaleByCredit;

import java.util.*;
import ggc.core.UnknownPartnerKey;
import ggc.app.exception.UnknownPartnerKeyException;

//FIXME import classes

/**
 * Lookup payments by given partner.
 */
public class DoLookupPaymentsByPartner extends Command<WarehouseManager> {

  public DoLookupPaymentsByPartner(WarehouseManager receiver) {
    super(Label.PAID_BY_PARTNER, receiver);
    addStringField("PartnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("PartnerId");
    try {
      Collection<Sale> sales = new ArrayList<Sale>(_receiver.printSales(partnerId));
      for (Sale a : sales) {
          if (a.isPaid()) {
            _display.addLine(a.toString());
          }
      }
      _display.display();
    } catch (UnknownPartnerKey upke) {
      throw new UnknownPartnerKeyException(partnerId);
    }
  }

}
