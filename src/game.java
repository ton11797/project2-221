import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class game {
    private Graph<String, DefaultEdge> G;
    private SimpleGraph<String, DefaultEdge> SG;
    private DijkstraShortestPath<String, DefaultEdge> DSP;
    private HashSet<String> HS;
    private String initialState;
    private String[] change;
    private String ans;
    private int n;
    game(int N,String I){
        n=N;
        initialState=I;
        HS = new HashSet<>();
        SG = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
        G  = (Graph<String, DefaultEdge>)SG;
        ans();
        genChange();
        createGraph();
        show();
    }
    public void printDefaultEdges(Collection<DefaultEdge> E, boolean f)
    {
        for (DefaultEdge e : E)
        {
                System.out.printf("%18s - %18s \n", G.getEdgeSource(e),G.getEdgeTarget(e));
        }
    }

    public void show(){
        if (G.containsVertex(initialState) && G.containsVertex(ans))
        {
            DSP = new DijkstraShortestPath<String, DefaultEdge>(SG, initialState, ans);
            List<DefaultEdge> path = DSP.getPathEdgeList();
            if (path != null)
            {
                System.out.printf("\nShortest path from %s to %s (length = %.0f) \n",
                        initialState, ans, DSP.getPathLength());
                printDefaultEdges(path, false);
            }
            else
                System.out.printf("\nNo path from %s to %s \n", initialState, ans);
        }
    }
    private void ans(){
        char[] a = new char[n*n];
        for(int i=0;i<n*n;i++)a[i]='0';
        ans = new String(a);
    }
    private void genChange(){
        change = new String[(n*n)];
        for(int k=0;k<n*n;k++) {
            int[][] table = new int[n ][n ];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    table[i][j] = 0;
                }
            }
            int ii=k/n;
            int jj=k%n;
            table[ii][jj]= 1;
            if(ii>0)table[ii-1][jj]=1;
            if(jj>0)table[ii][jj-1]=1;
            if(ii<n-1)table[ii+1][jj]=1;
            if(jj<n-1)table[ii][jj+1]=1;
            change[k]="";
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    change[k] = change[k].concat(Integer.toString(table[i][j]));
                }
            }
        }
    }
    private String changeState(String state,String change){
        char[] stateArray =state.toCharArray();
        char[] changeArray =change.toCharArray();
        String newState;
        for(int i=0;i<n*n;i++){
            if(changeArray[i]=='1'){
                if(stateArray[i]=='0'){
                    stateArray[i]='1';
                }else{
                    stateArray[i]='0';
                }
            }
        }
        newState = new String(stateArray);
        return newState;
    }
    private void createGraph(){
        HS.add(initialState);
        ArrayList<String> allTable = new ArrayList<>();
        allTable.add(initialState);
        G.addVertex(initialState);
        boolean found=false;
        for(int head=0;head<allTable.size() && found!=true;head++) {
            String currentState = allTable.get(head);
            for (int i = 0; i < n * n; i++) {
                String newState = changeState(currentState, change[i]);
                if (HS.contains(newState)) {
                } else {
                    HS.add(newState);
                    allTable.add(newState);
                    G.addVertex(newState);
                    G.addEdge(currentState,newState);
                    if(newState.equals(ans)){
                        System.out.printf("F");
                        found=true;
                        break;
                    }
                }
            }
        }
        System.out.print(HS.size());
    }
}
