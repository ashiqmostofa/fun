import java.util.Deque;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.util.ArrayDeque;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.io.IOException;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author work_harder
 */
public class Main {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		OutputWriter out = new OutputWriter(outputStream);
		TaskC solver = new TaskC();
		solver.solve(1, in, out);
		out.close();
	}
}

class TaskC {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Deque<Integer> d1,d2;
        d1=new ArrayDeque<Integer> ();
        d2=new ArrayDeque<Integer>();
        int n,k1,k2,win=-1;

        n=in.readInt();



        k1=in.readInt();
        for(int i=0;i<k1;i++){
            int tem=in.readInt();
            d1.addLast(tem);
        }
        k2=in.readInt();
        for(int i=0;i<k2;i++){
            int tem=in.readInt();
            d2.addLast(tem);
        }

        int cur=0;

        for(int i=0;i<3628800;i++){

            if(d1.size()==0){
                win=2;
                break;
            }
            if(d2.size()==0){
                win=1;
                break;
            }
            cur++;



            int t1=d1.pollFirst();
            int t2=d2.pollFirst();

            if(t1>t2){
                d1.addLast(t2);
                d1.addLast(t1);
            }
            else{

                d2.addLast(t1);
                d2.addLast(t2);
            }

        }

        if(win!=-1)out.print(cur+" "+win);
        else out.print(win);



    }
}

class InputReader {

    private InputStream stream;
	private byte[] buf = new byte[1024];
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;

	public InputReader(InputStream stream) {
		this.stream = stream;
	}

	public int read() {
		if (numChars == -1)
			throw new InputMismatchException();
		if (curChar >= numChars) {
			curChar = 0;
			try {
				numChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (numChars <= 0)
				return -1;
		}
		return buf[curChar++];
	}

    public int readInt() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		int res = 0;
		do {
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}

    public boolean isSpaceChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return isWhitespace(c);
	}

	public static boolean isWhitespace(int c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

    public interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}

class OutputWriter {
	private final PrintWriter writer;

	public OutputWriter(OutputStream outputStream) {
		writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
	}

    public void print(Object...objects) {
		for (int i = 0; i < objects.length; i++) {
			if (i != 0)
				writer.print(' ');
			writer.print(objects[i]);
		}
	}

    public void close() {
		writer.close();
	}

    public void print(int i) {
		writer.print(i);
	}

}

