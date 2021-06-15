/*
 * Author: Shashwat Sonkar
 * Date: 15/06/2021
 * */

import java.util.HashSet;
import java.util.Set;

public class CumulativeTrans {
    /*
    Class of Updated Transactions, contains all the parameters except the complete tx_ids to save space
    * */
    int id;
    int feeTot, weightTot;
    Set<Integer> ancestors = new HashSet<Integer>();
    public CumulativeTrans(int id,int feeTot, int weightTot)
    {
        this.id = id;
        this.feeTot = feeTot;
        this.weightTot = (weightTot/4);
    }
    public CumulativeTrans(int id,int feeTot, int weightTot, String ancs)
    {
        this.id = id;
        this.feeTot = feeTot;
        this.weightTot = (weightTot/4);
        String[] breakline = ancs.split(";");
        for (String s : breakline) this.ancestors.add(Integer.parseInt(s));
    }
}
