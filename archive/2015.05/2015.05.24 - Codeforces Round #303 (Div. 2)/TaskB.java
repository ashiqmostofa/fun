package main;

import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskB {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        String s1,s2;
        s1=in.readString();
        s2=in.readString();



        int differ=0;
        int differ1=0;
        int differ2=0;
        for(int i=0;i<s1.length();i++){
            if(s1.charAt(i)!=s2.charAt(i))differ++;
        }
        if(differ%2==1){
            out.print("impossible");
        }
        else{
            differ/=2;
            char[] arr=s1.toCharArray();
            for(int i=0;i<s1.length();i++){
                if(differ>0&&s1.charAt(i)!=s2.charAt(i)){
                    arr[i]=s2.charAt(i);
                    differ--;
                }
            }
            out.print(arr);
        }

    }
}
