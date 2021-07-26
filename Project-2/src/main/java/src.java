import java.util.*;
public class src {
    public static void main (String args[]){
        Scanner scan=new Scanner(System.in);
        int currNode=0;
        boolean gamestate=true;
        Maze room1= new Maze(9, 9);
        Maze currMaze=room1;
        MazeSolver solution=new MazeSolver();
        int length=solution.solve(currMaze).size();
        System.out.println("Mode? (easy, medium, hard)");
        String mode=scan.next().toLowerCase();
        int lives=0;
        if (mode.equals("easy")){
            lives=1;
        } else if (mode.equals("medium")){
            lives=3;
        } else if (mode.equals("hard")){
            lives=5;
        }
        printMaze(currMaze, currNode, mode, length);
        while (gamestate){
            System.out.println("Action?");
            System.out.println("Input actions for list of actions");
            String action=scan.next().toLowerCase();
            if (action.equals("actions")){
                System.out.println("actions, map, legend, move: (left, right, up, down) , quit");
            } else if (action.equals("map")){
                printMaze(currMaze, currNode, mode, length);
            } else if (action.equals("legend")){
                System.out.println("1: unknown\n0: plain path\n2: item\n9: you");
            }else if (action.equals("move")){
                System.out.println("Direction?");
                action=scan.next().toLowerCase();
                if (action.equals("left")){
                    if (currNode%currMaze.getWidth()!=0&&currMaze.vertices.get(currNode).edges.contains(currMaze.vertices.get(currNode-1))){
                        currNode--;
                        length--;
                    } else {
                        System.out.println("not a valid action");
                    }
                } else if (action.equals("right")){
                    if (currNode%currMaze.getWidth()!=currMaze.getWidth()-1&&currMaze.vertices.get(currNode).edges.contains(currMaze.vertices.get(currNode+1))){
                        currNode++;
                        length--;
                    } else {
                        System.out.println("not a valid action");
                    }
                } else if (action.equals("up")){
                    if (currNode>=currMaze.getWidth()&&currMaze.vertices.get(currNode).edges.contains(currMaze.vertices.get(currNode-currMaze.getWidth()))){
                        currNode-=currMaze.getWidth();
                        length--;
                    } else {
                        System.out.println("not a valid action");
                    }
                } else if (action.equals("down")){
                    if (currNode<=(currMaze.getHeight()-1)*currMaze.getWidth()-1&&currMaze.vertices.get(currNode).edges.contains(currMaze.vertices.get(currNode+currMaze.getWidth()))){
                        currNode+=currMaze.getWidth();
                        length--;
                    } else {
                        System.out.println("not a valid action");
                    }
                }
                printMaze(currMaze, currNode, mode, length);
            } else if (action.equals("quit")){
                gamestate=false;
            } else {
                System.out.println("Invalid action");
            }
            if(length==0){
                lives--;
                System.out.println("snuffed out");
                if (lives==0){
                    gamestate=false;
                } else {
                    currNode=0;
                    length=solution.solve(currMaze).size();
                    printMaze(currMaze, currNode, mode, length);
                    System.out.print("relit");
                }
            }
        }
    }
    public static void printMaze(Maze x, int currNode, String mode, int length){
        if (mode.equals("easy")){
            for (int y=0; y<x.getHeight(); y++){
                for (int z=0; z<x.getWidth(); z++){
                    if (y*x.getWidth()+z==currNode){
                        System.out.print(9);
                    } else {
                        System.out.print(x.vertices.get(y*x.getWidth()+z).val);
                    }
                    if (y*x.getWidth()+z!=x.getHeight()*x.getWidth()-1&&x.vertices.get(y*x.getWidth()+z).edges.contains(x.vertices.get(y*x.getWidth()+z+1)))
                        System.out.print(" - ");
                    else {
                        System.out.print("   ");
                    }
                }
                System.out.println();
                for (int z=0; z<x.getWidth(); z++){
                    if (y*x.getWidth()+z<x.getHeight()*x.getWidth()-x.getWidth()&&x.vertices.get(y*x.getWidth()+z).edges.contains(x.vertices.get(y*x.getWidth()+z+x.getWidth()))){
                        System.out.print("|   ");
                    } else {
                        System.out.print("    ");
                    }
                }
                System.out.println();
            }
        } else if (mode.equals("medium")){
            for (int y=0; y<x.getHeight(); y++){
                for (int z=0; z<x.getWidth(); z++){
                    if (y*x.getWidth()+z==currNode){
                        System.out.print(9);
                    } else {
                        System.out.print(x.vertices.get(y*x.getWidth()+z).val);
                    }
                    if (y*x.getWidth()+z!=x.getHeight()*x.getWidth()-1&&x.vertices.get(y*x.getWidth()+z).edges.contains(x.vertices.get(y*x.getWidth()+z+1)))
                        System.out.print(" - ");
                    else {
                        System.out.print("   ");
                    }
                }
                System.out.println();
                for (int z=0; z<x.getWidth(); z++){
                    if (y*x.getWidth()+z<x.getHeight()*x.getWidth()-x.getWidth()&&x.vertices.get(y*x.getWidth()+z).edges.contains(x.vertices.get(y*x.getWidth()+z+x.getWidth()))){
                        System.out.print("|   ");
                    } else {
                        System.out.print("    ");
                    }
                }
                System.out.println();
            }
        } else if (mode.equals("hard")){
            List<Integer> reveal= sight(length, currNode, x);
            for (int y=0; y<x.getHeight(); y++){
                for (int z=0; z<x.getWidth(); z++){
                    if (y*x.getWidth()+z==currNode){
                        System.out.print(9);
                    } else {
                        if (reveal.contains(y*x.getWidth()+z)){
                            System.out.print(x.vertices.get(y*x.getWidth()+z).val);
                        } else {
                            System.out.print(1);
                        }
                    }
                    if (reveal.contains(y*x.getWidth()+z)) {
                        if (y * x.getWidth() + z != x.getHeight() * x.getWidth() - 1 && reveal.contains(y*x.getWidth()+z+1)&& x.vertices.get(y * x.getWidth() + z).edges.contains(x.vertices.get(y * x.getWidth() + z + 1))){
                            System.out.print(" - ");
                        } else {
                            System.out.print("   ");
                        }
                    } else {
                        System.out.print("   ");
                    }
                }
                System.out.println();
                for (int z=0; z<x.getWidth(); z++){
                    if (y*x.getWidth()+z<x.getHeight()*x.getWidth()-x.getWidth()&&reveal.contains(y*x.getWidth()+z+x.getWidth())&&reveal.contains(y*x.getWidth()+z)&&x.vertices.get(y*x.getWidth()+z).edges.contains(x.vertices.get(y*x.getWidth()+z+x.getWidth()))){
                        System.out.print("|   ");
                    } else {
                        System.out.print("    ");
                    }
                }
                System.out.println();
            }
        }
    }
    public static List<Integer> sight(int length, int currNode, Maze maze){
        int sight=length/5;
        List<Integer> result = new ArrayList<Integer>();
        result.add(currNode);
        ArrayList<Integer>curr=new ArrayList<>();
        curr.add(currNode);
        ArrayList<Integer>temp=new ArrayList<>();
        for (int x=0; x<sight; x++){
            for (int y:curr){
                for (Vertex u:maze.vertices.get(y).edges){
                    if (!result.contains(u.id)){
                        result.add(u.id);
                        temp.add(u.id);
                    }
                }
            }
            while(!curr.isEmpty()){
                curr.remove(0);
            }
            while(!temp.isEmpty()){
                curr.add(temp.get(0));
                temp.remove(0);
            }
        }
        return result;
    }
}
