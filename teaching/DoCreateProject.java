package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.InvalidDisciplineException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.DuplicateProjectException;

/**
 * 4.3.1. Create project.
 */
public class DoCreateProject extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;

  /**
   * @param receiver
   */
  public DoCreateProject(SchoolManager receiver) {
    super(Label.CREATE_PROJECT, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
     _receiver.createProject(_nameProject.value(), _nameDiscipline.value()); 
    } catch(InvalidProjectException invalproj){
        throw new DuplicateProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(InvalidDisciplineException invaldisc){
        throw new NoSuchDisciplineException(_nameDiscipline.value());
    }
  }
}
