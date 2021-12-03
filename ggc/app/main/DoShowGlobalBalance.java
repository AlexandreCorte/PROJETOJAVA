package ggc.app.main;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

/**
 * Show global balance.
 */
class DoShowGlobalBalance extends Command<WarehouseManager> {

  DoShowGlobalBalance(WarehouseManager receiver) {
    super(Label.SHOW_BALANCE, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    int availableBalance = (int)Math.round(_receiver.getAvailableBalance());
    int accountingBalance = (int)Math.round(_receiver.getAccountingBalance());

    _display.popup(Message.currentBalance(availableBalance,accountingBalance));
  }
  
}
