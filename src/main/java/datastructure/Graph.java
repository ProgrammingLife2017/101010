package datastructure;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.*;

/**
 * Created by 101010 on 14-5-2017.
 */
public class Graph {
    private HashMap<Integer, LinkedList<Integer>> adjacent;

    public Graph(int v) {
        adjacent = new HashMap<>();
    }

    public void addEdge(int c, int n) {
        LinkedList<Integer> current = adjacent.get(c);
        if (current.equals(null)) {
            current = new LinkedList<Integer>();
        }
        current.add(n);
    }

//    public void sortUtil(int c, boolean[] visited) {
//        visited[c] = true;
//        int n;
//
//        Iterator<Integer> it = adjacent.get(c).iterator();
//        while(it.hasNext()) {
//            n = it.next();
//            if (!visited[n]) {
//                sortUtil(n, visited);
//            }
//        }
//    }

    public void drawGraph() {
        int nodeAmount = adjacent.size();
        Set<Integer> visited = new HashSet<>();


    }
}
