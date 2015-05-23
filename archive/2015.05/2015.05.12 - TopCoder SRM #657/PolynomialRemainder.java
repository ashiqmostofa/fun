package main;

public class PolynomialRemainder {
    public int findRoot(int a, int b, int c) {
        for(int i=0;i<=500000000;i++){
            if((((1L*a*i)%1000000000)*i+1L*b*i+c)%1000000000==0)return i;
        }
        return -1;
    }
}
