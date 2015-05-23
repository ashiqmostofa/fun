package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Deque;

public class TaskC {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Deque<Integer> d1,d2;
        d1=new ArrayDeque<Integer> ();
        d2=new ArrayDeque<Integer>();
        int n,k1,k2,win=-1;

        n=in.readInt();



        k1=in.readInt();
        for(int i=0;i<k1;i++){
            int tem=in.readInt();
            d1.addLast(tem);
        }
        k2=in.readInt();
        for(int i=0;i<k2;i++){
            int tem=in.readInt();
            d2.addLast(tem);
        }

        int cur=0;

        for(int i=0;i<3628820;i++){

            if(d1.size()==0){
                win=2;
                break;
            }
            if(d2.size()==0){
                win=1;
                break;
            }
            cur++;

//            out.print(d1);
//            out.print("\n");
//            out.print(d2);
//            out.print("\n");



            int t1=d1.pollFirst();
            int t2=d2.pollFirst();

            if(t1>t2){
                d1.addLast(t2);
                d1.addLast(t1);
            }
            else{

                d2.addLast(t1);
                d2.addLast(t2);
            }

        }

        if(win!=-1)out.print(cur+" "+win);
        else out.print(win);



    }
}
