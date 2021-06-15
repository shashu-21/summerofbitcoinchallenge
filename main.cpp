#include <bits/stdc++.h>
#include <fstream>
#include <string>
#include <iostream>
#include <limits.h>
# define MAX_WT static_cast<long long>(4000000)
using namespace std;

/*This program creates an updated mempool.csv which contains cumulative fee and weight of
  each transaction including all of its parents.
*/

class Transactions{
    public:
        string tx_id;
        int fee, weight;
        set<string> parent;
    public:
        Transactions(string tx_id, string fee, string weight, string parents): tx_id(tx_id) , fee(stoi(fee)), weight(stoi(weight))
        {
            
            int len = parents.length();
            if(len>=65){
                int i=0;
                while(i<len)
                {
                    parent.insert(parents.substr(i,64));
                    i+=65;
                }

            }
            
        }
};

class UpTrans{
    public:
        
        int feeTot, weightTot;
        set<int> ancestors;
    public:
        UpTrans(int feeTot, int weightTot):feeTot(feeTot), weightTot(weightTot)
        {}
        UpTrans( int feeTot, int weightTot, set<int> ancestors): feeTot(feeTot), weightTot(weightTot), ancestors(ancestors)
        {}
        

};
vector<string> tokenizer(const string &s, string del);

vector<string> tokenizer(const string &s, string del) //function to tokenize a given a string using a delimeter
{
    vector <string> res;
    int start = 0;
    int end = s.find(del);
    while (end != -1) {
        res.push_back(s.substr(start, end - start));
        start = end + del.size();
        end = s.find(del, start);
    }
    res.push_back(s.substr(start, end - start));
    return res;
}
set<int> ancestors;

pair<int, int> calcTot(const set<string> &parent,const vector <Transactions> &ob) // function to find the cumulative fee and weight and also find all the ancestors.
{
    int fee=0, weight=0;
    for(const auto &p:parent)
    {
        
        for(int i=0;i<ob.size();i++)
        {
            const auto &x = ob[i];
            if((x.tx_id)==p)
            {
           
                ancestors.insert(i);
                if(x.parent.empty())
                {
                    fee+=x.fee;
                    weight+=x.weight;
                }
                else
                {
                    pair <int, int> res = calcTot(x.parent,ob);
                    fee+=res.first + x.fee;
                    weight+=res.second + x.weight;
                }
                break;
            }
        }
    }
    return make_pair(fee,weight);
}



int main()
{
    vector <Transactions> ob;
    ifstream baseFile;
    baseFile.open("mempool.csv");
    int count = 0;
    while(baseFile.good())
    {
        string line;
        getline (baseFile, line,'\n');
        vector<string> temp = tokenizer(line,",");
        if(temp.size()==4 && count>0)
        {
           try{
           Transactions t(temp[0],temp[1],temp[2],temp[3]);
           ob.push_back(t);
           }
           catch(...)
           {
               cout<<"Exception Occured\n";
           }
        }
        count++;
        
    } 
    
    baseFile.close();

    vector <UpTrans> finOb;
    for(const auto &x: ob)
    {   
        if(x.parent.size()==0)
        {
            UpTrans temp(x.fee, x.weight);
            finOb.push_back(temp);
        }
        else
        {
           
            pair <int, int> res = calcTot(x.parent, ob);
            UpTrans temp(x.fee + res.first, x.weight + res.second, ancestors);
            ancestors.clear();
            finOb.push_back(temp);
        }
    }
    int n = finOb.size();
    

    ofstream outdata;
    outdata.open("upmempool.csv");
    for(int i=0;i<n;i++)
    {
        outdata<<finOb[i].feeTot<<","<<finOb[i].weightTot<<",";
        for(auto x:finOb[i].ancestors)
        outdata<<x<<";";
        outdata<<"\n";
    }
    outdata.close();
    return 0;
}