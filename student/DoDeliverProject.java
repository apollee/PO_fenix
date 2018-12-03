package sth.app.student;

import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.BadEntryException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;

//FIXME import other classes if needed

/**
 * 4.4.1. Deliver project.
 */
public class DoDeliverProject extends Command<SchoolManager> {

  //FIXME add input fields if needed
  Input<String> _nameProject;
  Input<String> _nameDiscipline;
  Input<String> _deliveryMessage;

  /**
   * @param receiver
   */
  public DoDeliverProject(SchoolManager receiver) {
    super(Label.DELIVER_PROJECT, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
    _deliveryMessage = _form.addStringInput(Message.requestDeliveryMessage());
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.deliverProject(_nameDiscipline.value(), _nameProject.value(), _deliveryMessage.value());
    //FIXME implement command
    } catch(BadEntryException e){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(InvalidDisciplineException e){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    }
  }
}
