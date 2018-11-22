package sth;

import java.lang.Comparable;
import java.io.Serializable;
import java.util.TreeMap;

public class Discipline implements Serializable{ 

    private int max_students = 20;
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

   /*public int compareTo(Discipline discipline){
    int value = _course.compareTo(discipline.getCourse());
    if(value != 0){
        return value;
    }else{
        return _name.compareTo(discipline.getName());
    } 
   }*/
}
