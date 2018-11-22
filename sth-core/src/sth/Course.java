package sth;

import java.lang.Comparable;
import java.io.Serializable;
import java.util.HashMap;

public class Course implements Serializable{

    int max_representatives = 7;
    private String _name;
    private HashMap<Integer,Student> _representatives;

    
    public Course(String name){
        _name = name;
        _representatives =  new HashMap<Integer, Student>();
    }

    public HashMap<Integer,Student> getRepresentatives(){
        return _representatives;
    }

    public String getName(){
        return _name;
    }   
    
    /*public int compareTo(Course course){
        return _name.compareTo(course.getName());
    }*/
}
        
