package com.development.knowledgehut.urbies.Behaviours;

import android.graphics.Point;

import com.development.knowledgehut.urbies.DrawableObjects.UrbieAnimation;
import com.development.knowledgehut.urbies.Objects.DataStore;
import com.development.knowledgehut.urbies.Objects.ObjectPathCreator;
import com.development.knowledgehut.urbies.Objects.Obstacles;
import com.development.knowledgehut.urbies.Screens.Urbies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.CEMENT;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.GLASS;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.NONE;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.WOODEN;


/**
 * Holds currently unused methods that might be needed in the future
 */

public class Unused {

    private GameMethods gameMethods;

    /***********************************************************************************************
     Given an array which holds the map element of the empty tiles, merge the elements and position
     into the arrays which will be used to move elements down
     ***********************************************************************************************/
    private void addEmptyTilesAfterBrokenObstacleRemoved(ArrayList<Integer> elementsToAdd, ArrayList<Integer>futurePositions, ArrayList<Point>futureCoordinates, ArrayList<Point>tileLocations) {

        if (elementsToAdd.size() > 1) {
            //make sure empty tile array starts at the highest element
            Collections.sort(elementsToAdd, Collections.<Integer>reverseOrder());
        }

        if (!futurePositions.isEmpty()) {
            futurePositions.addAll(elementsToAdd);

            //make sure array starts at the highest element
            Collections.sort(futurePositions, Collections.<Integer>reverseOrder());

            for (int i = 1; i < futurePositions.size(); i++) {
                int key = futurePositions.get(i);
                int j = i - 1;

                while (j >= 0 && (futurePositions.get(j).compareTo(key)) < 0) {
                    futurePositions.set(j + 1, futurePositions.get(j));
                    j--;
                }
                futurePositions.set(j + 1, key);
            }
        }

        if (!futureCoordinates.isEmpty()) {
            for (int i = 0; i < elementsToAdd.size(); i++) {
                int index = futurePositions.indexOf(elementsToAdd.get(i));
                futureCoordinates.add(index, tileLocations.get(elementsToAdd.get(i)));
            }
        }
    }

    /*if(availableTiles.size() == matches.size() + matchesOffScreen.size()){
                matches.addAll(matchesOffScreen);
            }
            else {
                int sum = availableTiles.size() - matches.size();
                for(int i = 0; i < sum; i++){
                    matches.add(matchesOffScreen.get(i));
                }
            }*/


     /*int count = 0;
            for(int i = 0; i < matches.size(); i++){
                if(count < availableTiles.size()) {
                    ObjectPathCreator o = new ObjectPathCreator();
                    o.setElement(matches.get(i));
                    Point pos = tilePos.get(availableTiles.get(count));
                    int loc = getReverseLocationOfY(tilePos, pos, width);
                    if (loc != -1) {
                        o.setPosition(new Point(pos.x, loc));
                    } else {
                        o.setPosition(new Point(pos.x, -200));
                    }
                    o.addToPath(findLine(o.getPosition().x, o.getPosition().y, pos.x, pos.y));
                    objectPathCreators.add(o);
                    count++;
                }
            }

            for(int i = 0; i < matchesOffScreen.size(); i++){
                if(count < availableTiles.size()) {
                    ObjectPathCreator o = new ObjectPathCreator();
                    o.setElement(matchesOffScreen.get(i));
                    Point pos = tilePos.get(availableTiles.get(count));

                    int loc = getReverseLocationOfY(tilePos, pos, width);
                    //need to amend here -- if cement broken need to follow path so urb does not pass thru other urbs or solid obstacles
                    if (loc != -1) {
                        o.setPosition(new Point(pos.x, loc));
                    } else {
                        o.setPosition(new Point(pos.x, -200));
                    }

                    o.addToPath(findLine(o.getPosition().x, o.getPosition().y, pos.x, pos.y));
                    objectPathCreators.add(o);

                    matches.add(matchesOffScreen.get(i));
                    count++;
                }
            }*/
/////////////////////////////////////////////////////////////////
    //If there are any empty tiles and an entrance point then
    //use A-star path finding to evaluate the shortest distance
    /////////////////////////////////////////////////////////////////
    //may need to do similar as below for matches under cement with an entry point!

        /*if(!entrance.isEmpty() && !emptyTiles.isEmpty()){
            PathFinding path = new PathFinding();

            //get a list of blocked cells that can not be used for path finding
            ArrayList<Integer>blockedPositions = irrelevantPositions(entrance.get(0), width, obstacleLocations, glassLocations);
            int[][] arrayWasteLand = convertArrayListTo2DArray(blockedPositions);
            ArrayList<ArrayList<int[]>> pathway = new ArrayList<>();

            //get the first element in the column of entrance.get(0)
            int num = entrance.get(0);
            int entranceEnd = num;
            while(num >= 0){
                if(map.get(num) == 1 && !obstacleLocations.contains(num) && !glassLocations.contains(num) && !emptyTiles.contains(num)){
                    entranceEnd = num;
                }
                if(obstacleLocations.contains(num)){
                    break;
                }
                num = num - width;
            }
            System.out.println("Entrance End = "+entranceEnd);


            for(int i = 0; i < emptyTiles.size(); i++) {
                //return shortest path in cell format
                pathway.add(path.getPath(emptyTiles.get(i), map.size() / width, width, (entrance.get(0) / width), (entrance.get(0) % width),
                        emptyTiles.get(i) / width, emptyTiles.get(i) % width, arrayWasteLand));
            }

            //print out pathway
            for (int j = 0; j < pathway.size(); j++) {
                System.out.println("Empty Tile: "+emptyTiles.get(j));
                for(int k = 0; k < pathway.get(j).size(); k++) {
                    System.out.println("pathway = [" + pathway.get(j).get(k)[0] + "][ " + pathway.get(j).get(k)[1] + "]");
                }
            }

            //experiment with moving the reference array list based on pathway
        }*/



    /*if (!brokenObstacleLocations.isEmpty() && !emptyTiles.isEmpty()) {
                //reset the startingPoint where the broken tile exists
                for (int j = 0; j < brokenObstacleLocations.size(); j++) {
                    if (matches.get(i) % width == obstacles.get(brokenObstacleLocations.get(j)).getLocation() % width && obstacles.get(brokenObstacleLocations.get(j)).getLocation() > matches.get(i)) {
                        startingPoint = obstacles.get(brokenObstacleLocations.get(j)).getLocation();

                        //if empty tiles exist and there is a broken obstacle then change the starting point location
                        if (!emptyTiles.isEmpty()) {
                            //get a list of the empty tiles locations, this should focus on the location of where the empty tiles are
                            for (int b = 0; b < emptyTiles.size(); b++) {
                                emptyTilePositions.add(tilePos.get(emptyTiles.get(b)));
                            }

                            //add the contents of emptyTilePositions to tempPosition
                            tempPosition.addAll(emptyTilePositions);
                            emptyTilesReplaced = true;
                            tilesComplete = true;

                            //is emptyTiles X position the same as one of the entrance points? if so then
                            //there is a clear pathway, otherwise some urbs will have to be moved
                            if (!entrance.isEmpty()) {
                                boolean clearPathway = false;
                                for (int loop = 0; loop < entrance.size(); loop++) {
                                    for (int index = 0; index < emptyTilePositions.size(); index++) {
                                        if (tilePos.get(entrance.get(loop)).x == emptyTilePositions.get(index).x) {
                                            System.out.println("there is a clear pathway " + emptyTilePositions.get(index).x + " = " + tilePos.get(entrance.get(loop)).x);
                                            clearPathway = true;
                                            break;

                                        }
                                    }
                                }
                                System.out.println("clearPathway = " + clearPathway);
                            }
                        }
                        break;
                    }
                }
            }*/

    /**************************************************************************************
     * Returns a list of object elements that need to be moved following
     * match extraction. Also evaluates the positions that the elements
     * will move to. In addition the elements that will replace the matched
     * items will have it's position evaluated from this method
     **************************************************************************************/
    public ArrayList<Integer> listOfObjectToMoveDown(
            List<UrbieAnimation> objects,
            ArrayList<Integer> matchedList,
            ArrayList<Integer> locations,
            int width,
            ArrayList<Point> tilePos,
            ArrayList<Point> newLocations,
            ArrayList<Point> newCoordinates,
            ArrayList<Obstacles> obstacles
    ) {

        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Point> position = new ArrayList<>();
        ArrayList<Point> total = new ArrayList<>();
        ArrayList<Point> temp2 = new ArrayList<>();
        ArrayList<Point> futurePos = new ArrayList<>();

        if (!matchedList.isEmpty()) {
            for (int i = 0; i < matchedList.size(); i++) {
                temp2.add(tilePos.get(matchedList.get(i)));
                int num = matchedList.get(i) - width;
                while (num >= 0) {
                    if (locations.get(num) == 1) {
                        //also need to get the urb value of num and check if it is not enclosed in an obstacle
                        int urb_num = gameMethods.findBitmapByMapLocation(objects, tilePos, num);

                        if (objects.get(urb_num).getStatus() == NONE) {
                            temp2.add(tilePos.get(num));
                        }
                        else if(objects.get(urb_num).getStatus() == GLASS || objects.get(urb_num).getStatus() == WOODEN || objects.get(urb_num).getStatus() == CEMENT) {
                            for(int o = 0; o < obstacles.size(); o++){
                                if(obstacles.get(o).getObstacle().getLocation() == num){
                                    if(obstacles.get(o).getDestroyCounter() == 0){
                                        temp2.add(tilePos.get(num));
                                        objects.get(urb_num).setStatus(NONE);
                                        break;
                                    }
                                }
                            }

                        }
                    }
                    num = num - width;
                }

                int counter = 0;
                num = matchedList.get(i) - width;
                while (num >= 0) {
                    //as long as tile is valid and matched list does not contain it and the new list doesn't already contain it and the tile has no restrictions - proceed
                    int urb_num = gameMethods.findBitmapByMapLocation(objects, tilePos, num);
                    if (locations.get(num) == 1 && !matchedList.contains(num) && !list.contains(num) && objects.get(urb_num).getStatus() == NONE) {
                        list.add(num);
                        position.add(temp2.get(counter));
                        counter++;
                    } else if (locations.get(num) == 1 && !matchedList.contains(num) && !list.contains(num) &&
                            (objects.get(urb_num).getStatus() == GLASS || objects.get(urb_num).getStatus() == WOODEN || objects.get(urb_num).getStatus() == CEMENT)){
                        for(int o = 0; o < obstacles.size(); o++){
                            if(obstacles.get(o).getObstacle().getLocation() == num) {
                                if(obstacles.get(o).getDestroyCounter() == 0){
                                    list.add(num);
                                    position.add(temp2.get(counter));
                                    objects.get(urb_num).setStatus(NONE);
                                    counter++;
                                    break;
                                }
                            }
                        }
                    }

                    num = num - width;
                }

                total.addAll(temp2);
                temp2.clear();
            }

            for (int j = 0; j < total.size(); j++) {
                if (!position.contains(total.get(j))) {
                    futurePos.add(total.get(j));
                }
            }
            //gameMethods.uniqueArrayPointList(futurePos);
            newCoordinates.addAll(futurePos);
        }

        //maybe i need to identify glass tiles here that are zero and not included in any of the lists
        //this might not work if the destroy counter has not been updated

        //TODO: May need to do the same here with WOODEN TILES && CEMENT TILES
        for(int o = 0; o < obstacles.size(); o++){
            if(obstacles.get(o).getStatus() == GLASS) {
                if(obstacles.get(o).getDestroyCounter() == 0) {
                    int m = gameMethods.findObjectByPosition(obstacles.get(o).getObstacle().getLocation(), objects);
                    if(objects.get(m).getStatus() == GLASS){
                        objects.get(m).setStatus(NONE);
                    }
                }
            }
        }

        newLocations.addAll(position);
        return list;
    }


    public ArrayList<Integer> moveDownTest(
            List<UrbieAnimation> objects,               //the urbs
            ArrayList<Integer> matchedList,             //the matched elements
            ArrayList<Integer> map,                     //valid map
            ArrayList<Obstacles>obstacles,              //obstacles
            ArrayList<Point>moveDownLocations,          //the move down locations
            ArrayList<Point> replacementPoints,         //the replacement locations
            ArrayList<Integer> replacementElements,     //the replacement elements
            ArrayList<Integer>offScreenElements,        //off-screen elements
            ArrayList<Point>tilePoints,                 //map tile locations
            int width)                                  //width of map
    {

        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> obstacleLocations = new ArrayList<>();
        ArrayList<Integer> nearMatch = new ArrayList<>();
        ArrayList<Integer> nearMatchObstacles = new ArrayList<>();
        ArrayList<Point> yPositions = new ArrayList<>();

        //////////////////////////////////////////////////////
        //Get a quick list of obstacles location values
        //////////////////////////////////////////////////////
        if(!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                obstacleLocations.add(obstacles.get(i).getLocation());
            }
        }

