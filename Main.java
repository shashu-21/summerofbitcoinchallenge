import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
        System.out.println(Resources.originalList.size());
        inFileReader.close();
    }
    static void inputCumTrans() throws FileNotFoundException {
        File inpFile = new File("src/upmempool.csv");
        Scanner inFileReader = new Scanner(inpFile);
        int sum = 0;
        while (inFileReader.hasNext())
        {
            String line = inFileReader.nextLine().strip();
            String[] breakline = line.split(",");
            CumulativeTrans temp;
            if(breakline.length==2)
                temp = new CumulativeTrans(Integer.parseInt(breakline[0]), Integer.parseInt(breakline[1]));
            else
                temp = new CumulativeTrans(Integer.parseInt(breakline[0]), Integer.parseInt(breakline[1]), breakline[2]);
            Resources.updatedList.add(temp);
        }
        System.out.println(Resources.updatedList.size());
        inFileReader.close();
    }
    static void calc()
    {
        int dp[][]=new int[2][1000000];
        ArrayList<Integer>[][] a =new ArrayList[2][1000000];

        

    }
    public static void main(String[] args) throws FileNotFoundException {

        //taking input from original mempool.csv
        inputTrans();
        //taking input from original upmempool.csv
        inputCumTrans();
        //calculating
        calc();


    }
}
