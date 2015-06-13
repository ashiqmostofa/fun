package main;

import com.sun.jmx.remote.internal.ArrayQueue;
import net.egork.collections.Pair;
import net.egork.collections.intervaltree.IntervalTree;
import net.egork.collections.map.CPPMap;
import net.egork.collections.sequence.ListUtils;
import net.egork.misc.ArrayUtils;
import net.egork.string.StringUtils;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;

import java.util.*;

public class Test {



    public void solve(int testNumber, InputReader in, OutputWriter out) {

        int [] a ={0,0,1};

        ArrayUtils.nextPermutation(a);
        out.print(a);

    }
}
