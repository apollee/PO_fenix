package sth.app.main;

import java.io.IOException;
import sth.exceptions.ImportFileException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {
  //FIXME add input fields if needed

  Input<String> _filename;

  /**
   * @param receiver
   */
  public DoSave(SchoolManager receiver) {
    super(Label.SAVE, receiver);
    //FIXME initialize input fields if needed
    _filename = _form.addStringInput(Message.newSaveAs()); 

 }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    try{
      _receiver.save(null);
    }catch(ImportFileException e){
      _form.parse();
	    try{
	      _receiver.save(_filename.value());
	    }catch(IOException | ImportFileException b){
	      b.printStackTrace();
	    }
    } catch(IOException c){
	    c.printStackTrace();
    }
    //FIXME implement command
  }

}
