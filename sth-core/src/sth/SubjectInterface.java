package sth;

import java.io.Serializable;

public interface SubjectInterface extends Serializable{

    public void registerObserver(Observer o, int id);
    /*public void removeObserver(Observer o);*/
    public void notifyObservers(String message);


}