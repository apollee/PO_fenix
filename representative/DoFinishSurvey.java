package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.BadEntryException;
import sth.exceptions.FinishSurveyException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.app.exceptions.NoSurveyException;
import sth.app.exceptions.FinishingSurveyException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;

/**
 * 4.5.5. Finish survey.
 */
public class DoFinishSurvey extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;

  /**
   * @param receiver
   */
  public DoFinishSurvey(SchoolManager receiver) {
    super(Label.FINISH_SURVEY, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.finalizeSurvey(_nameDiscipline.value(), _nameProject.value());
    } catch(InvalidDisciplineException i){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    } catch(InvalidProjectException ip){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    }catch(BadEntryException e){
      throw new NoSurveyException(_nameDiscipline.value(), _nameProject.value());
    } catch(FinishSurveyException f){
      throw new FinishingSurveyException(_nameDiscipline.value(), _nameProject.value());
    }
  }

}
