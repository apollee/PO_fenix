package sth;

import java.util.TreeMap;
import java.io.Serializable;
import sth.Serial;
public class Student extends Person implements Serializable{
    int max_disciplines = 6;
    private TreeMap<String,Discipline> _disciplines;
    private Course _course; 
   
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
