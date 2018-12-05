package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.BadEntryException;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.app.exceptions.NoSurveyException;
import sth.app.exceptions.OpeningSurveyException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;

/**
 * 4.5.3. Open survey.
 */
public class DoOpenSurvey extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;

  /**
   * @param receiver
   */
  public DoOpenSurvey(SchoolManager receiver) {
    super(Label.OPEN_SURVEY, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.openSurvey(_nameDiscipline.value(), _nameProject.value());
    } catch(InvalidDisciplineException i){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    } catch(InvalidProjectException ip){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(BadEntryException e){
      throw new NoSurveyException(_nameDiscipline.value(), _nameProject.value());
    } catch(OpenSurveyException o){
      throw new OpeningSurveyException(_nameDiscipline.value(), _nameProject.value());
    }
  }
}
