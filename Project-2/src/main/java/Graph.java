import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Graph {

    ArrayList<Vertex> vertices;

    public Graph(int numVertices) {
        vertices=new ArrayList<Vertex>();
        for (int x=0; x<numVertices; x++){
            Vertex y= new Vertex(x, 0);
            vertices.add(y);
        }
    }


    public void addEdge(int u, int v) {
        if (u!=v){
            vertices.get(u).edges.add(vertices.get(v));
            vertices.get(v).edges.add(vertices.get(u));
        }
    }

    public Vertex getVertex(int id) {
        if (id<0||id>=vertices.size()){
            return null;
        }
        return (vertices.get(id));
    }
}

class Vertex {
    int id;
    int val;
    Set<Vertex> edges;

    Vertex(int id, int val) {
        edges = new HashSet<>();
        this.id = id;
        this.val=val;
    }
}