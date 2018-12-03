package sth;

import java.io.Serializable;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.CloseSurveyException;
import sth.exceptions.FinishSurveyException;
import sth.exceptions.SurveyFinishException;


public class FinalizedState extends SurveyState implements Serializable{

	public FinalizedState(Survey survey){
		super(survey);
    }

    @Override
	public void open() throws OpenSurveyException{
		throw new OpenSurveyException();
	}

    @Override
	public void close() throws CloseSurveyException{
		throw new CloseSurveyException();
    }
    
    @Override
    public void cancel() throws SurveyFinishException{
        throw new SurveyFinishException();
    }

    @Override
    public void finalizeSurvey(){
        /*does nothing*/
    }

    @Override
    public String status(){
        return "Finalized";
    }
}