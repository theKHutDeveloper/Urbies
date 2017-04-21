package com.development.knowledgehut.urbies;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;

public class TileMovements {



    private final static int WIDTH = 5;
    private final static int HEIGHT = 6;
    //create a tile map based on objects at positions and use this to manage the movements - MAYBE
    private ArrayList<Integer>map = new ArrayList<>();
    private ArrayList<Integer>obstacles = new ArrayList<>();
    private ArrayList<Integer>emptyTiles = new ArrayList<>();
    private ArrayList<Integer>matches = new ArrayList<>();

    @Test
    public void mapTest() throws Exception{
        Collections.addAll(map,
                0,1,1,1,0,
                1,1,1,1,1,
                1,1,1,1,1,
                1,1,1,1,1,
                1,1,1,1,1,
                0,1,1,1,0);
        Collections.addAll(obstacles, 15,16,17,18,19);
        Collections.addAll(emptyTiles, 21,22,23); //get the value from finding emptyTiles method
        Collections.addAll(matches, 0,1,2);

        int[][] actualMap = mapOfObjects(map);
        int[][] expectedMap = new int[HEIGHT][WIDTH];

        ArrayList<Integer>blockedTileMap = blockedTileMap(map, obstacles);
        convertTo2D(blockedTileMap);
        assert(true);

    }

    /***************************************************************************
     create a 2D array from map ArrayList
     ***************************************************************************/
    private int[][]mapOfObjects(ArrayList<Integer>map){

        int[][]objectMap = new int[HEIGHT][WIDTH];

        int a = 0; int b = 0;
        for(int i = 0; i < map.size(); i++){

            objectMap[a][b] = i;

            b++;

            if(b >= WIDTH){
                b = 0;
                a++;
            }
        }

        for(int z = 0; z < HEIGHT; z++){
            for(int t = 0; t < WIDTH; t++){
                System.out.print(objectMap[z][t]+" ,");
            }
            System.out.println("+++++++++++++++++++++++");
        }

        return objectMap;
    }

    /***************************************************************************
    create a 2D array of blocked tiles e.g. not in use (0) or blocked obstacles
    ***************************************************************************/
    private ArrayList<Integer> blockedTileMap(ArrayList<Integer>map, ArrayList<Integer>obstacles){
        ArrayList<Integer>blocked = new ArrayList<>();

        //get the not in use (0) map tiles
        for(int i = 0; i < map.size(); i++){
            if(map.get(i) == 0){
                blocked.add(i);
            }
        }

        //get the obstacle locations
        for(int i = 0; i < obstacles.size(); i++){
            blocked.add(obstacles.get(i));
        }

        System.out.println("blocked = "+blocked);
        return blocked;
    }

    /***************************************************************************
    convert blocked ArrayList to 2D array
    ***************************************************************************/
    private int[][]convertTo2D(ArrayList<Integer>blocked){
        int[][]convert = new int[blocked.size()][2];

        for(int i = 0; i < blocked.size(); i++){
            int y = blocked.get(i) / WIDTH;
            int x = blocked.get(i) % WIDTH;

            convert[i][0] = y;
            convert[i][1] = x;
        }

        System.out.println("Converted...");
        for(int i = 0; i < blocked.size(); i++){
            System.out.println(convert[i][0]+ ", "+ convert[i][1]);
        }
        return convert;
    }
}
