package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskD {

    int n;
    int[] tree;
    int[] arr;

    void init(int node,int left,int right,int depth){

        if(left==right){
            tree[node]=arr[left];
            return;
        }

        int mid=(left+right)/2;

        init(node*2,left,mid,depth-1);
        init(node*2+1,mid+1,right,depth-1);

        if((depth)%2==0){
            tree[node]=tree[2*node]^tree[2*node+1];
        }

        else {
            tree[node]=tree[2*node]|tree[2*node+1];
        }

    }


    void fun(int node,int left,int right,int depth,int pos){

        if(left==right){
            tree[node]=arr[left];
            return;
        }

        int mid=(left+right)/2;

        if(pos>=left&&pos<=mid)fun(node*2,left,mid,depth-1,pos);
        if(pos>=mid+1&&pos<=right)fun(node*2+1,mid+1,right,depth-1,pos);

        if(depth%2==0){
            tree[node]=tree[2*node]^tree[2*node+1];
        }

        else {
            tree[node]=tree[2*node]|tree[2*node+1];
        }

    }



    public void solve(int testNumber, InputReader in, OutputWriter out) {
        tree=new int[1<<20];
        n=in.readInt();
        int m=in.readInt();
        arr=new int[1<<n+1];

        int d=n;

        for(int i=1;i<=(1<<n);i++){
            arr[i]=in.readInt();
        }

        init(1,1,1<<n,d);
        //out.printLine(tree[1]);
        for (int i = 0; i < m; i++) {
            int pos=in.readInt();
            int val=in.readInt();
            arr[pos]=val;
            fun(1,1,1<<n,d,pos);
            out.printLine(tree[1]);
        }


    }
}
