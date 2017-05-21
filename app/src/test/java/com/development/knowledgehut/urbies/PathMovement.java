package com.development.knowledgehut.urbies;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.development.knowledgehut.urbies.Behaviours.PathFinding;

public class PathMovement {

    @Test
    public void separateMadness()throws Exception{
        ArrayList<Integer> emptyTiles = new ArrayList<>();
        ArrayList<Integer> reference;
        ArrayList<Integer> obstacleLocations;
        ArrayList<Integer> glassLocations = new ArrayList<>();
        ArrayList<Integer> entrance = new ArrayList<>();
        ArrayList<Integer> matches = new ArrayList<>();
        ArrayList<Integer> map;
        ArrayList<Integer> offScreen = new ArrayList<>();
        int width = 6;

        map = initialiseMap();
        obstacleLocations = initialObstacles(entrance);
        Collections.addAll(matches,27,28,29,14,15,16); 
        Collections.sort(matches, Collections.<Integer>reverseOrder());
        Collections.addAll(emptyTiles, 24, 25, 26);

        reference = tileStatus(matches, map, obstacleLocations, glassLocations, emptyTiles);

        for(int i = 0; i < reference.size(); i++){
            if(reference.get(i) < 0){
                System.out.print(reference.get(i) + ", ");
            } else if(reference.get(i) < 10){
                System.out.print(" " + reference.get(i) + ", ");
            }
            else {
                System.out.print(reference.get(i) + ", ");
            }
            if(i % width == 5){
                System.out.println(" ");
            }
        }

        //=============================================================
        //      NO ENTRANCES
        //=============================================================
        //handles the situation where there are no entrances -
        //either because there are no solid obstacles or the row is blocked
        if(entrance.isEmpty()){
            for(int i = 0; i < matches.size(); i++) {
                int start = matches.get(i) % width;
                LinkedList<int[]> pathway = getPathway(reference, map, start, matches.get(i), width);

                if(pathway.isEmpty()){
                    System.out.println("Path is BLOCKED");
                    offScreen.add(matches.get(i));
                }
                else {
                    for (int k = 0; k < pathway.size(); k++) {
                        int[] element;
                        element = pathway.get(k);
                        int position = (element[0] * width) + element[1];

                        if (reference.get(position) != -3 && reference.get(position) != -5) {
                            int[][] temp = new int[k + 1][2];
                            for (int j = 0; j < k + 1; j++) {
                                temp[j][0] = pathway.get(j)[0];
                                temp[j][1] = pathway.get(j)[1];
                            }

                            System.out.println(position);
                            for (int j = 0; j < temp.length; j++) {
                                System.out.println("temp = [" + temp[j][0] + "][" + temp[j][1]+ "]");
                            }

                            int value = (pathway.peekFirst()[0] * width) + pathway.peekFirst()[1];
                            int no_value = reference.get(value);
                            reference.set(value, reference.get(position));
                            reference.set(position, no_value);
                            pathway.pollFirst();
                            k--;
                        }
                    }
                }
            }
            for (int i = 0; i < reference.size(); i++) {
                System.out.print(reference.get(i) + ", ");
                if (i % width == 5) {
                    System.out.println("");
                }
            }

            System.out.println("Matches Off Screen = "+offScreen);
        }
        //=============================================================
        //      ENTRANCES AVAILABLE
        //=============================================================
        else if(!entrance.isEmpty()){

            ArrayList<Integer>simpleMatch = new ArrayList<>();

            for(int i = matches.size() - 1; i >= 0; i--){
                if(matches.get(i) >= entrance.get(0) + width){
                    emptyTiles.add(matches.get(i));
                }
            }

            //match element is above entrance so will need to do something different here
            for(int i = 0; i < matches.size(); i++){
                if(!emptyTiles.contains(matches.get(i))){
                    simpleMatch.add(matches.get(i));
                }
            }

            //NOTE: SHOULD I DO SIMPLE MATCH FIRST? - MAYBE NOT

            //carry out the more complex routine as there are empty tiles or matches below entrances
            if(!emptyTiles.isEmpty()) {
                boolean moreObjectsToMove = true;

                do {
                    int entryPoint = getBestEntryPoint(entrance, width, emptyTiles.get(0));

                    ArrayList<Integer>aboveEntryPoint = findObjectsAboveEntryPoint(reference, entryPoint, width);

                    if(reference.get(entryPoint) == -3 || reference.get(entryPoint) == -5){
                        entryPoint = aboveEntryPoint.get(0);
                    }

                    //get the correct ordering of missing objects
                    emptyTiles = orderByFurthestFromEntry(emptyTiles, entryPoint, width);

                    //establish the route between entrance and first missing object
                    LinkedList<int[]> pathway = getPathway(reference, map, entryPoint, emptyTiles.get(0), width);

                    //move objects to the first empty position
                    for (int i = 0; i < pathway.size(); i++) {
                        int[] element;
                        element = pathway.get(i);
                        int position = (element[0] * width) + element[1];

                        if (reference.get(position) != -3 && reference.get(position) != -5) {
                            int[][] temp = new int[i + 1][2];
                            for (int j = 0; j < i + 1; j++) {
                                temp[j][0] = pathway.get(j)[0];
                                temp[j][1] = pathway.get(j)[1];
                            }

                            System.out.println(position);
                            for (int j = 0; j < temp.length; j++) {
                                System.out.println("temp = " + temp[j][0] + ": " + temp[j][1]);
                            }

                            int value = (pathway.peekFirst()[0] * width) + pathway.peekFirst()[1];
                            int no_value = reference.get(value);
                            reference.set(value, reference.get(position));
                            reference.set(position, no_value);
                            pathway.pollFirst();
                            i--;

                        }
                    }

                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    //delete when happy
                    for (int i = 0; i < reference.size(); i++) {
                        System.out.print(reference.get(i) + ", ");
                        if (i % width == 5) {
                            System.out.println("");
                        }
                    }
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                    for (int i = emptyTiles.size() - 1; i >= 0; i--) {
                        if (reference.get(emptyTiles.get(i)) != -3 && reference.get(emptyTiles.get(i)) != -5) {
                            emptyTiles.remove(i);
                        }
                    }

                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    //delete when happy
                    System.out.println("MissingObjects = " + emptyTiles);
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                    if(aboveEntryPoint.size() == 1){
                        moreObjectsToMove = false;
                    }

                } while (!emptyTiles.isEmpty() && moreObjectsToMove);
            }

            if(!simpleMatch.isEmpty()){
                for(int i = 0; i < simpleMatch.size(); i++){
                    int start = simpleMatch.get(i) % width;
                    LinkedList<int[]> pathway = getPathway(reference, map, start, simpleMatch.get(i), width);

                    if(pathway.isEmpty()){
                        System.out.println("Path is BLOCKED");
                        offScreen.add(simpleMatch.get(i));
                    }
                    else {
                        for (int k = 0; k < pathway.size(); k++) {
                            int[] element;
                            element = pathway.get(k);
                            int position = (element[0] * width) + element[1];

                            if (reference.get(position) != -3 && reference.get(position) != -5) {
                                int[][] temp = new int[k + 1][2];
                                for (int j = 0; j < k + 1; j++) {
                                    temp[j][0] = pathway.get(j)[0];
                                    temp[j][1] = pathway.get(j)[1];
                                }

                                System.out.println(position);
                                for (int j = 0; j < temp.length; j++) {
                                    System.out.println("temp = [" + temp[j][0] + "][" + temp[j][1]+ "]");
                                }

                                int value = (pathway.peekFirst()[0] * width) + pathway.peekFirst()[1];
                                int no_value = reference.get(value);
                                reference.set(value, reference.get(position));
                                reference.set(position, no_value);
                                pathway.pollFirst();
                                k--;
                            }
                        }
                    }
                }

                for (int i = 0; i < reference.size(); i++) {
                    System.out.print(reference.get(i) + ", ");
                    if (i % width == 5) {
                        System.out.println("");
                    }
                }
            }
        }


        assert true;
    }


