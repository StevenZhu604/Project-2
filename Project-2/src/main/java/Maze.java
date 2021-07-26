import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.*;

public class Maze extends Graph {
    private int height, width, seed;
    private Random gen;

    public Maze(int height, int width, int seed) {
        // Call Graph constructor
        super(height * width);
        this.height=height;
        this.width=width;
        this.seed=seed;
        generateMaze();
        // Generate maze
    }

    public Maze(int height, int width) {
        this(height, width, (int)(Math.random() * 32768));
    }

    // Getter functions
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    void generateMaze() {
        Vertex currVertex= vertices.get(0);
        Stack<Vertex> vertexStack=new Stack<>();
        vertexStack.push(currVertex);
        Boolean[] visitedVertices=new Boolean[height*width];
        for (int x=0; x<height*width; x++){
            visitedVertices[x]=false;
        }
        visitedVertices[0]=true;
        while(vertexStack.size()!=0){
            currVertex=vertexStack.peek();
            List<Vertex> neighbors=getNeighbors(currVertex);
            for (int x=0; x<neighbors.size(); x++){
                if (visitedVertices[neighbors.get(x).id]){
                    neighbors.remove(x);
                    x--;
                }
            }
            if (neighbors.size()!=0){
                vertexStack.push(neighbors.get(seed%neighbors.size()));
                this.addEdge(currVertex.id, neighbors.get(seed%neighbors.size()).id);
                visitedVertices[neighbors.get(seed%neighbors.size()).id]=true;
                seed *= 1.69;
            } else {
                vertexStack.pop();
            }
        }
    }
    List<Vertex> getNeighbors(Vertex v) {
        List<Vertex> neighbors = new ArrayList<>();
        if (v.id%width!=width-1){
            neighbors.add(vertices.get(v.id+1));
        }
        if (!(v.id>(height-1)*width-1)){
            neighbors.add(vertices.get(v.id+width));
        }
        if (v.id>=width){
            neighbors.add(vertices.get(v.id-width));
        }
        if (v.id%width!=0){
            neighbors.add(vertices.get(v.id-1));
        }
        return neighbors;
    }
}
