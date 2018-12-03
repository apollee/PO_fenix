package sth;

import java.util.TreeMap;
import java.io.Serializable;

public class Project implements Serializable{

    private String _name;
    private String _description;
    private Survey _survey;
    private TreeMap<Integer, ProjectSubmission> _projectSubmissions;
    private boolean _open = true;


    public Project(String name){
        _name = name;
        _projectSubmissions = new TreeMap<Integer,ProjectSubmission>();
    }

    public Project(String name, String description){
        _name = name;
        _description = description;
        _projectSubmissions = new TreeMap<Integer,ProjectSubmission>();
    }

    public void setDescription(String description){
        _description = description;
    }

    public void setSurvey(Survey survey){
        _survey = survey;
    }

    public void removeSurvey(){
        _survey = null;
    }

    public void setOpenValue(boolean open){
        _open = open;
    }
    
    public boolean getOpenValue(){
        return _open;
    }

    public Survey getSurvey(){
        return _survey;
    }

    public String getName(){
        return _name;
    }

    public TreeMap<Integer, ProjectSubmission> getProjectSubmissions(){
        return _projectSubmissions;
    }

}
