package sth;

import java.io.Serializable;

public class Project implements Serializable{
    
    private String _name;
    private String _description;
    private boolean _open = true;

    public Project(String name){
        _name = name;
    }

    public Project(String name, String description){
        _name = name;
        _description = description;
    }

    public void setOpen(boolean open){
        _open = open;
    }
    
    public boolean getOpenValue(){
        return _open;
    }
}
