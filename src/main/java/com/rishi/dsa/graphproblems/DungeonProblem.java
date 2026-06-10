package com.rishi.dsa.graphproblems;

import java.util.*;

public class DungeonProblem {

    public static void main(String[] args) {
        //Define the grid with obstacles here
        //Valid paths are marked with '.' and obstacles with '#'
        //Start and end marked with S and E
        // return the shortest path from S to E if exists else -1
        //it is a 3d grid so 6 movement directions are possible

//        char[][][] dungeon = {
//                {
//                        {'S', '.', '.'},
//                        {'#', '#', '.'},
//                        {'.', '.', '.'}
//                },
//                {
//                        {'#', '#', '.'},
//                        {'.', '.', '.'},
//                        {'.', '#', '#'}
//                },
//                {
//                        {'.', '.', '.'},
//                        {'#', '#', '.'},
//                        {'.', '.', 'E'}
//                }
//        };
//
//        int[] start = {0, 0, 0};
        char[][][] dungeon = {
                {
                        {'S', '.', '#', '.'},
                        {'#', '.', '#', '.'},
                        {'#', '.', '.', '.'},
                        {'#', '#', '#', '.'}
                },
                {
                        {'#', '#', '#', '.'},
                        {'.', '.', '#', '.'},
                        {'.', '.', '#', '.'},
                        {'.', '.', '.', 'E'}
                }
        };

        int[] start = {0, 0, 0};

        List<Integer> path = solve(dungeon, start);
        Collections.reverse(path);
        int R = dungeon[0].length;
        int C = dungeon[0][0].length;
        for(int index: path){
            int[] pathValue = resolveIndex(index, R, C);
            System.out.print(Arrays.toString(pathValue)+ "->");
        }
        System.out.println();
        int result = path.size()-1;
        System.out.println("Shortest move length is  "+result);
    }

    private static List<Integer> solve(char[][][] dungeon,int[] start) {
        int L = dungeon.length;
        int R = dungeon[0].length;
        int C = dungeon[0][0].length;
        boolean[] visited = new boolean[L*R*C];
        int[] parent = new int[L*R*C];
        Arrays.fill(parent, -1);
        int [][] directions = {
                {1,0,0},
                {-1,0,0},
                {0,1,0},
                {0,-1,0},
                {0,0,1},
                {0,0,-1}
        };
        Queue<int[]> queueToVisit = new LinkedList<>();
        queueToVisit.add(start);
        int startIndex = getIndex(start[0] , start[1], start[2],R ,C);
        visited[startIndex] = true;
        parent[startIndex] = startIndex;
        while(!queueToVisit.isEmpty()) {
            int levelSize = queueToVisit.size();
            for(int i = 0; i < levelSize; i++) {
                int[] current = queueToVisit.remove();
                int currentIndex = getIndex(current[0] , current[1], current[2],R ,C);
                if(dungeon[current[0]][current[1]][current[2]] == 'E') {
                    return reconstructPath(parent,currentIndex);
                }
                for(int[] vector : directions) {
                    int x = current[0] + vector[0];
                    int y = current[1] + vector[1];
                    int z = current[2] + vector[2];
                    if(!isValid(dungeon , visited, x, y, z)) {
                        continue;
                    }
                    int[]position = {x,y,z};
                    visited[getIndex(x,y,z,R,C)] = true;
                    parent[getIndex(x,y,z,R,C)] = currentIndex;
                    queueToVisit.add(position);
                }

            }
        }
        return new ArrayList<>();
    }
    private static boolean isValid(char[][][] dungeon, boolean[] visited, int x, int y, int z) {
        int L = dungeon.length;
        int R = dungeon[0].length;
        int C = dungeon[0][0].length;

        if (x < 0 || x >= L) return false;
        if (y < 0 || y >= R) return false;
        if (z < 0 || z >= C) return false;

        if (visited[getIndex(x,y,z,R,C)]) return false;
        return dungeon[x][y][z] != '#';
    }
    private static int getIndex(int x, int y, int z, int R , int C) {
        return x*R*C + y*C + z;
    }

    private static List<Integer> reconstructPath(int[]parent , int at){
        if(at >= parent.length ||at <0){
            throw new IllegalArgumentException();
        }
        List<Integer> path = new ArrayList<>();
        while(parent[at] != at){
           path.add(at);
           at = parent[at];
        }
        path.add(at);
        return path;
    }

    private static int[] resolveIndex(int n, int R, int C){
        int x =n/(R*C);
        int rem = n%(R*C);
        int y = rem/C;
        int z = rem%C;
        return new int[]{x,y,z};
    }


}
