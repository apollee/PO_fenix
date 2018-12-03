package sth;

import java.io.Serializable;

public class ProjectSubmission implements Serializable{
    
    private int _id;
    private String _deliveryMessage;

    public ProjectSubmission(int id, String deliveryMessage){
        _id = id;
        _deliveryMessage = deliveryMessage;
    }

    public int getId(){
        return _id;
    }

    public String getDeliveryMessage(){
        return _deliveryMessage;
    }
}
