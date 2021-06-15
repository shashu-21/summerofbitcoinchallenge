import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Resources {
    public static ArrayList<Transactions> originalList = new ArrayList<Transactions>();
    public static ArrayList<CumulativeTrans> updatedList = new ArrayList<CumulativeTrans>();

    static void sortInOrder()
    {
        for(int i=0;i<updatedList.size();i++)
        {
            for(int j=i+1;j<updatedList.size();j++)
            {
                float first = (float)(updatedList.get(i).feeTot)/(updatedList.get(i).weightTot);
                float second = (float)(updatedList.get(j).feeTot)/(updatedList.get(j).weightTot);
                if(first < second)
                {
                    int t = updatedList.get(i).feeTot;
                    updatedList.get(i).feeTot = updatedList.get(j).feeTot;
                    updatedList.get(j).feeTot = t;
                    t = updatedList.get(i).weightTot;
                    updatedList.get(i).weightTot = updatedList.get(j).weightTot;
                    updatedList.get(j).weightTot = t;
                    Set<Integer> temp = updatedList.get(i).ancestors;
                    updatedList.get(i).ancestors = updatedList.get(j).ancestors;
                    updatedList.get(j).ancestors = temp;
                    t =  updatedList.get(j).id;
                    updatedList.get(j).id =  updatedList.get(i).id;
                    updatedList.get(i).id = t;
                }

            }
        }
    }

}
