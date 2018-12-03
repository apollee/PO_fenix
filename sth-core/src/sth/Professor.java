package sth;

/*import sth.app.exceptions.DuplicateProjectException;*/
import java.util.Collection;
import java.util.TreeMap;
/*import javax.crypto.BadPaddingException;*/
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.LinkedList;
import java.util.ArrayList;
import java.text.Collator;
import java.io.Serializable;
import sth.exceptions.BadEntryException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.OpenSurveyException;

public class Professor extends Person implements Serializable{

    private TreeMap<String,Discipline> _disciplines;
    private TreeMap<String, HashMap<String, Discipline>> _disciplineCourse;

    public Professor(int id, String phoneNumber, String name){
        super(id, phoneNumber, name);
        _disciplines = new TreeMap<String,Discipline>(); 
        _disciplineCourse = new TreeMap<String, HashMap<String, Discipline>>();
    }
    
    public void createProject(String nameProject, String nameDiscipline) throws BadEntryException, InvalidDisciplineException{
       Project _project = new Project(nameProject, "");
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

    public void closeProject(String nameProject, String nameDiscipline) throws BadEntryException, InvalidDisciplineException, OpenSurveyException{
      Discipline _discipline = _disciplines.get(nameDiscipline);
      if((_discipline == null)){
        throw new InvalidDisciplineException();
      }

      Project _project = _discipline.getProjects().get(nameProject);
      if((_project != null)){
        if(_project.getOpenValue() == true){
            _project.setOpenValue(false);
            Survey survey = _project.getSurvey();
            if(survey != null){
              survey.setState(new OpenState(survey));
            }  
        }
      }else{
        throw new BadEntryException(nameProject);
      } 
    }

    public String projectSubmissions(String disciplineName, String projectName) throws BadEntryException, InvalidDisciplineException{
      String string = disciplineName + " - " + projectName + "\n";
      Discipline _discipline = _disciplines.get(disciplineName);
      if(_discipline == null){
        throw new InvalidDisciplineException();
      }
      Project _project = _discipline.getProjects().get(projectName);
      if(_project == null){
        throw new BadEntryException(projectName);
      }
      TreeMap<Integer, ProjectSubmission> _submissions = _project.getProjectSubmissions();
      for(ProjectSubmission _submission: _submissions.values()){
        string += "* " + _submission.getId() + " - " + _submission.getDeliveryMessage() + "\n";
      }
      return string;
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
    
    public String surveyResults(String disciplineName, String projectName) throws InvalidDisciplineException, InvalidProjectException, BadEntryException{
      String string = "";
      Discipline _discipline = _disciplines.get(disciplineName);
      if(_discipline == null){
        throw new InvalidDisciplineException();
      }
      Project _project = _discipline.getProjects().get(projectName);
      if(_project == null){
        throw new InvalidProjectException();
      }
      Survey _survey = _project.getSurvey();
      if(_survey == null){
        throw new BadEntryException(projectName);
      }
      String status = _survey.status();
      if(status == "Created"){
        string += _discipline.getName() + " - " + _project.getName() + " (por abrir)\n";
      }
      else if(status == "Open"){
        string += _discipline.getName() + " - " + _project.getName() + " (aberto)\n";
      }
      else if(status == "Closed"){
        string += _discipline.getName() + " - " + _project.getName() + " (fechado)\n";
      }
      else if(status == "Finalized"){
        int min = 1000;
        int max = 0;
        int total = 0;
        string += _discipline.getName() + " - " + _project.getName() + "\n";
        int numberSubmissions = _project.getProjectSubmissions().size();
        LinkedList<SurveyAnswer> _surveyAnswers = _survey.getAnswers();
        int numberAnswers = _surveyAnswers.size();
        string += " * Número de submissões: " + numberSubmissions + "\n" + " * Número de respostas: " + numberAnswers;

        for(SurveyAnswer answer: _surveyAnswers){
          int hours = answer.getHours();
          total += hours;
          if(hours > max){
            max = hours;
          }
          if(hours < min){
            min = hours;
          }
        }
        int average = total / numberAnswers;
        string += " * Tempos de resolução (horas) (ínimo, médio, máximo): " + min + ", " + average + ", " + max;
      }
      return string;
    }





    public TreeMap<String,Discipline> getDisciplines() {
        return _disciplines;
    }

    public TreeMap<String, HashMap<String, Discipline>> getDisciplineCourses() {
        return _disciplineCourse;
    }

    @Override
    public String toString(){
        String string = "";
        ArrayList<String> print = new ArrayList<String>();
        string += "DOCENTE|" + super.toString(); 

        for(Map.Entry<String, HashMap<String, Discipline>> entry : _disciplineCourse.entrySet()) {
          String key = entry.getKey();
          HashMap<String, Discipline> value = entry.getValue();
          for(Discipline discipline: value.values()) {
            print.add("* " + key + " - " + discipline.getName() + "\n"); 
          }
        }

      /*for (Map.Entry<Court, TreeMap<Time, String>> courtEntry : dayEntry.getValue().entrySet()) {
        Court currentCourt = courtEntry.getKey();
        output += currentCourt.toString() + ":\n";
        for (Map.Entry<Time, String> timeEntry : currentCourt.getValue().entrySet()) {
            Time currentTime = timeEntry.getKey();
            String currentName = timeEntry.getValue();
            output += currentTime.toString() + " - " + currentName + "\n";
        }*/


        /*for(HashMap<String, Discipline> _disciplines: _disciplineCourse.values()){
          for(Discipline _discipline: _disciplines.values()){
            print.add( "* " + (_discipline.getCourse()).getName() + " - " + _discipline.getName() + "\n");
          }
        }*/

        print.sort(Collator.getInstance(Locale.getDefault()));
        for(String str: print){
            string += str;
        }
        return string;


        /*for(Discipline _discipline: _disciplines.values()){
             print.add( "* " + (_discipline.getCourse()).getName() + " - " + _discipline.getName() + "\n");
        }
        print.sort(Collator.getInstance(Locale.getDefault()));
        for(String str: print){
            string += str;
        }
        return string;*/
    }

}
