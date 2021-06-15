import java.util.HashSet;
import java.util.Set;

public class CumulativeTrans {
    int feeTot, weightTot;
    Set<Integer> ancestors = new HashSet<Integer>();
    public CumulativeTrans(int feeTot, int weightTot)
    {
        this.feeTot = feeTot;
        this.weightTot = weightTot;
    }
    public CumulativeTrans(int feeTot, int weightTot, String ancs)
    {
        this.feeTot = feeTot;
        this.weightTot = weightTot;
//        this.ancestors = ancestors;
        String[] breakline = ancs.split(";");
        for (String s : breakline) this.ancestors.add(Integer.parseInt(s));

    }
}
