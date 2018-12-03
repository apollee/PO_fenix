package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.BadEntryException;
import sth.exceptions.FinishSurveyException;
import sth.exceptions.InvalidDisciplineException;
import sth.app.exceptions.NoSurveyException;
import sth.app.exceptions.FinishingSurveyException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.exceptions.InvalidProjectException;

//FIXME import other classes if needed

/**
 * 4.5.5. Finish survey.
 */
public class DoFinishSurvey extends Command<SchoolManager> {

  //FIXME add input fields
  Input<String> _nameProject;
  Input<String> _nameDiscipline;

  /**
   * @param receiver
   */
  public DoFinishSurvey(SchoolManager receiver) {
    super(Label.FINISH_SURVEY, receiver);
    //FIXME initialize input fields if needed
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    //FIXME implement command
    _form.parse();
    try{
      _receiver.finalizeSurvey(_nameDiscipline.value(), _nameProject.value());
    //FIXME implement command
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
