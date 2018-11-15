                    
package sth;

//FIXME import other classes if needed

import sth.Serial;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import sth.exceptions.BadEntryException;
import sth.exceptions.InvalidCourseSelectionException;
import sth.exceptions.NoSuchPersonIdException;
import sth.exceptions.ImportFileException;

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
 
  //FIXME implement constructors if needed

  public School(){
    _persons = new TreeMap<Integer, Person>();
    _students = new HashMap<Integer, Student>();
    _professors = new HashMap<Integer, Professor>();
    _administratives = new HashMap<Integer, Administrative>();
    _courses = new HashMap<String,Course>();
    _disciplines = new HashMap<String,Discipline>();
 }

  /**
   * @param filename
   * @throws BadEntryException
   * @throws IOException
   */
  public void importFile(String filename) throws IOException, BadEntryException, ImportFileException{
    //FIXME implement text file reader
    try{
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        line = reader.readLine();       
 
        while(line != null){
            String[] fields = line.split("\\|");
            if(fields[0].equals("FUNCIONÁRIO")){
                int id = Integer.parseInt(fields[1]);
                Administrative _administrative = new Administrative(id, fields[2], fields[3]);
                _administratives.put(id, _administrative);
                _persons.put(id, (Person) _administrative);
                line = reader.readLine();
            }else if(fields[0].equals("DOCENTE")){
                int id = Integer.parseInt(fields[1]);
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
                            _courses.put(course, _course); 
                        }
                        Discipline _discipline = _disciplines.get(discipline);
                        if(_discipline == null){
                            _discipline = new Discipline(discipline,_course);
                            _disciplines.put(discipline, _discipline);
                        }
                        if(_professor.getDisciplines() != null){
                            _professor.getDisciplines().put(discipline, _discipline);
                        }    
                        line = reader.readLine();
                        if(line != null){
                            fields = line.split("\\|");
                        
                        }
                    }
                }
            }else if(fields[0].equals("DELEGADO")){
                int id = Integer.parseInt(fields[1]);
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
                        _courses.put(course,_course);
                    }
                    _course.getRepresentatives().put(id,_student);
                    _student.setCourse(_course);
                    Discipline _discipline = _disciplines.get(discipline);
                    if(_discipline == null){
                        _discipline = new Discipline(discipline, _course);
                        _disciplines.put(discipline,_discipline);
                    }    
                    if(_student.getDisciplines() != null){
                        _student.getDisciplines().put(discipline,_discipline);
                        _discipline.getStudents().put(id, _student);
                    }
                    line = reader.readLine();
                    if(line != null){
                      fields = line.split("\\|");
                    }
                }
            }else if(fields[0].equals("ALUNO")){
                int id = Integer.parseInt(fields[1]);
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
                        _courses.put(course, _course); 
                    }
                    _student.setCourse(_course); 
                    Discipline _discipline = _disciplines.get(discipline);
                    if(_discipline == null){
                        _discipline = new Discipline(discipline, _course);
                        _disciplines.put(discipline, _discipline);
                    }
                    _student.getDisciplines().put(discipline,_discipline);
                    _discipline.getStudents().put(id,_student);
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
    
  public void registerAdministrative(String[] fields) {
    int id = Integer.parseInt(fields[1]);
    Administrative _administrative = new Administrative(id, fields[2], fields[3]);
    _administratives.put(id, _administrative);
    _persons.put(id, (Person) _administrative);
  }   
 
  //FIXME implement other methods

  public TreeMap<Integer, Person> getPersons() {
    return _persons;
  }

  public HashMap<Integer, Student> getStudents() {
    return _students;
  }

  public HashMap<Integer, Professor> getProfessors() {
    return _professors;
  }

  public HashMap<Integer, Administrative> getAdministratives() {
    return _administratives;
 }
