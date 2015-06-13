package main;

import net.egork.graph.Edge;
import net.egork.utils.io.InputReader;
import net.egork.utils.io.OutputWriter;


import java.util.*;

/**
 * @author Egor Kulikov (egorku@yandex-team.ru)
 */
class Graph {
    public static final int REMOVED_BIT = 0;

    protected int vertexCount;
    protected int edgeCount;

    private int[] firstOutbound;
    private int[] firstInbound;

    private Edge[] edges;
    private int[] nextInbound;
    private int[] nextOutbound;
    private int[] from;
    private int[] to;
    private long[] weight;
    public long[] capacity;
    private int[] reverseEdge;
    private int[] flags;


    public Graph(int vertexCount) {
        this(vertexCount, vertexCount);
    }

    public Graph(int vertexCount, int edgeCapacity) {
        this.vertexCount = vertexCount;
        firstOutbound = new int[vertexCount];
        Arrays.fill(firstOutbound, -1);

        from = new int[edgeCapacity];
        to = new int[edgeCapacity];
        nextOutbound = new int[edgeCapacity];
        flags = new int[edgeCapacity];
    }

    public static Graph createGraph(int vertexCount, int[] from, int[] to) {
        Graph graph = new Graph(vertexCount, from.length);
        for (int i = 0; i < from.length; i++)
            graph.addSimpleEdge(from[i], to[i]);
        return graph;
    }

    public static Graph createWeightedGraph(int vertexCount, int[] from, int[] to, long[] weight) {
        Graph graph = new Graph(vertexCount, from.length);
        for (int i = 0; i < from.length; i++)
            graph.addWeightedEdge(from[i], to[i], weight[i]);
        return graph;
    }

    public static Graph createFlowGraph(int vertexCount, int[] from, int[] to, long[] capacity) {
        Graph graph = new Graph(vertexCount, from.length * 2);
        for (int i = 0; i < from.length; i++)
            graph.addFlowEdge(from[i], to[i], capacity[i]);
        return graph;
    }

    public static Graph createFlowWeightedGraph(int vertexCount, int[] from, int[] to, long[] weight, long[] capacity) {
        Graph graph = new Graph(vertexCount, from.length * 2);
        for (int i = 0; i < from.length; i++)
            graph.addFlowWeightedEdge(from[i], to[i], weight[i], capacity[i]);
        return graph;
    }

    public static Graph createTree(int[] parent) {
        Graph graph = new Graph(parent.length + 1, parent.length);
        for (int i = 0; i < parent.length; i++)
            graph.addSimpleEdge(parent[i], i + 1);
        return graph;
    }

    public int addEdge(int fromID, int toID, long weight, long capacity, int reverseEdge) {
        ensureEdgeCapacity(edgeCount + 1);
        if (firstOutbound[fromID] != -1)
            nextOutbound[edgeCount] = firstOutbound[fromID];
        else
            nextOutbound[edgeCount] = -1;
        firstOutbound[fromID] = edgeCount;
        if (firstInbound != null) {
            if (firstInbound[toID] != -1)
                nextInbound[edgeCount] = firstInbound[toID];
            else
                nextInbound[edgeCount] = -1;
            firstInbound[toID] = edgeCount;
        }
        this.from[edgeCount] = fromID;
        this.to[edgeCount] = toID;
        if (capacity != 0) {
            if (this.capacity == null)
                this.capacity = new long[from.length];
            this.capacity[edgeCount] = capacity;
        }
        if (weight != 0) {
            if (this.weight == null)
                this.weight = new long[from.length];
            this.weight[edgeCount] = weight;
        }
        if (reverseEdge != -1) {
            if (this.reverseEdge == null) {
                this.reverseEdge = new int[from.length];
                Arrays.fill(this.reverseEdge, 0, edgeCount, -1);
            }
            this.reverseEdge[edgeCount] = reverseEdge;
        }
        if (edges != null)
            edges[edgeCount] = createEdge(edgeCount);
        return edgeCount++;
    }

    protected final GraphEdge createEdge(int id) {
        return new GraphEdge(id);
    }

    public final int addFlowWeightedEdge(int from, int to, long weight, long capacity) {
        if (capacity == 0) {
            return addEdge(from, to, weight, 0, -1);
        } else {
            int lastEdgeCount = edgeCount;
            addEdge(to, from, -weight, 0, lastEdgeCount + entriesPerEdge());
            return addEdge(from, to, weight, capacity, lastEdgeCount);
        }
    }

    protected int entriesPerEdge() {
        return 1;
    }

