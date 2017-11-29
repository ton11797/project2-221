import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class game 
{
    private Graph<String, DefaultEdge> G;
    private SimpleGraph<String, DefaultEdge> SG;
    private DijkstraShortestPath<String, DefaultEdge> DSP;
    private HashMap<String,Integer> HM;
    private String initialState;
    private String[] change;
    private String ans;
    private int n;
    private ArrayList<Integer> light;
    
    public game(int N,String I)
    {
        n=N;
        initialState=I;
        HM = new HashMap<String,Integer>();
        SG = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
        G  = (Graph<String, DefaultEdge>)SG;
        printTable(I);
        ans();
        genChange();
        createGraph();
        show();
    }
    public void printDefaultEdges(Collection<DefaultEdge> E, boolean f)
    {
        int i=0;
        String status;
        for (DefaultEdge e : E)
        {
            char[] TargetArray =G.getEdgeTarget(e).toCharArray();
            if(TargetArray[HM.get(G.getEdgeTarget(e))]=='1') status="on";
            else status="off";
            System.out.printf("\n>>> Move %d : turn %s row %d col %d\n\n",i+1,status,HM.get(G.getEdgeTarget(e))/n+1,HM.get(G.getEdgeTarget(e))%n+1);
            printTable(G.getEdgeTarget(e));
            System.out.println();
            for(int j=0;j<n;j++) System.out.printf("-----------");
            System.out.println("------------");
            i++;
        }
    }

    public void show()
    {
        if (G.containsVertex(initialState) && G.containsVertex(ans))
        {
            DSP = new DijkstraShortestPath<String, DefaultEdge>(SG, initialState, ans);
            List<DefaultEdge> path = DSP.getPathEdgeList();
            if (path != null)
            {
                System.out.printf("\n%.0f moves to turn off all lights\n",DSP.getPathLength());
                System.out.println();
                for(int j=0;j<n;j++) System.out.printf("-----------");
                System.out.println("------------");
                printDefaultEdges(path, false);
            }
            else System.out.printf("\nImpossible to turn off all lights\n");
        }
    }
    private void ans()
    {
        char[] a = new char[n*n];
        for(int i=0;i<n*n;i++)a[i]='0';
        ans = new String(a);
    }
    private void genChange()
    {
        change = new String[n*n];
        for(int k=0;k<n*n;k++) 
        {
            int[][] table = new int[n][n];
            for (int i=0;i<n;i++) 
            {
                for (int j=0;j<n;j++) table[i][j]=0;
            }
            int ii=k/n;
            int jj=k%n;
            table[ii][jj]= 1;
            if(ii>0)table[ii-1][jj]=1;
            if(jj>0)table[ii][jj-1]=1;
            if(ii<n-1)table[ii+1][jj]=1;
            if(jj<n-1)table[ii][jj+1]=1;
            change[k]="";
            for (int i = 0; i < n; i++) 
            {
                for (int j = 0; j < n; j++) 
                {
                    change[k] = change[k].concat(Integer.toString(table[i][j]));
                }
            }
        }
    }
    private String changeState(String state,String change)
    {
        char[] stateArray =state.toCharArray();
        char[] changeArray =change.toCharArray();
        String newState;
        for(int i=0;i<n*n;i++)
        {
            if(changeArray[i]=='1')
            {
                if(stateArray[i]=='0') stateArray[i]='1';
                else stateArray[i]='0';
            }
        }
        newState = new String(stateArray);
        return newState;
    }
    private void createGraph()
    {
        HM.put(initialState,0);
        ArrayList<String> allTable = new ArrayList<String>();
        allTable.add(initialState);
        G.addVertex(initialState);
        boolean found=false;
        for(int head=0;head<allTable.size() && found!=true;head++) 
        {
            String currentState = allTable.get(head);
            for (int i = 0; i < n * n; i++) 
            {
                String newState = changeState(currentState, change[i]);
                if (!HM.containsKey(newState)) 
                {
                    HM.put(newState,i);
                    allTable.add(newState);
                    G.addVertex(newState);
                    G.addEdge(currentState,newState);
                    if(newState.equals(ans))
                    {
                        found=true;
                        break;
                    }
                }
            }
        }
        if(found==false){
            System.out.println("!!!!!!!!! No Solution !!!!!!!!!!");
        }
    }
    public void printTable(String table)
    {
        char[] s=table.toCharArray();
        System.out.printf("      ");
        for(int i=0;i<n;i++)System.out.printf("   COL %d  ",i+1);
        System.out.println();
        for(int i=0;i<n*n;i++)
        {
            if(i%n==0)
            {
                System.out.printf("      ");
                for(int j=0;j<n;j++) System.out.printf("+---------");
                System.out.println("+");
                System.out.printf("      ");
                for(int j=0;j<n;j++) System.out.printf("|         ");
                System.out.printf("|\nROW %d ",i/n+1);
            }
            System.out.printf("|    %s    ",s[i]);
            if(i%n==n-1)
            {
                System.out.println("|");
                System.out.printf("      ");
                for(int j=0;j<n;j++) System.out.printf("|         ");
                System.out.println("|");
            }
        }
        System.out.printf("      ");
        for(int j=0;j<n;j++) System.out.printf("+---------");
        System.out.println("+");
    }
}
