package ggc.app.transactions;

import ggc.core.*;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

import java.util.ArrayList;
import java.util.List;

import ggc.app.exception.UnknownProductKeyException;
import ggc.app.exception.UnknownPartnerKeyException;

//FIXME import classes

/**
 * Register order.
 */
public class DoRegisterAcquisitionTransaction extends Command<WarehouseManager> {

  public DoRegisterAcquisitionTransaction(WarehouseManager receiver) {
    super(Label.REGISTER_ACQUISITION_TRANSACTION, receiver);
  }

  @Override
  public final void execute() throws CommandException, UnknownProductKey {
    int i = 0;
    // Input
    String partnerId = Form.requestString(Message.requestPartnerKey());
    String productId = Form.requestString(Message.requestProductKey());
    double price = Form.requestReal(Message.requestPrice());
    int amount = Form.requestInteger(Message.requestAmount());

    try{
      _receiver.checkPartner(partnerId);
    } catch (UnknownPartnerKey upk) {
      throw new UnknownPartnerKeyException(partnerId);
    }

    if (_receiver.checkProduct(productId))
      _receiver.registerAcquisitionTransaction(partnerId, productId, price, amount, 1);

    else {

      String value = Form.requestString(Message.requestAddRecipe());

      if (value.equals("n")) {
          _receiver.registerSimpleProduct(productId);
          _receiver.registerAcquisitionTransaction(partnerId, productId, price, amount, 0);
          _receiver.updateNotifications(productId);
      }

      else {
        int numberOfComponents = Form.requestInteger(Message.requestNumberOfComponents());
        double alpha = Form.requestReal(Message.requestAlpha());
        List<Component> components = new ArrayList<>();
        // While nao tem todos os componentes
        while (i != numberOfComponents) {
          // Pede o Product id
          String productIdRec = Form.requestString(Message.requestProductKey());

          try {
            int quantity = Form.requestInteger(Message.requestAmount()); // Pede quantidade do produto
            _receiver.productExists(productIdRec); // Verifica se existe o produto
            Product product = _receiver.getProduct(productIdRec.toLowerCase());
            Component component = new Component(quantity, product);
            components.add(component);

          } catch (UnknownProductKey upke) {
            throw new UnknownProductKeyException(productIdRec);
          }
          i++;
        }

        Recipe recipe = new Recipe(alpha, components);
        _receiver.registerAggregateProduct(productId, recipe);
        _receiver.registerAcquisitionTransaction(partnerId, productId, price, amount, 0);
        _receiver.updateNotifications(productId);
      }
    }
  }

}
