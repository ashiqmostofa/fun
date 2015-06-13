package main;

import net.egork.io.IOUtils;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskB {
    int[][] arr;
    int[] mx;
    int n,m,q;
    int process(int r){
        int cur=0;
        int ret=0;
        for(int i=0;i<m;i++){
            if(arr[r][i]==1){
                cur++;
                ret=Math.max(ret,cur);
            }
            else{
                cur=0;
            }
        }
        return ret;
    }


    public void solve(int testNumber, InputReader in, OutputWriter out) {

        n=in.readInt();
        m=in.readInt();
        q=in.readInt();

        mx=new int[n];



        arr = IOUtils.readIntTable(in,n,m);
        for(int i=0;i<n;i++)mx[i]=process(i);
//        System.out.println("hic");

        for(int qq=0;qq<q;qq++){
            int r,c;
            r=in.readInt();r--;
            c=in.readInt();c--;
            arr[r][c]=1-arr[r][c];
            mx[r]=process(r);
            c=0;
            for(int i=0;i<n;i++)c=Math.max(c,mx[i]);
            out.printLine(c);
        }
    }
}
