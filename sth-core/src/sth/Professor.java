package sth;

/*import sth.app.exceptions.DuplicateProjectException;*/
import java.util.Collection;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.ArrayList;
import java.text.Collator;
import java.io.Serializable;
import sth.exceptions.BadEntryException;
import sth.exceptions.InvalidDisciplineException;

public class Professor extends Person implements Serializable{

    private TreeMap<String,Discipline> _disciplines;

    public Professor(int id, String phoneNumber, String name){
        super(id, phoneNumber, name);
        _disciplines = new TreeMap<String,Discipline>(new Serial()); 
    }
    
    public void createProject(String nameProject, String nameDiscipline) throws BadEntryException, InvalidDisciplineException{
       Project _project = new Project(nameProject);
       Discipline _discipline = _disciplines.get(nameDiscipline);    
       if(_discipline == null){
        throw new InvalidDisciplineException();
       }


       if(_discipline.getProjects().get(nameProject) != null){
        throw new BadEntryException(nameProject);
       }else{
          _discipline.getProjects().put(nameProject,_project);
       }
    }

    public void closeProject(String nameProject, String nameDiscipline) throws BadEntryException, InvalidDisciplineException{
      Discipline _discipline = _disciplines.get(nameDiscipline);
      if((_discipline == null)){
        throw new InvalidDisciplineException();
      }

      Project _project = _discipline.getProjects().get(nameProject);
      if((_project != null)){
        if(_project.getOpenValue() == true){
            _project.setOpen(false);  
        }
      }else{
        throw new BadEntryException(nameProject);
      } 
    }

    public String studentsDiscipline(String discipline) throws BadEntryException{
      String string = "";
      Discipline _discipline = _disciplines.get(discipline);
      if(_discipline != null){
        TreeMap<Integer, Student> _students = _discipline.getStudents();
        for(Student _student: _students.values()){
            string +=  _student.toString(); 
        }        
    
      }else{
        throw new BadEntryException(discipline);
      }
      return string;
    }
    

    public TreeMap<String,Discipline> getDisciplines() {
        return _disciplines;
    }

    @Override
    public String toString(){
        String string = "";
        ArrayList<String> print = new ArrayList<String>();
        string += "DOCENTE|" + super.toString(); 
        for(Discipline _discipline: _disciplines.values()){
             print.add( "* " + (_discipline.getCourse()).getName() + " - " + _discipline.getName() + "\n");
        }
        print.sort(Collator.getInstance(Locale.getDefault()));
        for(String str: print){
            string += str;
        }
        return string;
    }

}
