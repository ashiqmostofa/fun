package main;

import net.egork.misc.ArrayUtils;

public class ColorfulLineGraphsDiv2 {

    int[][][] dp=new int [111][111][111];
    int n,k;
    int mod=1000000007;

    int fun(int pos,int r2,int r3){
        if(pos==n)return 1;
        int r1=pos-(r2+r3);
        if(dp[pos][r2][r3]!=-1)return dp[pos][r2][r3];

        long ret=1L*fun(pos+1,r2,r3)*(r2+r3+1);
        ret%=mod;

        if(k>1){
            ret+=1L*fun(pos+1,r2+1,r3)*(r1+r3+1);
            ret%=mod;
        }
        if(k>2){
            ret+=1L*fun(pos+1,r2,r3+1)*(r1+r2+1);
            ret%=mod;
        }

        return dp[pos][r2][r3]=(int)ret;
    }

    public int countWays(int N, int K) {
        n=N;
        k=K;
        ArrayUtils.fill(dp,-1);

        int ret=0;
        ret+=fun(1,0,0);
        ret%=mod;
        if(k>1){
            ret+=fun(1,1,0);
            ret%=mod;
        }
        if(k>2){
            ret+=fun(1,0,1);
            ret%=mod;
        }



        return ret;
    }
}
