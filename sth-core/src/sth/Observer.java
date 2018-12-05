package sth;

import java.io.Serializable;


public interface Observer extends Serializable{

    public void update(String notification);
}