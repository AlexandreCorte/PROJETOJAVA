package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.app.exception.UnknownTransactionKeyException;
import ggc.core.*;

//FIXME import classes

/**
 * Receive payment for sale transaction.
 */
public class DoReceivePayment extends Command<WarehouseManager> {

  public DoReceivePayment(WarehouseManager receiver) {
    super(Label.RECEIVE_PAYMENT, receiver);
    addIntegerField("TransactionId", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    int transactionId = integerField("TransactionId");
    try {
      _receiver.checkTransaction(transactionId);
    } catch (UnknownTransactionKey ute) {
      throw new UnknownTransactionKeyException(transactionId);
    }
    Transaction trans = _receiver.getTransaction(transactionId);
    _receiver.receivePayment(trans);

  }
}
