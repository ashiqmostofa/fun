package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskD {
    public void solve(int testNumber, InputReader in, OutputWriter out) {

        long n=in.readInt();
        long m=in.readInt();
        long k=in.readLong();

        long lo=0,hi=2500000000000L,mid;

        long ans=hi;

        while(lo<hi){
            mid=(lo+hi)/2;
            long cnt=0;
            long tem;
            for(int i=1;i<=n;i++){

                tem=mid;
                tem/=i;
                if(tem<=m){
                    cnt+=tem;
                }
                else{
                    cnt+=m;
                }
            }
//            out.printLine(lo+" "+hi+" "+mid+" "+cnt);
            if(cnt>k){
                hi=mid;
            }
            else if(cnt<k){
                lo=mid+1;
            }
            else{
                ans=Math.min(ans,mid);
                hi=mid;
            }
        }
        out.print(lo);
    }
}
