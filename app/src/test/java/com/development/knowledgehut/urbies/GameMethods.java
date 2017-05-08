package com.development.knowledgehut.urbies;

import com.development.knowledgehut.urbies.Behaviours.PathFinding;

import org.junit.Test;


import java.util.ArrayList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class GameMethods {

    private int width = 5;
    private ArrayList<Integer> map = new ArrayList<>();
    private ArrayList<Integer> matches = new ArrayList<>();
    private ArrayList<Integer> tilePos = new ArrayList<>();
    private ArrayList<Integer> obstacleLocations = new ArrayList<>();
    private ArrayList<Integer> glassLocations = new ArrayList<>();

    {//glassLocations.add(25);
        //Collections.addAll(obstacleLocations, 15, 17, 19);
        Collections.addAll(matches, 12, 13, 14);
    }

    @Test
    public void sortMultipleArray() throws Exception{
        int width = 5;
        int[][]mArray = new int[5][2];

        ArrayList<Integer>entryCol = new ArrayList<>();
        ArrayList<Integer>sorted = new ArrayList<>();
        ArrayList<Integer>expected = new ArrayList<>();
        ArrayList<Integer>addToEnd = new ArrayList<>();

        Collections.addAll(entryCol, 2, 3);

        mArray[0][0] = 24;
        mArray[0][1] = 4;
        mArray[1][0] = 23;
        mArray[1][1] = 3;
        mArray[2][0] = 22;
        mArray[2][1] = 2;
        mArray[3][0] = 21;
        mArray[3][1] = 1;
        mArray[4][0] = 18;
        mArray[4][1] = 3;


        for(int i = 0; i < width; i++) {
            for (int j = 0; j < mArray.length; j++) {
                if (mArray[j][1] == i) {
                    if(entryCol.contains(mArray[j][1])){
                        addToEnd.add(mArray[j][0]);
                    }
                    else sorted.add(mArray[j][0]);
                }
            }
        }

        Collections.addAll(expected, 21, 24, 22, 23, 18);
        sorted.addAll(addToEnd);

        System.out.println(sorted);

        assertEquals("result = ", expected, sorted);

    }



    @Test
    public void pathFindingTest() throws Exception{
        PathFinding pathFinding = new PathFinding();

        String expected = "[3 , 4] -> [3 , 3] -> [2 , 3]";
        String result = pathFinding.getPath("Example 1", 5, 5, 2, 3, 3, 4, new int [][]{{2,0}, {2,1}, {2,2}});
        System.out.println(result);
        assertEquals("result = ", expected, result);
    }


    @Test
    public void twoConditions() throws Exception{
        ArrayList<Integer>brokenObstacleLocations = new ArrayList<>();
        ArrayList<Integer>emptyTiles = new ArrayList<>();

        Collections.addAll(brokenObstacleLocations, 1, 2, 3);
        Collections.addAll(emptyTiles, 22,23,24);

        for(int i = 0; i < matches.size(); i++) {
            if (!brokenObstacleLocations.isEmpty() && !emptyTiles.isEmpty() && brokenObstacleLocations.contains(matches.get(i) % width)) {
                System.out.println("true");
            }
        }

        assert true;
    }

    @Test
    public void unknown() throws Exception {
        int[][]pathway = new int[7][2];
        LinkedList<int[][]> queue = new LinkedList<>();
        ArrayList<Integer>reference = new ArrayList<>();
        int width = 6;

        Collections.addAll(reference,
                0,  1,  2,  3,  4,  5,
                6,  7,  8,  9, 10, 11,
                12, 13, 14, -5, -5, -5,
                -2, -2, -2, 21, 22, 23,
                -3, -3, -3, -3, -3, -3, //27,28,29,
                30, 31, 32, 33, 34, 35
        );

        int t[][] = new int[1][2];
        t[0][0] = 4; t[0][1] = 0;
        queue.add(t);
        int t1[][] = new int[1][2];
        t1[0][0] = 4; t1[0][1] = 1;
        queue.add(t1);
        int t2[][] = new int[1][2];
        t2[0][0] = 4; t2[0][1] = 2;
        queue.add(t2);
        int t3[][] = new int[1][2];
        t3[0][0] = 4; t3[0][1] = 3;
        queue.add(t3);
        int t4[][] = new int[1][2];
        t4[0][0] = 4; t4[0][1] = 4;
        queue.add(t4);
        int t5[][] = new int[1][2];
        t5[0][0] = 4; t5[0][1] = 5;
        queue.add(t5);
        int t6[][] = new int[1][2];
        t6[0][0] = 3; t6[0][1] = 5;
        queue.add(t6);
        //add the rest of the objects in the same column as entrance (23)
        int t7[][] = new int[1][2];
        t7[0][0] = 2; t7[0][1] = 5;
        queue.add(t7);
        int t8[][] = new int[1][2];
        t8[0][0] = 1; t8[0][1] = 5;
        queue.add(t8);
        int t9[][] = new int[1][2];
        t9[0][0] = 0; t9[0][1] = 5;
        queue.add(t9);

        //access via new for-loop
        for(Object object : queue) {
            int[][] element = (int[][]) object;
            int position = (element[0][0] * width)+element[0][1];
            System.out.println("Position = "+position);
        }

        //this works so now i need to include the other urbs in the same column as 23
        for(int i = 0; i < queue.size(); i++){
            int[][] element;
            element = queue.get(i);
            int position = (element[0][0] * width)+element[0][1];

            if(reference.get(position) != -3 && reference.get(position) != -5){
                int[][]temp = new int[i + 1][2];
                for(int j = 0; j < i+1; j++){
                    temp[j][0] = queue.get(j)[0][0];
                    temp[j][1] = queue.get(j)[0][1];
                }
                System.out.println(position);
                for(int j = 0; j < temp.length; j++) {
                    System.out.println("temp = " + temp[j][0] + ": "+temp[j][1]);

                }
                int value = (queue.peekFirst()[0][0] * width) + queue.peekFirst()[0][1];
                int no_value = reference.get(value);
                reference.set(value, position);
                reference.set(position, no_value);
                queue.pollFirst();
                i--;
            }
        }
        System.out.println(reference);
    }


    @Test
    public void testing() throws Exception {
        ArrayList<Integer> expected = new ArrayList<>();
        ArrayList<Integer> expectedPositions = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            map.add(1);
        }

        int x = 0;
        for(int i = 0; i < map.size(); i++){
            tilePos.add(x);
            x = x + 1;
            if(i % width == 4){
                x = x + 100 - 5;
            }
        }

        ArrayList<Integer> list = listOfRemainingObjectsToBeMovedDown(matches, map, width);
        Collections.addAll(expected, 9, 8, 7, 4, 3, 2);//18, 16, 13, 11, 8, 6, 3, 1);
        assertEquals("Result =  ", expected, list);

        ArrayList<Integer>positions = positionsOfRemainingObjectsToBeMovedDown(matches, map,tilePos, width, list.size());
        Collections.addAll(expectedPositions, 404, 403, 402, 401, 400, 303, 301, 203);//, 201, 103, 101, 3, 1);
        assertEquals("Result = ", expectedPositions, positions);
    }

    @Test
    public void moveObjectsWhenMatchHasBrokenObstacle() throws Exception {
        ArrayList<Integer>reference = new ArrayList<>();
        ArrayList<Integer>tilePos = new ArrayList<>();
        ArrayList<Integer>snake = new ArrayList<>();

        int entryPoint = 17;
        int[][]pathway = new int[4][2];

        //copy of status of objects in tile map
        //at start
        Collections.addAll(reference,
                 0,   1,   2,   3,   4,
                 5,   6,   7,   8,   9,
                -5,  -5,  -5,  13,  14,
                -2,  16,  17,  -2,  -2,
                20,  21,  -3,  -3,  -3,
                25,  26,  22,  28,  24
        );

        //physical position of tiles
        Collections.addAll(tilePos,
                0,  1,  2,  3,  4,
                100,101,102,103,104,
                200,201,202,203,204,
                300,301,302,303,304,
                400,401,402,403,404,
                500,501,502,503,504
        );


        //the shortest path from entry point to destination
        pathway[0][0] = 4;
        pathway[0][1] = 4;
        pathway[1][0] = 4;
        pathway[1][1] = 3;
        pathway[2][0] = 4;
        pathway[2][1] = 2;
        pathway[3][0] = 3;
        pathway[3][1] = 2;

        //get all the valid objects in the same column above entryPoint
        int num = entryPoint - width;
        while(num >= 0){
            if(reference.get(num) == num){
                snake.add(num);
            }
            num = num - width;
        }

        //stores positions that are not currently used as value
        //in reference is empty or matched item
        ArrayList<Integer>storeUnusedPositions = new ArrayList<>();

        for(int i = 1; i < pathway.length; i++){

            int previousPosition = ((pathway[i-1][0] * width) + pathway[i-1][1]);
            int currentPosition = ((pathway[i][0] * width) + pathway[i][1]);
            System.out.println("current position = "+currentPosition);

            //path lists to store positions of elements
            PathList pathList = new PathList(currentPosition);
            PathList pathList7 = new PathList(7);
            PathList pathList2 = new PathList(2);

            //add to storeUnused if empty tile or matched tile
            if(reference.get(currentPosition) == -3 || reference.get(currentPosition) == -5){
                storeUnusedPositions.add(previousPosition);
                //pathList.setPosition(tilePos.get(previousPosition));
                //System.out.println("pathlist 17 for unused = "+pathList.getPosition());
            }
            //add to path list if swapping with an occupied tile
            else if(reference.get(currentPosition) >= 0){
                Collections.swap(reference, currentPosition, previousPosition);
                pathList.setPosition(tilePos.get(previousPosition));

                //add storeUnused to path list
                if(!storeUnusedPositions.isEmpty()){
                    for(int k = storeUnusedPositions.size()-1; k >=0; k--){
                        Collections.swap(reference, reference.indexOf(currentPosition), storeUnusedPositions.get(k));
                        pathList.setPosition(tilePos.get(storeUnusedPositions.get(k)));
                    }
                    storeUnusedPositions.clear();
                }

                //if entryPoint move the other elements in the same column (original column)
                //following the path of entryPoint
                if(currentPosition == entryPoint) {

                    //move down the others in the column
                    if (!snake.isEmpty()) {
                        int head = currentPosition;
                        for(int t = 0; t < snake.size(); t++) {
                            //is 17 on the same row or the same column
                            int distance = reference.indexOf(head) - reference.indexOf(snake.get(t));

                            while(distance > 1){
                                if(distance >= width){
                                    if(snake.get(t) == 7){
                                        if(!snake.contains(reference.get(reference.indexOf(snake.get(t))+ width))) {
                                            Collections.swap(reference, reference.indexOf(snake.get(t)), reference.indexOf(snake.get(t)) + width);
                                            pathList7.setPosition(tilePos.get(reference.indexOf(snake.get(t))));
                                        }
                                    }
                                    if(snake.get(t) == 2){
                                        if(!snake.contains(reference.get(reference.indexOf(snake.get(t))+ width))) {
                                            Collections.swap(reference, reference.indexOf(snake.get(t)), reference.indexOf(snake.get(t)) + width);
                                            pathList2.setPosition(tilePos.get(reference.indexOf(snake.get(t))));
                                        }
                                    }
                                    distance = distance - width;
                                }

                                if(distance < width  && distance > 1){
                                    if(snake.get(t) == 7){
                                        if(!snake.contains(reference.get(reference.indexOf(snake.get(t))+ 1))) {
                                            Collections.swap(reference, reference.indexOf(snake.get(t)), reference.indexOf(snake.get(t)) + 1);
                                            pathList7.setPosition(tilePos.get(reference.indexOf(snake.get(t))));
                                        }
                                    }
                                    if(snake.get(t) == 2) {
                                        if(!snake.contains(reference.get(reference.indexOf(snake.get(t))+ 1))) {
                                            Collections.swap(reference, reference.indexOf(snake.get(t)), reference.indexOf(snake.get(t)) + 1);
                                            pathList2.setPosition(tilePos.get(reference.indexOf(snake.get(t))));
                                        }
                                    }
                                    distance = distance - 1;
                                }
                            }
                            head = snake.get(t);
                        }
                    }
                }
            }

            System.out.println("reference = "+reference);
            System.out.println("Element 17 = "+pathList.getPosition());
            System.out.println("Element 7 = "+pathList7.getPosition());
            System.out.println("Element 2 = "+pathList2.getPosition());

        }

        if(reference.get(22) > 0 && reference.get(24) > 0){
            assert true;
        }
        else assert false;
    }



    private class PathList {
        private int location_id;
        private ArrayList<Integer> position;

        private PathList(int location_id) {
            this.location_id = location_id;
            position = new ArrayList<>();
        }

        private void setPosition(Integer p) {
            position.add(p);
        }

        private ArrayList<Integer> getPosition() {
            return position;
        }
    }



    /************************************************************************************************************
     * Return a list of remaining objects in tile map that needs to be moved down following
     * matches being found
     ************************************************************************************************************/
    private ArrayList<Integer> listOfRemainingObjectsToBeMovedDown(ArrayList<Integer> matches,
                                                                   ArrayList<Integer> map,
                                                                   int width) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> cleared = new ArrayList<>();

        for (int i = 0; i < matches.size(); i++) {
            int num = matches.get(i) - width;

            while (num >= 0) {
                if (map.get(num) == 1 && !obstacleLocations.contains(num) && !glassLocations.contains(num)
                        && !matches.contains(num) && !list.contains(num)) {
                    list.add(num);
                }
                else if(map.get(num) == 1 && obstacleLocations.contains(num)) {
                    break;
                }
                num = num - width;
            }
        }

        ArrayList<Integer>group = groupMatchesByRow(matches,width);
        ArrayList<Integer> additionals = getAdditionalObjectsToBeMoved(group,
                obstacleLocations, glassLocations, cleared, list);

        System.out.println("Additionals = "+additionals);

        if(!additionals.isEmpty()){
            list.addAll(additionals);
        }

        Collections.sort(list, Collections.<Integer>reverseOrder());
        return list;
    }

    /************************************************************************************************************
     * Return a list of positions that the remaining objects in tile map should be moved to following
     * matches being found
     ************************************************************************************************************/
    private ArrayList<Integer> positionsOfRemainingObjectsToBeMovedDown(ArrayList<Integer> matches,
                                                                        ArrayList<Integer> map,
                                                                        ArrayList<Integer>tilePos,
                                                                        int width, int listSize){
        ArrayList<Integer> positions = new ArrayList<>();
        ArrayList<Integer> cleared = new ArrayList<>();

        for(int i = 0; i < matches.size(); i++){
            ArrayList<Integer>temp = new ArrayList<>();

            int num = matches.get(i);

            while(num >= 0){//width){ //more than width? not more than = > ?
                if(map.get(num) == 1 && !obstacleLocations.contains(num) && !glassLocations.contains(num)) {
                    temp.add(tilePos.get(num));
                }
                else if(map.get(num) == 1 && obstacleLocations.contains(num)){
                    if(!isThisRowBlocked(map,width, obstacleLocations, num)) {
                        temp.add(tilePos.get(num));
                    }
                    temp.remove(temp.size()-1);
                    break;
                }
                num = num - width;
            }

            positions.addAll(temp);
        }

        ArrayList<Integer>group = groupMatchesByRow(matches,width);
        ArrayList<Integer> additionals = getAdditionalPointsToBeMoved(group,
                obstacleLocations, glassLocations, cleared, positions);

        System.out.println("Additionals = "+additionals);

        if(!additionals.isEmpty()){
            positions.addAll(additionals);
            Collections.sort(positions, Collections.<Integer>reverseOrder());
        }

        if(positions.size() > listSize){
            ArrayList<Integer>temp = new ArrayList<>();
            temp.addAll(positions.subList(0, listSize));

            ArrayList<Integer>futurePositions = new ArrayList<>();
            futurePositions.addAll(positions.subList(listSize, positions.size()));

            positions.clear();
            positions.addAll(temp);
            System.out.println("Future Positions = "+futurePositions);
        }
        return positions;
    }


    /*********************************************************************************
     * Return whether the selected row is blocked by invisible obstacles
     *********************************************************************************/
    private boolean isThisRowBlocked(ArrayList<Integer>map, int width, ArrayList<Integer>obstacleLocations, int element){
        boolean blocked = false;
        int start = element;

        if(start % width > 0){
            while(start % width > 0){
                start--;
            }
        }

        int count = 0;
        for(int i = start; i < start + width; i++){

            if(map.get(i) == 0 || (map.get(i) == 1) && obstacleLocations.contains(i)){
                count++;
            } else {
                count = 0;
            }
            if(count == width){
                blocked = true;
            }
        }
        return blocked;
    }

    /***********************************************************
     * Return a list of cleared locations that are not blocked
     ***********************************************************/
    private ArrayList<Integer> getClearedObstacles(ArrayList<Integer>map, int width,
                                                   ArrayList<Integer>obstacleLocations, int element){
        ArrayList<Integer>free = new ArrayList<>();
        int start = element;

        if(start % width > 0){
            while(start % width > 0){
                start--;
            }
        }

        for(int i = start; i < start + width; i++){
            if(map.get(i) == 1 && !obstacleLocations.contains(i)){
                free.add(i);
            }
        }
        return free;
    }


    /***********************************************************
     * Return a list of elements relative to the cleared column
     ***********************************************************/
    private ArrayList<Integer> getColumnElementsRelatingToClearedColumn(ArrayList<Integer>map,
                                                                        ArrayList<Integer>matches,
                                                                        int width,
                                                                        ArrayList<Integer>obstacleLocations,
                                                                        ArrayList<Integer>glassLocations,
                                                                        ArrayList<Integer>list,
                                                                        int element){

        ArrayList<Integer>columnValues = new ArrayList<>();

        int end = (element + width);
        while(end % width < width-1){
            if(map.get(end) == 1 && !matches.contains(end) && !obstacleLocations.contains(end)
                    && !glassLocations.contains(end) && !list.contains(end)){
                columnValues.add(end);
            }
            end = end + 1;
        }

        int num = element;
        while(num >= 0){
            if(map.get(num) == 1 && !matches.contains(num) && !obstacleLocations.contains(num)
                    && !glassLocations.contains(num) && !list.contains(num)){
                columnValues.add(num);
            }
            num = num - width;
        }
        return columnValues;
    }


    /*********************************************************************************
     * Return a list of elements positions relative to the cleared column
     *********************************************************************************/
    private ArrayList<Integer> getPositionOfElementsRelatingToClearedColumn(ArrayList<Integer>map,
                                                                            ArrayList<Integer>matches,
                                                                            int width,
                                                                            ArrayList<Integer>obstacleLocations,
                                                                            ArrayList<Integer>glassLocations,
                                                                            ArrayList<Integer>points,
                                                                            int element){
        ArrayList<Integer>elementPos = new ArrayList<>();

        int end = (element + width);
        while(end % width < (width - 1)){
            if(map.get(end) == 1 && !matches.contains(end) && !obstacleLocations.contains(end)
                    && !glassLocations.contains(end) && !points.contains(tilePos.get(end))){
                elementPos.add(tilePos.get(end));
            }
            end = end + 1;
        }

        int num = element;
        while(num >=0){
            if(map.get(num) == 1 && !matches.contains(num) && !obstacleLocations.contains(num)
                    && !glassLocations.contains(num) && !points.contains(tilePos.get(num))){
                elementPos.add(tilePos.get(num));
            }
            num = num - width;
        }

        return elementPos;
    }


    /*****************************
     * group matches by row
     *****************************/
    private ArrayList<Integer> groupMatchesByRow(ArrayList<Integer>matches, int width){
        ArrayList<Integer>group = new ArrayList<>();

        group.add(matches.get(0));
        int counter = 0;

        for(int i = 1; i < matches.size(); i++){
            if(matches.get(i) / width != group.get(counter) / width){
                group.add(matches.get(i));
                counter++;
            }
        }
        return group;
    }

    /******************************************************
     * get additional objects to be moved down also
     ******************************************************/
    //this only handles the situation where there is only one cleared obstacle
    private ArrayList<Integer> getAdditionalObjectsToBeMoved(ArrayList<Integer>group,
                                                             ArrayList<Integer>obstacleLocations,
                                                             ArrayList<Integer>glassLocations,
                                                             ArrayList<Integer>cleared,
                                                             ArrayList<Integer>list){
        ArrayList<Integer> additionalList = new ArrayList<>();

        for (int i = 0; i < group.size(); i++) {
            int num = group.get(i) - width;
            while (num >= 0) {
                if(map.get(num) == 1 && obstacleLocations.contains(num)) {
                    if(!isThisRowBlocked(map,width, obstacleLocations, num)) {
                        cleared = getClearedObstacles(map, width, obstacleLocations, num);
                        if(!cleared.isEmpty()){
                            for(int j = 0; j < cleared.size(); j++) {
                                additionalList.addAll(getColumnElementsRelatingToClearedColumn(map, matches, width, obstacleLocations, glassLocations,
                                        list, cleared.get(j)));
                            }
                        }
                    }
                }
                num = num - width;
            }
        }
        return additionalList;
    }


    /******************************************************
     * get positions that additional objects will be moved to
     ******************************************************/
    private ArrayList<Integer> getAdditionalPointsToBeMoved(ArrayList<Integer>group,
                                                            ArrayList<Integer>obstacleLocations,
                                                            ArrayList<Integer>glassLocations,
                                                            ArrayList<Integer>cleared,
                                                            ArrayList<Integer>positions){
        ArrayList<Integer>additionalPos = new ArrayList<>();

        for(int i = 0; i < group.size(); i++){
            int num = group.get(i) - width;
            while (num >= 0) {
                if(map.get(num) == 1 && obstacleLocations.contains(num)){
                    if(!isThisRowBlocked(map, width, obstacleLocations, num)){
                        cleared = getClearedObstacles(map, width, obstacleLocations, num);
                        if(!cleared.isEmpty()){
                            for(int j = 0; j < cleared.size(); j++) {
                                additionalPos.addAll(getPositionOfElementsRelatingToClearedColumn(map, matches, width,
                                        obstacleLocations, glassLocations, positions, cleared.get(j)));
                            }
                        }
                    }
                }
                num = num - width;
            }
        }
        return additionalPos;
    }
}
