package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.NoSuchSurveyException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSurveyException;


/**
 * 4.3.5. Show survey results.
 */
public class DoShowSurveyResults extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;
  /**
   * @param receiver
   */
  public DoShowSurveyResults(SchoolManager receiver) {
    super(Label.SHOW_SURVEY_RESULTS, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _display.popup(_receiver.surveyResults(_nameDiscipline.value(), _nameProject.value()));
    } catch(InvalidDisciplineException invaldisc){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    } catch(InvalidProjectException invalproj){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(NoSuchSurveyException nosurv){
      throw new NoSurveyException(_nameDiscipline.value(), _nameProject.value());
    }
  }

}
