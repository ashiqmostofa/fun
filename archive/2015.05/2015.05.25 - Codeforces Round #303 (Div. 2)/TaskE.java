package main;

import net.egork.collections.Pair;
import net.egork.misc.ArrayUtils;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class TaskE {
    public void solve(int testNumber, InputReader in, OutputWriter out) {

        int n,m;
        n=in.readInt();
        m=in.readInt();

        ArrayList<Integer>[] ed=new ArrayList[n];
        for(int i=0;i<n;i++)ed[i]=new ArrayList<Integer>();
        ArrayList<Integer>[] wt=new ArrayList[n];
        for(int i=0;i<n;i++)wt[i]=new ArrayList<Integer>();
        ArrayList<Integer>[] id=new ArrayList[n];
        for(int i=0;i<n;i++)id[i]=new ArrayList<Integer>();

        int [] edwt=new int[m+1];

        for(int i=0;i<m;i++){
            int t1,t2,t3;
            t1=in.readInt();
            t2=in.readInt();
            t3=in.readInt();
            t1--;
            t2--;
            ed[t1].add(t2);
            ed[t2].add(t1);

            wt[t1].add(t3);
            wt[t2].add(t3);

            id[t1].add(i+1);
            id[t2].add(i+1);

            edwt[i+1]=t3;

        }

        int cr=in.readInt();
        cr--;
        long[] dis=new long[n];
        Arrays.fill(dis, Long.MAX_VALUE);
        dis[cr]=0;

        TreeSet<Pair<Pair<Long,Integer>,Pair<Integer,Integer>>> pq=new TreeSet<>();

        pq.add(Pair.makePair(Pair.makePair(0L,0), Pair.makePair(0, cr)));

        long sum=0;

        ArrayList<Integer> ans=new ArrayList<>();

        while(pq.size()>0){
            Pair p=pq.pollFirst();
            int cur=(int)(((Pair)p.second).second);
            int cured=(int)(((Pair)p.second).first);
            int curedval=(int)(((Pair)p.first).second);
            long curwt=(long)(((Pair)p.first).first);



            if(cured>0&&dis[cur]>curwt){
                ans.add(cured);
                dis[cur]=curwt;
                sum+=curedval;
            }


            //out.printLine(dis);
            for(int i=0;i<ed[cur].size();i++){
                int nxt=(int)ed[cur].get(i);
                int w=(int)wt[cur].get(i);
                int edd=(int)id[cur].get(i);
                if(dis[cur]+w<=dis[nxt]){
                    pq.add(Pair.makePair(Pair.makePair(dis[cur]+w,w),Pair.makePair(edd,nxt)));
                }
            }
        }
        //System.out.print("hic");
        out.printLine(sum);
        for(int i:ans){
            out.print(i+" ");
        }

    }
}
