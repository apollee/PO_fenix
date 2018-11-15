package sth;
import java.text.Collator;
import java.util.Locale;
import java.util.Comparator;
import java.io.Serializable;

public class Serial implements Comparator<String>, Serializable {

    public int compare(String a, String b){
        return Collator.getInstance(Locale.getDefault()).compare(a,b);
    } 

}
