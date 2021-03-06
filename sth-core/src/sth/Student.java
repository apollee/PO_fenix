package sth;

import java.util.TreeMap;
import java.util.LinkedList;
import java.io.Serializable;
import sth.Serial;
import sth.exceptions.InvalidDisciplineException;
import sth.exceptions.BadEntryException;
import sth.exceptions.CloseSurveyException;
import sth.exceptions.FinishSurveyException;
import sth.exceptions.ImportFileException;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.CloseSurveyException;
import sth.exceptions.FinishSurveyException;
import sth.exceptions.InvalidProjectException;
import sth.exceptions.SurveyFinishException;
import sth.exceptions.NoSuchSurveyException;
import sth.exceptions.NoSubmissionException;
import sth.exceptions.NonEmptyException;

public class Student extends Person implements Serializable{
    int max_disciplines = 6;
    private TreeMap<String,Discipline> _disciplines;
    private Course _course = new Course(""); 
   
    public Student(int id, String phoneNumber, String name){
        super(id, phoneNumber, name);
        _disciplines = new TreeMap<String,Discipline>(new Serial());
    }

    public void setCourse(Course course){
        _course = course;
    }

    public Course getCourse(){
        return _course;
    }
    
    public TreeMap<String,Discipline> getDisciplines(){
        return _disciplines;
    }

    public void deliverProject(String disciplineName, String projectName, String deliveryMessage, int id)
    throws InvalidDisciplineException, InvalidProjectException{
        Discipline discipline = _disciplines.get(disciplineName);
        if(discipline == null){
            throw new InvalidDisciplineException();
        }

        Project project = discipline.getProjects().get(projectName);
        if(project == null){
            throw new InvalidProjectException();
        }else if(project.getOpenValue() == false){
            throw new InvalidProjectException();
        }
        
        ProjectSubmission submission= new ProjectSubmission(id, deliveryMessage);
        project.getProjectSubmissions().put(id, submission);
    }


    public void fillSurvey(String disciplineName, String projectName, int hours,
    String comment) throws NoSubmissionException, NoSuchSurveyException{
        int id = super.getId();
        Discipline discipline = _disciplines.get(disciplineName);
        Project project = discipline.getProjects().get(projectName);
        ProjectSubmission submission = project.getProjectSubmissions().get(id);
        if(submission == null){
            throw new NoSubmissionException();
        }
        Survey survey = project.getSurvey();
        if(survey == null || survey.status() != "Open"){
            throw new NoSuchSurveyException();
        }
        SurveyAnswer answer = new SurveyAnswer(hours, comment);
        survey.getAnswers().add(answer);
    }

    public String showSurvey(String disciplineName, String projectName) throws InvalidDisciplineException, InvalidProjectException, BadEntryException{
        String string = "";
        Discipline discipline = _disciplines.get(disciplineName);
        if(discipline == null){
            throw new InvalidDisciplineException();
        }
        Project project = discipline.getProjects().get(projectName);
        if(project == null){
            throw new InvalidProjectException();
        }
        
        if(project.getProjectSubmissions().get(super.getId()) == null){
            throw new InvalidProjectException();
        }
        
        Survey survey = project.getSurvey();
        if(survey == null){
            throw new BadEntryException(projectName);
        }
       
        String status = survey.status();
        if(status == "Created"){
            string += discipline.getName() + " - " + project.getName() + " (por abrir)\n";
        }
        else if(status == "Open"){
            string += discipline.getName() + " - " + project.getName() + " (aberto)\n";
        }
        else if(status == "Closed"){
            string += discipline.getName() + " - " + project.getName() + " (fechado)\n";
        }
        else if(status == "Finalized"){
            int total = 0;
            LinkedList<SurveyAnswer> _surveyAnswers = survey.getAnswers();
            int numberAnswers = _surveyAnswers.size();
            string += discipline.getName() + " - " + project.getName() + "\n" + " * Número de respostas: " + numberAnswers + "\n";
            for(SurveyAnswer answer: _surveyAnswers){
                int hours = answer.getHours();
                total += hours;
            }
            int average = total / numberAnswers;
            string += " * Tempo médio (horas): " + average + "\n";
        }
        return string;
    }


    public void createSurvey(String disciplineName, String projectName) throws
    BadEntryException, InvalidDisciplineException, InvalidProjectException{
        Discipline discipline = _course.getDisciplines().get(disciplineName);
        if(discipline == null){
            throw new InvalidDisciplineException();
        }
        Project project = discipline.getProjects().get(projectName);
        if(project == null || project.getOpenValue() == false){
            throw new InvalidProjectException();
        }
        Survey survey = project.getSurvey();
        if(survey != null){
            throw new BadEntryException(projectName);
        }
        survey = new Survey();
        project.setSurvey(survey);
    } 

