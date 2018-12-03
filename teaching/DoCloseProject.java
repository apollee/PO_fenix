package sth.app.teaching;

import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.OpeningSurveyException;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.BadEntryException;
import sth.exceptions.InvalidDisciplineException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.3.2. Close project.
 */
public class DoCloseProject extends Command<SchoolManager> {

  //FIXME add input fields if needed
  Input<String> _nameProject;
  Input<String> _nameDiscipline;
  /**
   * @param receiver
   */
  public DoCloseProject(SchoolManager receiver) {
    super(Label.CLOSE_PROJECT, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{ 
   _receiver.closeProject(_nameProject.value(), _nameDiscipline.value());
    //FIXME implement command
    } catch(BadEntryException e){
        throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(InvalidDisciplineException p){
        throw new NoSuchDisciplineException(_nameDiscipline.value());
    } catch(OpenSurveyException o){
      throw new OpeningSurveyException(_nameDiscipline.value(), _nameProject.value());
    }
  } 

}
