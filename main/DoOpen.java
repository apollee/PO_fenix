package sth.app.main;

import sth.app.exceptions.NoSuchPersonException;
import sth.exceptions.NoSuchPersonIdException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

  //FIXME add input fields if needed
  Input<String> _filename;
  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    _filename = _form.addStringInput(Message.openFile());
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws NoSuchPersonException {
    _form.parse();
    try {
    	_receiver.load(_filename.value());
    } catch (FileNotFoundException f) {
      	_display.popup(Message.fileNotFound());
    } catch (ClassNotFoundException | IOException e) {
      	e.printStackTrace();
    } catch (NoSuchPersonIdException p) {
        throw new NoSuchPersonException(p.getId());
    }
  }

}
