package sth;

import java.io.Serializable;
import java.util.LinkedList;
import sth.exceptions.OpenSurveyException;
import sth.exceptions.CloseSurveyException;
import sth.exceptions.FinishSurveyException;
import sth.exceptions.SurveyFinishException;

public class Survey implements Serializable{

    private SurveyState _state = new CreatedState(this);
    private LinkedList<SurveyAnswer> _surveyAnswers;

    public Survey(){
        _surveyAnswers = new LinkedList<SurveyAnswer>();
    }

    public void setState(SurveyState state) {
		_state = state;
    }

    public void open() throws OpenSurveyException{
        _state.open();
    }

    public void close() throws CloseSurveyException{
        _state.close();
    }

    public void cancel() throws SurveyFinishException{
        _state.cancel();
    }

    public void finalizeSurvey() throws FinishSurveyException{
        _state.finalizeSurvey();
    }

    public String status() {
        return _state.status();
    }

    public LinkedList<SurveyAnswer> getAnswers(){
        return _surveyAnswers;
    }

}
