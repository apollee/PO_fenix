package sth.app.representative;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.exceptions.InvalidDisciplineException;
import sth.app.exceptions.NoSuchDisciplineException;

//FIXME import other classes if needed

/**
 * 4.5.6. Show discipline surveys.
 */
public class DoShowDisciplineSurveys extends Command<SchoolManager> {

  //FIXME add input fields if needed
  Input<String> _nameDiscipline;

  /**
   * @param receiver
   */
  public DoShowDisciplineSurveys(SchoolManager receiver) {
    super(Label.SHOW_DISCIPLINE_SURVEYS, receiver);
    //FIXME initialize input fields if needed
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    //FIXME implement command
    _form.parse();
    try{
      _receiver.showSurveys(_nameDiscipline.value());
    } catch(InvalidDisciplineException i){
      throw new NoSuchDisciplineException(_nameDiscipline.value());
    }
  }

}
