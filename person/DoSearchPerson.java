package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.2.4. Search person.
 */
public class DoSearchPerson extends Command<SchoolManager> {

  //FIXME add input fields if needed
  Input<String> _name;

 
  /**
   * @param receiver
   */
  public DoSearchPerson(SchoolManager receiver) {
    super(Label.SEARCH_PERSON, receiver);
    _name = _form.addStringInput(Message.requestPersonName());
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();
    _display.popup(_receiver.searchPerson(_name.value()));
    /*try{
    _display.popup(_receiver.searchPerson(_name.value()));
    /*} catch(NoSuchPersonException e){
        throw new NoSuchPersonException(_name.value());
    }*/
  }

    //FIXME implement command

}
