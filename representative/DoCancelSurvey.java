package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.BadEntryException;
import sth.exceptions.NonEmptyException;;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.SurveyFinishException;
import sth.app.exceptions.NoSurveyException;
import sth.app.exceptions.NonEmptySurveyException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.SurveyFinishedException;

/**
 * 4.5.2. Cancel survey.
 */
public class DoCancelSurvey extends Command<SchoolManager> {
  Input<String> _nameDiscipline;
  Input<String> _nameProject;

  /**
   * @param receiver
   */
  public DoCancelSurvey(SchoolManager receiver) {
    super(Label.CANCEL_SURVEY, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.cancelSurvey(_nameDiscipline.value(), _nameProject.value());
    } catch(InvalidDisciplineException invaldis){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    } catch(InvalidProjectException invalproj){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    }catch(BadEntryException nosurv){
      throw new NoSurveyException(_nameDiscipline.value(), _nameProject.value());
    } catch(SurveyFinishException finished){
      throw new SurveyFinishedException(_nameDiscipline.value(), _nameProject.value());
    }catch(NonEmptyException nonemp){
      throw new NonEmptySurveyException(_nameDiscipline.value(), _nameProject.value());
    }
  }

}