    public final int addFlowEdge(int from, int to, long capacity) {
        return addFlowWeightedEdge(from, to, 0, capacity);
    }

    public final int addWeightedEdge(int from, int to, long weight) {
        return addFlowWeightedEdge(from, to, weight, 0);
    }

    public final int addSimpleEdge(int from, int to) {
        return addWeightedEdge(from, to, 0);
    }

    public final int vertexCount() {
        return vertexCount;
    }

    public final int edgeCount() {
        return edgeCount;
    }

    protected final int edgeCapacity() {
        return from.length;
    }

    public final Edge edge(int id) {
        initEdges();
        return edges[id];
    }

    public final int firstOutbound(int vertex) {
        int id = firstOutbound[vertex];
        while (id != -1 && isRemoved(id))
            id = nextOutbound[id];
        return id;
    }

    public final int nextOutbound(int id) {
        id = nextOutbound[id];
        while (id != -1 && isRemoved(id))
            id = nextOutbound[id];
        return id;
    }

    public final int firstInbound(int vertex) {
        initInbound();
        int id = firstInbound[vertex];
        while (id != -1 && isRemoved(id))
            id = nextInbound[id];
        return id;
    }

    public final int nextInbound(int id) {
        initInbound();
        id = nextInbound[id];
        while (id != -1 && isRemoved(id))
            id = nextInbound[id];
        return id;
    }

    public final int source(int id) {
        return from[id];
    }

    public final int destination(int id) {
        return to[id];
    }

    public final long weight(int id) {
        if (weight == null)
            return 0;
        return weight[id];
    }

    public final long capacity(int id) {
        if (capacity == null)
            return 0;
        return capacity[id];
    }

    public final long flow(int id) {
        if (reverseEdge == null)
            return 0;
        return capacity[reverseEdge[id]];
    }

    public final void pushFlow(int id, long flow) {
        if (flow == 0)
            return;
        if (flow > 0) {
            if (capacity(id) < flow)
                throw new IllegalArgumentException("Not enough capacity");
        } else {
            if (flow(id) < -flow)
                throw new IllegalArgumentException("Not enough capacity");
        }
        capacity[id] -= flow;
        capacity[reverseEdge[id]] += flow;
    }

    public int transposed(int id) {
        return -1;
    }

    public final int reverse(int id) {
        if (reverseEdge == null)
            return -1;
        return reverseEdge[id];
    }

    public final void addVertices(int count) {
        ensureVertexCapacity(vertexCount + count);
        Arrays.fill(firstOutbound, vertexCount, vertexCount + count, -1);
        if (firstInbound != null)
            Arrays.fill(firstInbound, vertexCount, vertexCount + count, -1);
        vertexCount += count;
    }

    protected final void initEdges() {
        if (edges == null) {
            edges = new Edge[from.length];
            for (int i = 0; i < edgeCount; i++)
                edges[i] = createEdge(i);
        }
    }

    public final void removeVertex(int vertex) {
        int id = firstOutbound[vertex];
        while (id != -1) {
            removeEdge(id);
            id = nextOutbound[id];
        }
        initInbound();
        id = firstInbound[vertex];
        while (id != -1) {
            removeEdge(id);
            id = nextInbound[id];
        }
    }

    private void initInbound() {
        if (firstInbound == null) {
            firstInbound = new int[firstOutbound.length];
            Arrays.fill(firstInbound, 0, vertexCount, -1);
            nextInbound = new int[from.length];
            for (int i = 0; i < edgeCount; i++) {
                nextInbound[i] = firstInbound[to[i]];
                firstInbound[to[i]] = i;
            }
        }
    }

    public final boolean flag(int id, int bit) {
        return (flags[id] >> bit & 1) != 0;
    }

    public final void setFlag(int id, int bit) {
        flags[id] |= 1 << bit;
    }

    public final void removeFlag(int id, int bit) {
        flags[id] &= -1 - (1 << bit);
    }

    public final void removeEdge(int id) {
        setFlag(id, REMOVED_BIT);
    }

    public final void restoreEdge(int id) {
        removeFlag(id, REMOVED_BIT);
    }

    public final boolean isRemoved(int id) {
        return flag(id, REMOVED_BIT);
    }

    public final Iterable<Edge> outbound(final int id) {
        initEdges();
        return new Iterable<Edge>() {
            public Iterator<Edge> iterator() {
                return new EdgeIterator(id, firstOutbound, nextOutbound);
            }
        };
    }

    public final Iterable<Edge> inbound(final int id) {
        initEdges();
        initInbound();
        return new Iterable<Edge>() {
            public Iterator<Edge> iterator() {
                return new EdgeIterator(id, firstInbound, nextInbound);
            }
        };
    }