        //////////////////////////////////////////////////////
        //remove from matchedList any clear obstacles and
        //treat as a regular object, as they will be immediately
        //unblocked but will not pop out.
        //////////////////////////////////////////////////////
        for(int i = 0; i < matchedList.size(); i++){
            if(obstacleLocations.contains(matchedList.get(i)) && obstacles.get(obstacleLocations.indexOf(matchedList.get(i))).getStatus() == GLASS){
                matchedList.remove(i);
            }
        }

        //////////////////////////////////////////////////////
        //loop through each matchedList element
        //////////////////////////////////////////////////////
        for(int i = 0; i < matchedList.size(); i++) {

            //////////////////////////////////////////////////////
            //get a list of elements that are near the matchedList to
            //see if any of these elements are solid obstacles which
            //are just about to be broken
            //////////////////////////////////////////////////////
            if (!obstacles.isEmpty()) {
                if ((matchedList.get(i) > 0) && (map.get(matchedList.get(i) - 1) == 1) && ((matchedList.get(i) / width) == (matchedList.get(i) - 1) / width)) { //on the same row
                    nearMatch.add(matchedList.get(i) - 1);
                }
                if ((matchedList.get(i) + 1 < map.size()) && (map.get(matchedList.get(i) + 1) == 1) && ((matchedList.get(i) / width) == (matchedList.get(i) + 1) / width)) { //on same row
                    nearMatch.add(matchedList.get(i) + 1);
                }
                if ((matchedList.get(i) - width > 0) && (map.get(matchedList.get(i) - width) == 1) && ((matchedList.get(i) % width) == (matchedList.get(i) - width) % width)) { //on the same column
                    nearMatch.add(matchedList.get(i) - width);
                }
                if ((matchedList.get(i) + width < map.size()) && (map.get(matchedList.get(i) + width) == 1) && ((matchedList.get(i) % width) == (matchedList.get(i) + width) % width)) { //on the same column
                    nearMatch.add(matchedList.get(i) + width);
                }

                ////////////////////////////////////////////////////////////////
                //check if there are any near matches that are not in the same
                //column as any of the matches, but is an obstacle and is at
                //damage level 1
                ////////////////////////////////////////////////////////////////
                ArrayList<Integer> temp = new ArrayList<>();

                for (Integer match : matchedList) {
                    temp.add(match % width);
                }

                for (Integer aNearMatch : nearMatch) {
                    //int val = obstacleLocations.indexOf(aNearMatch); //do I need val?

                    if (obstacleLocations.contains(aNearMatch) && !obstacles.get(obstacleLocations.indexOf(aNearMatch)).isVisible()
                            && obstacles.get(obstacleLocations.indexOf(aNearMatch)).getDestroyCounter() == 1 && !temp.contains(aNearMatch % width)) {
                        if (!nearMatchObstacles.contains(aNearMatch)) {
                            nearMatchObstacles.add(aNearMatch);
                        }
                    }
                }
            }

            //////////////////////////////////////////////////////
            //does the matchedList column contain empty tiles
            //////////////////////////////////////////////////////
            ArrayList<Integer> dropDown = new ArrayList<>();
            ArrayList<Point> dropDownCol = new ArrayList<>();
            ArrayList<Integer> bringBack = new ArrayList<>();

            for (int j = 0; j < obstacles.size(); j++) {
                if (nearMatch.contains(obstacleLocations.get(j)) && !obstacles.get(j).isVisible() && nearMatch.get(nearMatch.indexOf(obstacleLocations.get(j))) % width == matchedList.get(i) % width) {
                    if (nearMatch.contains(obstacleLocations.get(j))) {
                        //deduct damage...change animation
                        obstacles.get(j).deductDestroyCounter();
                        if (obstacles.get(j).getDestroyCounter() == 0) {
                            //update score
                            int same_column = obstacleLocations.get(j);
                            dropDownCol.add(tilePoints.get(obstacleLocations.get(j)));
                            dropDown.add(obstacleLocations.get(j));

                            while (same_column <= map.size()) {
                                if (offScreenElements.contains(same_column)) {
                                    bringBack.add(same_column);
                                    dropDownCol.add(tilePoints.get(same_column));
                                }
                                same_column = same_column + width;
                            }
                        }
                    }
                }
            }

            Collections.sort(dropDownCol, Collections.<Point>reverseOrder());
            Collections.sort(dropDown, Collections.<Integer>reverseOrder());

            yPositions.add(tilePoints.get(matchedList.get(i)));

            //////////////////////////////////////////////////////
            //get a list of valid column positions relating to
            //matchedList.get(i)
            //////////////////////////////////////////////////////
            if (matchedList.get(i) - width >= 0 || !bringBack.isEmpty()) {
                int num = matchedList.get(i) - width;

                //change the value of num if there is an empty tile that is
                //now freed following obstacle broken
                if (!bringBack.isEmpty()) {
                    num = bringBack.get(0);
                    yPositions.clear();
                }

                while (num >= 0) {
                    /////////////////////////////////////////////////////////////////////////
                    //if tile is valid AND is NOT an obstacle OR is an obstacle but is GLASS
                    /////////////////////////////////////////////////////////////////////////
                    if (map.get(num) == 1 && (!obstacleLocations.contains(num) || (obstacleLocations.contains(num) && obstacles.get(obstacleLocations.indexOf(num)).getStatus() == GLASS))) {
                        yPositions.add(tilePoints.get(num));
                    }
                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //tile is valid AND is near the matched list(i) AND is an obstacle of type CEMENT/WOOD and counter is ZERO
                    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
                    else if (map.get(num) == 1 && nearMatch.contains(num) && obstacleLocations.contains(num) && !obstacles.get(obstacleLocations.indexOf(num)).isVisible()
                            && obstacles.get(obstacleLocations.indexOf(num)).getDestroyCounter() == 0) {
                        yPositions.add(tilePoints.get(num));
                    }
                    //////////////////////////////////////////////////////
                    //tile is valid AND is an obstacle of type CEMENT/WOOD
                    //////////////////////////////////////////////////////
                    else if (map.get(num) == 1 && (obstacleLocations.contains(num)) && !obstacles.get(obstacleLocations.indexOf(num)).isVisible()
                            && (!nearMatch.contains(num) || (nearMatch.contains(num)))) {
                        yPositions.clear();
                        break;
                    }
                    num = num - width;
                }
            }
            //System.out.println("valid yPositions for match " + matchedList.get(i) + " = " + yPositions);

            /////////////////////////
            //set the initial values
            /////////////////////////
            int key = matchedList.get(i) - width;
            int counter = 0;

            if (!bringBack.isEmpty()) {
                key = dropDown.get(0);
            }

            ////////////////////////////////////////////////////////////////////////
            //as long as yPosition is not empty, e.g. not blocked by solid obstacle
            ////////////////////////////////////////////////////////////////////////
            if (!yPositions.isEmpty()) {
                //loop up coumn while greater than zero
                while (key >= 0) {
                    //if key is valid AND matches AND moveDownList doesn't contain the key
                    if (map.get(key) == 1 && !matchedList.contains(key) && !list.contains(key)) {
                        list.add(key);
                        moveDownLocations.add(yPositions.get(counter));     //update position to this valid location
                        counter++;
                    }
                    key = key - width;
                }
            }

            ////////////////////////////////////////////////////////////////////////////////////////
            //get a list of future positions by collating the left over positions from each match
            ////////////////////////////////////////////////////////////////////////////////////////
            for(Point yPosition:yPositions){
                if(!moveDownLocations.contains(yPosition)){
                    if(!replacementPoints.contains(yPosition)){
                        replacementPoints.add(yPosition);
                    }
                }
            }

            ////////////////////////////////
            //get a list of future elements
            ////////////////////////////////
            if(!bringBack.isEmpty()){
                replacementElements.addAll(bringBack);
            }

            if(!replacementElements.contains(matchedList.get(i))){
                replacementElements.add(matchedList.get(i));
            }

            yPositions.clear();
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //if there are obstacles that are broken as a result of nearby matches, but not part of any
        //column that includes matches
        ////////////////////////////////////////////////////////////////////////////////////////////
        int startingPoint = -1;

        ArrayList<Point>pos = new ArrayList<>();
        ArrayList<Integer>loc = new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////////////////////////
        //find any elements in the same column as the nearby match that are offscreen, this gives
        //an accurate place to start the drop down
        ////////////////////////////////////////////////////////////////////////////////////////////
        if(!nearMatchObstacles.isEmpty()){
            for(int i=0; i < nearMatchObstacles.size(); i++){
                startingPoint = nearMatchObstacles.get(i);
                if(!offScreenElements.isEmpty()){
                    for(Integer offscreen: offScreenElements){
                        if(nearMatchObstacles.get(i) % width == offscreen % width){
                            if(offscreen > startingPoint){
                                startingPoint = offscreen;
                            }
                        }
                    }
                }
                //////////////////////////////////////////////////////////////////////////
                //from the starting point, if more than zero, work your way up the column
                //////////////////////////////////////////////////////////////////////////
                int num = startingPoint;
                while(num >= 0){
                    if(map.get(num) == 1){
                        if(obstacleLocations.contains(num) || nearMatchObstacles.contains(num)){
                            pos.add(tilePoints.get(num));
                        }
                        else if(obstacleLocations.contains(num) && obstacles.get(obstacleLocations.indexOf(num)).getDestroyCounter() > 1 && // should it be >=1?
                                !obstacles.get(obstacleLocations.indexOf(num)).isVisible()){
                            pos.clear();
                        }
                    }
                    num = num - width;
                }

                num = startingPoint;
                while(num >=0){
                    if(map.get(num) == 1){
                        if(!offScreenElements.contains(num) && (!obstacleLocations.contains(num) || nearMatchObstacles.contains(num))){
                            loc.add(num);
                        }
                        else if(obstacleLocations.contains(num) && !obstacles.get(obstacleLocations.indexOf(num)).isVisible() && obstacles.get(obstacleLocations.indexOf(num)).getDestroyCounter() > 1){
                            loc.clear();
                        }
                    }
                    num = num - width;
                }

                /////////////////////////////////////////////////
                //add pos and loc values to list
                /////////////////////////////////////////////////
                for(int z = 0; z < loc.size(); z++){
                    list.add(loc.get(z));
                    moveDownLocations.add(pos.get(z));
                }

                ///////////////////////////////////////////////
                //remove elements from pos if it's size is more than loc
                ///////////////////////////////////////////////
                if(pos.size() > loc.size()){
                    for(int z = loc.size() -1; z>=0; z--){
                        pos.remove(z);
                    }
                    loc.clear();
                }

                ///////////////////////////////////////////////
                //add off-screen elements to loc array list
                ///////////////////////////////////////////////
                if(startingPoint > nearMatchObstacles.get(i)){
                    while(startingPoint >= 0){
                        if(offScreenElements.contains(startingPoint)){
                            loc.add(startingPoint);
                        }
                        startingPoint = startingPoint - width;
                    }
                }

                ///////////////////////////////////////////////
                //add position and element to the replace list
                ///////////////////////////////////////////////
                if(!loc.isEmpty()){
                    for(int z = 0; z < loc.size(); z++){
                        replacementElements.add(loc.get(z));
                        replacementPoints.add(pos.get(z));
                    }
                }
            }
        }

        //System.out.println("near match obstacles = "+nearMatchObstacles);
        //System.out.println("starting point = "+startingPoint);
        //System.out.println("pos = "+pos);
        //System.out.println("loc = "+loc);

        return list;
    }


    /***********************************************************************************************
     *getEmptyTilesInRow :
     ***********************************************************************************************/
    private ArrayList<Integer>getEmptyTilesInRow(ArrayList<Integer>map, ArrayList<Integer>offscreen, int element, int width){
        ArrayList<Integer>emptyTiles = new ArrayList<>();
        int column = element % width;
        int place = element;

        if(column <= (width -1) && column > 0){
            for(int i = column; i >=0; i--){
                if(map.get(place) == 1 && offscreen.contains(place)){
                    emptyTiles.add(place);
                }
                else{
                    break;
                }
                place--;
            }
        }

        place = element;
        if(column >=0 && column < (width -1)){
            for(int i =column; i <= width -1; i++){
                if(map.get(place) == 1 && offscreen.contains(place)){
                    emptyTiles.add(place);
                }
                else {
                    break;
                }
                place++;
            }
        }

        return emptyTiles;
    }


