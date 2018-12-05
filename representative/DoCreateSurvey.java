package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.BadEntryException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.InvalidDisciplineException;
import sth.app.exceptions.DuplicateSurveyException;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.app.exceptions.NoSuchProjectException;

/**
 * 4.5.1. Create survey.
 */
public class DoCreateSurvey extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;

  /**
   * @param receiver
   */
  public DoCreateSurvey(SchoolManager receiver) {
    super(Label.CREATE_SURVEY, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.createSurvey(_nameDiscipline.value(),_nameProject.value());
    } catch(InvalidDisciplineException invaldis){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    } catch(InvalidProjectException invalproj){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(BadEntryException duplicate){
      throw new DuplicateSurveyException(_nameDiscipline.value(), _nameProject.value());
    }
  }

}
