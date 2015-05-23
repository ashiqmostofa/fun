package main;



import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n=in.readInt();

        int[] arr=new int[n];

        for (int i=0;i<n;i++){
            arr[i]=in.readInt();

        }
        Arrays.sort(arr);
        int cur=1;
        int ans=0;
        for(int i=0;i<n;i++){
            if(arr[i]>=cur){
                cur=arr[i]+1;
            }
            else{
                ans+=cur-arr[i];
                cur++;
            }
        }
        out.print(ans);
    }
}
