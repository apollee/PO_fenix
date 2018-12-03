package sth;

//FIXME import other classes if needed

import sth.Serial;
import java.text.Collator;
import java.util.Locale;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import sth.exceptions.BadEntryException;
import sth.exceptions.CloseSurveyException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.NoSuchPersonIdException;
import sth.exceptions.ImportFileException;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.CloseSurveyException;
import sth.exceptions.FinishSurveyException;
import sth.exceptions.SurveyFinishException;


/**
 * School implementation.
 */

public class School implements Serializable {

  //FIXME define object fields (attributes and, possibly, associations)

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201810051538L;
  private TreeMap<Integer,Person> _persons;
  private HashMap<Integer,Student> _students;
  private HashMap<Integer,Professor> _professors;
  private HashMap<Integer,Administrative> _administratives;
  private HashMap<String,Course> _courses;
  private HashMap<String,Discipline> _disciplines;
  private TreeMap<String, HashMap<String, Discipline>> _disciplineCourse;
  private Person _person;


  //FIXME implement constructors if needed

  public School(){
    _persons = new TreeMap<Integer, Person>();
    _students = new HashMap<Integer, Student>();
    _professors = new HashMap<Integer, Professor>();
    _administratives = new HashMap<Integer, Administrative>();
    _courses = new HashMap<String,Course>();
    _disciplines = new HashMap<String,Discipline>();
    _disciplineCourse = new TreeMap<String, HashMap<String, Discipline>>();
  }

  /**
   * Imports the file with the pre-defined concepts in the start of the application
   * @param filename name of the import file
   * @throws BadEntryException
   * @throws IOException
   * @throws ImportFileException
   */
  public void importFile(String filename) throws IOException, BadEntryException, ImportFileException{
    //FIXME implement text file reader
    try{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        line = reader.readLine();

        while(line != null){
            String[] fields = line.split("\\|");
            int id = Integer.parseInt(fields[1]);
            if(_persons.get(id) != null){
                throw new BadEntryException(fields[0]);
            }

            if(fields[0].equals("FUNCIONÁRIO")){
                registerAdministrative(fields);
                line = reader.readLine();
            }else if(fields[0].equals("DOCENTE")){
                Professor _professor = new Professor(id, fields[2], fields[3]);
                _professors.put(id, _professor);
                _persons.put(id, (Person) _professor);

                line = reader.readLine();
                if(line != null){
                    fields = line.split("\\|");
                    while((line != null) && (fields[0].charAt(0) == '#')){
                        String course  = fields[0].substring(2);
                        String discipline = fields[1];
                        Course _course = _courses.get(course);
                        
                        if(_course == null){
                            _course = new Course(course);
                            _disciplineCourse.put(course, new HashMap<String, Discipline>());
                            _courses.put(course, _course);
                        }

                        if(_professor.getDisciplineCourses().get(course) == null){
                            _professor.getDisciplineCourses().put(course, new HashMap<String, Discipline>());
                        }

                        Discipline _discipline = _disciplines.get(discipline);
                        if(_discipline == null){
                            _discipline = new Discipline(discipline,_course);
                            _disciplines.put(discipline, _discipline);
                            _course.getDisciplines().put(discipline, _discipline);
                        }
                        _disciplineCourse.get(course).put(discipline, _discipline);

                        if(_professor.getDisciplines() != null){
                            _professor.getDisciplines().put(discipline, _discipline);
                            _professor.getDisciplineCourses().get(course).put(discipline, _discipline); 
                        }
                        line = reader.readLine();
                        if(line != null){
                            fields = line.split("\\|");

                        }
                    }
                }
            }else if(fields[0].equals("DELEGADO")){
                Student _student = new Student(id, fields[2], fields[3]);
                _students.put(id, _student);
                _persons.put(id, (Person) _student);

                line = reader.readLine();
                fields = line.split("\\|");
                while((line != null) && (fields[0].charAt(0) == '#')){
                    String course = fields[0].substring(2);
                    String discipline = fields[1];
                    Course _course = _courses.get(course);
                    if(_course == null){
                        _course = new Course(course);
                        _disciplineCourse.put(course, new HashMap<String, Discipline>());
                        _courses.put(course,_course);
                    }
                    if(_course.getRepresentatives().size() == 8){
                        System.out.println(id);
                        throw new BadEntryException(course);
                    }else{
                        _course.getRepresentatives().put(id,_student);
                    }
                    _student.setCourse(_course);
                    Discipline _discipline = _disciplines.get(discipline);
                    if(_discipline == null){
                        _discipline = new Discipline(discipline, _course);
                        _disciplines.put(discipline,_discipline);
                        _course.getDisciplines().put(discipline, _discipline);
                    }
                    if(_student.getDisciplines().size() == 6){
                        throw new BadEntryException(discipline);
                    }else{
                        if(_discipline.getStudents().size() == 100){
                            throw new BadEntryException(discipline);
                        }else{
                            _student.getDisciplines().put(discipline,_discipline);
                            _discipline.getStudents().put(id,_student);
                        }
                    }
                    line = reader.readLine();
                    if(line != null){
                      fields = line.split("\\|");
                    }
                }
            }else if(fields[0].equals("ALUNO")){
                Student _student = new Student(id, fields[2], fields[3]);
                _students.put(id, _student);
                _persons.put(id, (Person) _student);

                line = reader.readLine();
                fields = line.split("\\|");
                while((line != null) && (fields[0].charAt(0) == '#')){
                    String course  = fields[0].substring(2);
                    String discipline = fields[1];
                    Course _course = _courses.get(course);
                    if(_course == null){
                        _course = new Course(course);
                        _disciplineCourse.put(course, new HashMap<String, Discipline>());
                        _courses.put(course, _course);
                    }
                    _student.setCourse(_course);
                    Discipline _discipline = _disciplines.get(discipline);
                    if(_discipline == null){
                        _discipline = new Discipline(discipline, _course);
                        _disciplines.put(discipline, _discipline);
                        _course.getDisciplines().put(discipline, _discipline);
                    }
                    if(_student.getDisciplines().size() == 6){
                        throw new BadEntryException(discipline);
                    }else{
                        _student.getDisciplines().put(discipline,_discipline);
                        if(_discipline.getStudents().size() == 100){
                            throw new BadEntryException(discipline);
                        }else{
                            _discipline.getStudents().put(id,_student);
                        }
                    }
                    line = reader.readLine();
                    if(line != null){
                        fields = line.split("\\|");
                    }
                }

            }else{
                throw new BadEntryException(fields[0]);
            }
        }
        reader.close();
    }catch(BadEntryException e){
        throw new ImportFileException(e);
    }catch(IOException e){
        throw new ImportFileException(e);
    }
  }


