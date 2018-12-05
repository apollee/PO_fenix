package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.InvalidDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;

/**
 * 4.3.3. Show project submissions.
 */
public class DoShowProjectSubmissions extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;

  /**
   * @param receiver
   */
  public DoShowProjectSubmissions(SchoolManager receiver) {
    super(Label.SHOW_PROJECT_SUBMISSIONS, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _display.popup(_receiver.projectSubmissions(_nameDiscipline.value(),_nameProject.value()));
    } catch(InvalidDisciplineException invaldisc){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    } catch(InvalidProjectException invalproj){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    }  
  }
}
