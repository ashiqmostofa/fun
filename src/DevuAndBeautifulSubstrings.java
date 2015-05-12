
/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author work_harder
 */
public class DevuAndBeautifulSubstrings {

    static long dp[][][];
    static int count;
    static int n;

    public long countBeautifulSubstrings(int nn, int cnt) {
        dp=new long[2][55][2555];
        n=nn;
        count=cnt;
        dp[0][0][0]=1;


        for(int i=1;i<=n;i++){

            for(int k=0;k<=cnt;k++)dp[i&1][0][k]=0;

            for(int j=1;j<=n;j++){
                for(int k=1;k<=cnt;k++){
                    if(j==1){
                        dp[i&1][j][k]=dp[1-(i&1)][0][k-1];
                    }
                    else if(k>=j){
                        dp[i&1][j][k]=dp[1-(i&1)][j-1][k-j];
                    }
                    else dp[i&1][j][k]=0;

                    dp[i&1][0][k]+=dp[i&1][j][k];
                }
            }

        }
        long ret=0;
        for(int i=0;i<=n;i++)ret+=dp[n&1][i][cnt];

        return ret;


    }
}

