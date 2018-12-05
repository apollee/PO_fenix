package sth;

import java.lang.Comparable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;

public class Discipline implements SubjectInterface, Serializable{ 

    private int max_students = 20;
    private HashMap<Integer, Observer> _observers = new HashMap<Integer, Observer>();
    private TreeMap<Integer,Student> _students;
    private TreeMap<Integer,Professor> _professors;
    private TreeMap<String,Project> _projects;
    private String _name;
    private Course _course;

    public Discipline(String name, Course course){
        _name = name;
        _course = course;
        _students = new TreeMap<Integer, Student>();
        _professors = new TreeMap<Integer, Professor>();
        _projects = new TreeMap<String,Project>();
    }

   public String getName(){
    return _name;
   }

   public Course getCourse(){
    return _course;
   }

   public TreeMap<Integer,Student> getStudents(){
    return _students;
   }

   public TreeMap<String,Project> getProjects(){
    return _projects;
   }

   public void registerObserver(Observer observer, int id){
       _observers.put(id, observer);
   }

   /*public void removeObserver(Observer observer){
       int index = _observers.indexOf(observer);
       if(index >= 0){
           _observers.remove(index);
       }
   }*/

   public void notifyObservers(String message){
       for(Observer observer: _observers.values()){
           observer.update(message);
       }
   }
}
