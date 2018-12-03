package sth;

import java.io.Serializable;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.CloseSurveyException;
import sth.exceptions.FinishSurveyException;
import sth.exceptions.SurveyFinishException;


public abstract class SurveyState implements Serializable{

    private Survey _survey;

    public SurveyState(Survey survey){
        _survey = survey;
    }
    
    public Survey getSurvey(){
        return _survey;
    }

    public abstract void open() throws OpenSurveyException;
    public abstract void close() throws CloseSurveyException;
    public abstract void cancel() throws SurveyFinishException;
    public abstract void finalizeSurvey() throws FinishSurveyException;
    public abstract String status();
}
