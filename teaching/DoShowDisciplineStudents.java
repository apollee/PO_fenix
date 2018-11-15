package sth.app.teaching;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.SchoolManager;
import sth.app.exceptions.NoSuchDisciplineException;
import sth.exceptions.BadEntryException;

//FIXME import other classes if needed

/**
 * 4.3.4. Show course students.
 */
public class DoShowDisciplineStudents extends Command<SchoolManager> {

  Input<String> _nameDiscipline;
  //FIXME add input fields if needed

  /**
   * @param receiver
   */
  public DoShowDisciplineStudents(SchoolManager receiver) {
    super(Label.SHOW_COURSE_STUDENTS, receiver);
    _nameDiscipline = _form.addStringInput(Message.requestDisciplineName());
    //FIXME initialize input fields if needed
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try{
        _display.popup(_receiver.studentsDiscipline(_nameDiscipline.value()));
    //FIXME implement command
    } catch(BadEntryException e){
        throw new NoSuchDisciplineException(_nameDiscipline.value());
    }
  }

}
