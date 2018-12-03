package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.BadEntryException;
import sth.exceptions.InvalidDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSuchDisciplineException;

//FIXME import other classes if needed

/**
 * 4.3.3. Show project submissions.
 */
public class DoShowProjectSubmissions extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;
  //FIXME add input fields if needed

  /**
   * @param receiver
   */
  public DoShowProjectSubmissions(SchoolManager receiver) {
    super(Label.SHOW_PROJECT_SUBMISSIONS, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    //FIXME implement command
    _form.parse();
    try{
      _display.popup(_receiver.projectSubmissions(_nameDiscipline.value(),_nameProject.value()));
    } catch(InvalidDisciplineException p){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    } catch(BadEntryException e){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    }  
  }
}
