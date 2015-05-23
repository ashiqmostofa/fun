package main;

import net.egork.collections.Pair;
import net.egork.collections.sequence.Array;
import net.egork.numbers.IntegerUtils;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TaskD {
    HashMap<Integer,Long>[] dp;
    int n;
    ArrayList<Integer>[] ed;
    ArrayList<Integer>[] calc;
    ArrayList<Integer>[] nodes;
    OutputWriter outt;
    int[] cur_par;
    long[] dpp;

    static long mod=1000000007;






    long fun(int node,int par){

        if(node==0){
            //dpp[node]=1;
            return 0L;
        }

        if(dp[node].containsKey(par)){
            return dp[node].get(par);
        }


        else {
            cur_par[node]=par;
            long ret = 1;
            for (int cur : ed[node]) {
                if (cur == par) continue;
                ret *= (1 + fun(cur, node));
                ret %= mod;
            }
            return ret;
        }
    }

    void fun2(int node){

        if(node==0){
            return ;
        }
        long ret=fun(node,cur_par[node]);

        long par=dpp[cur_par[node]];

        par=(par+mod)*IntegerUtils.power((ret+1)%mod,mod-2,mod);
        par%=mod;

        //outt.print(node+" "+ret+" "+par+"\n");

        ret*=(1+par+mod);
        ret%=mod;

        dpp[node]=ret;

        for(int cur:ed[node]){
            if(cur==cur_par[node])continue;
            fun2(cur);
        }

    }


    public void solve(int testNumber, InputReader in, OutputWriter out) {

        outt=out;
        ed=new ArrayList[211111];
        dp=new HashMap[211111];
        cur_par=new int[211111];
        dpp=new long[211111];



        for(int i=0;i<211111;i++){
            cur_par[i]=-1;
            cur_par[0]=0;
            ed[i]=new ArrayList<>();
            dp[i]=new HashMap<>();
        }

        n=in.readInt();

        int tem;

        for(int i=2;i<=n;i++){
            tem=in.readInt();
            ed[tem].add(i);
            ed[i].add(tem);
        }


        dpp[1]=fun(1,0);

        out.print(fun(2,cur_par[2])+" "+cur_par[2]+" "+" ");

        for(int cur:ed[1]){
            fun2(cur);
        }

        for(int i=0;i<n;i++){

            out.print(dpp[i+1]+" ");

        }



    }
}
