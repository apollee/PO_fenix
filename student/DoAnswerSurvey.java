package sth.app.student;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.NoSuchSurveyException;
import sth.exceptions.NoSubmissionException;
import sth.app.exceptions.NoSuchProjectException;
import sth.app.exceptions.NoSurveyException;


/**
 * 4.4.2. Answer survey.
 */
public class DoAnswerSurvey extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  Input<String> _nameProject;
  Input<Integer> _hours;
  Input<String> _comment;

  /**
   * @param receiver
   */
  public DoAnswerSurvey(SchoolManager receiver) {
    super(Label.ANSWER_SURVEY, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    _nameProject = _form.addStringInput(Message.requestProjectName());
    _hours = _form.addIntegerInput(Message.requestProjectHours());
    _comment = _form.addStringInput(Message.requestComment());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
      _receiver.fillSurvey(_nameDiscipline.value(), _nameProject.value(), _hours.value(), _comment.value());
    } catch(NoSubmissionException nosub){
      throw new NoSuchProjectException(_nameDiscipline.value(), _nameProject.value());
    } catch(NoSuchSurveyException sosurv){
      throw new NoSurveyException(_nameDiscipline.value(), _nameProject.value());
    }
  }

}
