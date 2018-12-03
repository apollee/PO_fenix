package sth;
import java.text.Collator;
import java.util.Locale;
import java.util.Comparator;
import java.io.Serializable;

public class Serial2 implements Comparator<Person>, Serializable {

    public int compare(Person a, Person b){
        return Collator.getInstance(Locale.getDefault()).compare(a.getName(),b.getName());
    } 
}
