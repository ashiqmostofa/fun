package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;


import java.util.Arrays;

public class TaskC {

    int n;
    int [] x;
    int[] h;
    int[][] dp;

    int fun(int node,int flag){
        if(node==n-1)return 0;

        int ret=dp[node][flag];
        if(ret!=-1)return ret;

        ret=0;

        if(flag==0){
            if(x[node]-h[node]>x[node-1]){
                ret=fun(node+1,0)+1;
            }
            else {
                ret=fun(node+1,0);
                if(x[node]+h[node]<x[node+1]){
                    ret=Math.max(ret,fun(node+1,1)+1);
                }
            }
        }
        else{
            ret=fun(node+1,0);
            if(x[node-1]+h[node-1]<x[node]-h[node]){
                ret=Math.max(ret,fun(node+1,0)+1);
            }
            if(x[node]+h[node]<x[node+1]){
                ret=Math.max(ret,fun(node+1,1)+1);
            }

        }
        return dp[node][flag]=ret;

    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n=in.readInt();

        x=new int[n];
        h=new int[n];
        dp=new int[n+21][2];

        for(int i=0;i<n;i++){
            x[i]=in.readInt();
            h[i]=in.readInt();
        }
        for(int [] a:dp){
            Arrays.fill(a,-1);
        }
//        System.out.print("hic");
        if(n>1)out.print(fun(1,0)+2);
        else out.print(n);
    }
}
