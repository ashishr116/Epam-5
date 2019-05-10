package prims;import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;


class Edge{

    public int from;
    public int to;
    public int wt;

    public Edge(int from, int to, int wt){
        this.from=Math.min(from,to)-1;
        this.to=Math.max(from,to)-1;
        this.wt=wt;
    }

    public String toString(){
        return "F:"+from+" T:"+to+" W:"+wt+"\n";
    }

}

class EdgeComparator implements Comparator<Edge>{

    public int compare(Edge a, Edge b){
        if (a.wt!=b.wt){
            return Integer.signum(a.wt-b.wt);
        }
        else {
            return Integer.signum(a.from+a.to-b.from-b.to);
        }
    }



    /*
     * Complete the 'kruskals' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts WEIGHTED_INTEGER_GRAPH g as parameter.
     */

    /*
     * For the weighted graph, <name>:
     *
     * 1. The number of nodes is <name>Nodes.
     * 2. The number of edges is <name>Edges.
     * 3. An edge exists between <name>From[i] and <name>To[i]. The weight of the edge is <name>Weight[i].
     *
     */

    static void addOneEdge(Map<Integer, Set<Edge>> map,  Edge e, int idx){
        Set<Edge> s=map.get(idx);
        if (s==null) {
            s=new HashSet<Edge>();
            map.put(idx,s);
        } 
        s.add(e);
    }

    static void addEdge(Map<Integer, Set<Edge>> map,  Edge e){
        addOneEdge(map, e, e.from);
        addOneEdge(map, e, e.to);
    }

    static Map<Integer, Set<Edge>> build(int n, int[][] edges) {

        Map<Integer, Set<Edge>> map=new HashMap<Integer, Set<Edge>>();
        Edge e;    

        for (int i=0; i<edges.length; i++){
            e = new Edge(edges[i][0], edges[i][1], edges[i][2]);
            addEdge(map,e);
        }

        return map;

    }

    static void updateRemoveEdges(PriorityQueue<Edge> visiting, 
                             Map<Integer, Set<Edge>> edges,
                             int id,
                             Set<Integer> pending){
        Set<Edge> idSet=edges.get(id);
        for (Edge e: idSet){
            if ( !pending.contains(e.from) || !pending.contains(e.to) ) {
                visiting.remove(e);
            }
        }

    }

    static void updateAddEdges( PriorityQueue<Edge> visiting, 
                             Map<Integer, Set<Edge>> edges,
                             int id,
                             Set<Integer> pending) {
        Set<Edge> idSet=edges.get(id);
        for (Edge e: idSet){
            if ( pending.contains(e.from) || pending.contains(e.to) ) {
                visiting.add(e);
            }
        }
        

    }

    // Complete the prims function below.
    static int prims(int n, int[][] edges, int start) {
        
        PriorityQueue<Edge> visiting=new PriorityQueue<Edge>(n, new EdgeComparator());
        Map<Integer, Set<Edge>> edgesMap=build(n, edges);
        Set<Integer> pending=new HashSet<Integer>();
        
        for (int i=0;i<n;i++){
            pending.add(i);
        }

        start-=1;
        pending.remove(start);

        updateAddEdges(visiting, edgesMap, start, pending);

        Edge e;
        int total=0;
        int ne;
        while (!pending.isEmpty()) {
            e=visiting.poll();
            if (pending.contains(e.from)){
                ne = e.from;
            } else if (pending.contains(e.to)){
                ne = e.to;
            } else {
                continue; // no match
            }
            total += e.wt;
            updateRemoveEdges(visiting, edgesMap, ne, pending);
            pending.remove(ne);
            updateAddEdges(visiting, edgesMap, ne, pending);
        }

        return total;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0]);

        int m = Integer.parseInt(nm[1]);

        int[][] edges = new int[m][3];

        for (int i = 0; i < m; i++) {
            String[] edgesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int edgesItem = Integer.parseInt(edgesRowItems[j]);
                edges[i][j] = edgesItem;
            }
        }

        int start = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int result = prims(n, edges, start);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}