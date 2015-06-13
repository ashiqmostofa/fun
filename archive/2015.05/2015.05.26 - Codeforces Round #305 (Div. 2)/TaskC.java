package main;





import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

public class TaskC {
    long h1, a1, x1, y1, x, p1;
    long h2, a2, x2, y2, y, p2;
    int m;

    int count;

    long calk_x(long h){
        count++;
        if(count>m+21)return Integer.MAX_VALUE;
        if(h==a1)return 0;
        else return 1+calk_x((h*x1+y1)%m);
    }
    long calk_y(long h){
        count++;
        if(count>m+21)return Integer.MAX_VALUE;
        if(h==a2)return 0;
        else return 1+calk_y((h*x2+y2)%m);
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        m = in.readInt();
        h1 = in.readInt();
        a1 = in.readInt();
        x1 = in.readInt();
        y1 = in.readInt();
        h2 = in.readInt();
        a2 = in.readInt();
        x2 = in.readInt();
        y2 = in.readInt();



        count=0;
        x=calk_x(h1);
        if(x>=Integer.MAX_VALUE){
            out.printLine("-1");
            return;
        }

        count=0;
        y=calk_y(h2);
        if(y>=Integer.MAX_VALUE){
            out.printLine("-1");
            return;
        }
        //out.printLine(x+" "+p1+" "+y+" "+p2);
        count=0;
        p1=calk_x((a1*x1+y1)%m)+1;
        if(p1>=Integer.MAX_VALUE){
            p1=Integer.MAX_VALUE;
            //out.printLine("-1");
            //return;
        }
        count=0;
        p2=calk_y((a2*x2+y2)%m)+1;
        if(p2>=Integer.MAX_VALUE){
            p2=Integer.MAX_VALUE;
            //out.printLine("-1");
           // return;
        }

        if(p1==Integer.MAX_VALUE||p2==Integer.MAX_VALUE){



            if(p1==Integer.MAX_VALUE&&p2==Integer.MAX_VALUE){
                if(x==y){
                    out.print(x);
                    return;
                }
            }
            else if(p1!=Integer.MAX_VALUE&&y>=x){//
                long nn=y-x;
                nn/=p1;
                if(nn*p1+x==y){
                    out.print(y);
                    return;
                }
                else {//out.printLine("hic");
                    out.print(-1);
                    return;
                }
            }
            else if(p2!=Integer.MAX_VALUE&&x>=y){
                long nn=x-y;
                nn/=p2;
                if(nn*p2+y==x){
                    out.print(x);
                    return;
                }
                else {
                    out.print(-1);
                    return;
                }
            }

            else {
                out.print(-1);
                return;
            }

        }

        for(int i=0;i<=1000111;i++){
            long tem=x+i*p1;
            long t=tem-y;

            if(t<0)continue;

            long mm=t/p2;

            if(mm*p2+y==tem){
                out.print(tem);
                return;
            }
        }

        for(int i=0;i<=1000111;i++){
            long tem=y+i*p2;
            long t=tem-x;

            if(t<0)continue;

            long mm=t/p1;

            if(mm*p1+x==tem){
                out.print(tem);
                return;
            }
        }

        out.print(-1);



    }
}
