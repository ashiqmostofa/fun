package main;

import net.egork.misc.ArrayUtils;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskD {
    int dp[][][][];
    int n1,n2,k1,k2;

    int fun(int p1,int p2,int kk1,int kk2){

        if(p1==n1&&p2==n2){
            return 1;
        }
        if(dp[p1][p2][kk1][kk2]!=-1){
            return dp[p1][p2][kk1][kk2];
        }

        int ret=0;

        if(p1<n1&&kk1<k1){
            ret+=fun(p1+1,p2,kk1+1,0);
        }

        if(p2<n2&&kk2<k2){
            ret+=fun(p1,p2+1,0,kk2+1);
        }

        ret%=100000000;



        return dp[p1][p2][kk1][kk2]=ret;

    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {

        dp=new int[111][111][11][11];
        ArrayUtils.fill(dp,-1);
        n1=in.readInt();
        n2=in.readInt();
        k1=in.readInt();
        k2=in.readInt();

        out.print(fun(0,0,0,0));


    }
}
