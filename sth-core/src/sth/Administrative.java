package sth;

import java.io.Serializable;

public class Administrative extends Person implements Serializable{
    
    public Administrative(int id, String phoneNumber, String name){
        super(id, phoneNumber, name);
    }
    
    @Override
    public String toString(){
        return "FUNCIONÁRIO|" + super.toString();
    }

}
