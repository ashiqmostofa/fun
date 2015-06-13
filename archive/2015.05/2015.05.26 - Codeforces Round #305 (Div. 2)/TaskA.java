package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskA {
    String s;

    int fun(int fs,int ls){
        if(fs>ls)return 1;
        if(s.charAt(fs)!=s.charAt(ls))return 0;
        return fun(fs+1,ls-1);
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        s=in.readString();
        int k=in.readInt();
        if(s.length()%k!=0){
            out.print("NO");
        }
        else{

            int flag=1;
            int sz=s.length()/k;
            for(int i=0;i<k;i++){
                int fs=i*sz;
                int ls=fs+sz-1;

                flag&=fun(fs,ls);

            }

            if(flag==1){
                out.print("YES");
            }
            else{
                out.print("NO");
            }

        }
    }
}
