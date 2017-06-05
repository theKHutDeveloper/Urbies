package com.development.knowledgehut.urbies;

import com.development.knowledgehut.urbies.Behaviours.PathFinding;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class PathwayTest {

    @Test
    public void moveRemainingObjects() throws Exception {
        ArrayList<Integer> emptyTiles = new ArrayList<>();
        ArrayList<Integer> reference;
        ArrayList<Integer> obstacleLocations;
        ArrayList<Integer> glassLocations = new ArrayList<>();
        ArrayList<Integer> entrance = new ArrayList<>();
        ArrayList<Integer> matches = new ArrayList<>();
        ArrayList<Integer> map;
        //ArrayList<Integer> offScreen = new ArrayList<>();
        int width = 6;

        map = initialiseMap();
        obstacleLocations = initialObstacles(entrance);
        Collections.addAll(matches, 35,32,27,21,14,12,11 );
        Collections.sort(matches, Collections.<Integer>reverseOrder());
        //Collections.addAll(emptyTiles, 26, 25, 24);
        reference = tileStatus(matches, map, obstacleLocations, glassLocations, emptyTiles);
        printReference(reference, width);

        if(entrance.isEmpty()){
            System.out.println("There are no entrances");
        }
        else if(!entrance.isEmpty()){
            System.out.println("There are entrances available");
        }

        reference = moveDownByColumn(reference, width); //need to put this in a pathway, then it should work for entrance.isEmpty()
        //as well as serving as a starter for !entrance.isEmpty (as seen below)
        printReference(reference, width);

        do {
            matches.clear();
            matches = getMatchedTiles(reference);
            System.out.println(matches);

            int entryPoint = getBestEntryPoint(entrance, width, matches.get(0));
            System.out.println(entryPoint);
            int start = getStartOfEntryPointColumn(reference, entryPoint, width, matches.get(0));
            System.out.println(start);

            LinkedList<int[]> pathway = getPathway(reference, map, start, matches.get(0), width);
            printPathway(pathway, reference, width);
            printReference(reference, width);

            matches.clear();
            matches = getMatchedTiles(reference);
            System.out.println(matches);

        } while (moreToMove(reference,matches,entrance,width));

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
                18,19,20, 23);

        entrance.add(21);
        entrance.add(22);

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

    private void printReference(ArrayList<Integer>reference, int width){
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
    }

    private ArrayList<Integer> moveDownByColumn(ArrayList<Integer>reference, int width){
        for(int a = width -1; a >=0; a--) {
            ArrayList<Integer> columnResults = columnEntriesEmpty(width, reference, a);

            if(!columnResults.isEmpty()){
                System.out.println(columnResults);

                if(!columnResults.isEmpty()) {
                    int location = columnResults.get(0) - width;

                    while (!columnResults.isEmpty()) {
                        if (location >= 0) {
                            if (reference.get(location) >= 0) {
                                Collections.swap(reference, columnResults.get(0), location);
                                if(!columnResults.contains(location)){
                                    columnResults.add(location);
                                    Collections.sort(columnResults, Collections.<Integer>reverseOrder());
                                }
                                if(columnResults.size() > 1){
                                    location = columnResults.get(1) - width;
                                }
                                columnResults.remove(0);
                            } else if(reference.get(location) == -5 || reference.get(location) == -3 || reference.get(location) == -4) {
                                location = location - width;
                            } else if (reference.get(location) == -2) {
                                if(columnResults.size() > 1){
                                    location = columnResults.get(1) - width;
                                }
                                columnResults.remove(0);
                            }
                        } else {

                            columnResults.remove(0);
                        }
                    }
                }
            }
        }

        return reference;
    }

    private ArrayList<Integer>columnEntriesEmpty(int width, ArrayList<Integer>reference, int column){
        ArrayList<Integer>result = new ArrayList<>();

        for(int i = reference.size()-1; i >= 0; i--){
            if(i % width == column){
                if(reference.get(i) == -3 || reference.get(i) == -5){
                    result.add(i);
                }
            }
        }

        return result;
    }

    private ArrayList<Integer>getMatchedTiles(ArrayList<Integer>reference){
        ArrayList<Integer>matchedTiles = new ArrayList<>();

        for(int i = 0; i < reference.size(); i++){
            if(reference.get(i) == -5){
                matchedTiles.add(i);
            }
        }
        Collections.sort(matchedTiles, Collections.<Integer>reverseOrder());
        return matchedTiles;
    }

    private int getBestEntryPoint(ArrayList<Integer>entrance, int width, int freeSpace){
        int entryPoint = -1;
        ArrayList<Integer>tempColumn = new ArrayList<>();
        ArrayList<Integer>value = new ArrayList<>();

        if (!entrance.isEmpty()) {
            if (entrance.size() == 1) {
                entryPoint = entrance.get(0);
            } else {
                for (int t = 0; t < entrance.size(); t++) {
                    tempColumn.add(entrance.get(t) % width);
                }
                for(int t = 0; t < entrance.size(); t++){
                    value.add(Math.abs(freeSpace % width - tempColumn.get(t)));
                }

                int smallest = Collections.min(value);
                int index = value.indexOf(smallest);
                entryPoint = entrance.get(index);
            }
            if (entryPoint == -1 && entrance.size() > 1) {
                entryPoint = entrance.get(0);
            }
        }

        return entryPoint;
    }

    private int getStartOfEntryPointColumn(ArrayList<Integer>reference, int entryPoint, int width, int emptyElement){
        int value = -1;
        int num = entryPoint;
        int emptyElementRow = emptyElement / width;
        int entryPointRow = entryPoint / width;

        if(emptyElementRow > entryPointRow){
            int val = emptyElementRow - entryPointRow;
            num = entryPoint + (width * val);
        }

        while(num >= 0){
            if(reference.get(num) >= 0){
                value = reference.get(num);
            }
            num = num - width;
        }

        return value;
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

    private void printPathway(LinkedList<int[]>pathway, ArrayList<Integer>reference, int width){
        for (int k = 0; k < pathway.size(); k++) {
            int[] element;
            element = pathway.get(k);
            int position = (element[0] * width) + element[1]; //(element[0] * width) + element[1];

            if (reference.get(position) != -3 && reference.get(position) != -5) {
                int[][] temp = new int[k + 1][2];
                for (int j = 0; j < k + 1; j++) {
                    temp[j][0] = pathway.get(j)[0];
                    temp[j][1] = pathway.get(j)[1];
                }

                System.out.println(position);
                for (int[] aTemp : temp) {
                    System.out.println("temp = [" + aTemp[0] + "][" + aTemp[1] + "]");
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

    private boolean moreToMove(ArrayList<Integer>reference, ArrayList<Integer>matches, ArrayList<Integer>entrance, int width){
        boolean moreToMove = true;

        for(int i = 0; i < matches.size(); i++){
            int entryPoint = getBestEntryPoint(entrance, width, matches.get(i));
            int start = getStartOfEntryPointColumn(reference, entryPoint, width, matches.get(i));

            if(matches.get(i) < width || !hasOccupiedTile(matches.get(i),reference, width) || start == -1){
                moreToMove = false;
            }
            else {
                moreToMove = true;
                break;
            }
        }
        return moreToMove;
    }

    private boolean hasOccupiedTile(int element, ArrayList<Integer>reference, int width){
        boolean result = false;
        int num = element;

        while(num >= 0){
            if(reference.get(num) >= 0){
                result = true;
                break;
            }
            num = num - width;
        }

        return result;
    }

}
