package main;



import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskA {
    public void solve(int testNumber, InputReader in, OutputWriter out) {

        Long k,n,w;
        k=in.readLong();
        n=in.readLong();
        w=in.readLong();

        w=w*(w+1)/2;
        w*=k;
        w-=n;
        if(w<=0){
            out.print(0);
        }
        else {
            out.print(w);
        }


    }
}
