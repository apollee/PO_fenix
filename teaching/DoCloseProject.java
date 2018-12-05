package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.InvalidDisciplineException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.OpeningSurveyException;

/**
 * 4.3.2. Close project.
 */
public class DoCloseProject extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;

  /**
   * @param receiver
   */
  public DoCloseProject(SchoolManager receiver) {
    super(Label.CLOSE_PROJECT, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{ 
   _receiver.closeProject(_nameProject.value(), _nameDiscipline.value());
    } catch(InvalidDisciplineException invaldisc){
        throw new NoSuchDisciplineException(_nameDiscipline.value());
    }  catch(InvalidProjectException invalproj){
        throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    }catch(OpenSurveyException open){
      throw new OpeningSurveyException(_nameDiscipline.value(), _nameProject.value());
    }
  } 

}
