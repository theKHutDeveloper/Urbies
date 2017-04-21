package com.development.knowledgehut.urbies;


import com.development.knowledgehut.urbies.Behaviours.PathFinding;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

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