    private int getBestEntryPoint(ArrayList<Integer>entrance, int width, int freeSpace){
        int entryPoint = -1;

        if (!entrance.isEmpty()) {
            if (entrance.size() == 1) {
                entryPoint = entrance.get(0);
            } else {
                for (int t = 0; t < entrance.size(); t++) {
                    if (entrance.get(t) % width == freeSpace % width) {
                        entryPoint = entrance.get(t);
                        break;
                    }
                }
            }
            if (entryPoint == -1 && entrance.size() > 1) {
                entryPoint = entrance.get(0);
            }
        }

        return entryPoint;
    }


    private ArrayList<Integer> findObjectsAboveEntryPoint(ArrayList<Integer>reference, int entryPoint, int width){
        ArrayList<Integer>additionals = new ArrayList<>();

        int num = entryPoint - width;

        while(num >= 0){
            if(reference.get(num) >= 0){
                additionals.add(reference.get(num));
            }
            num = num - width;
        }

        return additionals;
    }

    private ArrayList<Integer>orderByFurthestFromEntry(ArrayList<Integer>freeTiles, int entryPoint,
                                                       int width)
    {
        //make sure list is sorted descending order before change takes place
        Collections.sort(freeTiles, Collections.<Integer>reverseOrder());

        Integer[][] mArray = new Integer[freeTiles.size()][2];
        ArrayList temp = new ArrayList();
        ArrayList<Integer>sorted = new ArrayList<>();


        int entryColumn = entryPoint % width;
        int entryRow = entryPoint / width;

        for(int i = 0; i < freeTiles.size(); i++){
            mArray[i][0] = freeTiles.get(i);
            mArray[i][1] = (Math.abs(entryRow - (freeTiles.get(i) / width)) * 10) + Math.abs(entryColumn - (freeTiles.get(i) % width));
            temp.add(mArray[i][1]);
        }

        Collections.sort(temp, Collections.<Integer>reverseOrder());

        for(int a = 0; a < temp.size(); a++){
            for(int i = 0; i < mArray.length; i++) {
                if(mArray[i][1].equals(temp.get(a))){
                    sorted.add(mArray[i][0]);
                    mArray[i][1] = -10;
                    break;
                }
            }
        }

        System.out.println("sorted tiles = "+sorted);
        return sorted;


    }


