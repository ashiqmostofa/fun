package main;

public class FallingSand {
    public String[] simulate(String[] board) {

        int n=board.length;
        int m=board[0].length();

        char[][] br=new char[n][m];
        int ii=0;
        for(String b :board){
            br[ii++]=b.toCharArray();
        }

        for(int i=0;i<m;i++){

            for(int j=0;j<n;j++){

                for(int k=n-2;k>=0;k--){



                    if(br[k+1][i]=='.'&&br[k][i]=='o'){
                        br[k][i]='.';
                        br[k+1][i]='o';
                    }


                }

            }

        }

        for(int i=0;i<n;i++){
            board[i]=new String(br[i]);
        }

        return board;
    }
}
