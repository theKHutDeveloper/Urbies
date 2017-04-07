package com.development.knowledgehut.urbies.Behaviours;


import android.graphics.Bitmap;
import android.graphics.Point;

import com.development.knowledgehut.urbies.DrawableObjects.UrbieAnimation;
import com.development.knowledgehut.urbies.Objects.DataStore;
import com.development.knowledgehut.urbies.Objects.MatchedDetails;
import com.development.knowledgehut.urbies.Objects.Obstacles;
import com.development.knowledgehut.urbies.Screens.Assets;
import com.development.knowledgehut.urbies.Screens.Urbies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.CEMENT;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.GLASS;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.NONE;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.WOODEN;

public class GameMethods {
    /********************************************************************************
     * Identify whether two objects that are being swapped are valid
     * E.g. they are not the same type, unless they belong to specific
     * types
     ********************************************************************************/
    public boolean validSwap(List<UrbieAnimation> objects, int a, int b) {
        boolean result;

        result = !(objects.get(a).getStatus() != NONE || objects.get(b).getStatus() != NONE)
                && objects.get(a).getType() != objects.get(b).getType();

        if (objects.get(a).getType() == objects.get(b).getType() && !result &&
                objects.get(a).getStatus() == NONE ||
                objects.get(b).getStatus() == NONE
                ) {
            switch (objects.get(a).getType()) {
                case MAGICIAN:
                case WHITE_CHOCOLATE:
                case STRIPE_HORIZONTAL:
                case STRIPE_VERTICAL:
                    result = true;
                    break;
            }
        }
        return result;
    }


    /****************************************************************************************
     * Returns the index of the tile map of the specified bitmap object.
     * E.g. bitmap object at position 5 could be position 12 of the tile map
     * depending on the design of the tile map level
     ****************************************************************************************/
    public int findMapLocationOfBitmap(ArrayList<Point> tiles, List<UrbieAnimation> objects, int bitmapIndex) {
        for (int i = 0; i < tiles.size(); i++) {
            if (objects.get(bitmapIndex).getX() == tiles.get(i).x && objects.get(bitmapIndex).getY() == tiles.get(i).y) {
                return i;
            }
        }
        return -1;
    }


