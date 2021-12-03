package ggc.app.main;

import ggc.app.exception.FileOpenFailedException;
import ggc.core.exception.UnavailableFileException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;
//FIXME import classes

/**
 * Open existing saved state.
 */
class DoOpenFile extends Command<WarehouseManager> {
  /** @param receiver */
  DoOpenFile(WarehouseManager receiver) {
    super(Label.OPEN, receiver);
    addStringField("filename", Message.openFile());

  }

  @Override
  public final void execute() throws CommandException {
    try {
      _receiver.load(stringField("filename"));
      _receiver.setFileName(stringField("filename"));

    } catch (UnavailableFileException ufe) {
      throw new FileOpenFailedException(stringField("filename"));
  }
  }
}
