package sth;

import java.io.Serializable;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.FinishSurveyException;


public class OpenState extends SurveyState implements Serializable{

	public OpenState(Survey survey){
		super(survey);
	}

	@Override
	public void open() throws OpenSurveyException{
		throw new OpenSurveyException();
	}

	@Override
	public void close() {
		getSurvey().setState(new ClosedState(getSurvey()));
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
		return "Open";
	}
}