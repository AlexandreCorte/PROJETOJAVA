package ggc.app.transactions;

import ggc.core.UnavailableProduct;
import ggc.core.UnknownPartnerKey;
import ggc.core.UnknownProductKey;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnknownProductKeyException;

//FIXME import classes

/**
 * Register order.
 */
public class DoRegisterBreakdownTransaction extends Command<WarehouseManager> {

  public DoRegisterBreakdownTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_BREAKDOWN_TRANSACTION, receiver);
    addStringField("partnerId", Message.requestPartnerKey());
    addStringField("productId", Message.requestProductKey());
    addIntegerField("quantity", Message.requestAmount());
  }

  @Override
  public final void execute() throws CommandException{
    String productId = stringField("productId");
    String partnerId = stringField("partnerId");
    int amount = integerField("quantity");
    try{

      _receiver.checkPartner(partnerId);
      _receiver.productExists(productId);
      _receiver.checkTotalStock(productId, amount);
      _receiver.registerBreakDownTransaction(productId, partnerId, amount);

    } catch(UnknownPartnerKey upke){
      throw new UnknownPartnerKeyException(partnerId);

    } catch (UnknownProductKey upke){
      throw new UnknownProductKeyException(productId);
      
    } catch(UnavailableProduct upe){
      throw new UnavailableProductException(productId, amount, _receiver.getProductStock(productId));
    }
  }

}
