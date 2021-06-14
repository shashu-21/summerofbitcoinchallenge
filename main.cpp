#include <bits/stdc++.h>
#include <fstream>
#include<string>
#include <iostream>
#include <limits.h>
# define MAX_WT static_cast<long long>(4000000)
using namespace std;
vector<string> tokenizer(string s, string del);
class Transactions{
    public:
        string tx_id;
        int fee, weight;
        unordered_set<string> parent;
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
        string tx_id;
        int feeTot, weightTot;
        unordered_set<string> ancestors;
    public:
        UpTrans(string tx_id, int feeTot, int weightTot): tx_id(tx_id) , feeTot(feeTot), weightTot(weightTot)
        {}
        UpTrans(string tx_id, int feeTot, int weightTot, unordered_set<string> ancestors): tx_id(tx_id) , feeTot(feeTot), weightTot(weightTot), ancestors(ancestors)
        {}
        

};
vector<string> tokenizer(string s, string del)
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
unordered_set<string> ancestors;
pair<int, int> calcTot(unordered_set<string> &parent, vector <Transactions> &ob)
{
    int fee=0, weight=0;
    for(auto p:parent)
    {
        // cout<<p<<"\n";
        // if(parent.size()==1)
        // cout<<"went"<<endl;
        ancestors.insert(p);
        for(auto x: ob)
        {
            if((x.tx_id)==p)
            {
               // cout<<p<<" "<<x.fee<<"\n";
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

    cout<<"vec count:"<<ob.size();

    vector <UpTrans> finOb;
    for(auto x: ob)
    {   
        if(x.parent.size()==0)
        {
            UpTrans temp(x.tx_id,x.fee, x.weight);
            finOb.push_back(temp);
        }
        else
        {
           
            pair <int, int> res = calcTot(x.parent, ob);
            UpTrans temp(x.tx_id,x.fee + res.first, x.weight + res.second, ancestors);
            ancestors.clear();
            finOb.push_back(temp);
        }
    }
    cout<<"vec count:"<<finOb.size();
    for(auto x: finOb)
    cout<<x.tx_id<<" "<<x.feeTot<<" "<<x.weightTot<<" "<<x.ancestors.size()<<"\n";

    
    return 0;
}