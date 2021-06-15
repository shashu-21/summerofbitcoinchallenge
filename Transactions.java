import java.util.HashSet;
import java.util.Set;

public class Transactions {
    String tx_id;
    int fee, weight;
    Set<String> parent = new HashSet<String>();
    public Transactions(String tx_id, int fee, int weight, String parents)
    {
        this.tx_id = tx_id;
        this.fee = fee;
        this.weight = (weight/4);
        int len = parents.length();
        if(len>=65){
            int i=0;
            while(i<len)
            {
                parent.add(parents.substring(i,i+64));
                i+=65;
            }

        }
    }

}
