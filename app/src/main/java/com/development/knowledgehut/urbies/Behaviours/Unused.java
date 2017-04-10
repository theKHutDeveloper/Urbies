package com.development.knowledgehut.urbies.Behaviours;

import android.graphics.Point;

import com.development.knowledgehut.urbies.DrawableObjects.UrbieAnimation;
import com.development.knowledgehut.urbies.Objects.Obstacles;

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
                        System.out.println("test = "+test);
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
                    System.out.println("temp2 after test = "+temp2);
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

                                            System.out.println("test 1");
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
                                    System.out.println("test 2");
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


}
