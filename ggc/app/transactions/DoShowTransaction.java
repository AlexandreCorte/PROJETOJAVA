package ggc.app.transactions;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.*;
import ggc.app.exception.UnknownTransactionKeyException;

public class DoShowTransaction extends Command<WarehouseManager> {

  public DoShowTransaction(WarehouseManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    addIntegerField("Id", Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws CommandException {
    int id = integerField("Id");
    try {
      Transaction transaction = _receiver.printTransaction(id);
        if (transaction.isPaid()){
          _display.popup(transaction);
        }
        else{
          SaleByCredit sale = (SaleByCredit)transaction;
          _display.popup(sale.toStringWithoutDate());
        }
    } catch (UnknownTransactionKey utk) {
      throw new UnknownTransactionKeyException(id);
    }
  }

}
