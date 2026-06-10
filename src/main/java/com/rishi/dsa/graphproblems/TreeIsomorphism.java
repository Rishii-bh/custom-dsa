package com.rishi.dsa.graphproblems;


import java.util.ArrayList;
import java.util.List;

public class TreeIsomorphism {
    public static void main(String[] args) {
        List<List<Integer>> t1 = new ArrayList<>();
        t1.add(List.of(1));
        t1.add(List.of(0, 2, 3));
        t1.add(List.of(1));
        t1.add(List.of(1, 4));
        t1.add(List.of(3));

        List<List<Integer>> t2 = List.of(
                List.of(1, 3, 4),
                List.of(0),
                List.of(4),
                List.of(0),
                List.of(0, 2)
        );

        System.out.println("tree t1 and t2 are isomorphic :"+ areIsomorphic(t1, t2));

        List<List<Integer>> t3 = List.of(
                List.of(1),
                List.of(0, 2),
                List.of(1, 3),
                List.of(2, 4),
                List.of(3)
        );

        List<List<Integer>> t4 = List.of(
                List.of(1, 2, 3, 4),
                List.of(0),
                List.of(0),
                List.of(0),
                List.of(0)
        );
        System.out.println("tree t3 and t4 are isomorphic :"+ areIsomorphic(t3, t4));
    }


    public static List<Integer> findCenter(List<List<Integer>> graph){
        if(graph == null || graph.isEmpty()){
            throw new IllegalArgumentException();
        }
        int[] degree = new int[graph.size()];
        List<Integer> leaves = new ArrayList<>();
        for(int i=0; i<degree.length; i++){
            degree[i] = graph.get(i).size();
            if(degree[i] <= 1){
                leaves.add(i);
                degree[i] = 0;
            }
        }

        int count = leaves.size();
        while(count < graph.size()){
            List<Integer> newLeaves = new ArrayList<>();
            for(int leaf : leaves){
                for(int adj : graph.get(leaf)){
                    degree[adj]--;
                    if(degree[adj] == 1){
                        newLeaves.add(adj);
                        degree[adj] = 0;
                    }
                }
            }
            count += newLeaves.size();
            leaves = newLeaves;
        }
        return leaves;
    }

    public static String encode(List<List<Integer>> graph , int currentNode, int parentNode){
        List<String> childEncodings = new ArrayList<>();

       for(int neighbor : graph.get(currentNode)){
           if(neighbor == parentNode){
               continue;
           }
           String child = encode(graph, neighbor, currentNode);
           childEncodings.add(child);
       }
       childEncodings.sort(String::compareTo);
       return "(" + String.join("", childEncodings) + ")";
    }

    public static String canonicalForm(List<List<Integer>> graph){
        List<String> form = new ArrayList<>();
        List<Integer> centers = findCenter(graph);
        for(int center: centers){
            form.add(encode(graph, center, -1));
        }
        form.sort(String::compareTo);
        return form.get(0);
    }

    public static boolean areIsomorphic(List<List<Integer>> graph1, List<List<Integer>> graph2){
        String form1 = canonicalForm(graph1);
        String form2 = canonicalForm(graph2);
        return form1.equals(form2);
    }

}
