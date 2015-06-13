package main;

import net.egork.misc.ArrayUtils;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskD {
    long a,b;





    public void solve(int testNumber, InputReader in, OutputWriter out) {
        a=in.readLong();
        b=in.readLong();


        long tem=b-a;
        long cur=1;
        long pos=0;
        long ans=0;

        while(tem>=cur) {
            ans+=cur;
            cur*=2;
            pos++;
        }
        while(b>=cur) {
            if((b&(1L<<pos))!=(a&(1L<<pos)))ans+=(1L<<pos);
            cur*=2;
            pos++;
        }

        out.print(ans);


    }
}
