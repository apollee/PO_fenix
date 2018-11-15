package sth;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import sth.exceptions.BadEntryException;
import java.io.Serializable;

public abstract class Person implements Serializable{

    private String _name;
    private String _phoneNumber;    
    private int _id;

    public Person(int id, String phoneNumber, String name){
        _name = name;
        _phoneNumber = phoneNumber;
        _id = id; 
    }

    public String getName(){
        return _name;
    }

    public String getPhoneNumber(){
        return _phoneNumber;
    }

    public int getId(){
        return _id;
    }

    public void changePhoneNumber(String phoneNumber){
       _phoneNumber = phoneNumber;
    }


    @Override
    public String toString(){
        return getId() + "|" + getPhoneNumber() + "|" + getName() + "\n";
    } 

} 
    
    
