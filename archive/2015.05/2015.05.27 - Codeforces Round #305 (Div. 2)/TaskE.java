package main;


import net.egork.io.IOUtils;
import net.egork.misc.ArrayUtils;
import net.egork.numbers.IntegerUtils;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.ArrayList;

public class TaskE {
    int n;
    int q;
    int curtot;
    long cur;
    int[] primes;
    int[] numbers;
    int[] dp;
    int[] is;
    int[] mul;
    ArrayList<Integer>[] prime_divisors;


    void primed(int n) {
        if (n == 1) return;
        if(dp[n]!=-1){
            return;
        }
        dp[n]=1;
        int cur = n;
        for (int j = 0; j < primes.length; j++) {
            if (primes[j] * primes[j] > cur) {
                break;
            }
            if (cur % primes[j] == 0) {

                while (cur % primes[j] == 0) {
                    cur /= primes[j];
                }
                prime_divisors[n].add(primes[j]);
                primed(cur);

                prime_divisors[n].addAll(prime_divisors[cur]);
                cur=1;
                break;
            }
        }

        if (cur > 1) {
            prime_divisors[n].add(cur);
        }


    }

    void fun(int val,int sign){

        for(int mask=0;mask<(1<<prime_divisors[val].size());mask++){

            if(mask==0){
                mul[mask]=1;
            }
            else {
                mul[mask] = mul[mask & (mask - 1)] * prime_divisors[val].get(Integer.numberOfTrailingZeros(mask));
            }
            if (sign < 0) {
                dp[mul[mask]]--;
            }
            if ((Integer.bitCount(mask) & 1)==1)
                cur -= dp[mul[mask]] * sign;
            else
                cur += dp[mul[mask]] * sign;
            if (sign > 0){
                dp[mul[mask]]++;
            }
        }

    }



    public void solve(int testNumber, InputReader in, OutputWriter out) {

        prime_divisors = new ArrayList[555555];
        for (int i = 0; i < 500001; i++) {
            prime_divisors[i] = new ArrayList<Integer>();
        }

        for(int i=2;i<=500000;i++){
            if(prime_divisors[i].isEmpty()){
                for (int j = i; j <= 500000; j+=i) {
                    prime_divisors[j].add(i);
                }
            }
        }

//        primes = IntegerUtils.generatePrimes(1000);

        dp = ArrayUtils.createArray(555555, -1);
        is = ArrayUtils.createArray(555555, 1);
        mul = ArrayUtils.createArray(555555, 1);

//        for(int i=500000;i>=2;i--)primed(i);

//        out.print(prime_divisors[6]);
//        out.print(prime_divisors[23]);
//        out.print(prime_divisors[30]);
//        out.print(prime_divisors[210]);

        n=in.readInt();
        q=in.readInt();
        numbers= IOUtils.readIntArray(in,n);
        dp=ArrayUtils.createArray(555555,0);
        for (int qq = 0; qq < q; qq++) {
            int w=in.readInt();
            w--;

            fun(numbers[w],is[w]);

            is[w]*=-1;
            out.printLine(cur);


        }


    }
}
