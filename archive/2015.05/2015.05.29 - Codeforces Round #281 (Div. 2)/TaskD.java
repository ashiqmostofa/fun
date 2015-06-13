package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskD {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n=in.readInt();
        if(n%2==0){
            out.print("white\n" +
                    "1 2");
        }
        else {
            out.print("black");
        }
    }
}
