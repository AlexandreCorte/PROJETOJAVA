package ggc.app.main;

import ggc.app.exception.FileOpenFailedException;
import ggc.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import ggc.core.WarehouseManager;

import java.io.FileNotFoundException;
import java.io.IOException;
//FIXME import classes

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<WarehouseManager> {

  /** @param receiver */
  DoSaveFile(WarehouseManager receiver) {
    super(Label.SAVE, receiver);
  }

  @Override
  public final void execute() throws CommandException {
    String filename = _receiver.getFilename();
    try {
      if ("".equals(filename)) {
        Form form = new Form();
        form.addStringField("filename", Message.newSaveAs());
        form.parse();
        _receiver.saveAs(form.stringField("filename"));
      } else {
        _receiver.save();
      }

    } catch (MissingFileAssociationException | IOException  e) {
        throw new FileOpenFailedException(stringField("filename"));
    }
  }
}
