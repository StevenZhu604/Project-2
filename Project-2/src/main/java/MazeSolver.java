import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class MazeSolver {

    // You do not need to use the following variables, but you may find them helpful.
    final int INFINITY = Integer.MAX_VALUE - 10000;
    private Map<Integer, Integer> distTo;
    private Map<Integer, Vertex> edgeTo;
    private PriorityQueue<Vertex> pq;

    /**
     * Finds the shortest path from the start (top left) to the end (bottom right) of a maze M.
     * @return A list of integers starting from 0 and ending in (m.length * m.width) - 1, describing the
     * path from the start to finish. For example, in the maze:
     * +---+---+---+
     * | 0   1 | 2 |
     * +---+   +   +
     * | 3 | 4   5 |
     * +   +---+   +
     * | 6   7   8 |
     * +---+---+---+
     * The solution should be a list of {0, 1, 4, 5, 8}.
     */
    public List<Integer> solve(Maze m) {
        // Again, the following is optional. You may remove it if you do not need it for your implementation.
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        pq = new PriorityQueue<>(m.getHeight() * m.getWidth(), new DistComparator());
        distTo.put(0,0);
        for (int x=1; x<m.getHeight()*m.getWidth(); x++){
            distTo.put(x, INFINITY);
        }
        Vertex curr=m.getVertex(0);
        pq.add(curr);
        while(!pq.isEmpty()){
            curr=pq.poll();
            for (int x=curr.edges.size()-1; x>=0; x--){
                Vertex temp= (Vertex) curr.edges.toArray()[x];
                if (distTo.get(temp.id)>distTo.get(curr.id)+1){
                    distTo.put(temp.id, distTo.get(curr.id)+1);
                    edgeTo.put(temp.id, curr);
                    pq.add(temp);
                }
            }
        }
        List<Integer> result = new ArrayList<Integer>();
        int temp=m.getHeight()*m.getWidth()-1;
        while (edgeTo.get(temp)!=null){
            result.add(0,temp);
            temp=edgeTo.get(temp).id;
        }
        result.add(0,0);
        return result;
    }

    // Feel free to create additional methods below, if needed.

    /**
     * This is passed into PriorityQueue as a parameter to properly compare the distTo values for two vertices.
     * You can delete this if you decide not to use distTo.
     * IMPORTANT: If distTo is changed, the PriorityQueue will NOT automatically update!
     * To update a PriorityQueue, remove a vertex and then immediately add it back.
     * If you would like to learn more:
     * https://stackoverflow.com/questions/683041/how-do-i-use-a-priorityqueue
     */
    private class DistComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            int d1 = distTo.get(v1.id);
            int d2 = distTo.get(v2.id);

            if (d1 < d2) return -1;
            else if (d1 > d2) return 1;
            return 0;
        }
    }
}