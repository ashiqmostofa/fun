package main;

import net.egork.collections.intcollection.IntArray;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.ArrayList;

public class TaskA {
    public void solve(int testNumber, InputReader in, OutputWriter out) {

        ArrayList arr=new ArrayList<Integer>();
        int n=in.readInt();

        int[][] a=new int[n][n];
        for(int i=0;i<n;i++){
            int cur=0;
            for(int j=0;j<n;j++){
                a[i][j]=in.readInt();
                if(a[i][j]==0||a[i][j]==-1||a[i][j]==2)continue;
                cur=1;
            }
            if(cur==0){
                arr.add(i+1);
            }
        }
        out.printLine(arr.size());
        for(int i=0;i<arr.size();i++)out.print(arr.get(i)+" ");


    }
}
