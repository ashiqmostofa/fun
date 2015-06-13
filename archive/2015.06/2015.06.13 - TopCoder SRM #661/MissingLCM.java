package main;

import net.egork.misc.ArrayUtils;
import net.egork.numbers.IntegerUtils;

public class MissingLCM {
    public int getMin(int N) {

        int [] pr= IntegerUtils.generatePrimes(1000000);

        int ret=2;
        for(int i:pr){

            if(i>N)break;

            long tem=i;

            while(tem*i<=N){
                tem*=i;
            }

//            System.out.println(i+" "+tem+" "+ret);

            ret=Math.max(ret,(int)tem*2);

        }

        return ret;
    }
}
