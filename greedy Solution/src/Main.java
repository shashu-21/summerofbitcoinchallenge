import javax.security.sasl.SaslClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    static void inputTrans() throws FileNotFoundException {
        File inpFile = new File("src/mempool.csv");
        Scanner inFileReader = new Scanner(inpFile);
        inFileReader.nextLine();
        while (inFileReader.hasNext())
        {
            String line = inFileReader.nextLine().strip();
            String breakline[] = line.split(",");
            Transactions temp;
            if(breakline.length==3)
                temp = new Transactions(breakline[0],Integer.parseInt(breakline[1]),Integer.parseInt(breakline[2]),"");
            else
                temp = new Transactions(breakline[0],Integer.parseInt(breakline[1]),Integer.parseInt(breakline[2]),breakline[3]);
            Resources.originalList.add(temp);
        }
        inFileReader.close();
    }
    static void inputCumTrans() throws FileNotFoundException {
        File inpFile = new File("src/upmempool.csv");
        Scanner inFileReader = new Scanner(inpFile);
        int sum = 0;
        int id = 0;
        while (inFileReader.hasNext())
        {
            String line = inFileReader.nextLine().strip();
            String[] breakline = line.split(",");
            CumulativeTrans temp;
            if(breakline.length==2)
                temp = new CumulativeTrans(id,Integer.parseInt(breakline[0]), Integer.parseInt(breakline[1]));
            else
                temp = new CumulativeTrans(id,Integer.parseInt(breakline[0]), Integer.parseInt(breakline[1]), breakline[2]);
            id++;
            Resources.updatedList.add(temp);
        }
        inFileReader.close();
    }
    static void printOutput(HashSet<Integer> trans) throws FileNotFoundException {
        File outFile = new File("src/block.txt");
        PrintWriter outWriter = new PrintWriter(outFile);
        for(int item : trans)
        {
            outWriter.write(Resources.originalList.get(item).tx_id + "\n");
        }
        outWriter.close();

    }
    static int calcGreedy() throws FileNotFoundException {
        int n = Resources.updatedList.size();
        HashSet<Integer> trans = new HashSet<>();
        int W = 1000000;
        int i = 0;
        int val = 0;
        while(W>0 && i<n)
        {
            var item = Resources.updatedList.get(i++);
            if(item.ancestors.size()==0)
            {
                if(!trans.contains(item.id)) {
                    W -= item.weightTot;
                    val += item.feeTot;
                    trans.add(item.id);
                }
            }
            else
            {
                if(!trans.contains(item.id))
                {
                    int wt = item.weightTot;
                    int fe = item.feeTot;
                    for(int ancestor : item.ancestors)
                    {
                        if(trans.contains(ancestor))
                        {
                            wt-=Resources.originalList.get(ancestor).weight;
                            fe-=Resources.originalList.get(ancestor).fee;
                        }
                        else
                            trans.add(ancestor);//adding all the ancestors not already present
                    }
                    trans.add(item.id);
                    W -= wt;
                    val += fe;
                }
            }
        }

        printOutput(trans);
        System.out.println(trans.size());
        return val;
    }
    public static void main(String[] args) throws FileNotFoundException {

        //taking input from original mempool.csv
        inputTrans();
        //taking input from original upmempool.csv
        inputCumTrans();
        Resources.sortInOrder();

        //calculating
        System.out.println(calcGreedy());


    }
}

