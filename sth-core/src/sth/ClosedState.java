package sth;

import java.io.Serializable;
import sth.exceptions.FinishSurveyException;


public class ClosedState extends SurveyState implements Serializable{

	public ClosedState(Survey survey){
		super(survey);
    }

    @Override
	public void open() {
		getSurvey().setState(new OpenState(getSurvey()));
	}

    @Override
	public void close() {
        /*does nothing*/
    }
    
    @Override
    public void cancel(){
        getSurvey().setState(new OpenState(getSurvey()));
    }

    @Override
    public void finalizeSurvey() throws FinishSurveyException{
        getSurvey().setState(new FinalizedState(getSurvey()));
    }

    @Override
    public String status(){
        return "Closed";
    }
}