    public ArrayList<Integer> listOfObjectToMoveDown(List<UrbieAnimation> objects,
                                                     ArrayList<Integer> matchedList, ArrayList<Integer> locations,
                                                     int width, ArrayList<Point> tilePos, ArrayList<Point> newLocations,
                                                     ArrayList<Point> newCoordinates, ArrayList<Obstacles> obstacles,
                                                     ArrayList<Integer> offScreenObjects, int height, ArrayList<Integer>notInPlay)
    {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Point> position = new ArrayList<>();
        ArrayList<Point> total = new ArrayList<>();
        ArrayList<Point> temp2 = new ArrayList<>();
        ArrayList<Point> futurePos = new ArrayList<>();
        ArrayList<Integer> blockedColumns = new ArrayList<>();
        ArrayList<Integer>emptyTiles = new ArrayList<>();
        ArrayList<Integer> obstacleLocations = new ArrayList<>();
        ArrayList<Integer>test = new ArrayList<>();
        ArrayList<Integer>addBackToList = new ArrayList<>();
        ArrayList<Integer>duplicateOfNotInPlay = new ArrayList<>();

        int testValue = -1;


        System.out.println("tile pos 21 - 23 = "+tilePos.get(21) + ", " + tilePos.get(22) + ", "+ tilePos.get(23));

        //identify the column where the blocked obstacle exists and get a list of obstacle locations
        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                blockedColumns.add(obstacles.get(i).getLocation() % width);
                obstacleLocations.add(obstacles.get(i).getLocation());
            }
        }


        if(!notInPlay.isEmpty()){
            //check there is no blocked row of cement or wood
            ArrayList<ArrayList<Integer>> blocked = gameMethods.getListOfBlockedRows(height, width, obstacles, locations);
            if(!blocked.isEmpty()){

                //is there a blocked tile that is zero, this means it is breaking
                for(int j = 0; j < obstacles.size(); j++) {
                    if(obstacles.get(j).getDestroyCounter() == 0) {
                        for (int i = 0; i < notInPlay.size(); i++) {
                            emptyTiles.add(objects.get(notInPlay.get(i)).getLocation());
                        }
                        Collections.sort(emptyTiles, Collections.<Integer>reverseOrder());

                        test.add(obstacles.get(j).getLocation());
                        //get difference between obs location and last empty tile
                        int diff = emptyTiles.get(0) - obstacles.get(j).getLocation();

                        //iterate
                        while(diff >= 0){
                            int sum = (obstacles.get(j).getLocation() + diff);
                            if(diff >= width){
                                diff = diff - width;
                            }
                            if(locations.get(sum) == 1 && (!obstacleLocations.contains(sum) || (obstacleLocations.contains(sum) && obstacles.get(obstacleLocations.indexOf(sum)).getDestroyCounter() == 0))) {
                                if(!test.contains(sum)) {
                                    test.add(sum);
                                }
                            }
                            diff = diff - 1;
                        }
                        for(int a = 0; a < emptyTiles.size(); a++){
                            if(!test.contains(emptyTiles.get(a))){
                                test.add(emptyTiles.get(a));
                            }

                            Collections.sort(test, Collections.<Integer>reverseOrder());
                        }
                        System.out.println("empty tiles = " + emptyTiles);
                        System.out.println("getPath = "+test);
                        System.out.println("==================================== ");
                        duplicateOfNotInPlay.addAll(notInPlay);
                        notInPlay.clear();
                        break;
                    }
                }
            }
        }

        //manage urbs that will be moved down underneath the blocked objects. This occurs when
        //objects under the blocked elements are a match
        for (int i = matchedList.size() - 1; i >= 0; i--) {
            for (int j = 0; j < obstacles.size(); j++) {
                //if obstacle is invisible and matchedList element is greater than the obstacles location and obstacle is on the same column as matchedList element
                if (!obstacles.get(j).isVisible() && matchedList.get(i) > obstacles.get(j).getObstacle().getLocation() && matchedList.get(i) % width == blockedColumns.get(j)) {
                    //if the sum of matchedList element and the obstacle location is more than width of tile map
                    //loop to see if there are any elements to be dropped down, if so add to list
                    if (matchedList.get(i) - obstacles.get(j).getObstacle().getLocation() > width) {
                        int numStart = matchedList.get(i);
                        int value = matchedList.get(i) - obstacles.get(j).getObstacle().getLocation();
                        int num = matchedList.get(i) - width;
                        int loop = (value / width) - 1;

                        for (int z = 0; z < loop; z++) {
                            if (locations.get(num) == 1) {
                                int urb_num = gameMethods.findBitmapByMapLocation(objects, tilePos, num);
                                if (urb_num > -1) {
                                    if (objects.get(urb_num).getStatus() == NONE) {
                                        position.add(tilePos.get(numStart));
                                        numStart = num;
                                        list.add(num);
                                        int urbToSetOffScreen = gameMethods.findBitmapByMapLocation(objects, tilePos, matchedList.get(i));
                                        objects.get(urbToSetOffScreen).setActive(false);
                                        objects.get(urbToSetOffScreen).setY(-200);
                                    }
                                }
                            }
                            num = num - width;
                        }
                    }
                    System.out.println("debug = " + gameMethods.findBitmapByMapLocation(objects, tilePos, matchedList.get(i)));
                    //add matched list element to offScreen if not already stored
                    //remove matchedList element from matchedList arraylist and store in addBackToList
                    if(!offScreenObjects.contains(matchedList.get(i))) {
                        offScreenObjects.add(matchedList.get(i));
                    }
                    addBackToList.add(matchedList.get(i));
                    matchedList.remove(i);
                    break;
                }
            }
        }

        if (!matchedList.isEmpty()) {
            //add available locations to the array list that will store the location of move down objects
            for (int i = 0; i < matchedList.size(); i++) {
                if(!test.isEmpty()){
                    if(test.contains(matchedList.get(i) + width)){
                        System.out.println("This is where I will add the code to change add off-screen objects");
                        for(int j = 0; j < test.size(); j++){
                            temp2.add(tilePos.get(test.get(j)));
                        }
                    }
                    System.out.println("temp2 after getPath = "+temp2);
                }

                temp2.add(tilePos.get(matchedList.get(i)));
                int num = matchedList.get(i) - width;
                while (num >= 0) {
                    if (locations.get(num) == 1) {
                        //also need to get the urb value of num and check if it is not enclosed in an obstacle
                        int urb_num = gameMethods.findBitmapByMapLocation(objects, tilePos, num);
                        System.out.println("matchedList "+i + " = "+matchedList.get(i) + " num = "+num + " urb_num = "+urb_num);
                        if(urb_num > -1) {
                            if (objects.get(urb_num).getStatus() == NONE) {
                                temp2.add(tilePos.get(num));
                            } else if (objects.get(urb_num).getStatus() == GLASS || objects.get(urb_num).getStatus() == WOODEN || objects.get(urb_num).getStatus() == CEMENT) {
                                //this ensures that any broken obstacle is added to the urbs to move down list, this only appears to work on GLASS
                                for (int o = 0; o < obstacles.size(); o++) {
                                    if (obstacles.get(o).getObstacle().getLocation() == num) {
                                        if (obstacles.get(o).getDestroyCounter() == 0) {

                                            System.out.println("getPath 1");
                                            temp2.add(tilePos.get(num));
                                            objects.get(urb_num).setStatus(NONE); //not sure if this works or if it does it will not work on the next section as I have set the broken obstacle to NONE
                                            testValue = urb_num;
                                            break;
                                        }
                                    }
                                }

                            }
                        }
                    }
                    num = num - width;
                }


                int counter = 0;
                num = matchedList.get(i) - width;

                //add objects to be moved down
                while (num >= 0) {
                    //using the values of off-screen element, incorporate them back into tile map
                    //now that an obstacle has been broken
                    if(!test.isEmpty()){
                        if(test.contains(matchedList.get(i) + width)) {
                            for (int j = 0; j < test.size(); j++) {
                                if (!duplicateOfNotInPlay.contains(test.get(j))) { //is this the matched list element that was removed earlier?
                                    list.add(test.get(j));
                                    position.add(temp2.get(counter));
                                    counter++;
                                }

                            }
                        }
                        test.clear();
                    }
                    //as long as tile is valid and matched list does not contain it and the new list doesn't already contain it and the tile has no restrictions - proceed
                    int urb_num = gameMethods.findBitmapByMapLocation(objects, tilePos, num);
                    //if location is valid and is not a matchedList element and is not already stored and object has a status of NONE
                    if (locations.get(num) == 1 && !matchedList.contains(num) && !list.contains(num) && objects.get(urb_num).getStatus() == NONE) {
                        list.add(num);
                        position.add(temp2.get(counter));
                        if(testValue > -1){
                            System.out.println("testValue = "+testValue);
                            testValue = -1;
                        }
                        counter++;
                    }
                    //if location is valid is not in matchedList and not in list and is an obstacle
                    else if (locations.get(num) == 1 && !matchedList.contains(num) && !list.contains(num) &&
                            (objects.get(urb_num).getStatus() == GLASS || objects.get(urb_num).getStatus() == WOODEN || objects.get(urb_num).getStatus() == CEMENT)) {
                        for (int o = 0; o < obstacles.size(); o++) {
                            if (obstacles.get(o).getObstacle().getLocation() == num) {
                                //if the obstacle is cleared then add position and item
                                if (obstacles.get(o).getDestroyCounter() == 0) {
                                    System.out.println("getPath 2");
                                    list.add(num);
                                    position.add(temp2.get(counter));
                                    objects.get(urb_num).setStatus(NONE);
                                    counter++;
                                    break;
                                }
                            }
                        }
                    }

                    num = num - width;
                }

                total.addAll(temp2);
                temp2.clear();
            }

            if(!duplicateOfNotInPlay.isEmpty()) {
                System.out.println("total = "+total);
                System.out.println("positions = "+position);
            }

            for (int j = 0; j < total.size(); j++) {
                if (!position.contains(total.get(j))) {
                    futurePos.add(total.get(j));
                }
            }
            //gameMethods.uniqueArrayPointList(futurePos);
            newCoordinates.addAll(futurePos);
        }

        //maybe i need to identify glass tiles here that are zero and not included in any of the lists
        //this might not work if the destroy counter has not been updated

        //TODO: May need to do the same here with WOODEN TILES && CEMENT TILES
        for (int o = 0; o < obstacles.size(); o++) {
            if (obstacles.get(o).getStatus() == GLASS) {
                if (obstacles.get(o).getDestroyCounter() == 0) {
                    int m = gameMethods.findObjectByPosition(obstacles.get(o).getObstacle().getLocation(), objects);
                    if (objects.get(m).getStatus() == GLASS) {
                        objects.get(m).setStatus(NONE);
                    }
                }
            }
        }

        newLocations.addAll(position);

        System.out.println("coordinatesToMoveTo = "+newLocations);

        //add the matched list elements that were removed earlier
        if(!addBackToList.isEmpty()) {
            matchedList.addAll(addBackToList);
        }

        if(!duplicateOfNotInPlay.isEmpty()) {
            for (int i = 0; i < duplicateOfNotInPlay.size(); i++) {
                if(!matchedList.contains(duplicateOfNotInPlay.get(i))){
                    matchedList.add(duplicateOfNotInPlay.get(i));
                }
            }
        }

        System.out.println("matchedList = "+matchedList);

        gameMethods.uniqueArrayIntegerList(list);
        System.out.println("objectsToMoveDown list = "+list);
        return list;
    }

    /********************************************************************************
     * Returns a list of empty tiles underneath the selected element on the same
     * column
     ********************************************************************************/
    public ArrayList<Integer> getEmptyTilesBelowSelectedTileIfExists(int element, ArrayList<Point> tiles, int width, List<UrbieAnimation>objects){
        ArrayList<Integer>emptyTiles = new ArrayList<>();

        int mapSize = tiles.size();
        int urb_num = gameMethods.findBitmapByMapLocation(objects, tiles, element);

        for(int i = 1; i <= (mapSize - element)/width; i++){
            if(urb_num + (width * i) < objects.size()) {
                if (!objects.get(urb_num + (width * i)).getActive() && objects.get(urb_num + (width * i)).getY() < 0) {
                    emptyTiles.add(element + (width * i));
                }
            }
            /*else {
                break;
            }*/
        }
        return emptyTiles;
    }

    /**********************************************************************
     Returns a list of all blocked columns
     **********************************************************************/
    public ArrayList<ArrayList<Integer>> getListOfBlockedColumns(int verticalSize, int horizontalSize, ArrayList<Obstacles> obstacles, ArrayList<Integer> tilemap) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList temp;
        int start = 0;

        for (int i = 0; i < horizontalSize; i++) {
            temp =isColumnBlocked(start, horizontalSize, obstacles, tilemap);
            if (!temp.isEmpty()) {
                result.add(temp);
            }
            start = start + verticalSize;
        }
        return result;
    }


    /**********************************************************************
     Returns a list of tilemap locations if the column is filled with solid obstacles e.g. Visible = INVISIBLE
     columnStartLocation is the location in the tilemap to start checking
     horizontalSize is the width of the tilemap
     obstacles is the array list of the obstacles in the level
     tilemap is the array list of 1, 0 which determines the valid tiles
     **********************************************************************/
    public ArrayList isColumnBlocked(int columnStartLocation, int horizontalSize, ArrayList<Obstacles> obstacles, ArrayList<Integer> tilemap) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> locations = new ArrayList<>();
        int counter = 0;

        for (int i = 0; i < obstacles.size(); i++) {
            if (!obstacles.get(i).isVisible()) {
                locations.add(obstacles.get(i).getObstacle().getLocation());
            } else locations.add(-1);
        }

        while (columnStartLocation < tilemap.size()) {
            if ((tilemap.get(columnStartLocation) == 1 && locations.contains(columnStartLocation))) {
                counter++;
                result.add(columnStartLocation);
            } else if (tilemap.get(columnStartLocation) == 0) {
                counter++;
                result.add(-1);
            }
            columnStartLocation = columnStartLocation + horizontalSize;
        }

        if (counter != (tilemap.size() / horizontalSize)) {
            result.clear();
        }

        return result;
    }