    protected void ensureEdgeCapacity(int size) {
        if (from.length < size) {
            int newSize = Math.max(size, 2 * from.length);
            if (edges != null)
                edges = resize(edges, newSize);
            from = resize(from, newSize);
            to = resize(to, newSize);
            nextOutbound = resize(nextOutbound, newSize);
            if (nextInbound != null)
                nextInbound = resize(nextInbound, newSize);
            if (weight != null)
                weight = resize(weight, newSize);
            if (capacity != null)
                capacity = resize(capacity, newSize);
            if (reverseEdge != null)
                reverseEdge = resize(reverseEdge, newSize);
            flags = resize(flags, newSize);
        }
    }

    private void ensureVertexCapacity(int size) {
        if (firstOutbound.length < size) {
            int newSize = Math.max(size, 2 * from.length);
            firstOutbound = resize(firstOutbound, newSize);
            if (firstInbound != null)
                firstInbound = resize(firstInbound, newSize);
        }
    }

    protected final int[] resize(int[] array, int size) {
        int[] newArray = new int[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    private long[] resize(long[] array, int size) {
        long[] newArray = new long[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    private Edge[] resize(Edge[] array, int size) {
        Edge[] newArray = new Edge[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public final boolean isSparse() {
        return vertexCount == 0 || edgeCount * 20 / vertexCount <= vertexCount;
    }

    protected class GraphEdge implements Edge {
        protected int id;

        protected GraphEdge(int id) {
            this.id = id;
        }

        public int getSource() {
            return source(id);
        }

        public int getDestination() {
            return destination(id);
        }

        public long getWeight() {
            return weight(id);
        }

        public long getCapacity() {
            return capacity(id);
        }

        public long getFlow() {
            return flow(id);
        }

        public void pushFlow(long flow) {
            Graph.this.pushFlow(id, flow);
        }

        public boolean getFlag(int bit) {
            return flag(id, bit);
        }

        public void setFlag(int bit) {
            Graph.this.setFlag(id, bit);
        }

        public void removeFlag(int bit) {
            Graph.this.removeFlag(id, bit);
        }

        public int getTransposedID() {
            return transposed(id);
        }

        public Edge getTransposedEdge() {
            int reverseID = getTransposedID();
            if (reverseID == -1)
                return null;
            initEdges();
            return edge(reverseID);
        }

        public int getReverseID() {
            return reverse(id);
        }

        public Edge getReverseEdge() {
            int reverseID = getReverseID();
            if (reverseID == -1)
                return null;
            initEdges();
            return edge(reverseID);
        }

        public int getID() {
            return id;
        }

        public void remove() {
            removeEdge(id);
        }

        public void restore() {
            restoreEdge(id);
        }
    }

    public class EdgeIterator implements Iterator<Edge> {
        private int edgeID;
        private final int[] next;
        private int lastID = -1;

        public EdgeIterator(int id, int[] first, int[] next) {
            this.next = next;
            edgeID = nextEdge(first[id]);
        }

        private int nextEdge(int id) {
            while (id != -1 && isRemoved(id))
                id = next[id];
            return id;
        }

        public boolean hasNext() {
            return edgeID != -1;
        }

        public Edge next() {
            if (edgeID == -1)
                throw new NoSuchElementException();
            lastID = edgeID;
            edgeID = nextEdge(next[lastID]);
            return edges[lastID];
        }

        public void remove() {
            if (lastID == -1)
                throw new IllegalStateException();
            removeEdge(lastID);
            lastID = -1;
        }
    }

}


/**
 * @author Egor Kulikov (egorku@yandex-team.ru)
 */
class MaxFlow {
    private final Graph graph;
    private int source;
    private int destination;
    private int[] queue;
    private int[] distance;
    private int[] nextEdge;



    public MaxFlow(Graph graph, int source, int destination) {
        this.graph = graph;
        this.source = source;
        this.destination = destination;
        int vertexCount = graph.vertexCount();
        queue = new int[vertexCount];
        distance = new int[vertexCount];
        nextEdge = new int[vertexCount];
    }

    public static long dinic(Graph graph, int source, int destination,int [][] gg) {
        return new MaxFlow(graph, source, destination).dinic(gg);
    }

    public long dinic(int [][] gg) {
        long totalFlow = 0;
        while (true) {
            edgeDistances();
            if (distance[destination] == -1)
                break;
            Arrays.fill(nextEdge, -2);
            totalFlow += dinicImpl(source, Long.MAX_VALUE,gg);
        }
        return totalFlow;
    }

    private void edgeDistances() {
        Arrays.fill(distance, -1);
        distance[source] = 0;
        int size = 1;
        queue[0] = source;
        for (int i = 0; i < size; i++) {
            int current = queue[i];
            int id = graph.firstOutbound(current);
            while (id != -1) {
                if (graph.capacity(id) != 0) {
                    int next = graph.destination(id);
                    if (distance[next] == -1) {
                        distance[next] = distance[current] + 1;
                        queue[size++] = next;
                    }
                }
                id = graph.nextOutbound(id);
            }
        }
    }

    private long dinicImpl(int source, long flow,int [][] gg) {
        if (source == destination)
            return flow;
        if (flow == 0 || distance[source] == distance[destination])
            return 0;
        int id = nextEdge[source];
        if (id == -2)
            nextEdge[source] = id = graph.firstOutbound(source);
        long totalPushed = 0;
        while (id != -1) {
            int nextDestinationID = graph.destination(id);
            if (graph.capacity(id) != 0 && distance[nextDestinationID] == distance[source] + 1) {
                long pushed = dinicImpl(nextDestinationID, Math.min(flow, graph.capacity(id)),gg);
                if (pushed != 0) {
                    graph.pushFlow(id, pushed);
                    //System.out.print(source+" "+id+" "+pushed+"\n");
                    if(source<nextDestinationID)gg[source][nextDestinationID]+=(int)pushed;
                    else gg[nextDestinationID][source]-=(int)pushed;

                    flow -= pushed;
                    totalPushed += pushed;
                    if (flow == 0)
                        return totalPushed;
                }
            }
            nextEdge[source] = id = graph.nextOutbound(id);
        }
        return totalPushed;
    }
}



public class TaskE {
    public void solve(int testNumber, InputReader in, OutputWriter out) {

        int n;
        n=in.readInt();
        int m;
        m=in.readInt();

        int[] val=new int[n+2];


        int[][] gg=new int[555][555];

        int sm=0;
        int sm2=0;

        int [] from ,to;
        long [] wt;
        from =new int[4*n+2*m];
        to =new int[4*n+2*m];
        wt =new long[4*n+2*m];
        int cur=0;

        for (int i=0;i<n;i++){
            int tem=in.readInt();
            sm2+=tem;
            val[i+1]=tem;
            from[cur]=0;
            to[cur]=i+1;
            wt[cur++]=tem;
//            gr.addFlowEdge(0,i+1,tem);
            int t1=i+1;
            from[cur]=t1;
            to[cur]=t1+n;
            wt[cur++]=Long.MAX_VALUE;
        }

        for (int i=0;i<n;i++){
            int tem=in.readInt();
            sm+=tem;

            from[cur]=n+1+i;
            to[cur]=2*n+1;
            wt[cur++]=tem;
//            gr.addFlowEdge(n+2+i,2*n+3,tem);
            int t2=i+1;
            from[cur]=t2;
            to[cur]=t2+n;
            wt[cur++]=Long.MAX_VALUE;
        }

        for(int i=0;i<m;i++){
            int t1=in.readInt();
            int t2=in.readInt();
//            gr.addFlowEdge(t1,t1+n+1,val[t1]);




//            gr.addFlowEdge(t2,t2+n+1,val[t2]);

//            gr.addFlowEdge(t2,t1+n+1,val[t2]);
            from[cur]=t2;
            to[cur]=t1+n;
            wt[cur++]=Long.MAX_VALUE;
//            gr.addFlowEdge(t1,t2+n+1,val[t2]);
            from[cur]=t1;
            to[cur]=t2+n;
            wt[cur++]=Long.MAX_VALUE;
        }

        //out.printLine(from);
        //out.printLine(to);
        //out.printLine(wt);

        Graph gr=Graph.createFlowGraph(2*n+2,from,to,wt);

        Long tem=MaxFlow.dinic(gr,0,2*n+1,gg);


        if(tem!=sm||sm2!=sm){
            out.print("NO");
        }
        else{

            for(int i=1;i<=n;i++){
                int tt=0;
                for(int j=1;j<=n;j++){

                    tt+=gg[i][j+n];
                }
                if(tt!=val[i]){
                    out.printLine("hic "+i+" "+tt+" "+val[i]);
                }

            }


            out.printLine("YES");
            for(int i=1;i<=n;i++){
                for(int j=1;j<=n;j++){
                    out.print(gg[i][j+n]+" ");
                }
                out.printLine();
            }
        }

    }
}
