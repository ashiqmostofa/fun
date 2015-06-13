package main;

import net.egork.io.IOUtils;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskD {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        long cur=0;
        int n;
        n=in.readInt();
        int [] arr= IOUtils.readIntArray(in,n);
        int[] pr=IOUtils.readIntArray(in,5);
        long[] ans=new long[5];
        for(int a:arr){
            cur+=a;
            for(int i=4;i>=0;i--)if(pr[i]>0){

                long cnt=cur/pr[i];
                ans[i]+=cnt;
                cnt*=pr[i];
                cur-=cnt;

            }

        }

        for(int i=0;i<5;i++){
            out.print(ans[i]+" ");
        }
        out.printLine();
        out.print(cur);
    }
}