/*
    public ArrayList<DataStore> getOnScreenObjectsToMoveDown(
            List<UrbieAnimation>objects, ArrayList<Point>tilePos,
            ArrayList<Integer>matches, ArrayList<Integer>map, ArrayList<Obstacles>obstacles, int width)
    {
        ArrayList<DataStore>remainingObjects = new ArrayList<>();
        ArrayList<Integer> nearMatchObstacles;
        ArrayList<Integer> entrance = new ArrayList<>();
        ArrayList<Integer> obstacleIndexWhereZero = new ArrayList<>();


        ArrayList<Integer>obstacleLocations = getInvisibleObjectPositionsInTileMap(obstacles);
        ArrayList<Integer>glassObstacles = getGlassPositionsInTileMap(obstacles);

        if(!obstacleLocations.isEmpty()){
            nearMatchObstacles = getActualNearMatchesThatAreObstacles(matches, obstacleLocations, width, map);

            if(!nearMatchObstacles.isEmpty()) {
                for(int i = 0; i < obstacles.size(); i++){
                    if(nearMatchObstacles.contains(obstacles.get(i).getLocation())) {
                        obstacles.get(i).deductDestroyCounter();
                        updateObstacle(obstacles, i, entrance, obstacleIndexWhereZero, objects, obstacleLocations, tilePos);
                    }
                }
            }
        }
        return remainingObjects;
    }






    *//*******************************************************************
     * get a list of invisible obstacle locations e.g. WOOD and CEMENT
     *******************************************************************//*
    private ArrayList<Integer> getInvisibleObjectPositionsInTileMap(ArrayList<Obstacles>obstacles){
        ArrayList<Integer>obstacleLocations = new ArrayList<>();

        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if(!obstacles.get(i).isVisible() && obstacles.get(i).getDestroyCounter() > 0) {
                    obstacleLocations.add(obstacles.get(i).getLocation());
                }
            }
        }
        return obstacleLocations;
    }

    *//**************************************************
     * get a list of GLASS obstacles
     **************************************************//*
    private ArrayList<Integer> getGlassPositionsInTileMap(ArrayList<Obstacles>obstacles){
        ArrayList<Integer>glassLocations = new ArrayList<>();

        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if(obstacles.get(i).getStatus() == GLASS){
                    glassLocations.add(obstacles.get(i).getLocation());
                }
            }
        }
        return glassLocations;
    }


    *//**************************************************
     * update Obstacles
     **************************************************//*
    private void updateObstacle(ArrayList<Obstacles>obstacles, int index, ArrayList<Integer>entrance,
                                ArrayList<Integer>obstacleIndexWhereZero, List<UrbieAnimation>objects,
                                ArrayList<Integer>obstacleLocations, ArrayList<Point>tilePos){
        obstacles.get(index).deductDestroyCounter();
        if(obstacles.get(index).getDestroyCounter() == 0){
            entrance.add(obstacles.get(index).getLocation());
            obstacleIndexWhereZero.add(index); //doesn't this get affected when you remove an obstacle?
            int urb_num = findBitmapByMapLocation(objects, tilePos, obstacles.get(index).getLocation());
            if(urb_num > -1){
                objects.get(urb_num).setStatus(NONE);
                objects.get(urb_num).setVisible(Urbies.VisibilityStatus.VISIBLE);
                int i = obstacleLocations.indexOf(obstacles.get(index).getLocation());
                obstacles.get(index).clearStatus();
                obstacleLocations.remove(i);
            }
        }
    }*/

    //Identify move down objects that will need to have the path defined as they can not move over obstacles
  /*  int pos1 = -1, pos2 = -1;
    ArrayList<ArrayList<Point>> p = new ArrayList<>();

        if(!obstacleLocations.isEmpty()){
        for(int i = 0; i < moveDownList.size(); i++){
            for(int j = 0; j < obstacleLocations.size(); j++){
                if(moveDownList.get(i) % width == obstacleLocations.get(j)% width && moveDownList.get(i) > obstacleLocations.get(j)){
                    System.out.println("Urb with location of "+moveDownList.get(i) + " should not move over an obstacle!!");
                    int num = obstacleLocations.get(j);
                    while(num % width <= (width -1)){
                        if(!obstacleLocations.contains(num)){
                            pos1 = num;
                            System.out.println("pos1 = "+pos1);
                            break;
                        }
                        num = num + 1;
                    }
                    num = obstacleLocations.get(j);
                    while(num % width >= 0){
                        if(!obstacleLocations.contains(num)){
                            pos2 = num;
                            System.out.println("pos2 = "+pos2);
                            break;
                        }
                        num = num - 1;
                    }

                    if(pos1 > -1 && pos2 > -1){
                        if(Math.abs(moveDownList.get(i) - pos1) >= Math.abs(moveDownList.get(i) - pos2)){
                            int urb = findBitmapByMapLocation(objects, tilePos, moveDownList.get(i));
                            int block = findBitmapByMapLocation(objects, tilePos, pos1);
                            ArrayList<Point>tempPoint = new ArrayList<>();
                            p.add(findPath(objects.get(urb).getX(), objects.get(urb).getY(), objects.get(block).getX(), objects.get(block).getY()));

                        }
                        else if(Math.abs(moveDownList.get(i) - pos1) < Math.abs(moveDownList.get(i) - pos2)){
                            int urb = findBitmapByMapLocation(objects, tilePos, moveDownList.get(i));
                            int block = findBitmapByMapLocation(objects, tilePos, pos2);
                            p.add(findPath(objects.get(urb).getX(), objects.get(urb).getY(), objects.get(block).getX(), objects.get(block).getY()));
                        }
                    }
                    else if(pos1 > -1 && pos2 == -1){
                        int urb = findBitmapByMapLocation(objects, tilePos, moveDownList.get(i));
                        int block = findBitmapByMapLocation(objects, tilePos, pos1);
                        p.add(findPath(objects.get(urb).getX(), objects.get(urb).getY(), objects.get(block).getX(), objects.get(block).getY()));
                    }
                    else if(pos1 == -1 && pos2 > -1){
                        int urb = findBitmapByMapLocation(objects, tilePos, moveDownList.get(i));
                        int block = findBitmapByMapLocation(objects, tilePos, pos2);
                        p.add(findPath(objects.get(urb).getX(), objects.get(urb).getY(), objects.get(block).getX(), objects.get(block).getY()));
                    }

                    for(int l = 0; l <p.size(); l++){
                        for(int k = 0; k < p.get(l).size(); k++) {
                            System.out.print("Path = " + p.get(l).get(k));
                        }
                    }

                }
            }
        }
    }

    */



    ////////////////////////////////////////////////////////////////////////////////////////////
    //3b. Identify objects that needs to be adjusted due to broken obstacles and empty tiles
    ////////////////////////////////////////////////////////////////////////////////////////////
        /*if(!entrance.isEmpty() && !emptyTiles.isEmpty()){
           //get btw empty tile to broken obstacle 0
            int i = 0;
            ArrayList<Integer>journey = new ArrayList<>();

            int begin = emptyTiles.get(i);
            int broken = entrance.get(0);
            journey.add(begin);

            if(emptyTiles.contains(entrance.get(0) + width)){
                broken = entrance.get(0) + width;
                journey.add(entrance.get(0));
            }

            while(begin > broken) {
                if((begin / width) > (broken / width)){
                    if (!obstacleLocations.contains(begin) && !glassLocations.contains(begin)) {
                        begin = begin - width;
                        journey.add(begin);
                    }
                    else if(begin / width > broken / width){
                        begin = begin - 1;
                        journey.add(begin);
                    }
                    else if(begin / width < broken / width){
                        begin = begin + 1;
                        journey.add(begin);
                    }

                }
                else if((begin / width) == (broken / width)){
                    if(begin > broken){
                        begin = begin - 1;
                        journey.add(begin);
                    }
                    else if(begin < broken){
                        begin  = begin + 1;
                        journey.add(begin);
                    }
                }

            }

            Collections.sort(journey, Collections.<Integer>reverseOrder());
            System.out.println("Journey of "+emptyTiles.get(0) + " = "+journey);
        }*/

        /*calcDistance = (begin / width) - (brokenObstacleLocations.get(0) / width);

                if(calcDistance >= width){
                    journey.add(begin - width);
                    begin = begin - width;
                }
                else {
                    if(calcDistance > 0){
                        journey.add(begin - 1);
                        begin = begin - 1;
                    } else {
                        journey.add(begin + 1);
                        begin = begin + 1;
                    }
                }*/


    /*********************************************************************************
     * Return a list of elements positions relative to the cleared column
     *********************************************************************************/
    /*private ArrayList<Point> getPositionOfElementsRelatingToClearedColumn(List<UrbieAnimation>objects,
                                                                            ArrayList<Integer>map,
                                                                            ArrayList<Integer>matches,
                                                                            ArrayList<Point>tilePos,
                                                                            int width,
                                                                            ArrayList<Integer>obstacleLocations,
                                                                            ArrayList<Integer>glassLocations,
                                                                            ArrayList<Point>points,
                                                                            int element){
        ArrayList<Point>elementPos = new ArrayList<>();

        int end = (element + width);
        while(end % width < (width - 1)){
            int urb_num = findBitmapByMapLocation(objects, tilePos, end);
            if(map.get(end) == 1 && !matches.contains(end) && !obstacleLocations.contains(end)
                    && !glassLocations.contains(end) && !points.contains(tilePos.get(end))
                    && objects.get(urb_num).getStatus() == NONE){
                elementPos.add(tilePos.get(end));
            }
            end = end + 1;
        }

        int num = element;
        while(num >=0){
            int urb_num = findBitmapByMapLocation(objects, tilePos, num);
            if(map.get(num) == 1 && !matches.contains(num) && !obstacleLocations.contains(num)
                    && !glassLocations.contains(num) && !points.contains(tilePos.get(num))
                    && objects.get(urb_num).getStatus() == NONE){
                elementPos.add(tilePos.get(num));
            }
            num = num - width;
        }

        return elementPos;
    }
*/

    /******************************************************
     * get positions that additional objects will be moved to
     ******************************************************/
    /*private ArrayList<Point> getAdditionalPointsToBeMoved(List<UrbieAnimation>objects,
                                                          ArrayList<Integer>group,
                                                          ArrayList<Integer>matches,
                                                           ArrayList<Integer>map,
                                                          ArrayList<Point>tilePos,
                                                            ArrayList<Integer>obstacleLocations,
                                                            ArrayList<Integer>glassLocations,
                                                            ArrayList<Integer>cleared,
                                                            ArrayList<Point>positions, int width){
        ArrayList<Point>additionalPos = new ArrayList<>();

        for(int i = 0; i < group.size(); i++){
            int num = group.get(i) - width;
            while (num >= 0) {
                if(map.get(num) == 1 && obstacleLocations.contains(num)){
                    if(!isThisRowBlocked(map, width, obstacleLocations, num)){
                        cleared = getClearedObstacles(map, width, obstacleLocations, num);
                        if(!cleared.isEmpty()){
                            for(int j = 0; j < cleared.size(); j++) {
                                additionalPos.addAll(getPositionOfElementsRelatingToClearedColumn(objects,map, matches, tilePos,width,
                                        obstacleLocations, glassLocations, positions, cleared.get(j)));
                            }
                        }
                    }
                }
                num = num - width;
            }
        }
        return additionalPos;
    }*/


    //NEW STUFF HERE
    //Handle the next to cement etc outside of method? or just group together in method e.g.
    //public void handle(...){
    //  isMatchNextToObstacle();
    //  listOfRemainingObjectsToMoveDown(...);
    //  ...
    // }

    //THINGS WERE GOING FINE...BUT I NEED TO AMEND THE LIST AND POSITIONS TO BE DONE WITHIN THE MATCHES E.G.
    //for(int i = 0; i < matches.size(); i++){
    //  listOfRemainingObjects.addAll(listOfRemainingObjectsToBeMovedDown(objects, matches.get(i), map, tilePos, obstacles, width));
    //  positionsOfRemainingObjects = positionsThatRemainingObjectsNeedToMoveTo(objects, matches.get(i), map, obstacles, tilePos, width, listOfRemainingObjects.size())
    // }
    /*public ArrayList<ObjectPathCreator> handleTileMovements(ArrayList<Obstacles> obstacles, List<UrbieAnimation>objects,
                                                            ArrayList<Integer> matches,
                                                            ArrayList<Integer> map, ArrayList<Point>tilePos,
                                                            ArrayList<Integer> offScreenMatches, int width){

        ArrayList<Integer>nearMatchObstacles;
        ArrayList<Integer>listOfRemainingObjects = new ArrayList<>();
        ArrayList<Point>positionsOfRemainingObjects = new ArrayList<>();
        ArrayList<Point>futurePositions = new ArrayList<>();
        ArrayList<Integer>futureObjects = new ArrayList<>();
        ArrayList<ObjectPathCreator>objectPathCreators = new ArrayList<>();
        ArrayList<ArrayList<Point>>allPositions = new ArrayList<>();


        //1. store a list of any obstacles
        ArrayList<Integer> obstacleLocations = getInvisibleObjectPositionsInTileMap(obstacles);
        ArrayList<Integer> glassLocations = getGlassPositionsInTileMap(obstacles);

        //2. Are there any obstacles that are damaged near the matched list elements?
        if (!obstacleLocations.isEmpty()) {
            nearMatchObstacles = getActualNearMatchesThatAreObstacles(matches, obstacleLocations, width, map);

            if(!nearMatchObstacles.isEmpty()){
                handleDamagedObstacles(objects, obstacles, obstacleLocations, nearMatchObstacles, tilePos);
            }
        }

        //3. Does the matched list contain a urb submerged in GLASS? - if so remove glass urb from match list
        if(!glassLocations.isEmpty()){
            for (int i = 0; i < matches.size(); i++) {
                if (glassLocations.contains(matches.get(i))) {
                    handleDamagedGlass(objects, obstacles, glassLocations, matches,tilePos);
                }
            }
        }

        //4. Get the list of remaining objects to fall
        listOfRemainingObjects = listOfRemainingObjectsToBeMovedDown(objects, matches, map, tilePos, obstacles, width);

        //5. Get the positions that the remaining objects fall to
        allPositions = positionsThatRemainingObjectsNeedToMoveTo(objects, matches, map,
                obstacles, tilePos, width, listOfRemainingObjects.size());


        //Add to object path creator
        ObjectPathCreator creator = new ObjectPathCreator();

        System.out.println("List of remaining objects = "+listOfRemainingObjects);
        System.out.println("Size diff = "+listOfRemainingObjects.size() + ", "+allPositions.get(0).size());

        if(listOfRemainingObjects.size() < allPositions.get(0).size()){
            creator.addAllElements(listOfRemainingObjects);

            int size = allPositions.get(0).size() - listOfRemainingObjects.size();

            ArrayList<Point>sample = new ArrayList<>();
            sample.addAll(allPositions.get(0).subList(0, allPositions.get(0).size() - size));
            creator.addAllPositions(sample);
            ArrayList<Point>copy = new ArrayList<>();
            copy.addAll(allPositions.get(0).subList(allPositions.get(0).size() - size, allPositions.get(0).size()));
            allPositions.get(1).addAll(copy);
            System.out.println("Copy = "+copy);
            objectPathCreators.add(creator);

        }
        else {
            creator.addAllElements(listOfRemainingObjects);
            creator.addAllPositions(allPositions.get(0));


            objectPathCreators.add(creator);
        }

        System.out.println("List of remaining objects = "+listOfRemainingObjects);
        System.out.println("MoveDown Positions = "+allPositions.get(0));

        //6. Get a list of future objects taken from offScreenMatches
        if(!allPositions.get(1).isEmpty()){
            int counter =-1;
            for(int i = 0; i < matches.size(); i++){
                futureObjects.add(matches.get(i));
                //counter--;
            }
            if(futurePositions.size() > matches.size()){
                if(!offScreenMatches.isEmpty()) {
                    int size = futurePositions.size() - matches.size();
                    for (int i = 0; i < size; i++) {
                        for (int k = 0; k < objects.size(); k++) {
                            if (objects.get(k).getY() < 0) {
                                objects.get(k).setActive(true);
                                objects.get(k).setLocation(counter);
                                counter--;
                                System.out.println(k + ", " + objects.get(k).getPosition() + ", " + objects.get(k).getLocation());
                                //matches.add(objects.get(k).getLocation());
                                futureObjects.add(counter);
                                break;
                            }
                        }
                    }
                }
            }
        }

        ObjectPathCreator futureCreator = new ObjectPathCreator();
        futureCreator.addAllElements(futureObjects);
        futureCreator.addAllPositions(allPositions.get(1)); //also allPositions.get(2) if glass or cement
        objectPathCreators.add(futureCreator);

        System.out.println("Future Objects = "+futureObjects);
        //System.out.println("Future Positions = "+futurePositions);
        return objectPathCreators;
    }
*/
    /*******************************************************************************
     * returns a list of remaining objects that need to be moved down the tiles
     *******************************************************************************/
    /*private ArrayList<Integer> listOfRemainingObjectsToBeMovedDown(List<UrbieAnimation>objects,
                                                                   ArrayList<Integer> matches,
                                                                   ArrayList<Integer> map,
                                                                   ArrayList<Point>tilePos,
                                                                   ArrayList<Obstacles> obstacles,
                                                                   int width) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> cleared = new ArrayList<>();
        ArrayList<Integer> obstacleLocations = getInvisibleObjectPositionsInTileMap(obstacles);
        ArrayList<Integer> glassLocations = getGlassPositionsInTileMap(obstacles);

        if(!matches.isEmpty()) {
            for (int i = 0; i < matches.size(); i++) {
                int num = matches.get(i) - width;

                while (num >= 0) {
                    int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                    if (map.get(num) == 1 && !obstacleLocations.contains(num)
                            && !glassLocations.contains(num) && !matches.contains(num)
                            && !list.contains(num) && objects.get(urb_num).getStatus() == NONE) {
                        list.add(num);
                    } else if (map.get(num) == 1 && obstacleLocations.contains(num)) {
                        break;
                    }
                    num = num - width;
                }
            }

            ArrayList<Integer> group = groupMatchesByRow(matches, width);
            ArrayList<Integer> additionals = getAdditionalObjectsToBeMoved(objects, map, matches, tilePos, group,
                    obstacleLocations, glassLocations, width, cleared, list);

            if (!additionals.isEmpty()) {
                list.addAll(additionals);
            }

        }
        return list;
    }

    *//***********************************************************************************
     * returns a list of points of where the remaining objects that need to be moved to
     ***********************************************************************************//*
    private ArrayList<ArrayList<Point>> positionsThatRemainingObjectsNeedToMoveTo(List<UrbieAnimation>objects,
                                                                       ArrayList<Integer>matches,
                                                                       ArrayList<Integer>map,
                                                                       ArrayList<Obstacles>obstacles,
                                                                       ArrayList<Point>tilePos,
                                                                       int width, int listSize){
        ArrayList<ArrayList<Point>>positions = new ArrayList<>();
        ArrayList<Integer> obstacleLocations = getInvisibleObjectPositionsInTileMap(obstacles);
        ArrayList<Integer> glassLocations = getGlassPositionsInTileMap(obstacles);
        ArrayList<Integer> cleared = new ArrayList<>();
        ArrayList<Point>topPosition = new ArrayList<>();
        ArrayList<Point>moveDown = new ArrayList<>();


        for(int i = 0; i < matches.size(); i++){
            ArrayList<Point>temp = new ArrayList<>();
            int num = matches.get(i);

            while(num >= width){
                int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                if(map.get(num)==1 && !obstacleLocations.contains(num) && !glassLocations.contains(num)
                        && !moveDown.contains(tilePos.get(num)) && (objects.get(urb_num).getStatus() == NONE)){
                    moveDown.add(tilePos.get(num));
                }
                else if(map.get(num)== 1 && obstacleLocations.contains(num) && !moveDown.contains(tilePos.get(num))){
                    if(!isThisRowBlocked(map, width, obstacleLocations, num)){
                        temp.add(tilePos.get(num));
                    }
                    temp.remove(temp.size()- 1);
                    break;
                }
                num = num - width;
            }
            moveDown.addAll(temp);


            //get the last num element which should be < width
            if(num >= 0 && num < width){
                int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                if(map.get(num)==1 && !obstacleLocations.contains(num) && !glassLocations.contains(num)
                        && !topPosition.contains(tilePos.get(num)) && (objects.get(urb_num).getStatus() == NONE)){
                    topPosition.add(tilePos.get(num));
                }
            }
        }

        positions.add(moveDown);
        positions.add(topPosition);
        System.out.println("Original positions = "+positions);

        ArrayList<Integer>group = groupMatchesByRow(matches,width);

        //additionals will be matches off screen (not in play) that are to be replaced
        ArrayList<Point> additionals = getAdditionalPointsToBeMoved(objects, group, matches, map,
                tilePos, obstacleLocations, glassLocations, cleared, positions.get(0), width);

        //indicates that the remaining will be future positions, so move the lowest Y values
        //to the end?
        *//*if(positions.size() > listSize){
            ArrayList<Integer>help;
            ArrayList<Point>copy2 = new ArrayList<>();

            help = sortPointArrayInDescendingOrderByY(positions);
            int size = positions.size() - listSize;
            for ( int i = positions.size() - size; i < positions.size(); i++){
                copy2.add(positions.get(help.get(i)));
            }

            for(int i = 0; i < copy2.size(); i++){
                if(positions.contains(copy2.get(i))){
                    int find = positions.indexOf(copy2.get(i));
                    positions.remove(find);
                }
            }
            positions.addAll(copy2);
            System.out.println("Points for Future = "+copy2);
        }*//*

       // System.out.println("Additionals = "+additionals);
        if(!additionals.isEmpty()){
            positions.add(additionals);
        }

        return positions;
    }
*/

          /*private int exploreInDirection(int direction, int current, int width, ArrayList<Integer>map, ArrayList<Integer>obstacleLocations, ArrayList<Integer>glassLocations){
        int result = -1;

        switch(direction) {
            case 1:
                if (current + 1 < map.size() && ((current + 1) / width == current / width) && map.get(current + 1) == 1 && !obstacleLocations.contains(current + 1) && !glassLocations.contains(current + 1)) {
                    result = current + 1;
                }
                else result =  -1;
            break;
            case -1:
                if(current - 1 > 0 && ((current - 1) / width != current / width) && map.get(current -1) == 1 && !obstacleLocations.contains(current - 1) && !glassLocations.contains(current - 1)) {
                    result = current - 1;
                }
                else result =  -1;
            break;
            case 2:
                if(current + width < map.size() && (current + width) % width <= (width - 1) && map.get(current + width) == 1 && !obstacleLocations.contains(current + width) && !glassLocations.contains(current + width)){
                    result = current + width;
                }
                else result =  -1;
            break;
        }
        return result;
    }
*/


    /*******************************************************************
     * get a list of invisible obstacle locations e.g. WOOD and CEMENT
     *******************************************************************/
    private ArrayList<Integer> getInvisibleObjectPositionsInTileMap(ArrayList<Obstacles> obstacles) {
        ArrayList<Integer> obstacleLocations = new ArrayList<>();

        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (!obstacles.get(i).isVisible() && obstacles.get(i).getDestroyCounter() > 0) {
                    obstacleLocations.add(obstacles.get(i).getLocation());
                }
            }
        }
        return obstacleLocations;
    }

    /**************************************************
     * get a list of GLASS obstacles
     **************************************************/
    private ArrayList<Integer> getGlassPositionsInTileMap(ArrayList<Obstacles> obstacles) {
        ArrayList<Integer> glassLocations = new ArrayList<>();

        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (obstacles.get(i).getStatus() == GLASS && obstacles.get(i).getDestroyCounter() > 0) {
                    glassLocations.add(obstacles.get(i).getLocation());
                }
            }
        }
        return glassLocations;
    }

    /*private void handleDamagedObstacles(List<UrbieAnimation>objects, ArrayList<Obstacles>obstacles,
                                        ArrayList<Integer>obstacleLocations,
                                        ArrayList<Integer>nearMatchObstacles,
                                        ArrayList<Point>tilePos){

        for (int i = 0; i < obstacles.size(); i++) {
            if (nearMatchObstacles.contains(obstacles.get(i).getLocation())) {
                obstacles.get(i).deductDestroyCounter();
                if (obstacles.get(i).getDestroyCounter() == 0) {
                    int urb_num = findBitmapByMapLocation(objects, tilePos, obstacles.get(i).getLocation());
                    if (urb_num > -1) {
                        objects.get(urb_num).setStatus(NONE);
                        objects.get(urb_num).setVisible(Urbies.VisibilityStatus.VISIBLE);
                        int index = obstacleLocations.indexOf(obstacles.get(i).getLocation());
                        obstacles.get(i).clearStatus();
                        obstacleLocations.remove(index);
                    }
                }
            }
        }
    }

    private void handleDamagedGlass(List<UrbieAnimation>objects, ArrayList<Obstacles>obstacles,
                                    ArrayList<Integer>glassLocations, ArrayList<Integer>matches,
                                    ArrayList<Point>tilePos){

        for (int i = 0; i < matches.size(); i++) {
            if (glassLocations.contains(matches.get(i))) {
                for (int j = 0; j < obstacles.size(); j++) {
                    if (obstacles.get(j).getLocation() == matches.get(i)) {
                        int urb_num = findBitmapByMapLocation(objects, tilePos, obstacles.get(j).getLocation());
                        if (urb_num > -1) {
                            objects.get(urb_num).setStatus(NONE);
                            obstacles.get(j).deductDestroyCounter();
                            obstacles.get(j).clearStatus();
                            int index = glassLocations.indexOf(obstacles.get(j).getLocation());
                            glassLocations.remove(index);
                        }
                        break;
                    }
                }
                matches.remove(i);
                i--;
            }
        }

    }*/


    /* *//***********************************************************
     * Return a list of cleared locations that are not blocked
     ***********************************************************//*
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

    *//***********************************************************
     * Return a list of elements relative to the cleared column
     ***********************************************************//*
    private ArrayList<Integer> getColumnElementsRelatingToClearedColumn(List<UrbieAnimation>objects,
                                                                        ArrayList<Integer>map,
                                                                        ArrayList<Integer>matches,
                                                                        ArrayList<Point>tilePos,
                                                                        int width,
                                                                        ArrayList<Integer>obstacleLocations,
                                                                        ArrayList<Integer>glassLocations,
                                                                        ArrayList<Integer>list,
                                                                        int element){

        ArrayList<Integer>columnValues = new ArrayList<>();

        int end = (element + width);
        while(end % width < width-1){
            int urb_num = findBitmapByMapLocation(objects, tilePos, end);
            if(map.get(end) == 1 && !matches.contains(end) && !obstacleLocations.contains(end)
                    && !glassLocations.contains(end) && !list.contains(end) && objects.get(urb_num).getStatus() == NONE){
                columnValues.add(end);
            }
            end = end + 1;
        }

        int num = element;
        while(num >= 0){
            int urb_num = findBitmapByMapLocation(objects, tilePos, num);
            if(map.get(num) == 1 && !matches.contains(num) && !obstacleLocations.contains(num)
                    && !glassLocations.contains(num) && !list.contains(num) && objects.get(urb_num).getStatus() == NONE){
                columnValues.add(num);
            }
            num = num - width;
        }
        return columnValues;
    }*/

     /* *//*********************************************************************************
     * Return whether the selected row is blocked by invisible obstacles
     *********************************************************************************//*
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
    }*/

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
    /*private ArrayList<Integer> getAdditionalObjectsToBeMoved(List<UrbieAnimation>objects,
                                                             ArrayList<Integer>map,
                                                             ArrayList<Integer> matches,
                                                             ArrayList<Point>tilePos,
                                                             ArrayList<Integer>group,
                                                             ArrayList<Integer>obstacleLocations,
                                                             ArrayList<Integer>glassLocations,
                                                             int width,
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
                                additionalList.addAll(getColumnElementsRelatingToClearedColumn(objects, map, matches, tilePos, width, obstacleLocations, glassLocations,
                                        list, cleared.get(j)));
                            }
                        }
                    }
                }
                num = num - width;
            }
        }
        return additionalList;
    }*/

