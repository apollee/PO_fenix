package sth;

import java.text.Collator;
import java.util.Locale;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.ArrayList;
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
import sth.exceptions.InvalidProjectException;
import sth.exceptions.NoSuchPersonIdException;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.CloseSurveyException;
import sth.exceptions.FinishSurveyException;
import sth.exceptions.SurveyFinishException;
import sth.exceptions.NoSuchSurveyException;
import sth.exceptions.NoSubmissionException;
import sth.exceptions.NonEmptyException;

/**
 * The façade class.
 */
public class SchoolManager {

  private School _school = new School();
    private String _filename = "";
  private boolean _filechanged = true;
  private Person _person;


  public void save(String filename) throws IOException, ImportFileException {
    if(_filechanged){
      if(_filename == ""){
        if(filename == null){ 
          throw new ImportFileException();
        }
        _filename = filename;
      }
      _filechanged = false;
      ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)));
      oos.writeObject(_school);
      oos.close();
    }
  }

  public String load(String filename) throws FileNotFoundException, ClassNotFoundException, IOException, NoSuchPersonIdException{
    _filechanged = true;
    _filename = filename;
    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_filename)));
    School school2 = (School) ois.readObject();
    ois.close();   
    _person = school2.login(_person.getId());
    _school = school2;
    String string = "";
    ArrayList<String> notifications = _person.getNotifications();
    for(String notification: notifications){
      string += notification;
    }
    return string;
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
       String string = "";
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


/*===============================PORTAL DA PESSOA===============================*/

  public String showPerson(){
       return _school.showPerson();
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

/*==============================PORTAL DO DOCENTE===============================*/

  public void createProject(String nameProject, String nameDiscipline) throws 
  InvalidDisciplineException, InvalidProjectException{
    _filechanged=true;
    _school.createProject(nameProject, nameDiscipline);
  }


  public void closeProject(String nameProject, String nameDiscipline) throws 
  InvalidDisciplineException, InvalidProjectException, OpenSurveyException{
    _filechanged=true;
    _school.closeProject(nameProject, nameDiscipline);
  }

  public String surveyResults(String disciplineName, String projectName) throws 
  InvalidDisciplineException, InvalidProjectException, NoSuchSurveyException{
    return _school.surveyResults(disciplineName, projectName);
  }

  public String projectSubmissions(String disciplineName, String nameProject) 
  throws InvalidDisciplineException, InvalidProjectException{
    return _school.projectSubmissions(disciplineName, nameProject);
  }

  public String studentsDiscipline(String discipline) throws InvalidDisciplineException{
    return _school.studentsDiscipline(discipline);
  }

 /*============================PORTAL DO ESTUDANTE===============================*/

  public void deliverProject(String disciplineName, String nameProject, 
  String DeliveryMessage) throws InvalidDisciplineException, InvalidProjectException{
    _filechanged=true;
    _school.deliverProject(disciplineName, nameProject, DeliveryMessage);
  } 

  public void fillSurvey(String disciplineName, String projectName, int hours, 
  String comment) throws NoSubmissionException, NoSuchSurveyException {
    _filechanged=true;
    _school.fillSurvey(disciplineName, projectName, hours, comment);
  }

  public String showSurvey(String disciplineName, String projectName) throws InvalidDisciplineException, InvalidProjectException, BadEntryException{
    return _school.showSurvey(disciplineName, projectName);
  }

 /*===================================================PORTAL DO DELEGADO===============================*/

 public void createSurvey(String disciplineName, String projectName) throws 
 BadEntryException, InvalidDisciplineException, InvalidProjectException{
  _filechanged=true; 
  _school.createSurvey(disciplineName, projectName);
 }

 public void cancelSurvey(String disciplineName, String projectName) throws 
 BadEntryException, NonEmptyException, SurveyFinishException, InvalidDisciplineException, InvalidProjectException{
  _filechanged=true;
  _school.cancelSurvey(disciplineName, projectName);
 }

 public void openSurvey(String disciplineName, String projectName) throws 
 BadEntryException, OpenSurveyException, InvalidDisciplineException, InvalidProjectException{
  _filechanged=true;
  _school.openSurvey(disciplineName, projectName);
 }

 public void closeSurvey(String disciplineName, String projectName) throws 
 BadEntryException, CloseSurveyException, InvalidDisciplineException, InvalidProjectException{
  _filechanged=true;
  _school.closeSurvey(disciplineName, projectName);
 }

 public void finalizeSurvey(String disciplineName, String projectName) throws
  BadEntryException, FinishSurveyException, InvalidDisciplineException, InvalidProjectException{
  _filechanged=true;
  _school.finalizeSurvey(disciplineName, projectName);
 }

 public String showSurveys(String disciplineName) throws InvalidDisciplineException{
  return _school.showSurveys(disciplineName);
 }

}
