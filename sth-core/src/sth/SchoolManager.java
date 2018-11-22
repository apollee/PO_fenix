package sth;

import java.text.Collator;
import java.util.Locale;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Collection;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import sth.exceptions.BadEntryException;
import sth.exceptions.ImportFileException;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.NoSuchPersonIdException;

//FIXME import other classes if needed

/**
 * The façade class.
 */
public class SchoolManager {

  private School _school = new School();
    private String _filename = "";
  private boolean _filechanged = true;
  private Person _person;

  //FIXME add object attributes if needed

  //FIXME implement constructors if needed

  public void save(String filename) throws IOException, ImportFileException {
    if(_filechanged){
      if(_filename == ""){
        if(filename == null){ /*interessa?*/
            throw new ImportFileException();
        }
        _filename = filename;
      }
      _filechanged = false;;
      ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
      oos.writeObject(_school);
      oos.close();
    }
  }

  public void load(String filename) throws FileNotFoundException, ClassNotFoundException, IOException, NoSuchPersonIdException{
    _filechanged = true;
    _filename = filename;
    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_filename)));
    School school2 = (School) ois.readObject();
    ois.close();   
    if(school2.getPersons().get(_person.getId()) != null){
        _school = school2;
    }else{
       throw new NoSuchPersonIdException(_person.getId());
    }
  }

  
  /**
   * @param datafile
   * @throws ImportFileException
   * @throws InvalidCourseSelectionException
   */
  public void importFile(String datafile) throws ImportFileException {
    try {
      _school.importFile(datafile);
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(e);
    }
  }

   /**
   * @param id
   * @throws NoSuchPersonIdException
   */
 public void login(int id) throws NoSuchPersonIdException {
       _person = _school.getPersons().get(id);
       _school.login(id);
    }

  /**
   * @return true when the currently logged in person is an administrative
   */
  public boolean hasAdministrative() {
    int id = _person.getId();
    Administrative _admin = _school.getAdministratives().get(id);
    if(_admin == null){
        return false;
    }
  return true;
  }

  /**
   * @return true when the currently logged in person is a professor
   */
  public boolean hasProfessor() {
    int id = _person.getId();
    Professor _prof = _school.getProfessors().get(id);
    if(_prof == null){
        return false;
    }
  return true;
  }

  /**
   * @return true when the currently logged in person is a student
   */
  public boolean hasStudent() {
    int id = _person.getId();
    Student _student = _school.getStudents().get(id);
    if(_student == null){
        return false;
    }
  return true;
  }

  /**
   * @return true when the currently logged in person is a representative
   */
  public boolean hasRepresentative() {
    int id = _person.getId();
    Student _student = _school.getStudents().get(id);
    if(_student == null){
        return false;
    }
    if((_student.getCourse().getRepresentatives().get(id)) == null){
        return false;
    } 
    return true;
  }

  //FIXME implement other methods (in general, one for each command in sth-app)

    public String showPerson(){
        return _school.showPerson();
    }

    public String studentsDiscipline(String discipline) throws BadEntryException{
        return _school.studentsDiscipline(discipline);
    }

  public void changePhoneNumber(String newPhoneNumber){ 
     _filechanged = true;
     _person.changePhoneNumber(newPhoneNumber);
  }

  public String showAllPersons() {
    return _school.showAllPersons();
  } 
 
  public String searchPerson(String name) {
    return _school.searchPerson(name);
  }


  public void createProject(String nameProject, String nameDiscipline) throws BadEntryException, InvalidDisciplineException{
    _school.createProject(nameProject, nameDiscipline);
  }


  public void closeProject(String nameProject, String nameDiscipline) throws BadEntryException, InvalidDisciplineException{
    _school.closeProject(nameProject, nameDiscipline);
  }
 
} 
 