/*
http://gregtrowbridge.com/a-basic-pathfinding-algorithm/
// Create a 4x4 grid
// Represent the grid as a 2-dimensional array
var gridSize = 4;
var grid = [];
for (var i=0; i<gridSize; i++) {
  grid[i] = [];
  for (var j=0; j<gridSize; j++) {
    grid[i][j] = 'Empty';
  }
}

// Think of the first index as "distance from the top row"
// Think of the second index as "distance from the left-most column"

// This is how we would represent the grid with obstacles above
grid[0][0] = "Start";
grid[2][2] = "Goal";

grid[1][1] = "Obstacle";
grid[1][2] = "Obstacle";
grid[1][3] = "Obstacle";
grid[2][1] = "Obstacle";

// Start location will be in the following format:
// [distanceFromTop, distanceFromLeft]
var findShortestPath = function(startCoordinates, grid) {
  var distanceFromTop = startCoordinates[0];
  var distanceFromLeft = startCoordinates[1];

  // Each "location" will store its coordinates
  // and the shortest path required to arrive there
  var location = {
    distanceFromTop: distanceFromTop,
    distanceFromLeft: distanceFromLeft,
    path: [],
    status: 'Start'
  };

  // Initialize the queue with the start location already inside
  var queue = [location];

  // Loop through the grid searching for the goal
  while (queue.length > 0) {
    // Take the first location off the queue
    var currentLocation = queue.shift();

    // Explore North
    var newLocation = exploreInDirection(currentLocation, 'North', grid);
    if (newLocation.status === 'Goal') {
      return newLocation.path;
    } else if (newLocation.status === 'Valid') {
      queue.push(newLocation);
    }

    // Explore East
    var newLocation = exploreInDirection(currentLocation, 'East', grid);
    if (newLocation.status === 'Goal') {
      return newLocation.path;
    } else if (newLocation.status === 'Valid') {
      queue.push(newLocation);
    }

    // Explore South
    var newLocation = exploreInDirection(currentLocation, 'South', grid);
    if (newLocation.status === 'Goal') {
      return newLocation.path;
    } else if (newLocation.status === 'Valid') {
      queue.push(newLocation);
    }

    // Explore West
    var newLocation = exploreInDirection(currentLocation, 'West', grid);
    if (newLocation.status === 'Goal') {
      return newLocation.path;
    } else if (newLocation.status === 'Valid') {
      queue.push(newLocation);
    }
  }

  // No valid path found
  return false;

};

// This function will check a location's status
// (a location is "valid" if it is on the grid, is not an "obstacle",
// and has not yet been visited by our algorithm)
// Returns "Valid", "Invalid", "Blocked", or "Goal"
var locationStatus = function(location, grid) {
  var gridSize = grid.length;
  var dft = location.distanceFromTop;
  var dfl = location.distanceFromLeft;

  if (location.distanceFromLeft < 0 ||
      location.distanceFromLeft >= gridSize ||
      location.distanceFromTop < 0 ||
      location.distanceFromTop >= gridSize) {

    // location is not on the grid--return false
    return 'Invalid';
  } else if (grid[dft][dfl] === 'Goal') {
    return 'Goal';
  } else if (grid[dft][dfl] !== 'Empty') {
    // location is either an obstacle or has been visited
    return 'Blocked';
  } else {
    return 'Valid';
  }
};


// Explores the grid from the given location in the given
// direction
var exploreInDirection = function(currentLocation, direction, grid) {
  var newPath = currentLocation.path.slice();
  newPath.push(direction);

  var dft = currentLocation.distanceFromTop;
  var dfl = currentLocation.distanceFromLeft;

  if (direction === 'North') {
    dft -= 1;
  } else if (direction === 'East') {
    dfl += 1;
  } else if (direction === 'South') {
    dft += 1;
  } else if (direction === 'West') {
    dfl -= 1;
  }

  var newLocation = {
    distanceFromTop: dft,
    distanceFromLeft: dfl,
    path: newPath,
    status: 'Unknown'
  };
  newLocation.status = locationStatus(newLocation, grid);

  // If this new location is valid, mark it as 'Visited'
  if (newLocation.status === 'Valid') {
    grid[newLocation.distanceFromTop][newLocation.distanceFromLeft] = 'Visited';
  }

  return newLocation;
};


// OK. We have the functions we need--let's run them to get our shortest path!

// Create a 4x4 grid
// Represent the grid as a 2-dimensional array
var gridSize = 4;
var grid = [];
for (var i=0; i<gridSize; i++) {
  grid[i] = [];
  for (var j=0; j<gridSize; j++) {
    grid[i][j] = 'Empty';
  }
}

// Think of the first index as "distance from the top row"
// Think of the second index as "distance from the left-most column"

// This is how we would represent the grid with obstacles above
grid[0][0] = "Start";
grid[2][2] = "Goal";

grid[1][1] = "Obstacle";
grid[1][2] = "Obstacle";
grid[1][3] = "Obstacle";
grid[2][1] = "Obstacle";

console.log(findShortestPath([0,0], grid));
 */


