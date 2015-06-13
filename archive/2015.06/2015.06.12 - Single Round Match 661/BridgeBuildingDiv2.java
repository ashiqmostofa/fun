package main;

import net.egork.misc.ArrayUtils;
import net.egork.numbers.IntegerUtils;

public class BridgeBuildingDiv2 {
    int n;
    int [][] arr;
    int[] road;
    int[][] dis;
    int mx;

    void fun(int r,int c,int d){

        if(dis[r][c]<=d)return;
        dis[r][c]=d;
//        mx=Math.max(d,mx);

        if(c>0){
            fun(r,c-1,d+arr[r][c-1]);
        }
        if(c<n-1){
            fun(r,c+1,d+arr[r][c]);
        }
        if(road[c]==1){
            fun(1-r,c,d);
        }

    }


    public int minDiameter(int[] a, int[] b, int K) {


        arr=new int[2][a.length+2];
        n=a.length+1;
        arr[0]=a;
        arr[1]=b;
        int ret=1<<30;

        dis=new int[2][n+2];

        road=new int[n];
        for(int i=K,j=n-1;i>0;i--){
            road[j--]=1;
        }

        do{
            for(int i=0;i<n;i++)System.out.print(road[i]+" ");
            System.out.println();
            mx=0;
            for(int i=0;i<2;i++)for(int j=0;j<n;j++){
                ArrayUtils.fill(dis,1<<30);

                fun(i,j,0);
                for(int ii=0;ii<2;ii++){
                    for(int jj=0;jj<n;jj++){
                        mx=Math.max(mx,dis[ii][jj]);
                    }

                }
                System.out.println(i+" "+j);
                for(int ii=0;ii<2;ii++){
                    for(int jj=0;jj<n;jj++){
                        System.out.print(dis[ii][jj]+" ");
                    }
                    System.out.println();
                }

            }
            ret=Math.min(ret,mx);
//            System.out.println(mx);


        }while(ArrayUtils.nextPermutation(road));

        return ret;
    }
}
