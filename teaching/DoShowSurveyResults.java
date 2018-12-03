package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.BadEntryException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSurveyException;
import sth.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.3.5. Show survey results.
 */
public class DoShowSurveyResults extends Command<SchoolManager> {

  //FIXME add input fields if needed
  Input<String> _nameDiscipline;
  Input<String> _nameProject;
  /**
   * @param receiver
   */
  public DoShowSurveyResults(SchoolManager receiver) {
    super(Label.SHOW_SURVEY_RESULTS, receiver);
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
      _display.popup(_receiver.surveyResults(_nameDiscipline.value(), _nameProject.value()));
      //FIXME implement command
    } catch(InvalidDisciplineException id){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    } catch(InvalidProjectException ip){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(BadEntryException e){
      throw new NoSurveyException(_nameDiscipline.value(), _nameProject.value());
    }
  }

}