////////////////////////////////////////////////////////////////////////////////////////////
    //2. Are there matches that are below obstacles, if so remove from matches array list
    ////////////////////////////////////////////////////////////////////////////////////////////
    //Is any row blocked completely? if so, this section matters if not then not relevant
        /*ArrayList<Integer> matchesBelow = new ArrayList<>();

        if (!obstacleLocations.isEmpty()) {
            System.out.println("isRowBlocked = " + isRowBlocked(map, width, obstacleLocations));
            if (isRowBlocked(map, width, obstacleLocations)) {
                matchesBelow = matchesBelowObstacles(matches, obstacleLocations, width);
                if (!matchesBelow.isEmpty()) {
                    for (int i = 0; i < matchesBelow.size(); i++) {
                        int index = matches.indexOf(matchesBelow.get(i));
                        if (index > -1) matches.remove(index);
                    }
                    //  System.out.println("STM matchesBelow = " + matchesBelow);
                    matchesOffScreen.addAll(matchesBelow);
                }

                //stores a list of objects to move down that are below blocked obstacles
                belowMatchesToMoveDown = manageMatchesUnderObstacles(matchesBelow, width, obstacleLocations, objects, tilePos, map);
                // System.out.println("STM belowMatchesToMoveDown = " + belowMatchesToMoveDown);
            }
        }*/

    /**************************************************************************************************
     returns a list of matches that are underneath obstacles
     **************************************************************************************************/
    private ArrayList<Integer> matchesBelowObstacles(ArrayList<Integer> matches, ArrayList<Integer> obstacleLocations, int width) {
        ArrayList<Integer> matchesBelow = new ArrayList<>();

        for (int i = 0; i < matches.size(); i++) {
            for (int j = 0; j < obstacleLocations.size(); j++) {
                if (matches.get(i) > obstacleLocations.get(j) && matches.get(i) % width == obstacleLocations.get(j) % width) {
                    matchesBelow.add(matches.get(i));
                    break;
                }
            }
        }
        return matchesBelow;
    }

    /**************************************************************************************************
     returns a list of matches that are underneath obstacles
     **************************************************************************************************/
    private ArrayList<DataStore> manageMatchesUnderObstacles(ArrayList<Integer> belowObstacleMatches, int width, ArrayList<Integer> obstacleLocations, List<UrbieAnimation> objects, ArrayList<Point> tilePos, ArrayList<Integer> map) {
        ArrayList<DataStore> dataStore = new ArrayList<>();

        for (int i = 0; i < belowObstacleMatches.size(); i++) {
            int num = belowObstacleMatches.get(i) - width;
            int numStart = belowObstacleMatches.get(i);
            boolean blocked = false;

            while (!blocked) {

                if (obstacleLocations.contains(num) || num < 0) {
                    blocked = true;
                } else {
                    if (map.get(num) == 1) {
                        /*int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                        if (urb_num > -1) {
                            if (objects.get(urb_num).getStatus() == NONE) {
                                DataStore d = new DataStore();
                                d.setPosition(tilePos.get(numStart));
                                numStart = num;
                                d.setElement(num);
                                dataStore.add(d);
                                int urbToSetOffScreen = findBitmapByMapLocation(objects, tilePos, belowObstacleMatches.get(i));
                                objects.get(urbToSetOffScreen).setActive(false);
                            }
                        }*/
                    }

                }
                num = num - width;
            }

        }

        return dataStore;
    }

    private ArrayList<ObjectPathCreator> manageMatchesUnderObstaclesTest(ArrayList<Integer> belowObstacleMatches, int width, ArrayList<Integer> obstacleLocations, List<UrbieAnimation> objects, ArrayList<Point> tilePos, ArrayList<Integer> map) {
        ArrayList<ObjectPathCreator> dataStore = new ArrayList<>();

        for (int i = 0; i < belowObstacleMatches.size(); i++) {
            int num = belowObstacleMatches.get(i) - width;
            int numStart = belowObstacleMatches.get(i);
            boolean blocked = false;

            while (!blocked) {

                if (obstacleLocations.contains(num) || num < 0) {
                    blocked = true;
                } else {
                    if (map.get(num) == 1) {
                       /* int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                        if (urb_num > -1) {
                            if (objects.get(urb_num).getStatus() == NONE) {
                                ObjectPathCreator d = new ObjectPathCreator();
                                d.addToPositions(tilePos.get(numStart));
                                numStart = num;
                                d.addToElements(num);
                                dataStore.add(d);
                                int urbToSetOffScreen = findBitmapByMapLocation(objects, tilePos, belowObstacleMatches.get(i));
                                objects.get(urbToSetOffScreen).setActive(false);
                            }
                        }*/
                    }

                }
                num = num - width;
            }

        }

        return dataStore;
    }

    public ArrayList<Point> findPath( int x0, int y0, int x1, int y1) {
        ArrayList<Point>spritePath = new ArrayList<>();
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx-dy;
        int e2;

        while (true){
            spritePath.add(new Point(x0,y0));
            if (x0 == x1 && y0 == y1)
                break;

            e2 = 2 * err;
            if (e2 > -dy){
                err = err - dy;
                x0 = x0 + sx;
            }

            if (e2 < dx){
                err = err + dx;
                y0 = y0 + sy;
            }
        }

        return spritePath;
    }

    //exits out of loop on first condition of true
    private boolean isRowBlocked(ArrayList<Integer> map, int width, ArrayList<Integer> obstacleLocations) {
        boolean blocked = false;

        for (int i = 0; i < map.size(); i = i + width) {
            int count;
            if (map.get(i) == 0 || obstacleLocations.contains(i)) {
                count = 1;
                for (int j = 1; j < width; j++) {
                    if (map.get(i + j) == 0 || (map.get(i + j) == 1 && obstacleLocations.contains(i + j))) {
                        count++;
                    } else {
                        count = 0;
                    }
                }
                if (count == 5) {
                    blocked = true;
                    break;
                }
            }
        }

        return blocked;
    }


    /*private int isRowBlocked(ArrayList<Integer> map, int width, ArrayList<Integer> obstacleLocations) {
        boolean blocked = false;
        int found = -1;

        for (int i = 0; i < map.size(); i = i + width) {
            int count;
            if (map.get(i) == 0 || obstacleLocations.contains(i)) {
                count = 1;
                for (int j = 1; j < width; j++) {
                    if (map.get(i + j) == 0 || (map.get(i + j) == 1 && obstacleLocations.contains(i + j))) {
                        count++;
                    } else {
                        count = 0;
                    }
                }
                if (count == 5) {
                    blocked = true;
                    found = i;
                    break;
                }
            }
        }

        return found;
    }*/


    private boolean isRowBlockedAboveEmptyTile(ArrayList<Integer>refCopy, int element, int width){
        boolean result = false;

        //get the beginning of the row relevant to element
        int remainder = element % width;
        int start = element - remainder;

        int count = 0;
        for(int i = start; i < (start + width); i++){
            if(refCopy.get(i) == -2){
                count++;
            }
            else {
                result = false;
                break;
            }
        }

        if(count == width){
            result = true;
        }

        return result;
    }
    /*public ArrayList<ObjectPathCreator> separateTheMadnessTest(
            List<UrbieAnimation> objects, ArrayList<Integer> matches, ArrayList<Obstacles> obstacles,
            int width, ArrayList<Point> tilePos, ArrayList<Integer> map,
            ArrayList<Integer> matchesOffScreen, ArrayList<DataStore> future,
            ArrayList<Integer> entrance
    ) {
        ArrayList<Integer> obstacleLocations = new ArrayList<>();
        ArrayList<Integer> obstacleColumns = new ArrayList<>();
        ArrayList<Integer> glassLocations = new ArrayList<>();
        ArrayList<Integer> nearMatchObstacles;
        ArrayList<Integer> brokenObstacleLocations = new ArrayList<>();
        ArrayList<ObjectPathCreator> store = new ArrayList<>();
        ArrayList<Integer> moveDownList = new ArrayList<>();
        ArrayList<Point> positions = new ArrayList<>();
        ArrayList<Point> leftOverPositions = new ArrayList<>();
        ArrayList<ObjectPathCreator> belowMatchesToMoveDown = new ArrayList<>();
        ArrayList<Integer> emptyTiles;


        ////////////////////////////////////////////////////////////////////////////////////////////
        //!!get a list of invisible obstacle locations e.g. WOOD, CEMENT and also GLASS obstacles
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (!obstacles.get(i).isVisible() && obstacles.get(i).getDestroyCounter() > 0) {
                    obstacleLocations.add(obstacles.get(i).getLocation());
                    obstacleColumns.add(obstacles.get(i).getLocation() % width);
                } else if (obstacles.get(i).getStatus() == GLASS) {
                    glassLocations.add(obstacles.get(i).getLocation());
                }
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //1. Are there any obstacles that are damaged near the matched list elements?
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (!obstacleLocations.isEmpty()) {
            nearMatchObstacles = getActualNearMatchesThatAreObstacles(matches, obstacleLocations, width, map);

            //handle damaged urbs e.g. deduct counter etc
            if (!nearMatchObstacles.isEmpty()) {
                for (int i = 0; i < obstacles.size(); i++) {
                    if (nearMatchObstacles.contains(obstacles.get(i).getLocation())) {
                        obstacles.get(i).deductDestroyCounter();
                        if (obstacles.get(i).getDestroyCounter() == 0) {
                            entrance.add(obstacles.get(i).getLocation());
                            brokenObstacleLocations.add(i); //doesn't this get affected when you remove an obstacle?
                            int urb_num = findBitmapByMapLocation(objects, tilePos, obstacles.get(i).getLocation());
                            if (urb_num > -1) {
                                objects.get(urb_num).setStatus(NONE);
                                objects.get(urb_num).setVisible(Urbies.VisibilityStatus.VISIBLE);
                                int index = obstacleLocations.indexOf(obstacles.get(i).getLocation());
                                obstacles.get(i).clearStatus();
                                obstacleLocations.remove(index);
                                obstacleColumns.remove(index);
                            }
                        }
                    }
                    System.out.println("Obstacles " + i + " " + obstacles.get(i).getLocation());
                }

                System.out.println("ObstacleIndexWhereZero = " + brokenObstacleLocations);
                // System.out.println("STM nearMatchObstacles = " + nearMatchObstacles);
            }
        }

        emptyTiles = getEmptyTiles(map, tilePos, objects);
        System.out.println("Empty tiles = " + emptyTiles);

        //if there are no longer any obstacles remove entrance
        if(obstacleLocations.isEmpty() && !entrance.isEmpty()){
            entrance.clear();
        }

        System.out.println("Entrance = " + entrance);
        ////////////////////////////////////////////////////////////////////////////////////////////
        //2. Are there matches that are below obstacles, if so remove from matches array list
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Is any row blocked completely? if so, this section matters if not then not relevant
        ArrayList<Integer> matchesBelow = new ArrayList<>();

        if (!obstacleLocations.isEmpty()) {
            System.out.println("isRowBlocked = " + isRowBlocked(map, width, obstacleLocations));
            if (isRowBlocked(map, width, obstacleLocations)) {
                matchesBelow = matchesBelowObstacles(matches, obstacleLocations, width);
                if (!matchesBelow.isEmpty()) {
                    for (int i = 0; i < matchesBelow.size(); i++) {
                        int index = matches.indexOf(matchesBelow.get(i));
                        if (index > -1) matches.remove(index);
                    }
                    matchesOffScreen.addAll(matchesBelow);
                }
                belowMatchesToMoveDown = manageMatchesUnderObstaclesTest(matchesBelow, width, obstacleLocations, objects, tilePos, map);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //3. Does the matched list contain a urb submerged in GLASS? - if so remove glass urb from match list
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (!glassLocations.isEmpty()) {
            for (int i = 0; i < matches.size(); i++) {
                if (glassLocations.contains(matches.get(i))) {
                    for (int j = 0; j < obstacles.size(); j++) {
                        if (obstacles.get(j).getLocation() == matches.get(i)) {
                            int urb_num = findBitmapByMapLocation(objects, tilePos, obstacles.get(j).getLocation());
                            if (urb_num > -1) {
                                objects.get(urb_num).setStatus(NONE);
                                obstacles.get(j).deductDestroyCounter();
                                obstacles.get(j).clearStatus();
                                int index = glassLocations.indexOf(obstacles.get(j).getLocation());
                                glassLocations.remove(index);
                            }
                            break;
                        }
                    }
                    matches.remove(i);
                    i--;
                }
            }
        }

        /////////////////////////////////////////////////////////////////
        //If there are any empty tiles and an entrance point then
        //use A-star path finding to evaluate the shortest distance
        /////////////////////////////////////////////////////////////////


        for (int i = 0; i < matches.size(); i++) {
            ArrayList<Point> tempPosition = new ArrayList<>();
            int startingPoint = matches.get(i);
            PathFinding path = new PathFinding();


            //obstacles are higher than and in the same column as matches.get(i)
            //there is an entry point (e.g. obstacle was previously broken)
            //there are no current broken obstacles
            if (brokenObstacleLocations.isEmpty() && !obstacleLocations.isEmpty() && !entrance.isEmpty()) {

                //find out if any obstacles are higher up in map and on the same column as this will determine the downward movements
                //of the remaining/new objects on screen
                for (int b = 0; b < obstacleLocations.size(); b++) {
                    if (obstacleLocations.get(b) < matches.get(i) && obstacleColumns.get(b) == matches.get(i) % width) {
                        //pathfinding will need to take place here for this scenario
                        //starting point remains the same

                        //get a list of blocked cells that can not be used for path finding
                        ArrayList<Integer> blockedPositions = irrelevantPositions(entrance.get(0), width, obstacleLocations, glassLocations);

                        int[][] arrayWasteLand = convertArrayListTo2DArray(blockedPositions);

                        ArrayList<ArrayList<int[]>> pathway = new ArrayList<>();

                        *//*for (int l = 0; l < emptyTiles.size(); l++) {
                            //return shortest path in cell format
                            pathway.add(path.getPath(startingPoint, map.size() / width, width,
                                    (entrance.get(0) / width), (entrance.get(0) % width),
                                    emptyTiles.get(l) / width, emptyTiles.get(l) % width, arrayWasteLand));
                        }

                        //print out pathway
                        for (int j = 0; j < pathway.size(); j++) {
                            for (int k = 0; k < pathway.get(j).size(); k++) {
                                System.out.println("pathway = [" + pathway.get(j).get(k)[0] + "][ " + pathway.get(j).get(k)[1] + "]");
                            }
                        }*//*
                    }
                }

            }

            //there is a current broken tile and there are empty tiles
            else if (!brokenObstacleLocations.isEmpty() && !obstacleLocations.isEmpty() && !entrance.isEmpty() && !emptyTiles.isEmpty()) {

                //reset the startingPoint where the broken tile exists
                for (int j = 0; j < brokenObstacleLocations.size(); j++) {
                    //have to do it this way has I have already removed the required obstacleLocations value and obstacleColumns value
                    if (matches.get(i) % width == obstacles.get(brokenObstacleLocations.get(j)).getLocation() % width && obstacles.get(brokenObstacleLocations.get(j)).getLocation() > matches.get(i)) {
                        startingPoint = obstacles.get(brokenObstacleLocations.get(j)).getLocation();

                        //now do pathfinding

                    }
                }
            }
            //either no obstacles or no empty tiles or match is above anything that could impact it
            else {

                //hidden for now until happy things are working
                //just do regular movement down
                //Identify the valid positions that objects will be able to drop down to
                *//*int num = startingPoint - width;
                tempPosition.add(tilePos.get(startingPoint));

                while (num >= 0) {
                    if (map.get(num) == 1) {

                        int urb_num = findBitmapByMapLocation(objects, tilePos, num);

                        if (urb_num > -1) {
                            if (objects.get(urb_num).getStatus() == NONE) {
                                tempPosition.add(tilePos.get(num));
                            }
                        }
                    }
                    num = num - width;
                }

                //Identify the objects that will be moved down
                int counter = 0;
                num = startingPoint - width;


                while (num >= 0) {
                    int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                    if (urb_num > -1) {
                        if (map.get(num) == 1 && !matches.contains(num) && !moveDownList.contains(num) && objects.get(urb_num).getStatus() == NONE) {
                            moveDownList.add(num);
                            positions.add(tempPosition.get(counter));
                            tempPosition.remove(counter);
                        }
                    }
                    num = num - width;
                }
            }
*//*

                //all scenarios do these
           *//* leftOverPositions.addAll(tempPosition);
            uniqueArrayPointList(leftOverPositions);
            tempPosition.clear();
            emptyTiles.clear();
            brokenObstacleLocations.clear();*//*




            *//*if (!entrance.isEmpty() && !emptyTiles.isEmpty()) {
                for(int t = 0; t < brokenObstacleLocations.size(); t++){ //can I just use entrance instead of broken obstacle location?

                }

                PathFinding path = new PathFinding();

                //get a list of blocked cells that can not be used for path finding
                ArrayList<Integer> blockedPositions = irrelevantPositions(entrance.get(0), width, obstacleLocations, glassLocations);
                int[][] arrayWasteLand = convertArrayListTo2DArray(blockedPositions);
                ArrayList<ArrayList<int[]>> pathway = new ArrayList<>();

                for (int l = 0; l < emptyTiles.size(); l++) {
                    //return shortest path in cell format
                    pathway.add(path.getPath(emptyTiles.get(l), map.size() / width, width,
                            (entrance.get(0) / width), (entrance.get(0) % width),
                            emptyTiles.get(l) / width, emptyTiles.get(l) % width, arrayWasteLand));
                }

                //print out pathway
                for (int j = 0; j < pathway.size(); j++) {
                    for (int k = 0; k < pathway.get(j).size(); k++) {
                        System.out.println("pathway = [" + pathway.get(j).get(k)[0] + "][ " + pathway.get(j).get(k)[1] + "]");
                    }
                }
            }*//*
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //4. Start looping through matches, considering any broken obstacles and empty tiles
        ////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Point> tempPosition = new ArrayList<>();
        ArrayList<Point> emptyTilePositions = new ArrayList<>();
        boolean emptyTilesReplaced = false;
        boolean tilesComplete = false;


        for (int i = 0; i < matches.size(); i++) {
            int startingPoint = matches.get(i);

            if (!brokenObstacleLocations.isEmpty() && !emptyTiles.isEmpty()) {
                //reset the startingPoint where the broken tile exists
                for (int j = 0; j < brokenObstacleLocations.size(); j++) {
                    if (matches.get(i) % width == obstacles.get(brokenObstacleLocations.get(j)).getLocation() % width && obstacles.get(brokenObstacleLocations.get(j)).getLocation() > matches.get(i)) {
                        startingPoint = obstacles.get(brokenObstacleLocations.get(j)).getLocation();

                        //if empty tiles exist and there is a broken obstacle then change the starting point location
                        if (!emptyTiles.isEmpty()) {
                            //get a list of the empty tiles locations, this should focus on the location of where the empty tiles are
                            for (int b = 0; b < emptyTiles.size(); b++) {
                                emptyTilePositions.add(tilePos.get(emptyTiles.get(b)));
                            }

                            //add the contents of emptyTilePositions to tempPosition
                            tempPosition.addAll(emptyTilePositions);
                            emptyTilesReplaced = true;
                            tilesComplete = true;

                            //is emptyTiles X position the same as one of the entrance points? if so then
                            //there is a clear pathway, otherwise some urbs will have to be moved
                            if (!entrance.isEmpty()) {
                                boolean clearPathway = false;
                                for (int loop = 0; loop < entrance.size(); loop++) {
                                    for (int index = 0; index < emptyTilePositions.size(); index++) {
                                        if (tilePos.get(entrance.get(loop)).x == emptyTilePositions.get(index).x) {
                                            System.out.println("there is a clear pathway " + emptyTilePositions.get(index).x + " = " + tilePos.get(entrance.get(loop)).x);
                                            clearPathway = true;
                                            break;

                                        }
                                    }
                                }
                                System.out.println("clearPathway = " + clearPathway);
                            }
                        }
                        break;
                    }
                }
            }

            //Identify the valid positions that objects will be able to drop down to
            int num = startingPoint - width;
            tempPosition.add(tilePos.get(startingPoint));

            while (num >= 0) {
                if (map.get(num) == 1) {

                    int urb_num = findBitmapByMapLocation(objects, tilePos, num);

                    if (urb_num > -1) {
                        if (objects.get(urb_num).getStatus() == NONE) {
                            tempPosition.add(tilePos.get(num));
                        }
                    }
                }
                num = num - width;
            }

            //Identify the objects that will be moved down
            int counter = 0;

            if (!emptyTilesReplaced) {
                num = startingPoint - width;
            } else {
                num = startingPoint;
            }

            while (num >= 0) {
                int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                if (urb_num > -1) {
                    if (map.get(num) == 1 && !matches.contains(num) && !moveDownList.contains(num) && objects.get(urb_num).getStatus() == NONE) {
                        moveDownList.add(num);
                        positions.add(tempPosition.get(counter));
                        tempPosition.remove(counter);
                    }
                }
                num = num - width;
            }

            if (!brokenObstacleLocations.isEmpty() && moveDownList.isEmpty()) {
                //remove where startingPoint coordinates are
                boolean remove = leftOverPositions.remove(tilePos.get(startingPoint));
                if (remove) {
                    System.out.println("false positive removed");
                }
            }

            leftOverPositions.addAll(tempPosition);
            uniqueArrayPointList(leftOverPositions);
            tempPosition.clear();
            emptyTiles.clear();
            emptyTilesReplaced = false;
            brokenObstacleLocations.clear();
        }


        //remove any leftOverPositions that are also in positions
        for (int i = leftOverPositions.size() - 1; i >= 0; i--) {
            if (positions.contains(leftOverPositions.get(i))) {
                leftOverPositions.remove(i);
            }
        }

        for (int i = 0; i < moveDownList.size(); i++) {
            ObjectPathCreator d = new ObjectPathCreator();
            d.addToElements(moveDownList.get(i));
            d.addToPositions(positions.get(i));
            store.add(d);
        }

        for (int i = 0; i < leftOverPositions.size(); i++) {
            DataStore d = new DataStore();
            d.setElement(findLocationByPosition(leftOverPositions.get(i), tilePos)); //need to get element based on position of leftOverPosition
            d.setPosition(leftOverPositions.get(i));
            future.add(d);
        }

        System.out.println("MoveDownList = " + moveDownList);
        System.out.println("LeftOverPositions = " + leftOverPositions);

        matches.addAll(matchesBelow);

        if (tilesComplete) {
            int counter = -1;
            for (int k = 0; k < objects.size(); k++) {
                if (objects.get(k).getY() < 0) {
                    objects.get(k).setActive(true);
                    objects.get(k).setLocation(counter);
                    counter--;
                    System.out.println(k + ", " + objects.get(k).getPosition() + ", " + objects.get(k).getLocation());
                    matches.add(objects.get(k).getLocation());
                }
            }
        }

        if (!belowMatchesToMoveDown.isEmpty()) {
            store.addAll(belowMatchesToMoveDown);
        }

        //plot path for how objects are placed on screen, when their are broken tiles
        //entrancePoints, future(element and locations

        for (int i = 0; i < store.size(); i++) {
            System.out.println("store " + i + " = " + " " + store.get(i).getElements() + ", " + store.get(i).getPositions());
        }
        return store;

        //urbs not moving down due to matches below cement, once the blocked row has been opened then all urbs should
        //be able to be positioned under cement
    }

*/

            /*private ArrayList<Integer> irrelevantPositions(int entrance, int width,
                                                   ArrayList<Integer> obstacleLocations,
                                                   ArrayList<Integer> glassLocations) {

        ArrayList<Integer> values = new ArrayList<>();

        int x = 0;
        int y = 0;
        int loop = 0;

        while (loop < entrance) {
            values.add(y);
            values.add(x);
            x++;

            if (x == width) {
                x = 0;
                y++;
            }
            loop++;
        }

        //add the glass/wood/cement obstacle locations also
        for (int i = 0; i < obstacleLocations.size(); i++) {
            if (obstacleLocations.get(i) >= entrance) {
                y = obstacleLocations.get(i) / width;
                x = obstacleLocations.get(i) % width;
                values.add(y);
                values.add(x);
            }
        }

        for (int i = 0; i < glassLocations.size(); i++) {
            if (glassLocations.get(i) >= entrance) {
                y = glassLocations.get(i) / width;
                x = glassLocations.get(i) % width;
                values.add(y);
                values.add(x);
            }
        }

        System.out.println("not Relevant = " + values);
        return values;
    }*/

