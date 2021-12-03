package ggc.app.partners;

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
 * Show all transactions for a specific partner.
 */
class DoShowPartnerSales extends Command<WarehouseManager> {

  DoShowPartnerSales(WarehouseManager receiver) {
    super(Label.SHOW_PARTNER_SALES, receiver);
    addStringField("PartnerId", Message.requestPartnerKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("PartnerId");
    try{
    Collection<Sale> sales = new ArrayList<Sale>(_receiver.printSales(partnerId));
    for (Sale a: sales){
      if (a.isPaid()){
        _display.addLine(a);
      }
      else{
        SaleByCredit saleByCredit = (SaleByCredit)a;
        _display.addLine(saleByCredit.toStringWithoutDate());
      }
    _display.display();
    }
    }catch (UnknownPartnerKey upke){
      throw new UnknownPartnerKeyException(partnerId);
    }
  }
}
