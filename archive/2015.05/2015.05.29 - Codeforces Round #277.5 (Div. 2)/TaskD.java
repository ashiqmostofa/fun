package main;

import net.egork.collections.Pair;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskD {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        ArrayList<Integer>[] ed=new ArrayList[3333];
        for (int i = 0; i < 3333; i++) {
            ed[i]=new ArrayList<Integer>();
        }

        int n=in.readInt();
        int m=in.readInt();
        for (int i = 0; i < m; i++) {
            int a=in.readInt();
            int b=in.readInt();
            ed[a].add(b);
        }
        ArrayList<Integer>arr=new ArrayList<>();

        long ans=0;

        for (int i = 1; i <= n; i++) {
            int [] cnt=new int[n+1];
            for(int a:ed[i]){
                for(int b:ed[a]){
                    if(b==i)continue;
                    cnt[b]++;
                }
                //out.printLine(i+" "+a+" "+cnt[a]);
            }
            for(int a=1;a<=n;a++)ans+=(cnt[a]*(cnt[a]-1)/2);
        }

        out.print(ans);


    }
}
