package rustandmudrer;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    static class EdgeWeightedGraph{        
        int noOfVertices;
        HashMap<Integer,Set<Integer>> adjVertices;
        
        public EdgeWeightedGraph(int v){            
            this.noOfVertices = v+1;
            adjVertices = new HashMap<Integer,Set<Integer>>();            
        }
        
        public void addEdge( int v, int w) {            
            if(adjVertices.containsKey(v)){
                Set<Integer> set = adjVertices.get(v);
                set.add(w);
                adjVertices.put(v,set);
            }
            else{
                Set<Integer> set = new HashSet<Integer>();
                set.add(w);
                adjVertices.put(v,set);
            }
            
            if(adjVertices.containsKey(w)){
                Set<Integer> set = adjVertices.get(w);
                set.add(v);
                adjVertices.put(w,set);
            }
            else{
                Set<Integer> set = new HashSet<Integer>();
                set.add(v);
                adjVertices.put(w,set);
            }
        }       
        
        
        private Set<Integer> getAdjVertices(int v){
            if(adjVertices.get(v) == null){ return new HashSet<Integer>();}
            return adjVertices.get(v);
        }         

    }
    

    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;
 
        public FastReader()
        {
            br = new BufferedReader(new
                     InputStreamReader(System.in));
        }
 
        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int nextInt()
        {
            return Integer.parseInt(next());
        }
 
        long nextLong()
        {
            return Long.parseLong(next());
        }
 
        double nextDouble()
        {
            return Double.parseDouble(next());
        }
 
        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }

  public static void main(String[] args)
    {
        FastReader fs=new FastReader();
      int t = fs.nextInt();
      while(t-->0){
          int v = fs.nextInt();
          int e = fs.nextInt();
          EdgeWeightedGraph G = new EdgeWeightedGraph(v);
         
          for(int i=0; i<e ;i++){
              int u = fs.nextInt();
              int w = fs.nextInt();              
              G.addEdge(u,w);              
          }
          int s = fs.nextInt();
          int[] d = new int[v+1];
          Arrays.fill(d,1);
          d[s] = 0;
          for(int w: G.getAdjVertices(s)){
              d[w] = Integer.MAX_VALUE;
          }
          
          for(int w: G.getAdjVertices(s)){
              find(w,d,G);
          }
          
          for(int i=1; i<=v ;i++){              
              if(i==s){continue;}              
              System.out.print(d[i]+" ");
              
          }
          System.out.println();
      }
    }
    
    public static void find(int w,int[] d, EdgeWeightedGraph G){
        boolean[] marked = new boolean[d.length];
        int min = 1;
        int vertex = 0;        
        whileloop:
        while(d[w] ==  Integer.MAX_VALUE){
            forloop:
            for(int i=1; i<d.length;i++){
                if(!marked[i] && d[i]==min){
                    vertex = i;
                    marked[vertex] = true;
                    break forloop;
                }
            }
            if(vertex == 0){ min++; continue whileloop;}
            for(int j=1; j<d.length;j++){
                if( d[j] == Integer.MAX_VALUE && !G.getAdjVertices(vertex).contains(j)){
                    d[j] = d[vertex]+1;                
                }
            } 
            vertex = 0;
        }
    }
    
    public static void printd(int[] d){
        for(int i=1;i<d.length;i++){
            System.out.print("("+i+":"+d[i]+")");
        }
        System.out.println();
    }
    
}