  /**
  * Registers the administratives that come from the file that is imported.
  * @param fields  information about the administrative
  */

  public void registerAdministrative(String[] fields) throws BadEntryException{
    int id = Integer.parseInt(fields[1]);
    if(_persons.get(id) != null){
        throw new BadEntryException(fields[0]);
    }
    Administrative _administrative = new Administrative(id, fields[2], fields[3]);
    _administratives.put(id, _administrative);
    _persons.put(id, (Person) _administrative);
  }

  //FIXME implement other methods

  /**
  * Returns the persons in the school database
  * @return <code>TreeMap</code>
  */
   public TreeMap<Integer, Person> getPersons() {
    return _persons;
  }

  /**
  * Returns the students in the school database
  * @return <code>HashMap</code>
  */
  public HashMap<Integer, Student> getStudents() {
    return _students;
  }

  /**
  * Returns the professors in the school database
  * @return <code>HashMap</code>
  */
  public HashMap<Integer, Professor> getProfessors() {
    return _professors;
  }

  /**
  * Returns the administratives in the school database
  * @return <code>HashMap</code>
  */
  public HashMap<Integer, Administrative> getAdministratives() {
    return _administratives;
 }

 /**
 * Checks if the person who tried to login is in the school database or not
 * @param id id of the student who is trying to log in
 * @throws NoSuchPersonIdException
 */
  public void login(int id) throws NoSuchPersonIdException{
    _person = getPersons().get(id);
    if(_person == null){
        throw new NoSuchPersonIdException(id);
    }
  }

  /*===================================================PORTAL DA PESSOA===============================*/

  /**
  * Prints the information of the person who logged in
  * @return String
  */
  public String showPerson(){
    return _person.toString();
  }

  /**
  * Prints the students in a discipline
  * @param discipline discipline of the students that will be printed
  * @throws BadEntryException
  * @return String
  */
  public String studentsDiscipline(String discipline) throws BadEntryException{
    int id = _person.getId();
    Professor _professor = getProfessors().get(id);

    return _professor.studentsDiscipline(discipline);
  }

  /**
  * Prints the information of all the persons in the database
  * @return String
  */
  public String showAllPersons() {
    String string = "";
    for(Person person: getPersons().values()){
        string += person.toString();
    }
    return string;
  }

  /**
  * Searches for a persons name and prints the information of all the persons in the database with that name
  * @param name of the person to be searched
  * @return String
  */
  public String searchPerson(String name){
    String string = "";
    ArrayList<Person> list = new ArrayList<Person>();
    
    for(Person person: getPersons().values()){
        String _name = person.getName();
        if(_name.contains(name)){
            /*string += person.toString();*/
            list.add(person);
        }
    }
    list.sort(Comparator.comparing(Person::getName));
    for (int i = 0; i < list.size(); i++){
        string += list.get(i).toString();
    }

    return string;
  }