    /********************************************************************************
     * Find a bitmap at a specified location in the map system
     ********************************************************************************/
    public int findBitmapByMapLocation(List<UrbieAnimation> objects, ArrayList<Point> tiles, int mapElement) {
        Point point;
        point = tiles.get(mapElement);

        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getX() == point.x && objects.get(i).getY() == point.y) {
                return i;
            }
        }
        return -1;
    }


    /********************************************************************************
     * Given the type return the bitmap associated to it
     ********************************************************************************/
    public Bitmap findBitmapFromType(Urbies.UrbieType type) {
        Bitmap temp = null;

        switch (type) {
            case WHITE_CHOCOLATE:
                temp = Assets.chameleon;
                break;
            case MAGIC_BOMB:
                temp = Assets.magicBomb;
                break;
            case MAGICIAN:
                temp = Assets.magician;
                break;
            case STRIPE_HORIZONTAL:
                temp = Assets.stripe_h;
                break;
            case STRIPE_VERTICAL:
                temp = Assets.stripe_v;
                break;
            case PAC:
                temp = Assets.pac;
                break;
            case PIGTAILS:
                temp = Assets.pigtails;
                break;
            case PUNK:
                temp = Assets.punk;
                break;
            case ROCKER:
                temp = Assets.rocker;
                break;
            case GIRL_NERD:
                temp = Assets.nerd_girl;
                break;
            case LADY:
                temp = Assets.lady;
                break;
            case BABY:
                temp = Assets.baby;
                break;
            case NERD:
                temp = Assets.nerd;
                break;
        }
        return temp;
    }


    /********************************************************************************
     * Given the bitmap return the type associated to it
     ********************************************************************************/
    public Urbies.UrbieType findTypeBasedOnBitmap(Bitmap bitmap) {
        Urbies.UrbieType type = Urbies.UrbieType.NONE;

        if (bitmap == Assets.pac) {
            type = Urbies.UrbieType.PAC;
        } else if (bitmap == Assets.pigtails) {
            type = Urbies.UrbieType.PIGTAILS;
        } else if (bitmap == Assets.punk) {
            type = Urbies.UrbieType.PUNK;
        } else if (bitmap == Assets.baby) {
            type = Urbies.UrbieType.BABY;
        } else if (bitmap == Assets.lady) {
            type = Urbies.UrbieType.LADY;
        } else if (bitmap == Assets.nerd_girl) {
            type = Urbies.UrbieType.GIRL_NERD;
        } else if (bitmap == Assets.nerd) {
            type = Urbies.UrbieType.NERD;
        } else if (bitmap == Assets.rocker) {
            type = Urbies.UrbieType.ROCKER;
        } else if (bitmap == Assets.stripe_v) {
            type = Urbies.UrbieType.STRIPE_VERTICAL;
        } else if (bitmap == Assets.stripe_h) {
            type = Urbies.UrbieType.STRIPE_HORIZONTAL;
        } else if (bitmap == Assets.chameleon) {
            type = Urbies.UrbieType.WHITE_CHOCOLATE;
        } else if (bitmap == Assets.magician) {
            type = Urbies.UrbieType.MAGICIAN;
        }
        return type;
    }


    /********************************************************************************
     * Find all types in the list that match the specified elements type
     ********************************************************************************/
    public ArrayList<Integer> findMatchingObjectTypes(List<UrbieAnimation> objects, int element) {

        ArrayList<Integer> returnedList = new ArrayList<>();
        Urbies.UrbieType type = objects.get(element).getType();

        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getType() == type && objects.get(i).getStatus() == NONE) {
                returnedList.add(i);
            }
        }
        return returnedList;
    }


    /********************************************************************************
     * Find all types in the list that match the specified type
     ********************************************************************************/
    public ArrayList<Integer> getMatchingObjectsByType(List<UrbieAnimation> objects, Urbies.UrbieType type) {
        ArrayList<Integer> returnedList = new ArrayList<>();

        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getType() == type && objects.get(i).getStatus() == NONE) {
                returnedList.add(i);
            }
        }
        return returnedList;
    }


    /********************************************************************************
     * Return a list of the elements within a specified column
     ********************************************************************************/
    public ArrayList<Integer> findObjectsInColumn(List<UrbieAnimation> objects, int element, ArrayList<Integer> map, ArrayList<Point> tiles, int width) {
        ArrayList<Integer> returnedList = new ArrayList<>();

        //add frozen but  maybe do this separate from this method -
        int row = element / width;
        int rows = map.size() / width;

        returnedList.add(element);

        for (int i = 1; i < (rows - row); i++) {
            if (map.get(element + (width * i)) == 1) {
                returnedList.add(element + (width * i));
            }
        }

        for (int i = 1; (row - i) >= 0; i++) {
            if (map.get(element - (width * i)) == 1) {
                returnedList.add(element - (width * i));
            }
        }

        //get unique values
        uniqueArrayIntegerList(returnedList);

        //convert to urbs
        for (int i = 0; i < returnedList.size(); i++) {
            int index = returnedList.get(i);
            if(index > -1) {
                returnedList.set(i, findBitmapByMapLocation(objects, tiles, index));
            }
        }

        for (int i = returnedList.size() - 1; i >= 0; i--) {
            if (objects.get(returnedList.get(i)).getStatus() != NONE) {
                returnedList.remove(i);
            }
        }

        return returnedList;
    }


    /********************************************************************************
     * Return a list of elements within a specific row
     ********************************************************************************/
    public ArrayList<Integer> findObjectsInRow(List<UrbieAnimation> objects, int element, ArrayList<Integer> map, ArrayList<Point> tiles, int width) {
        ArrayList<Integer> returnedList = new ArrayList<>();
        int column = element % width;

        //add frozen but  maybe do this separate from this method -
        returnedList.add(element);

        for (int i = 1; (i + column) < width; i++) {
            if (map.get(element + i) == 1) {
                returnedList.add(element + i);
            }
        }

        for (int i = 1; i <= column; i++) {
            if (map.get(element - i) == 1) {
                returnedList.add(element - i);
            }
        }

        //get unique values
        uniqueArrayIntegerList(returnedList);

        //convert to urbs
        for (int i = 0; i < returnedList.size(); i++) {
            int index = returnedList.get(i);
            returnedList.set(i, findBitmapByMapLocation(objects, tiles, index));
        }

        for (int i = returnedList.size() - 1; i >= 0; i--) {
            if (objects.get(returnedList.get(i)).getStatus() != NONE) {
                returnedList.remove(i);
            }
        }

        return returnedList;
    }


    /********************************************************************************
     * Sorts a Point array in descending order on Y coordinates
     ********************************************************************************/
    public ArrayList<Integer> sortPointArrayInDescendingOrderByY(ArrayList<Point> points) {
        ArrayList<Integer> actual = new ArrayList<>();
        ArrayList<Point> tempArrayList = new ArrayList<>();

        if (!points.isEmpty()) {
            tempArrayList.addAll(points);

            int element;
            int temp, max;

            int size = tempArrayList.size();
            max = tempArrayList.get(0).y;
            element = 0;

            for (int k = 0; k < size; k++) {

                if (size > 1) {
                    for (int i = 1; i < size; i++) {
                        temp = Math.max(tempArrayList.get(i - 1).y, tempArrayList.get(i).y);
                        if (temp > max) {
                            if (temp == tempArrayList.get(i).y) {
                                element = i;
                                max = temp;
                            } else {
                                element = i - 1;
                                max = temp;
                            }
                        }
                    }
                } else if (size == 1) {
                    element = 0;
                }

                actual.add(element);
                tempArrayList.set(element, new Point(0, -100));
                max = 0;
            }
        }
        return actual;
    }


    /********************************************************************************
     * Eliminate duplicates from an Integer ArrayList
     ********************************************************************************/
    public void uniqueArrayIntegerList(ArrayList<Integer> object) {
        HashSet<Integer> uniqueElements = new HashSet<>();
        uniqueElements.addAll(object);
        object.clear();
        object.addAll(uniqueElements);
        Collections.sort(object, Collections.reverseOrder());
    }


    /********************************************************************************
     * Eliminate duplicates from an ArrayList of MatchedDetails
     ********************************************************************************/
    private void uniqueMatchedDetails(ArrayList<MatchedDetails> objects) {

        //remove duplicates
        if (objects.size() > 1) {
            for (int i = 0; i < objects.size(); i++) {
                for (int j = i + 1; j < objects.size(); j++) {
                    if (objects.get(i).getReturnedMatches().equals(objects.get(j).getReturnedMatches())) {
                        objects.remove(j);
                        j--;
                    }
                }
            }
        }

        //remove objects that are a subset of another array list
        if (objects.size() > 1) {
            for (int i = 0; i < objects.size(); i++) {
                for (int j = 0; j < objects.size(); j++) {
                    if (i != j) {
                        if (objects.get(i).getReturnedMatches().containsAll(objects.get(j).getReturnedMatches())) {
                            objects.remove(j);
                            j--;
                            if (i > 0) {
                                i--;
                            }
                        }
                    }
                }
            }
        }

        //remove objects that have some elements within another array list
        if (objects.size() > 1) {
            for (int i = 0; i < objects.size(); i++) {
                for (int k = 1; k < objects.size(); k++) {
                    if (i != k) {
                        if (!Collections.disjoint(objects.get(i).getReturnedMatches(), objects.get(k).getReturnedMatches())) {
                            objects.get(i).getReturnedMatches().addAll(objects.get(k).getReturnedMatches());
                            uniqueArrayIntegerList(objects.get(i).getReturnedMatches());
                            objects.remove(k);
                            k--;
                            if (i > 0) {
                                i--;
                            }
                        }
                    }
                }
            }
        }
    }


    /********************************************************************************
     * Eliminate duplicates from an ArrayList of Point objects
     ********************************************************************************/
    private void uniqueArrayPointList(ArrayList<Point> object) {
        HashSet<Point> uniqueElements = new HashSet<>();
        uniqueElements.addAll(object);
        object.clear();
        object.addAll(uniqueElements);
    }


    /********************************************************************************
     * Finds automatic matches
     ********************************************************************************/
    public ArrayList<MatchedDetails> findAutomaticMatches(List<UrbieAnimation> objects, int width, ArrayList<Integer> map, ArrayList<Obstacles> obstacles) {
        ArrayList<MatchedDetails> matchedDetails = new ArrayList<>();
        MatchedDetails temp;


        for (int i = 0; i < objects.size(); i++) {
            if(objects.get(i).getActive()) {
                int element = objects.get(i).getLocation();

                if (objects.get(i).getStatus() == NONE && objects.get(i).getY() > 0) {
                    temp = combineMatches(
                            findUserMatchesByRow(objects, element, width, map, obstacles),
                            findUserMatchesByColumn(objects, element, width, map, obstacles),
                            width, obstacles
                    );

                    if (!temp.getReturnedMatches().isEmpty()) {
                        matchedDetails.add(temp);
                    }
                }
            }
        }

        if (!matchedDetails.isEmpty()) {
            uniqueMatchedDetails(matchedDetails);
        }

        //System.out.println("Automatic Matched Details"+matchedDetails);
        return matchedDetails;
    }


    /********************************************************************************
     * Combines horizontal and vertical matches
     ********************************************************************************/
    public MatchedDetails combineMatches(ArrayList<Integer> horizontal, ArrayList<Integer> vertical, int width, ArrayList<Obstacles> obstacles, int... userSelect) {
        MatchedDetails matchedDetails = new MatchedDetails();
        ArrayList<Integer> matches = new ArrayList<>();
        ArrayList<Integer> intersects = new ArrayList<>();

        matches.addAll(horizontal);
        matches.addAll(vertical);

        for (int j = 0; j < matches.size(); j++) {
            if (Collections.frequency(matches, matches.get(j)) > 1) {
                intersects.add(matches.get(j));
            }
        }
        if (intersects.isEmpty()) intersects.add(-1);

        uniqueArrayIntegerList(matches);
        Collections.sort(matches, Collections.reverseOrder());

        matchedDetails.setReturnedMatches(matches);

        if (userSelect.length > 0) {
            matchedDetails.setMatchShape(getMatchShape(matches, width, userSelect[0], userSelect[1]));
        } else {
            matchedDetails.setMatchShape(getMatchShape(matches, width));
        }

        matchedDetails.setSpecialType();

        int numberToRemove = 0;
        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (matchedDetails.getReturnedMatches().contains(obstacles.get(i).getObstacle().getLocation())) {
                    numberToRemove++;
                }
            }
        }

        if (numberToRemove < 3) {
            //remove urb contained in the obstacle if applicable, this will have an impact in the setIntersecting_element
            if (!obstacles.isEmpty()) {
                for (int i = 0; i < obstacles.size(); i++) {
                    if (matchedDetails.getReturnedMatches().contains(obstacles.get(i).getObstacle().getLocation())) {
                        int index = matchedDetails.getReturnedMatches().indexOf(obstacles.get(i).getObstacle().getLocation());
                        matchedDetails.getReturnedMatches().remove(index);
                    }
                }
            }
        }


        if (matchedDetails.getMatchShape() == Urbies.MatchShape.LINE_OF_FOUR_HORIZONTAL ||
                matchedDetails.getMatchShape() == Urbies.MatchShape.LINE_OF_FOUR_VERTICAL ||
                matchedDetails.getMatchShape() == Urbies.MatchShape.LINE_OF_FIVE_OR_MORE) {
            if (userSelect.length > 0) {
                matchedDetails.setIntersecting_element(userSelect[0]);
            } else {
                if (!matchedDetails.getReturnedMatches().isEmpty()) {
                    if (matchedDetails.getReturnedMatches().size() > 2) {
                        matchedDetails.setIntersecting_element(matches.get(2));
                    } else matchedDetails.setIntersecting_element(matches.get(0));
                }
            }
        } else {
            matchedDetails.setIntersecting_element(intersects.get(0));
        }

        return matchedDetails;
    }


    /********************************************************************************
     * Find object in list by it location value
     ********************************************************************************/
    public int findObjectByPosition(int index, List<UrbieAnimation> objects) {
        int found = -1;

        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getLocation() == index) {
                found = i;
                break;
            }
        }

        return found;
    }


    /********************************************************************************
     * Find user matches by column
     ********************************************************************************/
    public ArrayList<Integer> findUserMatchesByColumn(List<UrbieAnimation> objects, int a, int width, ArrayList<Integer> map, ArrayList<Obstacles> obstacles) {
        ArrayList<Integer> matches = new ArrayList<>();
        int row = a / width;
        int rows = map.size() / width;

        int element = findObjectByPosition(a, objects);
        Urbies.UrbieType type = objects.get(element).getType();
        matches.add(a);

        for (int i = 1; i < (rows - row); i++) {
            if (map.get(a + (width * i)) == 1) {
                int bmpLoc = findObjectByPosition(a + (width * i), objects);

                if (bmpLoc > -1) {
                    if (objects.get(bmpLoc).getType() == type && objects.get(bmpLoc).getVisible() == Urbies.VisibilityStatus.VISIBLE && objects.get(bmpLoc).getActive()) { //.getStatus() == NONE) {
                        if (!matches.contains(a + (width * i))) {
                            matches.add(a + (width * i));
                        }
                    } else break;
                } else break;
            } else break;
        }

        for (int i = 1; (row - i) >= 0; i++) {
            if (map.get(a - (width * i)) == 1) {
                int bmpLoc = findObjectByPosition(a - (width * i), objects);

                if (bmpLoc > -1) {
                    if (objects.get(bmpLoc).getType() == type && objects.get(bmpLoc).getVisible() == Urbies.VisibilityStatus.VISIBLE && objects.get(bmpLoc).getActive()) { //.getStatus() == NONE) {
                        if (!matches.contains(a - (width * i))) {
                            matches.add(a - (width * i));
                        }
                    } else break;
                } else break;
            } else break;
        }

        if (matches.size() < 3) {
            matches.clear();
        } else {
            Collections.sort(matches, Collections.<Integer>reverseOrder());

            //do matches contain visible obstacles?
            obstacleContainedInMatch(obstacles, matches);
        }

        return matches;
    }


    /********************************************************************************
     * Find user matches by row
     ********************************************************************************/
    public ArrayList<Integer> findUserMatchesByRow(List<UrbieAnimation> objects, int a, int width, ArrayList<Integer> map, ArrayList<Obstacles> obstacles) {
        ArrayList<Integer> matches = new ArrayList<>();

        Urbies.UrbieType type = objects.get(findObjectByPosition(a, objects)).getType();
        int column = a % width;

        matches.add(a);

        for (int i = 1; (i + column) < width; i++) {
            if (map.get(a + i) == 1) {
                int bmpLoc = findObjectByPosition(a + i, objects);

                if (bmpLoc > -1) {
                    if (objects.get(bmpLoc).getType() == type && objects.get(bmpLoc).getVisible() == Urbies.VisibilityStatus.VISIBLE && objects.get(bmpLoc).getActive()) { //getStatus() == NONE) {
                        if (!matches.contains(a + i)) {
                            matches.add(a + i);
                        }
                    } else break;
                } else break;
            } else break;
        }

        for (int i = 1; i <= column; i++) {
            if (map.get(a - i) == 1) {
                int bmpLoc = findObjectByPosition(a - i, objects);

                if (bmpLoc > -1) {
                    if (objects.get(bmpLoc).getType() == type && objects.get(bmpLoc).getVisible() == Urbies.VisibilityStatus.VISIBLE && objects.get(bmpLoc).getActive()) { //.getStatus() == NONE) {
                        if (!matches.contains(a - i)) {
                            matches.add(a - i);
                        }
                    } else break;
                } else break;
            } else break;
        }

        if (matches.size() < 3) {
            matches.clear();
        } else {
            Collections.sort(matches, Collections.reverseOrder());
            //do matches contain visible obstacles?
            obstacleContainedInMatch(obstacles, matches);
        }

        return matches;
    }


    /********************************************************************************
     * Get the future positions for objects based on the point coordinates
     ********************************************************************************/
    public void associateCoordinatesWithPosition(ArrayList<Point> coordinates, ArrayList<Point> tileCoordinates, ArrayList<Integer> positions) {
        for (int i = 0; i < coordinates.size(); i++) {
            for (int j = 0; j < tileCoordinates.size(); j++) {
                if (coordinates.get(i) == tileCoordinates.get(j)) {
                    if (!positions.contains(j)) {
                        positions.add(j);
                    }
                    break;
                }
            }
        }
    }


    /********************************************************************************
     * Get match shape of matches
     ********************************************************************************/
    private Urbies.MatchShape getMatchShape(ArrayList<Integer> matches, int width, int... userSelect) {
        boolean horizontal = false;
        boolean vertical = false;
        int matchSize = matches.size();
        Urbies.MatchShape matchShape = Urbies.MatchShape.NONE;

        if (matchSize == 3) {
            matchShape = Urbies.MatchShape.LINE;
        } else if (matchSize == 4) {
            if (userSelect.length > 0) {
                //userSelect[0], = one, userSelect[1] = two, result = Math.abs(one - two),
                //if match is line of four  and  results = 1 then match is line of four horizontal
                //if match is line of four and results = horizontalSize then match is line of four vertical
                int result = Math.abs(userSelect[0] - userSelect[1]);
                if (result == 1) {
                    matchShape = Urbies.MatchShape.LINE_OF_FOUR_HORIZONTAL;
                } else {
                    matchShape = Urbies.MatchShape.LINE_OF_FOUR_VERTICAL;
                }
            } else {
                if (matches.get(0) - matches.get(matchSize - 1) == matchSize - 1) {
                    matchShape = Urbies.MatchShape.LINE_OF_FOUR_HORIZONTAL;
                } else matchShape = Urbies.MatchShape.LINE_OF_FOUR_VERTICAL;
            }
        } else if (matchSize > 4) {
            if (matches.get(0) - matches.get(matchSize - 1) == matchSize - 1) {
                matchShape = Urbies.MatchShape.LINE_OF_FIVE_OR_MORE;
                horizontal = true;
            } else {
                for (int i = 1; i < matchSize; i++) {
                    if (matches.get(i - 1) - matches.get(i) == width) {
                        vertical = true;
                    } else {
                        vertical = false;
                        break;
                    }
                }
                if (vertical) {
                    matchShape = Urbies.MatchShape.LINE_OF_FIVE_OR_MORE;
                }
            }

            if (!horizontal && !vertical) {
                matchShape = Urbies.MatchShape.L_OR_T_SHAPE;
            }
        }

        return matchShape;
    }


    /**************************************************************************************
     * Returns a list of object elements that need to be moved following
     * match extraction. Also evaluates the positions that the elements
     * will move to. In addition the elements that will replace the matched
     * items will have it's position evaluated from this method
     **************************************************************************************/
    /*public ArrayList<Integer> listOfObjectToMoveDown(
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
                        int urb_num = findBitmapByMapLocation(objects, tilePos, num);

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
                    int urb_num = findBitmapByMapLocation(objects, tilePos, num);
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
            uniqueArrayPointList(futurePos);
            newCoordinates.addAll(futurePos);
        }

        //maybe i need to identify glass tiles here that are zero and not included in any of the lists
        //this might not work if the destroy counter has not been updated

        //TODO: May need to do the same here with WOODEN TILES && CEMENT TILES
        for(int o = 0; o < obstacles.size(); o++){
            if(obstacles.get(o).getStatus() == GLASS) {
                if(obstacles.get(o).getDestroyCounter() == 0) {
                    int m = findObjectByPosition(obstacles.get(o).getObstacle().getLocation(), objects);
                    if(objects.get(m).getStatus() == GLASS){
                        objects.get(m).setStatus(NONE);
                    }
                }
            }
        }

        newLocations.addAll(position);
        return list;
    }
*/

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
            System.out.println("valid yPositions for match " + matchedList.get(i) + " = " + yPositions);

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

        System.out.println("near match obstacles = "+nearMatchObstacles);
        System.out.println("starting point = "+startingPoint);
        System.out.println("pos = "+pos);
        System.out.println("loc = "+loc);

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

    /*************************************************

     *************************************************/
    public ArrayList<DataStore> separateTheMadness(List<UrbieAnimation>objects, ArrayList<Integer>matches, ArrayList<Obstacles>obstacles, int width, ArrayList<Point>tilePos, ArrayList<Integer>map,
                                                   ArrayList<Integer>matchesOffScreen//, ArrayList<Integer>emptyTiles
    ){
        ArrayList<Integer> obstacleLocations = new ArrayList<>();
        ArrayList<Integer> glassLocations = new ArrayList<>();
        ArrayList<Integer> nearMatchObstacles;
        ArrayList<Integer> glassMatches = new ArrayList<>();
        ArrayList<Integer> obstacleIndexWhereZero = new ArrayList<>();
        ArrayList<DataStore>store = new ArrayList<>();
        ArrayList<Integer> moveDownList = new ArrayList<>();
        ArrayList<Point> positions = new ArrayList<>();

        ////////////////////////////////////////////////////////////////////////////////////////////
        //!!get a list of invisible obstacle locations e.g. WOOD, CEMENT and also GLASS obstacles
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if(!obstacles.get(i).isVisible() && obstacles.get(i).getDestroyCounter() > 0) {
                    obstacleLocations.add(obstacles.get(i).getLocation());
                }
                else if(obstacles.get(i).getStatus() == GLASS){
                    glassLocations.add(obstacles.get(i).getLocation());
                }
            }
            System.out.println("STM ObstacleLocations = "+obstacleLocations);
            System.out.println("STM GlassLocations = "+glassLocations);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //1. Are there any obstacles that are damaged near the matched list elements?
        ////////////////////////////////////////////////////////////////////////////////////////////
        if(!obstacles.isEmpty()) {
            nearMatchObstacles = getActualNearMatchesThatAreObstacles(matches, obstacleLocations, width, map);

            //handle damaged urbs e.g. deduct counter etc
            if(!nearMatchObstacles.isEmpty()) {
                for(int i = 0; i < obstacles.size(); i++){
                    if(nearMatchObstacles.contains(obstacles.get(i).getLocation())) {
                        obstacles.get(i).deductDestroyCounter();
                        if(obstacles.get(i).getDestroyCounter() == 0){
                            obstacleIndexWhereZero.add(i);
                            int urb_num = findBitmapByMapLocation(objects, tilePos, obstacles.get(i).getLocation());
                            if(urb_num > -1){
                                objects.get(urb_num).setStatus(NONE);
                                objects.get(urb_num).setVisible(Urbies.VisibilityStatus.VISIBLE);
                                int index = obstacleLocations.indexOf(obstacles.get(i).getLocation());
                                obstacleLocations.remove(index);
                            }
                        }
                    }
                }
                System.out.println("STM nearMatchObstacles = " + nearMatchObstacles);
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////
        //2. Are there matches that are below obstacles, if so remove from matches array list
        ////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Integer> matchesBelow = matchesBelowObstacles(matches, obstacleLocations, width);
        if(!matchesBelow.isEmpty()){
            for(int i = 0; i < matchesBelow.size(); i++){
                int index = matches.indexOf(matchesBelow.get(i));
                if(index > -1) matches.remove(index);
            }
            System.out.println("STM matchesBelow = "+matchesBelow);
            matchesOffScreen.addAll(matchesBelow);
        }

        //stores a list of objects to move down that are below blocked obstacles
        ArrayList<DataStore> belowMatchesToMoveDown = manageMatchesUnderObstacles(matchesBelow, width, obstacleLocations, objects, tilePos, map);
        System.out.println("STM belowMatchesToMoveDown = "+belowMatchesToMoveDown);


        ////////////////////////////////////////////////////////////////////////////////////////////
        //3. Does the matched list contain a urb submerged in GLASS? - if so remove glass urb from match list
        ////////////////////////////////////////////////////////////////////////////////////////////
        for(int i = 0; i < matches.size(); i++){
            if(glassLocations.contains(matches.get(i))){
                for(int j = 0; j < obstacles.size(); j++){
                    if(obstacles.get(j).getLocation() == matches.get(i)){
                        int urb_num = findBitmapByMapLocation(objects, tilePos, obstacles.get(j).getLocation());
                        if(urb_num > -1){
                            objects.get(urb_num).setStatus(NONE);
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

        ////////////////////////////////////////////////////////////////////////////////////////////
        //4. Start looping through matches, considering any broken obstacles and empty tiles
        ////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Point>tempPosition = new ArrayList<>();

        for(int i = 0; i < matches.size(); i++){
            int startingPoint = matches.get(i);
            for(int j = 0; j < obstacleIndexWhereZero.size(); j++){
                if(matches.get(i)%width == obstacles.get(obstacleIndexWhereZero.get(j)).getLocation()%width && obstacles.get(obstacleIndexWhereZero.get(j)).getLocation() > matches.get(i)){
                    startingPoint = obstacles.get(obstacleIndexWhereZero.get(j)).getLocation();
                    break;
                }
            }
            //NOTE will need to do something when there are empty tiles also


            //Identify the valid positions that objects will be able to drop down to
            tempPosition.add(tilePos.get(startingPoint));
            int num = startingPoint - width;

            while (num >= 0) {
                if (map.get(num) == 1) {

                    int urb_num = findBitmapByMapLocation(objects, tilePos, num);

                    if(urb_num > -1) {
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
            while(num >=0){
                int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                if(map.get(num) == 1 && !matches.contains(num) && !moveDownList.contains(num) && objects.get(urb_num).getStatus() == NONE){
                    moveDownList.add(num);
                    positions.add(tempPosition.get(counter));
                    counter++;
                }
                num = num - width;
            }

        }


        for(int i = 0; i < moveDownList.size(); i++){
            DataStore d = new DataStore();
            d.setElement(moveDownList.get(i));
            d.setPosition(tempPosition.get(i));
            store.add(d);
        }

        //add back any matches removed previously
        matches.addAll(matchesBelow);
        return store;//belowMatchesToMoveDown;
    }



    /**************************************************************************************************
     returns a list of obstacles that are damaged as a result of the match
     (this does not include obstacles that are at a zero counter
     **************************************************************************************************/
    private ArrayList<Integer>getActualNearMatchesThatAreObstacles(ArrayList<Integer>matches, ArrayList<Integer>obstacleLocations, int width, ArrayList<Integer>map){
        ArrayList<Integer>nearMatchObstacles = new ArrayList<>();
        ArrayList<Integer> nearMatches = new ArrayList<>();

        //gets a list of values nearby the matches (above, below and either side)
        for (int i = 0; i < matches.size(); i++) {
            if ((matches.get(i) > 0) && (map.get(matches.get(i) - 1) == 1) && ((matches.get(i) / width) == (matches.get(i) - 1) / width)) { //on the same row
                if(!matches.contains(matches.get(i) - 1)) {
                    nearMatches.add(matches.get(i) - 1);
                }
            }
            if ((matches.get(i) + 1 < map.size()) && (map.get(matches.get(i) + 1) == 1) && ((matches.get(i) / width) == (matches.get(i) + 1) / width)) { //on same row
                if(!matches.contains(matches.get(i) + 1)){
                    nearMatches.add(matches.get(i) + 1);
                }
            }
            if ((matches.get(i) - width > 0) && (map.get(matches.get(i) - width) == 1) && ((matches.get(i) % width) == (matches.get(i) - width) % width)) { //on the same column
                if(!matches.contains(matches.get(i) - width)) {
                    nearMatches.add(matches.get(i) - width);
                }
            }
            if ((matches.get(i) + width < map.size()) && (map.get(matches.get(i) + width) == 1) && ((matches.get(i) % width) == (matches.get(i) + width) % width)) { //on the same column
                if(!matches.contains(matches.get(i) + width)) {
                    nearMatches.add(matches.get(i) + width);
                }
            }
        }

        //check against the near matches whether any of these values are obstacles, if so store them
        for(int i =0; i < nearMatches.size(); i++) {
            if (obstacleLocations.contains(nearMatches.get(i))) {
                nearMatchObstacles.add(nearMatches.get(i));
            }
        }

        return nearMatchObstacles;
    }


    //returns a list of matches that are underneath obstacles
    private ArrayList<Integer> matchesBelowObstacles(ArrayList<Integer>matches, ArrayList<Integer>obstacleLocations, int width){
        ArrayList<Integer> matchesBelow = new ArrayList<>();

        for(int i = 0; i < matches.size(); i++){
            for(int j = 0; j < obstacleLocations.size(); j++){
                if(matches.get(i) > obstacleLocations.get(j) && matches.get(i) % width == obstacleLocations.get(j) % width){
                    matchesBelow.add(matches.get(i));
                    break;
                }
            }
        }
        return matchesBelow;
    }

    //gets the matches below obstacles and their relevant positions to move down if necessary
    private ArrayList<DataStore> manageMatchesUnderObstacles(ArrayList<Integer>belowObstacleMatches, int width, ArrayList<Integer>obstacleLocations, List<UrbieAnimation>objects, ArrayList<Point>tilePos, ArrayList<Integer>map){
        ArrayList<DataStore> dataStore = new ArrayList<>();

        for(int i = 0; i < belowObstacleMatches.size(); i++){
            int num = belowObstacleMatches.get(i) - width;
            int numStart = belowObstacleMatches.get(i);
            boolean blocked = false;

            while(!blocked){

                if(obstacleLocations.contains(num) || num < 0){
                    blocked = true;
                }
                else {
                    if(map.get(num) == 1){
                        int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                        if (urb_num > -1) {
                            if (objects.get(urb_num).getStatus() == NONE) {
                                DataStore d = new DataStore();
                                d.setPosition(tilePos.get(numStart));
                                numStart = num;
                                d.setElement(num);
                                dataStore.add(d);
                                int urbToSetOffScreen = findBitmapByMapLocation(objects, tilePos, belowObstacleMatches.get(i));
                                objects.get(urbToSetOffScreen).setActive(false);
                                objects.get(urbToSetOffScreen).setY(-200);
                            }
                        }
                    }

                }
                num = num - width;
            }

        }

        return dataStore;
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
            ArrayList<ArrayList<Integer>> blocked = getListOfBlockedRows(height, width, obstacles, locations);
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
                                int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                                if (urb_num > -1) {
                                    if (objects.get(urb_num).getStatus() == NONE) {
                                        position.add(tilePos.get(numStart));
                                        numStart = num;
                                        list.add(num);
                                        int urbToSetOffScreen = findBitmapByMapLocation(objects, tilePos, matchedList.get(i));
                                        objects.get(urbToSetOffScreen).setActive(false);
                                        objects.get(urbToSetOffScreen).setY(-200);
                                    }
                                }
                            }
                            num = num - width;
                        }
                    }
                    System.out.println("debug = " + findBitmapByMapLocation(objects, tilePos, matchedList.get(i)));
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
                        int urb_num = findBitmapByMapLocation(objects, tilePos, num);
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
                    int urb_num = findBitmapByMapLocation(objects, tilePos, num);
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
            uniqueArrayPointList(futurePos);
            newCoordinates.addAll(futurePos);
        }

        //maybe i need to identify glass tiles here that are zero and not included in any of the lists
        //this might not work if the destroy counter has not been updated

        //TODO: May need to do the same here with WOODEN TILES && CEMENT TILES
        for (int o = 0; o < obstacles.size(); o++) {
            if (obstacles.get(o).getStatus() == GLASS) {
                if (obstacles.get(o).getDestroyCounter() == 0) {
                    int m = findObjectByPosition(obstacles.get(o).getObstacle().getLocation(), objects);
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

        uniqueArrayIntegerList(list);
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
        int urb_num = findBitmapByMapLocation(objects, tiles, element);

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


    /********************************************************************************
     * Returns a list of three positions that make up a possible match
     ********************************************************************************/
    private ArrayList<Integer> possibleMoves(List<UrbieAnimation> objects, ArrayList<Integer> mapLevel, int horizontal_size) {

        ArrayList<Integer> possible_moves = new ArrayList<>();
        ArrayList<Integer> tempMoves;
        ArrayList<Integer> tempPlacement;

        tempMoves = MovesAvailable(mapLevel, horizontal_size, objects);

        for (int i = 0; i < tempMoves.size(); i = i + 2) {
            tempPlacement = findMatchingObjectNearby(tempMoves.get(i), tempMoves.get(i + 1), objects, horizontal_size, mapLevel);
            possible_moves.addAll(tempPlacement);
        }

        //System.out.println("possible moves " + possible_moves);
        return possible_moves;
    }


    /********************************************************************************
     * Return a collection of lists of possible matches, taken from
     * possibleMoves
     ********************************************************************************/
    public ArrayList<Integer> findRandomPotentialMatch(List<UrbieAnimation> objects, ArrayList<Integer> mapLevel, int horizontal_size, ArrayList<Obstacles> obstacles) {
        ArrayList<Integer> possibleMatches = possibleMoves(objects, mapLevel, horizontal_size);
        ArrayList<Integer> chosen = new ArrayList<>();
        Random random = new Random();

        possibleMatches.addAll(findObstaclesThatCanBeMatched(obstacles, mapLevel, horizontal_size, objects));

        if (!possibleMatches.isEmpty()) {
            int matchSize = possibleMatches.size() / 3;
            int rnd = random.nextInt(matchSize);
            int start = rnd * 3;

            for (int i = start; i < (start + 3); i++) {
                chosen.add(possibleMatches.get(i));
            }
        }

        //System.out.println("chosen " + chosen);
        return chosen;
    }

    /****************************************************************************
     * find and return urb locations in pairs
     ****************************************************************************/
    private ArrayList<Integer> findPairs(int element, ArrayList<Integer> mapLevel, int horizontalSize, List<UrbieAnimation> objects) {
        ArrayList<Integer> pairs = new ArrayList<>();

        int size = mapLevel.size();

        if (mapLevel.get(element) == 1) {   //occupied tile
            int pos1 = findObjectByPosition(element, objects);
            if (pos1 > -1) {
                if (objects.get(pos1).getStatus() == NONE && objects.get(pos1).getActive()) {

                    if (element < (size - horizontalSize)) {
                        if (mapLevel.get(element + horizontalSize) == 1) {
                            int pos2 = findObjectByPosition(element + horizontalSize, objects);
                            if (pos2 > -1) {
                                if (objects.get(pos2).getStatus() == NONE && objects.get(pos2).getActive()) {
                                    if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                                        pairs.add(pos1);
                                        pairs.add(pos2);
                                    }
                                }
                            }
                        }
                    }


                    if (element % horizontalSize < (horizontalSize - 1)) {
                        if (mapLevel.get(element + 1) == 1) {
                            int pos2 = findObjectByPosition(element + 1, objects);
                            if (pos2 > -1) {
                                if (objects.get(pos2).getStatus() == NONE && objects.get(pos2).getActive()) {
                                    if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                                        pairs.add(pos1);
                                        pairs.add(pos2);
                                    }
                                }
                            }
                        }
                    }

                    if (element % horizontalSize < (horizontalSize - 2)) {
                        if (mapLevel.get(element + 2) == 1) {
                            int pos2 = findObjectByPosition(element + 2, objects);
                            if (pos2 > -1) {
                                if (objects.get(pos2).getStatus() == NONE && objects.get(pos2).getActive()) {
                                    if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                                        pairs.add(pos1);
                                        pairs.add(pos2);
                                    }
                                }
                            }
                        }
                    }

                    if (element < (mapLevel.size() - (horizontalSize * 2))) {
                        if (mapLevel.get(element + (horizontalSize * 2)) == 1) {
                            int pos2 = findObjectByPosition(element + (horizontalSize * 2), objects);
                            if (pos2 > -1) {
                                if (objects.get(pos2).getStatus() == NONE && objects.get(pos2).getActive()) {
                                    if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                                        pairs.add(pos1);
                                        pairs.add(pos2);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        //System.out.println("pairs " + pairs);
        return pairs;
    }


    /****************************************************************************
     * This method is used to build a list of possible matches within
     * method possibleMoves
     ****************************************************************************/
    private ArrayList<Integer> MovesAvailable(ArrayList<Integer> mapLevel, int horizontalSize, List<UrbieAnimation> objects) {
        ArrayList<Integer> availableMoves = new ArrayList<>();
        ArrayList<Integer> tempArray;

        int size = mapLevel.size();
        for (int i = 0; i < size; i++) {
            tempArray = findPairs(i, mapLevel, horizontalSize, objects);

            if (!tempArray.isEmpty()) {
                availableMoves.addAll(tempArray);
            }
        }

        //System.out.println("pairs = " + availableMoves);
        return availableMoves;
    }


    /**********************************************************************
     * given a matching pair of ghosts, this method checks for another matching ghost within
     * the nearby location. If this exists then it is added to the returning arraylist
     **********************************************************************/
    private ArrayList<Integer> findMatchingObjectNearby(int et1, int et2, List<UrbieAnimation> objects, int horizontalSize, ArrayList<Integer> mapLevel) {
        ArrayList<Integer> arr1 = new ArrayList<>();
        int mapSize = mapLevel.size();
        int pos1 = objects.get(et1).getLocation();
        int pos2 = objects.get(et2).getLocation();
        Urbies.UrbieType type = objects.get(et1).getType();


        /****************************************************************************
         * et1 and et2 are adjacent to each other
         ****************************************************************************/
        if (pos2 == (pos1 + 1)) {

            if (pos1 % horizontalSize > 0 && pos1 < (mapSize - horizontalSize)) {
                if (mapLevel.get(pos1 + horizontalSize - 1) == 1) {
                    int pos3 = findObjectByPosition(pos1 + horizontalSize - 1, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 % horizontalSize > 0 && pos1 > horizontalSize) {
                if (mapLevel.get(pos1 - (horizontalSize + 1)) == 1) {
                    int pos3 = findObjectByPosition(pos1 - (horizontalSize + 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 % horizontalSize > 1) {
                if (mapLevel.get(pos1 - 2) == 1) {
                    int pos3 = findObjectByPosition(pos1 - 2, objects);
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 % horizontalSize < (horizontalSize - 1) && pos2 < (mapSize - horizontalSize)) {
                if (mapLevel.get(pos2 + (horizontalSize + 1)) == 1) {
                    int pos3 = findObjectByPosition(pos2 + (horizontalSize + 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 % horizontalSize < (horizontalSize - 2)) {
                if (mapLevel.get(pos2 + 2) == 1) {
                    int pos3 = findObjectByPosition(pos2 + 2, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 % horizontalSize < (horizontalSize - 1) && pos2 >= horizontalSize) {
                if (mapLevel.get(pos2 - (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(pos2 - (horizontalSize - 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /****************************************************************************
         * et1 and et2 are apart by 2 e.g (16, 18)
         ****************************************************************************/
        if (pos2 == (pos1 + 2)) {
            if (pos1 % horizontalSize > 0) {
                if (mapLevel.get(pos1 - 1) == 1) {
                    int pos3 = findObjectByPosition(pos1 - 1, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 % horizontalSize < horizontalSize - 1) {
                if (mapLevel.get(pos2 + 1) == 1) {
                    int pos3 = findObjectByPosition(pos2 + 1, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 >= horizontalSize && pos1 % horizontalSize < (horizontalSize - 1)) {
                if (mapLevel.get(pos1 - (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(pos1 - (horizontalSize - 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 < (mapSize - horizontalSize) && pos2 % horizontalSize > 0) {
                if (mapLevel.get(pos2 + (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(pos2 + (horizontalSize - 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        /****************************************************************************
         * et1 and et2 are apart by HORIZONTAL_SIZE * 2
         ****************************************************************************/
        if (pos2 == (pos1 + (horizontalSize * 2))) {
            if (pos2 % horizontalSize < (horizontalSize - 1)) {
                if (mapLevel.get(pos2 - (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(pos2 - (horizontalSize - 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(pos3);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 < (mapSize - horizontalSize)) {
                if (mapLevel.get(pos2 + horizontalSize) == 1) {
                    int pos3 = findObjectByPosition(pos2 + horizontalSize, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 >= horizontalSize) {
                if (mapLevel.get(pos1 - horizontalSize) == 1) {
                    int pos3 = findObjectByPosition(pos1 - horizontalSize, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 % horizontalSize > 0) {
                if (mapLevel.get(pos1 + (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(pos1 + (horizontalSize - 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(pos3);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /****************************************************************************
         * et1 and et2 are apart by HORIZONTAL_SIZE - 1**********************************************************************
         ****************************************************************************/
        if (pos2 == (pos1 + (horizontalSize - 1))) {
            if (pos1 % horizontalSize > 1) {
                if (mapLevel.get(pos1 - 2) == 1) {
                    int pos3 = findObjectByPosition(pos1 - 2, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 >= horizontalSize) {
                if (mapLevel.get(pos1 - horizontalSize) == 1) {
                    int pos3 = findObjectByPosition(pos1 - horizontalSize, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.get(pos3);
                                        arr1.get(et1);
                                        arr1.get(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 % horizontalSize > 0 && pos1 > horizontalSize) {
                if (mapLevel.get(pos1 - (horizontalSize + 1)) == 1) {
                    int pos3 = findObjectByPosition(pos1 - (horizontalSize + 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.get(pos3);
                                        arr1.get(et1);
                                        arr1.get(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 % horizontalSize < (horizontalSize - 2)) {
                if (mapLevel.get(pos2 + 2) == 1) {
                    int pos3 = findObjectByPosition(pos2 + 2, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.get(et1);
                                        arr1.get(et2);
                                        arr1.get(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 < (mapSize - horizontalSize)) {
                if (mapLevel.get(pos2 + horizontalSize) == 1) {
                    int pos3 = findObjectByPosition(pos2 + horizontalSize, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.get(et1);
                                        arr1.get(et2);
                                        arr1.get(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 < (mapSize - horizontalSize) && pos2 % horizontalSize < (horizontalSize - 1)) {
                if (mapLevel.get(pos2 + (horizontalSize + 1)) == 1) {
                    int pos3 = findObjectByPosition(pos2 + (horizontalSize + 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /****************************************************************************
         * et1 and et2 are apart by HORIZONTAL_SIZE + 1
         ****************************************************************************/
        if (pos2 == (pos1 + (horizontalSize + 1))) {
            if (pos1 >= horizontalSize) {
                if (mapLevel.get(pos1 - horizontalSize) == 1) {
                    int pos3 = findObjectByPosition(pos1 - horizontalSize, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.get(pos3);
                                        arr1.get(et1);
                                        arr1.get(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 > horizontalSize && pos1 % horizontalSize < (horizontalSize - 1)) {
                if (mapLevel.get(pos1 - (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(pos1 - (horizontalSize - 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 % horizontalSize < (horizontalSize - 2)) {
                if (mapLevel.get(pos1 + 2) == 1) {
                    int pos3 = findObjectByPosition(pos1 + 2, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(pos3);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 % horizontalSize > 1) {
                if (mapLevel.get(pos2 - 2) == 1) {
                    int pos3 = findObjectByPosition(pos2 - 2, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(pos3);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (pos2 < (mapSize - horizontalSize)) {
                if (mapLevel.get(pos2 + horizontalSize) == 1) {
                    int pos3 = findObjectByPosition(pos2 + horizontalSize, objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.get(et1);
                                        arr1.get(et2);
                                        arr1.get(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 < (mapSize - horizontalSize) && pos2 % horizontalSize > 0) {
                if (mapLevel.get(pos2 + (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(et2 + (horizontalSize - 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        /****************************************************************************
         * et1 and et2 are apart by HORIZONTAL_SIZE
         ****************************************************************************/
        if (pos2 == (pos1 + horizontalSize)) {
            if (pos1 % horizontalSize > 0 && pos1 > horizontalSize) {
                if (mapLevel.get(pos1 - (horizontalSize + 1)) == 1) {
                    int pos3 = findObjectByPosition(pos1 - (horizontalSize + 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 % horizontalSize < (horizontalSize - 1) && pos1 >= horizontalSize) {
                if (mapLevel.get(pos1 - (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(pos1 - (horizontalSize - 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 >= (horizontalSize * 2)) {
                if (mapLevel.get(pos1 - (horizontalSize * 2)) == 1) {
                    int pos3 = findObjectByPosition(pos1 - (horizontalSize * 2), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(pos3);
                                        arr1.add(et1);
                                        arr1.add(et2);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 < (mapSize - horizontalSize) && pos2 % horizontalSize < (horizontalSize - 1)) {
                if (mapLevel.get(pos2 + (horizontalSize + 1)) == 1) {
                    int pos3 = findObjectByPosition(pos2 + (horizontalSize + 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 < (mapSize - horizontalSize) && pos2 % horizontalSize > 0) {
                if (mapLevel.get(pos2 + (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(pos2 + (horizontalSize - 1), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (pos2 < (mapSize - (horizontalSize * 2))) {
                if (mapLevel.get(pos2 + (horizontalSize * 2)) == 1) {
                    int pos3 = findObjectByPosition(pos2 + (horizontalSize * 2), objects);
                    if(pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + horizontalSize, objects);
                                if(pos4 > -1) {
                                    if (objects.get(pos4).getStatus() == NONE && objects.get(pos4).getY() > 0) {
                                        arr1.add(et1);
                                        arr1.add(et2);
                                        arr1.add(pos3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return arr1;
    }


    /***************************************************************************
     * Get a list of elements that will be changed by the colour bomb
     ***************************************************************************/
    public ArrayList<Integer> collectElementsForColourBomb(ArrayList<Integer> map, int element, int width) {
        ArrayList<Integer> returnList = new ArrayList<>();
        int column = element % width;

        //centre elements
        if (element > width && element < (map.size() - width) && column > 0 && column < (width - 1)) {
            if (map.get(element - (width + 1)) == 1) returnList.add(element - (width + 1));
            if (map.get(element - width) == 1) returnList.add(element - width);
            if (map.get(element - (width - 1)) == 1) returnList.add(element - (width - 1));
            if (map.get(element - 1) == 1) returnList.add(element - 1);
            if (map.get(element) == 1) returnList.add(element);
            if (map.get(element + 1) == 1) returnList.add(element + 1);
            if (map.get(element + (width - 1)) == 1) returnList.add(element + (width - 1));
            if (map.get(element + width) == 1) returnList.add(element + width);
            if (map.get(element + (width + 1)) == 1) returnList.add(element + width + 1);
        }
        //on the last row  but not the first or last element on row
        else if (element > (map.size() - width) && column > 0 && column < (width - 1)) {
            if (map.get(element) == 1) returnList.add(element);
            if (map.get(element - 1) == 1) returnList.add(element - 1);
            if (map.get(element - width) == 1) returnList.add(element - width);
            if (map.get(element - (width + 1)) == 1) returnList.add(element - (width + 1));
            if (map.get(element - (width - 1)) == 1) returnList.add(element - (width - 1));
            if (map.get(element + 1) == 1) returnList.add(element + 1);
        }
        //on the first row but not first or last element on row
        else if ((element < width && column != 0) || (element < width && column != (width - 1))) {
            if (map.get(element) == 1) returnList.add(element);
            if (map.get(element - 1) == 1) returnList.add(element - 1);
            if (map.get(element + width) == 1) returnList.add(element + width);
            if (map.get(element + (width + 1)) == 1) returnList.add(element + (width + 1));
            if (map.get(element + (width - 1)) == 1) returnList.add(element + (width - 1));
            if (map.get(element + 1) == 1) returnList.add(element + 1);
        }
        //left column but not the first or last element in the column
        else if (column == 0 && element >= width && element < (map.size() - width)) {
            if (map.get(element) == 1) returnList.add(element);
            if (map.get(element + 1) == 1) returnList.add(element + 1);
            if (map.get(element + width) == 1) returnList.add(element + width);
            if (map.get(element + (width + 1)) == 1) returnList.add(element + (width + 1));
            if (map.get(element - (width - 1)) == 1) returnList.add(element - (width - 1));
            if (map.get(element - width) == 1) returnList.add(element - width);
        }
        //the first element
        else if (element == 0) {
            if (map.get(element) == 1) returnList.add(element);
            if (map.get(element + 1) == 1) returnList.add(element + 1);
            if (map.get(element + width) == 1) returnList.add(element + width);
            if (map.get(element + (width + 1)) == 1) returnList.add(element + (width + 1));
        } else if (element == (map.size() - width)) {
            if (map.get(element) == 1) returnList.add(element);
            if (map.get(element + 1) == 1) returnList.add(element + 1);
            if (map.get(element - width) == 1) returnList.add(element - width);
            if (map.get(element - (width - 1)) == 1) returnList.add(element - (width - 1));
        }
        //right column but not the first or last element in the column
        else if (column == (width - 1) && element > width && element < (map.size() - width)) {
            if (map.get(element) == 1) returnList.add(element);
            if (map.get(element - 1) == 1) returnList.add(element - 1);
            if (map.get(element + width) == 1) returnList.add(element + width);
            if (map.get(element + (width - 1)) == 1) returnList.add(element + (width - 1));
            if (map.get(element - (width + 1)) == 1) returnList.add(element - (width + 1));
            if (map.get(element - width) == 1) returnList.add(element - width);
        } else if (element == width - 1) {
            if (map.get(element) == 1) returnList.add(element);
            if (map.get(element - 1) == 1) returnList.add(element - 1);
            if (map.get(element + width) == 1) returnList.add(element + width);
            if (map.get(element + (width - 1)) == 1) returnList.add(element + (width - 1));
        } else if (element == (map.size() - 1)) {
            if (map.get(element) == 1) returnList.add(element);
            if (map.get(element - 1) == 1) returnList.add(element - 1);
            if (map.get(element - width) == 1) returnList.add(element - width);
            if (map.get(element - (width + 1)) == 1) returnList.add(element - (width + 1));
        }
        return returnList;
    }

    /******************************************************************************************
     * If a match contains a obstacle that is visible then deduct the counter
     * If the counter is at zero then change the animation
     ******************************************************************************************/
    public void obstacleContainedInMatch(ArrayList<Obstacles> obstacles, ArrayList<Integer> matches) {
        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (matches.contains(obstacles.get(i).getObstacle().getLocation())) {
                    obstacles.get(i).deductDestroyCounter();
                    if (obstacles.get(i).getDestroyCounter() == 0) {
                        if (obstacles.get(i).getStatus() == GLASS) {
                            obstacles.get(i).getObstacle().changeBitmapProperties(Assets.glassTileAnim, 30, 6, 4000, false, true);
                        }
                    }
                }
            }
        }
    }

    /******************************************************************************************
     * Check against the matched list to see if matches are directly above /
     * directly below or either side of the obstacle.
     * <p>
     * The result needs to be compared against the BitmapAnimation location
     * feature e.g. if(obstacleList.contains(frozenTiles.get(i).getLocation())){}
     *******************************************************************************************/
    public int isMatchNextToObstacle(int tilePosition, ArrayList<Integer> matchList, int mapSize, int horizontalSize) {
        int obstacleHit = -1;

        if (tilePosition < mapSize - horizontalSize) {
            if (matchList.contains(tilePosition + horizontalSize)) {
                obstacleHit = tilePosition;
            }
        }
        if (tilePosition > horizontalSize) {
            if (matchList.contains(tilePosition - horizontalSize)) {
                obstacleHit = tilePosition;
            }
        }

        if (tilePosition % horizontalSize > 0) {
            if (matchList.contains(tilePosition - 1)) {
                obstacleHit = tilePosition;
            }
        }
        if (tilePosition % horizontalSize < horizontalSize - 1) {
            if (matchList.contains(tilePosition + 1)) {
                obstacleHit = tilePosition;
            }
        }
        return obstacleHit;
    }

    /**********************************************************************
     Returns a list of triples of potential matches that involve obstacles
     **********************************************************************/
    private ArrayList<Integer> findObstaclesThatCanBeMatched(ArrayList<Obstacles> obstacles, ArrayList<Integer> mapLevel, int horizontalSize, List<UrbieAnimation> objects) {
        ArrayList<Integer> pairs;
        ArrayList<Integer>   potentialForMatches = new ArrayList<>();
        ArrayList<Integer> matchOfThree = new ArrayList<>();
        ArrayList<Integer> urbMatchOfThree = new ArrayList<>();

        for (int i = 0; i < obstacles.size(); i++) {
            if (obstacles.get(i).getStatus() != CEMENT || obstacles.get(i).getStatus() == WOODEN) {
                pairs = findObstaclePairs(obstacles.get(i).getObstacle().getLocation(), mapLevel, horizontalSize, objects);

                if (!pairs.isEmpty()) {
                    potentialForMatches.addAll(pairs);
                }
            }

        }

        for (int i = 0; i < potentialForMatches.size(); i = i + 2) {
            matchOfThree.addAll(canObstaclePairResultInMatch(potentialForMatches.get(i), potentialForMatches.get(i + 1), objects, horizontalSize, mapLevel));
        }

        //The result needs to be converted into urb positions
        for (int i = 0; i < matchOfThree.size(); i++) {
            urbMatchOfThree.add(findObjectByPosition(matchOfThree.get(i), objects));
        }
        //System.out.println("MatchOfThreeObstacles = " + matchOfThree + " UrbMatchOfThree = " + urbMatchOfThree);
        return urbMatchOfThree;
    }

    /**********************************************************************
     Generates a list of obstacle matches based on a matched pair of objects
     **********************************************************************/
    private ArrayList<Integer> canObstaclePairResultInMatch(int urbPos1, int urbPos2, List<UrbieAnimation> objects, int horizontalSize, ArrayList<Integer> mapLevel) {
        ArrayList<Integer> listsOfThree = new ArrayList<>();
        int mapSize = mapLevel.size();
        int pos1, pos2;

        if (urbPos2 > urbPos1) {
            pos1 = objects.get(urbPos1).getLocation();
            pos2 = objects.get(urbPos2).getLocation();
        } else {
            pos1 = objects.get(urbPos2).getLocation();
            pos2 = objects.get(urbPos1).getLocation();
        }
        Urbies.UrbieType type = objects.get(urbPos1).getType();

        //e.g 17,18
        if (pos2 == (pos1 + 1)) {
            if (pos2 % horizontalSize < (horizontalSize - 1)) {
                //make sure the tile which will be occupied  is currently valid and not an obstacle
                if (mapLevel.get(pos2 + 1) == 1) {
                    int urb = findObjectByPosition(pos2 + 1, objects);
                    if (objects.get(urb).getStatus() == NONE) {

                        //if(pos2 - horizontalSize - 1 == type
                        if (pos2 > horizontalSize) {
                            if (mapLevel.get(pos2 - horizontalSize - 1) == 1) {
                                int pos3 = findObjectByPosition(pos2 - (horizontalSize - 1), objects);
                                if (mapLevel.get(pos2 - (horizontalSize - 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos2 - (horizontalSize - 1));
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                }
                            }
                        }
                        //pos2 + horizontalSize  + 1 == type
                        if (pos2 < (mapSize - horizontalSize) && (pos2 % horizontalSize < horizontalSize - 1)) {
                            if (mapLevel.get(pos2 + (horizontalSize + 1)) == 1) {
                                int pos3 = findObjectByPosition(pos2 + (horizontalSize + 1), objects);
                                if (mapLevel.get(pos2 + (horizontalSize + 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                    listsOfThree.add(pos2 + (horizontalSize + 1));
                                }
                            }
                        }
                        //pos2 + 2 == type
                        if (pos2 % horizontalSize < (horizontalSize - 2)) {
                            if (mapLevel.get(pos2 + 2) == 1) {
                                int pos3 = findObjectByPosition(pos2 + 2, objects);
                                if (mapLevel.get(pos2 + 2) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                    listsOfThree.add(pos2 + 2);
                                }
                            }
                        }
                    }
                }
            }
            if (pos1 % horizontalSize > 0) {
                if (mapLevel.get(pos1 - 1) == 1) {
                    int urb = findObjectByPosition(pos1 - 1, objects);
                    if (objects.get(urb).getStatus() == NONE) {
                        //continue with check

                        //if(pos1 - horizontalSize + 1 == type
                        if (pos1 > horizontalSize && pos1 % horizontalSize > 0) {
                            if (mapLevel.get(pos1 - (horizontalSize + 1)) == 1) {
                                int pos3 = findObjectByPosition(pos1 - (horizontalSize + 1), objects);
                                if (mapLevel.get(pos1 - (horizontalSize + 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1 - (horizontalSize + 1));
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                }
                            }
                        }
                        //pos1 + horizontalSize  - 1 == type
                        if (pos1 < (mapSize - horizontalSize) && (pos1 % horizontalSize > 0)) {
                            if (mapLevel.get(pos1 + (horizontalSize - 1)) == 1) {
                                int pos3 = findObjectByPosition(pos1 + (horizontalSize - 1), objects);
                                if (mapLevel.get(pos1 + (horizontalSize + 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                    listsOfThree.add(pos1 + (horizontalSize - 1));
                                }
                            }
                        }
                        //pos1 - 2 == type
                        if (pos1 % horizontalSize >= 2) {
                            if (mapLevel.get(pos1 - 2) == 1) {
                                int pos3 = findObjectByPosition(pos1 - 2, objects);
                                if (mapLevel.get(pos1 - 2) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1 - 2);
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                }
                            }
                        }
                    }
                }
            }
        }
        //16,18
        else if (pos2 == (pos1 + 2)) {
            if (pos1 > horizontalSize) {
                if (mapLevel.get(pos1 + 1) == 1) {
                    int urb = findObjectByPosition(pos1 + 1, objects);
                    if (objects.get(urb).getStatus() == NONE) {

                        if (pos1 % horizontalSize < horizontalSize - 1) {
                            if (mapLevel.get(pos1 - (horizontalSize - 1)) == 1) {
                                int pos3 = findObjectByPosition(pos1 - (horizontalSize - 1), objects);
                                if (mapLevel.get(pos1 - (horizontalSize - 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1 - (horizontalSize - 1));
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                }
                            }
                        }
                        if (pos1 < mapSize - horizontalSize) {
                            if (mapLevel.get(pos1 + (horizontalSize + 1)) == 1) {
                                int pos3 = findObjectByPosition(pos1 + (horizontalSize + 1), objects);
                                if (mapLevel.get(pos1 + (horizontalSize + 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                    listsOfThree.add(pos1 + (horizontalSize + 1));
                                }
                            }
                        }
                    }
                }
            }
            //e.g. 0,5
        } else if (pos2 == (pos1 + horizontalSize)) {
            if (pos2 < mapSize - horizontalSize) {
                if (mapLevel.get(pos2 + horizontalSize) == 1) {
                    int urb = findObjectByPosition(pos2 + horizontalSize, objects);
                    if (objects.get(urb).getStatus() == NONE) {

                        if (pos2 % horizontalSize > 0) {
                            if (mapLevel.get(pos2 + (horizontalSize - 1)) == 1) {
                                int pos3 = findObjectByPosition(pos2 + (horizontalSize - 1), objects);
                                if (mapLevel.get(pos2 + (horizontalSize - 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                    listsOfThree.add(pos2 + (horizontalSize - 1));
                                }
                            }
                        }

                        if (pos2 % horizontalSize < horizontalSize - 1) {
                            if (mapLevel.get(pos2 + (horizontalSize + 1)) == 1) {
                                int pos3 = findObjectByPosition(pos2 + (horizontalSize + 1), objects);
                                if (mapLevel.get(pos2 + (horizontalSize + 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                    listsOfThree.add(pos2 + (horizontalSize + 1));
                                }
                            }
                        }


                        if (pos2 < mapSize - (horizontalSize * 2)) {
                            if (mapLevel.get(pos2 + (horizontalSize * 2)) == 1) {
                                int pos3 = findObjectByPosition(pos2 + (horizontalSize * 2), objects);
                                if (mapLevel.get(pos2 + (horizontalSize * 2)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                    listsOfThree.add(pos2 + (horizontalSize * 2));
                                }
                            }
                        }
                    }
                }
            }

            if (pos1 > horizontalSize) {
                if (mapLevel.get(pos1 - horizontalSize) == 1) {
                    int urb = findObjectByPosition(pos1 - horizontalSize, objects);
                    if (objects.get(urb).getStatus() == NONE) {

                        if (pos1 % horizontalSize > 0) {
                            if (mapLevel.get(pos1 - (horizontalSize + 1)) == 1) {
                                int pos3 = findObjectByPosition(pos1 - (horizontalSize + 1), objects);
                                if (mapLevel.get(pos1 - (horizontalSize + 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1 - (horizontalSize + 1));
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                }
                            }
                        }

                        if (pos1 % horizontalSize < horizontalSize - 1) {
                            if (mapLevel.get(pos1 - (horizontalSize - 1)) == 1) {
                                int pos3 = findObjectByPosition(pos1 - (horizontalSize - 1), objects);
                                if (mapLevel.get(pos1 - (horizontalSize - 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1 - (horizontalSize - 1));
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                }
                            }
                        }

                        if (pos1 >= (horizontalSize * 2)) {
                            if (mapLevel.get(pos1 - (horizontalSize * 2)) == 1) {
                                int pos3 = findObjectByPosition(pos1 - (horizontalSize * 2), objects);
                                if (mapLevel.get(pos1 - (horizontalSize * 2)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                    listsOfThree.add(pos1 - (horizontalSize * 2));
                                    listsOfThree.add(pos1);
                                    listsOfThree.add(pos2);
                                }
                            }
                        }
                    }
                }
            }
            //e.g 0, 10
        } else if (pos2 == (pos1 + (horizontalSize * 2))) {
            if (mapLevel.get(pos2 - horizontalSize) == 1) {
                int urb = findObjectByPosition(pos2 - horizontalSize, objects);
                if (objects.get(urb).getStatus() == NONE) {

                    if (pos2 % horizontalSize > 0) {
                        if (mapLevel.get(pos2 - (horizontalSize + 1)) == 1) {
                            int pos3 = findObjectByPosition(pos2 - (horizontalSize + 1), objects);
                            if (mapLevel.get(pos2 - (horizontalSize + 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                listsOfThree.add(pos1);
                                listsOfThree.add(pos2 - (horizontalSize + 1));
                                listsOfThree.add(pos2);
                            }
                        }
                    }

                    if (pos2 % horizontalSize < (horizontalSize - 1)) {
                        if (mapLevel.get(pos2 - (horizontalSize - 1)) == 1) {
                            int pos3 = findObjectByPosition(pos2 - (horizontalSize - 1), objects);
                            if (mapLevel.get(pos2 - (horizontalSize - 1)) == 1 && objects.get(pos3).getStatus() == NONE && objects.get(pos3).getType() == type) {
                                listsOfThree.add(pos1);
                                listsOfThree.add(pos2 - (horizontalSize - 1));
                                listsOfThree.add(pos2);
                            }
                        }
                    }
                }
            }
        }

        return listsOfThree;
    }

    /**********************************************************************
     Generates a list of matched pairs where either or both are contained within
     an obstacle
     **********************************************************************/
    private ArrayList<Integer> findObstaclePairs(int element, ArrayList<Integer> mapLevel, int horizontalSize, List<UrbieAnimation> objects) {
        ArrayList<Integer> pairs = new ArrayList<>();
        int size = mapLevel.size();

        //TODO: Need to make sure that the pos2 is NOT of type CEMENT or WOOD
        if (mapLevel.get(element) == 1) {   //occupied tile
            int pos1 = findObjectByPosition(element, objects);

            if (element < (size - horizontalSize)) {
                if (mapLevel.get(element + horizontalSize) == 1) {
                    int pos2 = findObjectByPosition(element + horizontalSize, objects);
                    if (objects.get(pos2).getStatus() != WOODEN || objects.get(pos2).getStatus() != CEMENT) {
                        if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                            pairs.add(pos1);
                            pairs.add(pos2);
                        }
                    }
                }
            }

            if (element > horizontalSize) {
                if (mapLevel.get(element - horizontalSize) == 1) {
                    int pos2 = findObjectByPosition(element - horizontalSize, objects);
                    if (objects.get(pos2).getStatus() != WOODEN || objects.get(pos2).getStatus() != CEMENT) {
                        if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                            pairs.add(pos1);
                            pairs.add(pos2);
                        }
                    }
                }
            }

            if (element % horizontalSize < (horizontalSize - 1)) {
                if (mapLevel.get(element + 1) == 1) {
                    int pos2 = findObjectByPosition(element + 1, objects);
                    if (objects.get(pos2).getStatus() != WOODEN || objects.get(pos2).getStatus() != CEMENT) {
                        if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                            pairs.add(pos1);
                            pairs.add(pos2);
                        }
                    }
                }
            }

            if (element % horizontalSize > 0) {
                if (mapLevel.get(element - 1) == 1) {
                    int pos2 = findObjectByPosition(element - 1, objects);
                    if (objects.get(pos2).getStatus() != WOODEN || objects.get(pos2).getStatus() != CEMENT) {
                        if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                            pairs.add(pos1);
                            pairs.add(pos2);
                            //System.out.println(pairs + "4");
                        }
                    }
                }
            }

            if (element % horizontalSize < (horizontalSize - 2)) {
                if (mapLevel.get(element + 2) == 1) {
                    int pos2 = findObjectByPosition(element + 2, objects);
                    if (objects.get(pos2).getStatus() != WOODEN || objects.get(pos2).getStatus() != CEMENT) {
                        if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                            pairs.add(pos1);
                            pairs.add(pos2);
                        }
                    }
                }
            }

            if (element % horizontalSize >= 2) {
                if (mapLevel.get(element - 2) == 1) {
                    int pos2 = findObjectByPosition(element - 2, objects);
                    if (objects.get(pos2).getStatus() != WOODEN || objects.get(pos2).getStatus() != CEMENT) {
                        if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                            pairs.add(pos1);
                            pairs.add(pos2);
                        }
                    }
                }
            }

            if (element < (mapLevel.size() - (horizontalSize * 2))) {
                if (mapLevel.get(element + (horizontalSize * 2)) == 1) {
                    int pos2 = findObjectByPosition(element + (horizontalSize * 2), objects);
                    if (objects.get(pos2).getStatus() != WOODEN || objects.get(pos2).getStatus() != CEMENT) {
                        if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                            pairs.add(pos1);
                            pairs.add(pos2);
                        }
                    }
                }
            }

            if (element >= (horizontalSize * 2)) {
                if (mapLevel.get(element - (horizontalSize * 2)) == 1) {
                    int pos2 = findObjectByPosition(element - (horizontalSize * 2), objects);
                    if (objects.get(pos2).getStatus() != WOODEN || objects.get(pos2).getStatus() != CEMENT) {
                        if (objects.get(pos1).getType() == objects.get(pos2).getType()) {
                            pairs.add(pos1);
                            pairs.add(pos2);
                        }
                    }
                }
            }
        }
        return pairs;
    }

    /**********************************************************************
     Returns a list of tilemap locations if the row is filled with solid obstacles e.g. Visible = INVISIBLE
     rowStartLocation is the location in the tilemap to start the check
     horizontalSize is the width of the tilemap
     obstacles is the Arraylist of the obstacles in the level
     tilemap is the Arraylist of 1, 0 which determines the valid tiles
     **********************************************************************/
    private ArrayList<Integer> isRowBlocked(int rowStartLocation, int horizontalSize, ArrayList<Obstacles> obstacles, ArrayList<Integer> tilemap) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> locations = new ArrayList<>();
        int counter = 0;

        for (int i = 0; i < obstacles.size(); i++) {
            if (!obstacles.get(i).isVisible()) {
                locations.add(obstacles.get(i).getObstacle().getLocation());
            } else locations.add(-1);
        }

        int column = rowStartLocation % horizontalSize;

        for (int i = column; i < horizontalSize; i++) {
            if ((tilemap.get(rowStartLocation) == 1 && locations.contains(rowStartLocation))) {
                counter++;
                result.add(rowStartLocation);
            } else if (tilemap.get(rowStartLocation) == 0) {
                counter++;
                result.add(-1);
            }
            rowStartLocation++;
        }

        if (counter != horizontalSize) {
            result.clear();
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
    private ArrayList isColumnBlocked(int columnStartLocation, int horizontalSize, ArrayList<Obstacles> obstacles, ArrayList<Integer> tilemap) {
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

    /**********************************************************************
     Returns a list of all blocked rows
     **********************************************************************/
    public ArrayList<ArrayList<Integer>> getListOfBlockedRows(int verticalSize, int horizontalSize, ArrayList<Obstacles> obstacles, ArrayList<Integer> tilemap) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> temp;
        int start = 0;

        for (int i = 0; i < verticalSize; i++) {
            temp = isRowBlocked(start, horizontalSize, obstacles, tilemap);
            if (!temp.isEmpty()) {
                result.add(temp);
            }
            start = start + horizontalSize;
        }
        return result;
    }

    /**********************************************************************
     Returns a list of all blocked columns
     **********************************************************************/
    public ArrayList<ArrayList<Integer>> getListOfBlockedColumns(int verticalSize, int horizontalSize, ArrayList<Obstacles> obstacles, ArrayList<Integer> tilemap) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList temp;
        int start = 0;

        for (int i = 0; i < horizontalSize; i++) {
            temp = isColumnBlocked(start, horizontalSize, obstacles, tilemap);
            if (!temp.isEmpty()) {
                result.add(temp);
            }
            start = start + verticalSize;
        }
        return result;
    }
}
