package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;

/**
 * 4.4.1. Deliver project.
 */
public class DoDeliverProject extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;
  Input<String> _deliveryMessage;

  /**
   * @param receiver
   */
  public DoDeliverProject(SchoolManager receiver) {
    super(Label.DELIVER_PROJECT, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
    _deliveryMessage = _form.addStringInput(Message.requestDeliveryMessage());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.deliverProject(_nameDiscipline.value(), _nameProject.value(), _deliveryMessage.value());
    } catch(InvalidProjectException invalproj){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(InvalidDisciplineException invaldisc){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    }
  }
}