    public void cancelSurvey(String disciplineName, String projectName) throws
    BadEntryException, SurveyFinishException, NonEmptyException, InvalidDisciplineException, InvalidProjectException{
        Discipline discipline = _course.getDisciplines().get(disciplineName);
        if(discipline == null){
            throw new InvalidDisciplineException();
        }
        Project project = discipline.getProjects().get(projectName);
        if(project == null){
            throw new InvalidProjectException();
        }
        Survey survey = project.getSurvey();
        if(survey == null){
            throw new BadEntryException(projectName);
        }
        if(survey.status() == "Open" || survey.status() == "Created"){
            if(survey.getAnswers().size() >= 1){
                throw new NonEmptyException();
            }
            project.removeSurvey();
            return;
        }
        survey.cancel();
        if(survey.status() != "Open"){
            if(survey.getAnswers().size() >= 1){
                throw new NonEmptyException();
            }
        }
    }
    
    public void openSurvey(String disciplineName, String projectName) throws 
    BadEntryException, OpenSurveyException, InvalidDisciplineException, InvalidProjectException{
        Discipline discipline = _course.getDisciplines().get(disciplineName);
        if(discipline == null){
            throw new InvalidDisciplineException();
        }
        Project project = discipline.getProjects().get(projectName);
        if(project == null){
            throw new InvalidProjectException();
        }
        Survey survey = project.getSurvey();
        if(survey == null){
            throw new BadEntryException(projectName);
        }
        survey.open();
    }

    public void closeSurvey(String disciplineName, String projectName) throws
    BadEntryException, CloseSurveyException, InvalidDisciplineException, InvalidProjectException{
        Discipline discipline = _course.getDisciplines().get(disciplineName);
        if(discipline == null){
            throw new InvalidDisciplineException();
        }
        Project project = discipline.getProjects().get(projectName);
        if(project == null){
            throw new InvalidProjectException();
        }
        Survey survey = project.getSurvey();
        if(survey == null){
            throw new BadEntryException(projectName);
        }
        survey.close();
    }

    public void finalizeSurvey(String disciplineName, String projectName)
    throws BadEntryException, FinishSurveyException, InvalidDisciplineException, InvalidProjectException{
        Discipline discipline = _course.getDisciplines().get(disciplineName);
        if(discipline == null){
            throw new InvalidDisciplineException();
        }
        Project project = discipline.getProjects().get(projectName);
        if(project == null){
            throw new InvalidProjectException();
        }
        Survey survey = project.getSurvey();
        if(survey == null){
            throw new BadEntryException(projectName);
        }
        survey.finalizeSurvey();
        String message = "Resultados do inquérito do projecto " + projectName + " da discipline " + disciplineName + "\n";
        discipline.notifyObservers(message);
    }

    public String showSurveys(String disciplineName) throws 
    InvalidDisciplineException{
        String string = "";
        Discipline discipline = _course.getDisciplines().get(disciplineName);
        if(discipline == null){
            throw new InvalidDisciplineException();
        }
        TreeMap<String, Project> projects = discipline.getProjects();
        if(projects == null){
            return ""; /*??*/
        }
        for(Project project: projects.values()){
            Survey survey = project.getSurvey();
            if(survey != null){
                String status = survey.status();
                if(status == "Created"){
                    string += discipline.getName() + " - " + project.getName() + " (por abrir)\n";
                }
                else if(status == "Open"){
                    string += discipline.getName() + " - " + project.getName() + " (aberto)\n";
                }
                else if(status == "Closed"){
                    string += discipline.getName() + " - " + project.getName() + " (fechado)\n";
                }
                else if(status == "Finalized"){
                    int total = 0;
                    LinkedList<SurveyAnswer> _surveyAnswers = survey.getAnswers();
                    int numberAnswers = _surveyAnswers.size();
                    string += discipline.getName() + " - " + project.getName() + " - " + numberAnswers + " respostas - ";
                    for(SurveyAnswer answer: _surveyAnswers){
                        int hours = answer.getHours();
                        total += hours;
                    }
                    int average = total / numberAnswers;
                    string += average + " horas\n";
                    }
                }
        }
        return string;
    }

    @Override
    public String toString(){
        String string = "";
        int id = super.getId();
        if(_course.getRepresentatives().get(id) == null){
            string +=  "ALUNO|" + super.toString();
        }else{
            string +=  "DELEGADO|" + super.toString();
        }
        for(Discipline _discipline: _disciplines.values()){
            string += "* " + _course.getName() + " - " + _discipline.getName() + "\n"; 
        
        }
        return string;
    }
}
