package sth;

import java.io.Serializable;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.CloseSurveyException;
import sth.exceptions.FinishSurveyException;

public class CreatedState extends SurveyState implements Serializable{

	public CreatedState(Survey survey){
		super(survey);
    }

    @Override
	public void open(){
        getSurvey().setState(new OpenState(getSurvey()));
    }

    @Override    
	public void close() throws CloseSurveyException{
		throw new CloseSurveyException();
    }

    @Override    
    public void cancel(){
        
    }

    @Override    
    public void finalizeSurvey() throws FinishSurveyException{
        throw new FinishSurveyException();
    }

    @Override    
    public String status(){
        return "Created";
    }
}