package sth.app.teaching;

import sth.exceptions.InvalidDisciplineException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.DuplicateProjectException;
import sth.exceptions.BadEntryException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.3.1. Create project.
 */
public class DoCreateProject extends Command<SchoolManager> {

  //FIXME add input fields if needed
  Input<String> _nameProject;
  Input<String> _nameDiscipline;

  /**
   * @param receiver
   */
  public DoCreateProject(SchoolManager receiver) {
    super(Label.CREATE_PROJECT, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
     _receiver.createProject(_nameProject.value(), _nameDiscipline.value()); 
    //FIXME implement command
    } catch(BadEntryException e){
        throw new DuplicateProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(InvalidDisciplineException e){
        throw new NoSuchDisciplineException(_nameDiscipline.value());
    }
  }
}
