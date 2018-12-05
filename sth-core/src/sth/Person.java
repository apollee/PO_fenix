package sth;

import java.io.Serializable;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import sth.exceptions.BadEntryException;

public abstract class Person implements Observer, Serializable{

    private ArrayList<String> _notifications = new ArrayList<String>(); 
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

    public ArrayList<String> getNotifications(){
        ArrayList<String> notifications = _notifications;
        _notifications = new ArrayList<>();
        return notifications;
    }

    public void update(String notification){
        _notifications.add(notification);
    }

    @Override
    public String toString(){
        return getId() + "|" + getPhoneNumber() + "|" + getName() + "\n";
    } 

} 
    
    
