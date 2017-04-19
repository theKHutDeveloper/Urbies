package com.development.knowledgehut.urbies.Behaviours;


import java.util.Comparator;
import java.util.PriorityQueue;

public class PathFinding {
    private static final int COST = 1;
    private static final int UP_COST = 100; //should not be used in this game

    static class Cell {
        int hCost = 0;  //heuristic cost
        int finalCost = 0; //sum of G + H
        int i, j;
        Cell parent;

        Cell(int i, int j){
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString(){
            return "["+this.i+" , "+this.j+"]";
        }
    }

    private static Cell [][] grid = new Cell[5][5]; //blocked cells are just null Cell values in grid
    private static PriorityQueue<Cell> open;
    private static boolean closed [][];
    private static int startI, startJ;
    private static int endI, endJ;

    private static void setBlocked(int i, int j){
        grid[i][j] = null;
    }

    private static void setStartCell(int i, int j){
        startI = i;
        startJ = j;
    }

    private  static void setEndCell(int i, int j){
        endI = i;
        endJ = j;
    }

    private static void checkAndUpdateCost(Cell current, Cell t, int cost){
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.hCost + cost;

        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost < t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }

    private static void AStar(){
        //add the start location to open list
        open.add(grid[startI][startJ]);
        Cell current;

        while(true){
            current = open.poll();
            if(current == null) break;
            closed[current.i][current.j] = true;

            if(current.equals(grid[endI][endJ])){
                return;
            }

            Cell t;
            if(current.i -1 >= 0){
                t = grid[current.i - 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + COST);
            }

            if(current.j -1 >= 0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost + UP_COST);
            }

            if(current.j + 1 < grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost + COST);
            }

            if(current.i + 1 < grid.length){
                t = grid[current.i + 1][current.j];
                checkAndUpdateCost(current, t, current.finalCost + COST);
            }
        }
    }

    public String getPath(int tCase, int x, int y, int si, int sj, int ei, int ej, int[][] blocked){
        System.out.println("Test Case # "+tCase);

        String example = " ";

        //reset
        grid = new Cell[x][y];
        closed = new boolean[x][y];

        open = new PriorityQueue(16, new Comparator() {


            @Override
            public int compare(Object o1, Object o2) {
                Cell c1 = (Cell)o1;
                Cell c2 = (Cell)o2;
                return c1.finalCost < c2.finalCost ? -1:
                        c1.finalCost > c2.finalCost ? 1 : 0;
            }
        });

        //set start position
        setStartCell(si, sj);

        //set end position
        setEndCell(ei, ej);

        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                grid[i][j] = new Cell(i, j);
                grid[i][j].hCost = Math.abs(i-endI)+Math.abs(j-endJ);
            }
        }
        grid[si][sj].finalCost = 0;

           /*
             Set blocked cells. Simply set the cell values to null
             for blocked cells.
           */
        for (int[] aBlocked : blocked) {
            setBlocked(aBlocked[0], aBlocked[1]);
        }

        //Display initial map
        System.out.println("Grid: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<y;++j){
                if(i==si&&j==sj)System.out.print("SO  "); //Source
                else if(i==ei && j==ej)System.out.print("DE  ");  //Destination
                else if(grid[i][j]!=null)System.out.printf("%-3d ", 0);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();

        AStar();
        System.out.println("\nScores for cells: ");
        for(int i=0;i<x;++i){
            for(int j=0;j<x;++j){
                if(grid[i][j]!=null)System.out.printf("%-3d ", grid[i][j].finalCost);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();

        if(closed[endI][endJ]){
            //Trace back the path
            System.out.println("Path: ");
            Cell current = grid[endI][endJ];
            example = "" + current;
            while(current.parent!=null){
                example = example + (" -> "+current.parent);
                current = current.parent;
            }
            System.out.println();
        }else example = ("No possible path");

        return example;
    }

}