  /*===================================================PORTAL DO DOCENTE===============================*/

  /**
  * Creates a project of the discipline with the name that was given by the professor
  * @param nameProject name of the project
  * @param nameDiscipline name of the discipline
  * @throws BadEntryException
  * @throws InvalidDisciplineException
  */
  public void createProject(String nameProject, String nameDiscipline) throws BadEntryException, InvalidDisciplineException{
    int id = _person.getId();
    Professor _professor = getProfessors().get(id);
    _professor.createProject(nameProject, nameDiscipline);
  }

  /**
  * Closes a project of the discipline with the name that was given by the professor
  * @param nameProject name of the project
  * @param nameDiscipline name of the discipline
  * @throws BadEntryException
  * @throws InvalidDisciplineException
  */

  public void closeProject(String nameProject, String nameDiscipline) throws BadEntryException, InvalidDisciplineException, OpenSurveyException{
    int id = _person.getId();
    Professor _professor = getProfessors().get(id);
    _professor.closeProject(nameProject, nameDiscipline);
  }

  public String surveyResults(String disciplineName, String projectName) throws InvalidDisciplineException, InvalidProjectException, BadEntryException{
    int id = _person.getId();
    Professor _professor = getProfessors().get(id);
    return _professor.surveyResults(disciplineName, projectName);
  }

  public String projectSubmissions(String disciplineName, String nameProject) throws BadEntryException, InvalidDisciplineException{
    int id = _person.getId();
    Professor _professor = getProfessors().get(id);
    return _professor.projectSubmissions(disciplineName, nameProject);
  }
  

  /*===================================================PORTAL DO ESTUDANTE===============================*/

  /**
  * Delivers a project(with the name that was given) of the discipline(with the name that was given).
    The delivery includes the given delivery message
  * @param nameProject name of the project
  * @param nameDiscipline name of the discipline
  * @param deliveryMessage delivery message of the submission
  * @throws BadEntryException
  * @throws InvalidDisciplineException
  */
  public void deliverProject(String disciplineName, String nameProject, String deliveryMessage)
  throws BadEntryException, InvalidDisciplineException{
    int id = _person.getId();
    Student _student = getStudents().get(id);
    _student.deliverProject(disciplineName, nameProject, deliveryMessage, id);
  }

  public void fillSurvey(String disciplineName, String projectName, int hours, String comment) throws BadEntryException, ImportFileException {
    int id = _person.getId();
    Student _student = getStudents().get(id);
    _student.fillSurvey(disciplineName, projectName, hours, comment);      
  }

  public String showSurvey(String disciplineName, String projectName) throws InvalidDisciplineException, InvalidProjectException, BadEntryException{
    int id = _person.getId();
    Student _student = getStudents().get(id);
    return _student.showSurvey(disciplineName, projectName);
  }

 /*===================================================PORTAL DO DELEGADO===============================*/

 public void createSurvey(String disciplineName, String projectName) throws BadEntryException, InvalidDisciplineException, InvalidProjectException{
    int id = _person.getId();
    Student _student = getStudents().get(id);
    _student.createSurvey(disciplineName, projectName);
  }

  public void cancelSurvey(String disciplineName, String projectName) throws BadEntryException, ImportFileException, SurveyFinishException, InvalidDisciplineException, InvalidProjectException{
    int id = _person.getId();
    Student _student = getStudents().get(id);
    _student.cancelSurvey(disciplineName, projectName);
  }

  public void openSurvey(String disciplineName, String projectName) throws BadEntryException, OpenSurveyException, InvalidDisciplineException, InvalidProjectException{
    int id = _person.getId();
    Student _student = getStudents().get(id);
    _student.openSurvey(disciplineName, projectName);
  }

  public void closeSurvey(String disciplineName, String projectName) throws BadEntryException, CloseSurveyException, InvalidDisciplineException, InvalidProjectException{
    int id = _person.getId();
    Student _student = getStudents().get(id);
    _student.closeSurvey(disciplineName, projectName);
  }

  public void finalizeSurvey(String disciplineName, String projectName) throws BadEntryException, FinishSurveyException, InvalidDisciplineException, InvalidProjectException{
    int id = _person.getId();
    Student _student = getStudents().get(id);
    _student.finalizeSurvey(disciplineName, projectName);
  }

  public String showSurveys(String disciplineName) throws InvalidDisciplineException{
    int id = _person.getId();
    Student _student = getStudents().get(id);
    return _student.showSurveys(disciplineName);
  }
}