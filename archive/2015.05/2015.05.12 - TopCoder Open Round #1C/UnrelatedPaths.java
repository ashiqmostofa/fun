package main;

public class UnrelatedPaths {
    public int maxUnrelatedPaths(int[] parent) {
        int ret=0;
        int n=parent.length;
        int[] flag=new int[n+1];
        for(int par : parent){
            flag[par]=1;
        }
        for(int b:flag) {
            if(b==0)ret++;
        }
        return ret;
    }
}
