package ggc.app.transactions;

import ggc.core.WarehouseManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.UnknownProductKey;
import ggc.core.UnknownPartnerKey;
import ggc.core.UnavailableProduct;
import ggc.app.exception.UnknownPartnerKeyException;
import ggc.app.exception.UnavailableProductException;
import ggc.app.exception.UnknownProductKeyException;

/**
 * 
 */
public class DoRegisterSaleTransaction extends Command<WarehouseManager> {

  public DoRegisterSaleTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    }

  @Override
  public final void execute() throws CommandException {
    String partnerId = Form.requestString(Message.requestPartnerKey());
    int deadline = Form.requestInteger(Message.requestPaymentDeadline());
    String productId = Form.requestString(Message.requestProductKey());
    int amount = Form.requestInteger(Message.requestAmount());
    try{
    _receiver.checkPartner(partnerId);
    _receiver.productExists(productId);
    _receiver.registerSaleTransaction(partnerId, productId, deadline, amount);
    }catch(UnknownPartnerKey upk){
      throw new UnknownPartnerKeyException(partnerId);
    }catch(UnknownProductKey upke){
    throw new UnknownProductKeyException(productId);
    }
    catch(UnavailableProduct upe){
      throw new UnavailableProductException(productId, amount, _receiver.getProductStock(productId));
    }
  }
}
