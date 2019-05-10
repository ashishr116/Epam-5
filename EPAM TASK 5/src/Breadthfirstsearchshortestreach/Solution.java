package Breadthfirstsearchshortestreach;
import java.util.*;

class Graph {
    private static int EDGE_DISTANCE;
    public boolean[] visited;
    public int[] distance;
    public ArrayList<HashSet<Integer>> graph;
   public int start;
    
    public Graph(int n, int edgeWeight) {
        this.EDGE_DISTANCE = edgeWeight;
        
        this.visited = new boolean[n];
        this.distance = new int[n];
        Arrays.fill(distance, -1);
        
        this.graph = new ArrayList<HashSet<Integer>>();
        for(int i = 0; i < n; i++) {
            this.graph.add(new HashSet<Integer>());
        }
    }
    
    public void shortestReach(int s) {
        
        this.start = s;
        
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(start);
        visited[start] = true;
        distance[start] = 0;
        
        while (queue.size() > 0) {
            int u = queue.remove();for (int v : graph.get(u)) {if (!visited[v]) {
                    queue.add(v);
                    visited[v] = true;
                    distance[v] = distance[u] + EDGE_DISTANCE;
                }
            }
        }
        
        for (int i : distance) {
            if (i != 0) {
                System.out.print(i + " ");
            } 
        }
        
        System.out.println();Arrays.fill(distance, -1);
        Arrays.fill(visited, false);
    }
}

public class Solution {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int queries = scanner.nextInt();
        
        for(int t = 0; t < queries; t++) {
        	Graph bfs = new Graph(scanner.nextInt(), 6);
            int m = scanner.nextInt();
            for(int i = 0; i < m; i++) {
                int u = scanner.nextInt() - 1;
                int v = scanner.nextInt() - 1;
                bfs.graph.get(u).add(v);
                bfs.graph.get(v).add(u);
            }
            bfs.shortestReach(scanner.nextInt() - 1);
        }
        
        scanner.close();
    }
}