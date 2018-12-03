package sth;

import java.lang.Comparable;
import java.io.Serializable;
import java.util.HashMap;

public class Course implements Serializable{

    int max_representatives = 7;
    private String _name;
    private HashMap<Integer,Student> _representatives;
    private HashMap<String, Discipline> _disciplines;

    public Course(String name){
        _name = name;
        _representatives =  new HashMap<Integer, Student>();
        _disciplines = new HashMap<String, Discipline>();
    }

    public HashMap<Integer,Student> getRepresentatives(){
        return _representatives;
    }

    public HashMap<String, Discipline> getDisciplines(){
        return _disciplines;
    }

    public String getName(){
        return _name;
    }   
}
        
