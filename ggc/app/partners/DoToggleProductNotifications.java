package ggc.app.partners;

import ggc.core.UnknownPartnerKey;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.UnknownProductKey;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Toggle product-related notifications.
 */
class DoToggleProductNotifications extends Command<WarehouseManager> {

  DoToggleProductNotifications(WarehouseManager receiver) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, receiver);
    addStringField("PartnerId", Message.requestPartnerKey());
    addStringField("ProductId", Message.requestProductKey());
  }

  @Override
  public void execute() throws CommandException {
    String partnerId = stringField("PartnerId");
    String productId = stringField("ProductId");
    try{
      _receiver.turnOnOfNotifications(productId, partnerId);

    }
    catch(UnknownPartnerKey upke){
      throw new UnknownPartnerKeyException(partnerId);
    }
    catch(UnknownProductKey upke){
      throw new UnknownProductKeyException(productId);
    }
  }

}
