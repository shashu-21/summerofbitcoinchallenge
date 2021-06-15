## summerofbitcoinchallenge

**Directory Structure**

The repository contains a directory 'src' within which the java source code for generating the block is present. The repo also contains main.cpp and block.txt.

**Source Files**

* main.cpp: This program takes mempool.csv file and input and creates another file upmempool.csv which contains the cumulative fee and weight of
  each transaction including all of its parents.
  
* Main.java: This contains the driver code for block generation where different classes and functions are accessed to generate and output the block of permissible transactions.
* Resources.java: This is the class that stores the dynamic arrays of objects of Transactions and CumulativeTrans class. It allows the use of these list in the main function and also includes a sorting function.
* Transactions.java and CumulativeTrans.java: These contain classes objects of which are used to represent each entry in the input file.

**Idea**

There were two points of interests when tackling this problem:
* 0/1 Knapsack: This problem is a variation of 0/1 Knapsack problem, the difference here being that order of transactions must be maintained, which calls for checking for conflicts before each decision. The problem can be solved in O(2^n) time using a recursive approach, but since the number of entries here are very high we cannot use this approach. Another approach can be dynamic programmming using tabulation, with O(n^2 x W) time complexity and O(n^2 x W) space complexity in the solution that I first tried or O(nxW) time complexity using HashSets, which causes the program to crash due to insufficient memory. This is because 0/1 knapsack is NP Complete with pseudo polynomial time comlexity.
    
* Greedy Approach: Since the DP approach was very costly in terms of computational time and memory, the approach I followed was to apply a greedy algorithm on the sorted list. The heuristic followed was to priortize those transactions with a higher fee/weight ratio. Using this approach the time complexity came out to be O(W) and space complexity of O(n). This Solution is not the best solution for the given set of transactions, but it is close to total fees that the transactions have.


**References**

[Miner Fees](https://en.bitcoin.it/wiki/Miner_fees)

[0/1 Knapsack Using DP](https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/)

[NP Completeness of 0/1 Knapsack](https://www.baeldung.com/cs/knapsack-problem-np-completeness#:~:text=The%20decision%20version%20of%20the,is%20an%20NP%2DComplete%20problem.&text=Therefore%2C%20the%20knapsack%20problem%20can,.)
