/*
* Author: Shashwat Sonkar
* Date: 15/06/2021
* */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static void inputTrans() throws FileNotFoundException { //function to take input from mempool.csv
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
    static void inputCumTrans() throws FileNotFoundException { //function to take input from upmempool.csv
        File inpFile = new File("src/upmempool.csv");
        Scanner inFileReader = new Scanner(inpFile);
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
    static void printOutput(ArrayList<Integer> trans) throws FileNotFoundException { // function to print the block in a txt file
        File outFile = new File("src/block.txt");
        PrintWriter outWriter = new PrintWriter(outFile);
        for(int item : trans)
        {
            outWriter.write(Resources.originalList.get(item).tx_id + "\n");
        }
        outWriter.close();

    }
    static int calcGreedy() throws FileNotFoundException { //function to determine the maximum fee generated and create the block
        int n = Resources.updatedList.size();
        ArrayList<Integer> trans = new ArrayList<>();
        int W = 1000000;
        int i = 0;
        int val = 0;
        while(i<n)
        {
            var item = Resources.updatedList.get(i++);
            if(item.ancestors.size()==0)
            {
                if(!trans.contains(item.id)) {
                    if(W-item.weightTot >=0) {
                        W -= item.weightTot;
                        val += item.feeTot;
                        trans.add(item.id);
                    }
                }
            }
            else
            {
                ArrayList<Integer> temp = new ArrayList<>();
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
                            temp.add(ancestor);//adding all the ancestors not already present
                    }
                    if(W-wt>=0) {
                        trans.addAll(temp);//adding all the ancestors not present so far.
                        trans.add(item.id); // adding the element at last, such that order is maintained
                        W -= wt;
                        val += fe;

                    }
                }
            }
        }

        printOutput(trans);
        System.out.println(W);
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