/*if(emptyTiles.isEmpty() && !brokenObstacleLocations.isEmpty() && matches.get(i)> brokenObstacleLocations.get(0)) {
                if(brokenObstacleLocations.contains(matches.get(i) % width)) {


                    System.out.println("test");
                    positionLists = test(reference, matches.get(i) - width, entrance, width, map, tilePos);

                    if(!positionLists.isEmpty()) {
                        for (int h = 0; h < positionLists.size(); h++) {
                            ObjectPathCreator opc = new ObjectPathCreator();
                            opc.setElement(positionLists.get(h).getLocation_id());
                            opc.setPosition(positionLists.get(h).getPosition().get(positionLists.get(h).getPosition().size() - 1));
                            int sNum = findBitmapByMapLocation(objects, tilePos, opc.getElement());
                            int sx = objects.get(sNum).getX();
                            int sy = objects.get(sNum).getY();

                            for (int q = 0; q < positionLists.get(h).getPosition().size(); q++) {
                                ArrayList<Point> getPath = findLine(sx, sy, positionLists.get(h).getPosition().get(q).x, positionLists.get(h).getPosition().get(q).y);
                                opc.addToPath(getPath);

                                sx = positionLists.get(h).getPosition().get(q).x;
                                sy = positionLists.get(h).getPosition().get(q).y;
                            }

                            int futureLoc = findLocationByPosition(opc.getPosition(), tilePos);

                            if(futureLoc != -1) {
                                opc.setFutureElement(futureLoc);
                            }
                            objectPathCreators.add(opc);
                        }
                    }
                }
            }

            else if (!brokenObstacleLocations.isEmpty() && !emptyTiles.isEmpty() && brokenObstacleLocations.contains(matches.get(i) % width)) {

                ArrayList<Integer>emptyCol = new ArrayList<>();
                for(int a = 0; a < emptyTiles.size(); a++){
                    emptyCol.add(emptyTiles.get(a) % width);
                }

                if(emptyCol.contains(brokenObstacleLocations)) {
                    emptyTiles = orderListByColumnWithEntrancesLast(emptyTiles, entrance, width);
                } else{
                    Collections.sort(emptyTiles);
                }
                reference = tileStatus(matches, map, obstacleLocations, glassLocations, emptyTiles); //update tileStatus
                positionLists = fillEmptyTiles(matches, width, map, reference, entrance, tilePos, emptyTiles, pathway, i);

                if(!positionLists.isEmpty()) {
                    for (int h = 0; h < positionLists.size(); h++) {
                        ObjectPathCreator opc = new ObjectPathCreator();
                        opc.setElement(positionLists.get(h).getLocation_id());
                        opc.setPosition(positionLists.get(h).getPosition().get(positionLists.get(h).getPosition().size() - 1));
                        int sNum = findBitmapByMapLocation(objects, tilePos, opc.getElement());
                        int sx = objects.get(sNum).getX();
                        int sy = objects.get(sNum).getY();

                        for (int q = 0; q < positionLists.get(h).getPosition().size(); q++) {
                            ArrayList<Point> getPath = findLine(sx, sy, positionLists.get(h).getPosition().get(q).x, positionLists.get(h).getPosition().get(q).y);
                            opc.addToPath(getPath);

                            sx = positionLists.get(h).getPosition().get(q).x;
                            sy = positionLists.get(h).getPosition().get(q).y;
                        }

                        int futureLoc = findLocationByPosition(opc.getPosition(), tilePos);

                        if(futureLoc != -1) {
                            opc.setFutureElement(futureLoc);
                        }
                        objectPathCreators.add(opc);
                    }
                }
                if(!positionLists.isEmpty()) {
                    //need to update reference array with the new positions filled by position lists
                    reference = tileStatus(matches, map, obstacleLocations, glassLocations, emptyTiles);
                    emptyTiles.clear(); //this should be placed somewhere else
                }

            }*/

}
