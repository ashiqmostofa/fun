package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskD {
    long mod=1000000007;
    long [] dp;
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        dp=new long[111111];
        dp[0]=1;
        int n=in.readInt();
        int k=in.readInt();
        for(int i=1;i<=100000;i++){
            dp[i]=dp[i-1];
            if(i>=k){
                dp[i]+=dp[i-k];
                dp[i]%=mod;
            }
        }
        for(int i=2;i<=100000;i++){
            dp[i]+=dp[i-1];
            dp[i]%=mod;
        }
//        for (int i = 0; i < 5; i++) {
//            out.printLine(dp[i]);
//        }
        dp[0]=0;
        for (int i = 0; i < n; i++) {
            int a,b;
            a=in.readInt();
            b=in.readInt();
            out.printLine((dp[b]-dp[a-1]+mod)%mod);
        }

    }
}
