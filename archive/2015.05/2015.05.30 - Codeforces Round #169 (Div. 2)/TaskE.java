package main;



import net.egork.collections.FenwickTree;
import net.egork.graph.Graph;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;


public class TaskE {

    FenwickTree root = new FenwickTree(211111);
    FenwickTree tree = new FenwickTree(211111);
    Graph g;
    int n, m, a, b;

    int[] from_root = new int[211111];
    int[] depth = new int[211111];
    int[] mask = new int[211111];
    //int[] vis = new int[111111];

    int fun(int node, int d, int par) {
        //if(vis[node]==1)return 0;
        mask[node] = a;
        a++;
        //System.out.println(node);
        from_root[mask[node]] = d;
        int id = g.firstOutbound(node);
        int mx = 0;

        while (id != -1) {
            int to = g.destination(id);
            if (par == to) {
                id = g.nextOutbound(id);
                continue;
            }
            mx = Math.max(mx, 1 + fun(to, d + 1, node));
            id = g.nextOutbound(id);
            if(node==1){
                a++;
            }
        }

        return depth[mask[node]] = mx;
    }


    public void solve(int testNumber, InputReader in, OutputWriter out) {
        //System.out.print("hic");
        n = in.readInt();
        m = in.readInt();

        g = new Graph(n + 1);


        for (int i = 0; i < n - 1; i++) {
            a = in.readInt();
            b = in.readInt();
            g.addSimpleEdge(a, b);
            g.addSimpleEdge(b, a);
        }
        a = 1;

        fun(1, 1, -1);


        for (int i = 0; i < m; i++) {
            int t, v, x, d, mx;
            t = in.readInt();
            if (t == 0) {
                v = in.readInt();
                x = in.readInt();
                d = in.readInt();

                if (v == 1) {
                    root.add(1, x);
//                    out.printLine("root plus " + x);
//                    out.printLine("root minus " + (d + 1));
                    root.add(d + 1, -x);
                    continue;
                }


                if (d + 1 >= from_root[mask[v]]) {

                    mx = d+2-from_root[mask[v]];
                    if(mx<=from_root[mask[v]]) {
                        tree.add(mask[v] -  (from_root[mask[v]]-mx) + 1, x);
//                        out.printLine("tree add # "+(mask[v] -  (from_root[mask[v]]-mx) + 1)+" "+x);
                    }
                    else{
                        tree.add(Math.min(mask[v] + (mx - from_root[mask[v]])+1, mask[v] + depth[mask[v]] + 1), x);
//                        out.printLine("tree add * "+(Math.min(mask[v] + (mx - from_root[mask[v]])+1, mask[v] + depth[mask[v]] + 1))+" "+x);
                    }

                    root.add(1,x);
//                    out.printLine("root plus " + x);
//                    out.printLine("root minus " + ( mx + 1));
                    root.add(mx+1, -x);


                }
                else {
                    tree.add(mask[v] - d, x);
//                    out.printLine("tree plus " + (mask[v] - d) + " " + x);
                }

                mx = Math.min(d, depth[mask[v]] + 1);
                tree.add(mask[v] + mx, -x);
//                out.printLine("tree minus " + (mask[v] + mx) + " " + x);


            }
            else {
                //out.print("hic\n");
//                out.printLine("TREEEE  "+tree.get(2,3));
                v = in.readInt();
                if (v != 1) {
                    long fr=root.get(1, from_root[mask[v]]);
                    long tr= tree.get(mask[v] - (from_root[mask[v]] - 2), mask[v]);

                    out.printLine("from root "+fr);
                    out.printLine(" from tree "+tr);
                    out.printLine(" tree "+mask[v]+" "+(from_root[mask[v]] - 2));


                    out.printLine(fr +tr);
                }
                else {
                    long fr=root.get(1, from_root[mask[v]]);
                    //                    out.printLine("from root "+fr);
                    out.printLine(fr);
                }
            }
        }


    }
}