    private LinkedList<int[]> getPathway(ArrayList<Integer>reference,
                                         ArrayList<Integer>map, int entryPoint,
                                         int destination, int width)
    {
        LinkedList<int[]> pathway;

        PathFinding path = new PathFinding();
        ArrayList<Integer> blockedPositions = irrelevantPositions(reference, entryPoint, width);
        int[][] arrayWasteLand = convertArrayListTo2DArray(blockedPositions);

        pathway = path.getPath(map.size() / width, width, (entryPoint / width),
                (entryPoint % width), destination / width, destination % width,
                arrayWasteLand);

        return pathway;
    }

    private int[][] convertArrayListTo2DArray(ArrayList<Integer> positions) {
        int[][] convertArray = new int[positions.size() / 2][2];

        int j = 0;
        for (int i = 0; i < positions.size() / 2; i++) {

            convertArray[i][0] = positions.get(j);
            convertArray[i][1] = positions.get(j + 1);
            j = j + 2;

        }

        return convertArray;
    }

    private ArrayList<Integer> irrelevantPositions(ArrayList<Integer>reference,int entrance,
                                                   int width) {

        ArrayList<Integer> values = new ArrayList<>();

        int x = 0;
        int y = 0;

        for(int i = 0; i < reference.size(); i++){
            if(i < entrance){
                values.add(y);
                values.add(x);
                x++;

                if (x == width) {
                    x = 0;
                    y++;
                }
            }
            else if(reference.get(i) == -2 || reference.get(i) == -4){
                y = i / width;
                x = i % width;
                values.add(y);
                values.add(x);
            }
        }

        System.out.println("not Relevant = " + values);
        return values;
    }

    private ArrayList<Integer> initialiseMap(){
        ArrayList<Integer> map = new ArrayList<>();

        Collections.addAll(map,
                1,1,1,1,1,1,
                1,1,1,1,1,1,
                1,1,1,1,1,1,
                1,1,1,1,1,1,
                1,1,1,1,1,1,
                1,1,1,1,1,1);
        return map;
    }

    private ArrayList<Integer> initialObstacles(ArrayList<Integer>entrance){
        ArrayList<Integer>obstacles = new ArrayList<>();

        Collections.addAll(obstacles,
                18,19,20,22,23);

        entrance.add(21);

        return obstacles;
    }

    private ArrayList<Integer> tileStatus(ArrayList<Integer> matches, ArrayList<Integer> map, ArrayList<Integer> obstacleLocations, ArrayList<Integer> glassLocations, ArrayList<Integer> emptyTiles) {
        ArrayList<Integer> reference = new ArrayList<>();

        for (int i = 0; i < map.size(); i++) {

            if (map.get(i) == 0) {
                reference.add(-1);
            } else if (map.get(i) == 1 && obstacleLocations.contains(i)) {
                reference.add(-2);
            } else if (map.get(i) == 1 && emptyTiles.contains(i)) {
                reference.add(-3);
            } else if (map.get(i) == 1 && glassLocations.contains(i)) {
                reference.add(-4);
            } else if (map.get(i) == 1 && matches.contains(i)) {
                reference.add(-5);
            } else reference.add(i);
        }

        return reference;
    }

    @Test
    public void testLine()throws Exception{
        ArrayList<int[]>result = findLine(100, 210, 105, 210);
        for(int i = 0; i < result.size(); i++){
            System.out.println(result.get(i)[0]);
            System.out.println(result.get(i)[1]);
        }
        assert(true);
    }

    private ArrayList<int[]> findLine(int x0, int y0, int x1, int y1){
        ArrayList<int[]>spritePath = new ArrayList<>();

        int sy = (y0 < y1) ? 1 : -1;
        int sx = (x0 < x1) ? 1 : -1;


        while(true) {
            int[] c = new int[2];
            c[0] = x0;
            c[1] = y0;
            spritePath.add(c);
            if (x0 == x1 && y0 == y1) {
                break;
            }

            if (x0 == x1) {
                y0 = y0 + sy;

            }
            if (y0 == y1) {
                x0 = x0 + sx;
            }

            if(y0 != y1 && x0 != x1){
                break;
            }
        }
        return spritePath;
    }
}
