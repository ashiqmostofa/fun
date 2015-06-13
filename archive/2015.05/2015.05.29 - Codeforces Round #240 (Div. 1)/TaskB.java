package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.ArrayList;

public class TaskB {
    public void solve(int testNumber, InputReader in, OutputWriter out) {

        int n=in.readInt();
        int k=in.readInt();

        ArrayList<Integer>[] arr=new ArrayList[2222];
        for (int i = 0; i <2222; i++) {
            arr[i]=new ArrayList<>();
        }

        for(int i=1;i<=2000;i++){
            for(int j=1;j<=2000;j++){
                if(i%j==0)arr[i].add(j);
            }
        }

        int[][] dp=new int[2][2222];
        dp[0][1]=1;
        int mod=1000000007;
        int ans=0;
        for (int kk = 1; kk <= k; kk++) {
            int m=kk&1;
            for(int i=1;i<=n;i++){
                dp[m][i]=0;
            }
            ans=0;
            for(int i=1;i<=n;i++){
                for(int div:arr[i]){
                    dp[m][i]+=dp[1-m][div];
                    dp[m][i]%=mod;
                }
                ans+=dp[m][i];
                ans%=mod;
            }

        }
        out.print(ans);


    }
}
