package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.Arrays;

public class TaskD {
    public void solve(int testNumber, InputReader in, OutputWriter out) {

        int n=in.readInt();
        int [] arr=new int[n];
        for(int i=0;i<n;i++){
            arr[i]=in.readInt();
        }
        Arrays.sort(arr);

        int curwait=0,cur=0;
        for(int i=0;i<n;i++){

            if(arr[i]<curwait){

            }
            else{
                curwait+=arr[i];
                cur++;
            }


        }
        out.print(cur);

    }
}
