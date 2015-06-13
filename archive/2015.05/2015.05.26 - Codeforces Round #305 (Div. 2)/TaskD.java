package main;

import net.egork.collections.Pair;
import net.egork.collections.sequence.Array;
import net.egork.io.IOUtils;
import net.egork.misc.ArrayUtils;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.*;

public class TaskD {
    public void solve(int testNumber, InputReader in, OutputWriter out) {

        int n=in.readInt();
        int [] arr=new int[n+2];


        for(int i=1;i<=n;i++){
            arr[i]=in.readInt();
        }

        int[] stack=ArrayUtils.createArray(n+2,0);
        int[] left=ArrayUtils.createArray(n+2,0);
        int[] right=ArrayUtils.createArray(n+2,0);
        int stk=0;

        for(int i=1;i<=n;i++){
            while(stk>0&&arr[stack[stk]]>=arr[i])stk--;
            if(stk>0){
                left[i]=stack[stk];
            }
            else{
                left[i]=0;
            }
            stack[++stk]=i;
        }

        stk=0;

        for(int i=n;i>0;i--){
            while(stk>0&&arr[stack[stk]]>=arr[i])stk--;
            if(stk>0){
                right[i]=stack[stk];
            }
            else{
                right[i]=n+1;
            }
            stack[++stk]=i;
        }

//        TreeSet<Pair<Integer,Integer>> set=new TreeSet<>();

        HashMap<Integer,Integer> map=new HashMap<>();


        for(int i=1;i<=n;i++){

            if(map.containsKey(arr[i])){
                int val=map.get(arr[i]);
                map.put(arr[i],Math.max(val,(right[i]-left[i]-1)));
            }
            else{
                map.put(arr[i],(right[i]-left[i]-1));
            }


        }

        int cur=0;
        int [] ans=new int[n+1];
        TreeSet<Pair<Integer,Integer>> set=new TreeSet<>();

        for(int i=1;i<=n;i++) {
            set.add(Pair.makePair(-map.get(arr[i]), -arr[i]));
            //set.add(Pair.makePair(right[i]-left[i]-1, -arr[i]));
        }



        for(int i=n;i>=1;i--){


            while(set.size()>0&&-set.first().first>=i){
                cur=Math.max(-set.first().second,cur);
                set.pollFirst();
            }

            ans[i]=cur;


        }

        for(int i=1;i<=n;i++){
            out.print(ans[i]+" ");
        }

    }
}
