package com.development.knowledgehut.urbies.Behaviours;


import android.graphics.Bitmap;
import android.graphics.Point;

import com.development.knowledgehut.urbies.DrawableObjects.UrbieAnimation;
import com.development.knowledgehut.urbies.Objects.DataStore;
import com.development.knowledgehut.urbies.Objects.MatchedDetails;
import com.development.knowledgehut.urbies.Objects.ObjectPathCreator;
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
            if (index > -1) {
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
            if (objects.get(i).getActive()) {
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

        ArrayList<Integer> matchesThatCanBeIntersectingElement = new ArrayList<>();
        for (int i = 0; i < matchedDetails.getReturnedMatches().size(); i++) {
            matchesThatCanBeIntersectingElement.add(matchedDetails.getReturnedMatches().get(i));
        }
        //get a list of matches that can be valid intersecting element, without having to remove matches
        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (matchesThatCanBeIntersectingElement.contains(obstacles.get(i).getLocation())) {
                    int index = matchesThatCanBeIntersectingElement.indexOf(obstacles.get(i).getLocation());
                    matchesThatCanBeIntersectingElement.remove(index);
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
                    if (matchesThatCanBeIntersectingElement.size() > 2) {
                        matchedDetails.setIntersecting_element(matchesThatCanBeIntersectingElement.get(2));
                    } else
                        matchedDetails.setIntersecting_element(matchesThatCanBeIntersectingElement.get(0));
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
     * Find the location of a specific position in the list of tile positions
     ********************************************************************************/
    private int findLocationByPosition(Point position, ArrayList<Point> tilePos) {
        int found = -1;

        for (int i = 0; i < tilePos.size(); i++) {
            if (tilePos.get(i) == position) {
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


    /********************************************************************************
     * returns a list of empty tile locations, where the tile is active
     ********************************************************************************/
    private ArrayList<Integer> getEmptyTiles(ArrayList<Integer> map, ArrayList<Point> tilePos, List<UrbieAnimation> objects) {
        ArrayList<Integer> emptyTiles = new ArrayList<>();

        for (int i = 0; i < tilePos.size(); i++) {
            if (map.get(i) == 1) {
                boolean found = false;

                for (int j = 0; j < objects.size(); j++) {
                    if (tilePos.get(i).equals(objects.get(j).getPosition())) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    emptyTiles.add(i);
                }
            }
        }

        return emptyTiles;
    }


    //TODO: 1. If I have urbs removed below obstacles and the row isn't blocked I need to identify if there
    //TODO:  - a clear path for the urbs e.g. 12 is the entrance matched list was 16, 17, 18. There is nothing
    //TODO:  - in the way. If there are urbs in the way then I will need to move then before any of the
    //TODO:  - remaining urbs are moved into position.
    //I HAVE IDENTIFIED WHEN THERE IS A PATHWAY AVAILABLE WHEN THERE ARE EMPTY TILES

    //TODO: 2. Then I need to identify whether there are any new urbs that will need to have it's path manipulated
    //TODO:  - due to avoiding passing through blocked obstacles.
    /*
        2. Get path of replacement objects
        I should only be concerned if current element y position is < freeslot y and will move to > freeslot y. This method
        will add points to the findLine method so that the objects do not pass through the cement/wood


        psuedo code

        function amendPath(ArrayList<Integer>elements, ArrayList<Point>positions, ArrayList<Integer>freeslots,
        List<UrbieAnimation>objects, ArrayList<Point>tilePos){
            for(int i = 0; i < elements.size(); i++){
                if(objects.get(elements.get(i)).getY() < tilePos.get(freeslot.get(0)).y && position.get(i).getY() >
                 tilePos.get(freeslot.get(0)).y){
                 //what is the shortest x position distance, if more than one, if more than one and distance the same just
                 //get the first one
                 //the adjustment is:
                 element.get(i).x -> freeslot.get(?).x,
                 element.get(i).y -> freeslot.get(?).y,
                 element.get(i).y -> position.get(i).get.y
                 element.get(i).x -> position.get(i).get.x
                 }
            }
        }

     */

    /*************************************************
     to act as a replacement for listOfObjectsToMoveDown
     *************************************************/
    public ArrayList<DataStore> separateTheMadness(
            List<UrbieAnimation> objects, ArrayList<Integer> matches, ArrayList<Obstacles> obstacles,
            int width, ArrayList<Point> tilePos, ArrayList<Integer> map,
            ArrayList<Integer> matchesOffScreen, ArrayList<DataStore> future,
            ArrayList<Integer> entrance
    ) {
        ArrayList<Integer> obstacleLocations = new ArrayList<>();
        ArrayList<Integer> glassLocations = new ArrayList<>();
        ArrayList<Integer> nearMatchObstacles;
        ArrayList<Integer> obstacleIndexWhereZero = new ArrayList<>();
        ArrayList<DataStore> store = new ArrayList<>();
        ArrayList<Integer> moveDownList = new ArrayList<>();
        ArrayList<Point> positions = new ArrayList<>();
        ArrayList<Point> leftOverPositions = new ArrayList<>();
        ArrayList<DataStore> belowMatchesToMoveDown = new ArrayList<>();
        ArrayList<Integer> emptyTiles;


        ////////////////////////////////////////////////////////////////////////////////////////////
        //!!get a list of invisible obstacle locations e.g. WOOD, CEMENT and also GLASS obstacles
        ////////////////////////////////////////////////////////////////////////////////////////////
        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (!obstacles.get(i).isVisible() && obstacles.get(i).getDestroyCounter() > 0) {
                    obstacleLocations.add(obstacles.get(i).getLocation());
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
                            obstacleIndexWhereZero.add(i); //doesn't this get affected when you remove an obstacle?
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
                    System.out.println("Obstacles " + i + " " + obstacles.get(i).getLocation());
                }

                System.out.println("ObstacleIndexWhereZero = " + obstacleIndexWhereZero);
                // System.out.println("STM nearMatchObstacles = " + nearMatchObstacles);
            }
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
                    //  System.out.println("STM matchesBelow = " + matchesBelow);
                    matchesOffScreen.addAll(matchesBelow);
                }

                //stores a list of objects to move down that are below blocked obstacles
                belowMatchesToMoveDown = manageMatchesUnderObstacles(matchesBelow, width, obstacleLocations, objects, tilePos, map);
                // System.out.println("STM belowMatchesToMoveDown = " + belowMatchesToMoveDown);
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
        ////////////////////////////////////////////////////////////////////////////////////////////
        //4. Start looping through matches, considering any broken obstacles and empty tiles
        ////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Point> tempPosition = new ArrayList<>();
        ArrayList<Point> emptyTilePositions = new ArrayList<>();
        boolean emptyTilesReplaced = false;
        boolean tilesComplete = false;

        //Are there any empty tiles, only happens with invisible blocks
        emptyTiles = getEmptyTiles(map, tilePos, objects);
        System.out.println("Empty tiles = " + emptyTiles);

        for (int i = 0; i < matches.size(); i++) {
            int startingPoint = matches.get(i);

            if (!obstacleIndexWhereZero.isEmpty() && !emptyTiles.isEmpty()) {
                //reset the startingPoint where the broken tile exists
                for (int j = 0; j < obstacleIndexWhereZero.size(); j++) {
                    if (matches.get(i) % width == obstacles.get(obstacleIndexWhereZero.get(j)).getLocation() % width && obstacles.get(obstacleIndexWhereZero.get(j)).getLocation() > matches.get(i)) {
                        startingPoint = obstacles.get(obstacleIndexWhereZero.get(j)).getLocation();

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

            if (!obstacleIndexWhereZero.isEmpty() && moveDownList.isEmpty()) {
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
            obstacleIndexWhereZero.clear();
        }

        //remove any leftOverPositions that are also in positions
        for (int i = leftOverPositions.size() - 1; i >= 0; i--) {
            if (positions.contains(leftOverPositions.get(i))) {
                leftOverPositions.remove(i);
            }
        }

        for (int i = 0; i < moveDownList.size(); i++) {
            DataStore d = new DataStore();
            d.setElement(moveDownList.get(i));
            d.setPosition(positions.get(i));
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
            System.out.println("store " + i + " = " + " " + store.get(i).getElement() + ", " + store.get(i).getPosition());
        }
        return store;//belowMatchesToMoveDown;

        //urbs not moving down due to matches below cement, once the blocked row has been opened then all urbs should
        //be able to be positioned under cement
    }


    /**************************************************************************************************
     returns a list of obstacles that are damaged as a result of the match
     (this does not include obstacles that are at a zero counter
     **************************************************************************************************/
    private ArrayList<Integer> getActualNearMatchesThatAreObstacles(ArrayList<Integer> matches, ArrayList<Integer> obstacleLocations, int width, ArrayList<Integer> map) {
        ArrayList<Integer> nearMatchObstacles = new ArrayList<>();
        ArrayList<Integer> nearMatches = new ArrayList<>();

        //gets a list of values nearby the matches (above, below and either side)
        for (int i = 0; i < matches.size(); i++) {
            if ((matches.get(i) > 0) && (map.get(matches.get(i) - 1) == 1) && ((matches.get(i) / width) == (matches.get(i) - 1) / width)) { //on the same row
                if (!matches.contains(matches.get(i) - 1)) {
                    nearMatches.add(matches.get(i) - 1);
                }
            }
            if ((matches.get(i) + 1 < map.size()) && (map.get(matches.get(i) + 1) == 1) && ((matches.get(i) / width) == (matches.get(i) + 1) / width)) { //on same row
                if (!matches.contains(matches.get(i) + 1)) {
                    nearMatches.add(matches.get(i) + 1);
                }
            }
            if ((matches.get(i) - width > 0) && (map.get(matches.get(i) - width) == 1) && ((matches.get(i) % width) == (matches.get(i) - width) % width)) { //on the same column
                if (!matches.contains(matches.get(i) - width)) {
                    nearMatches.add(matches.get(i) - width);
                }
            }
            if ((matches.get(i) + width < map.size()) && (map.get(matches.get(i) + width) == 1) && ((matches.get(i) % width) == (matches.get(i) + width) % width)) { //on the same column
                if (!matches.contains(matches.get(i) + width)) {
                    nearMatches.add(matches.get(i) + width);
                }
            }
        }

        //check against the near matches whether any of these values are obstacles, if so store them
        for (int i = 0; i < nearMatches.size(); i++) {
            if (obstacleLocations.contains(nearMatches.get(i))) {
                nearMatchObstacles.add(nearMatches.get(i));
            }
        }

        return nearMatchObstacles;
    }


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
                            }
                        }
                    }

                }
                num = num - width;
            }

        }

        return dataStore;
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

        //et1 and et2 are adjacent to each other
        if (pos2 == (pos1 + 1)) {

            if (pos1 % horizontalSize > 0 && pos1 < (mapSize - horizontalSize)) {
                if (mapLevel.get(pos1 + horizontalSize - 1) == 1) {
                    int pos3 = findObjectByPosition(pos1 + horizontalSize - 1, objects);
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if (pos4 > -1) {
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
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if (pos4 > -1) {
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

        //et1 and et2 are apart by 2 e.g (16, 18)
        if (pos2 == (pos1 + 2)) {
            if (pos1 % horizontalSize > 0) {
                if (mapLevel.get(pos1 - 1) == 1) {
                    int pos3 = findObjectByPosition(pos1 - 1, objects);
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if (pos4 > -1) {
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

        // et1 and et2 are apart by HORIZONTAL_SIZE * 2
        if (pos2 == (pos1 + (horizontalSize * 2))) {
            if (pos2 % horizontalSize < (horizontalSize - 1)) {
                if (mapLevel.get(pos2 - (horizontalSize - 1)) == 1) {
                    int pos3 = findObjectByPosition(pos2 - (horizontalSize - 1), objects);
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - horizontalSize, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - horizontalSize, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - horizontalSize, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + horizontalSize, objects);
                                if (pos4 > -1) {
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

        //et1 and et2 are apart by HORIZONTAL_SIZE - 1
        if (pos2 == (pos1 + (horizontalSize - 1))) {
            if (pos1 % horizontalSize > 1) {
                if (mapLevel.get(pos1 - 2) == 1) {
                    int pos3 = findObjectByPosition(pos1 - 2, objects);
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + 1, objects);
                                if (pos4 > -1) {
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

        //et1 and et2 are apart by HORIZONTAL_SIZE + 1
        if (pos2 == (pos1 + (horizontalSize + 1))) {
            if (pos1 >= horizontalSize) {
                if (mapLevel.get(pos1 - horizontalSize) == 1) {
                    int pos3 = findObjectByPosition(pos1 - horizontalSize, objects);
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 + 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 + 1, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 - 1) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 - 1, objects);
                                if (pos4 > -1) {
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

        //et1 and et2 are apart by HORIZONTAL_SIZE
        if (pos2 == (pos1 + horizontalSize)) {
            if (pos1 % horizontalSize > 0 && pos1 > horizontalSize) {
                if (mapLevel.get(pos1 - (horizontalSize + 1)) == 1) {
                    int pos3 = findObjectByPosition(pos1 - (horizontalSize + 1), objects);
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - horizontalSize, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - horizontalSize, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos1 - horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos1 - horizontalSize, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + horizontalSize, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + horizontalSize, objects);
                                if (pos4 > -1) {
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
                    if (pos3 > -1) {
                        if (objects.get(pos3).getType() == type && mapLevel.get(pos2 + horizontalSize) == 1 && objects.get(pos3).getY() > 0) {
                            if (objects.get(pos3).getStatus() == NONE) {
                                int pos4 = findObjectByPosition(pos2 + horizontalSize, objects);
                                if (pos4 > -1) {
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
    private void obstacleContainedInMatch(ArrayList<Obstacles> obstacles, ArrayList<Integer> matches) {
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
     *
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
        ArrayList<Integer> potentialForMatches = new ArrayList<>();
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


    /*
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
                            obstacleIndexWhereZero.add(i); //doesn't this get affected when you remove an obstacle?
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
        }
     */

    private void handleDamagedObstacles(List<UrbieAnimation>objects, ArrayList<Obstacles>obstacles,
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

    }
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
    public ArrayList<ObjectPathCreator> handleTileMovements(ArrayList<Obstacles> obstacles, List<UrbieAnimation>objects,
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

    /*******************************************************************************
     * returns a list of remaining objects that need to be moved down the tiles
     *******************************************************************************/
    private ArrayList<Integer> listOfRemainingObjectsToBeMovedDown(List<UrbieAnimation>objects,
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

    /***********************************************************************************
     * returns a list of points of where the remaining objects that need to be moved to
     ***********************************************************************************/
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
        /*if(positions.size() > listSize){
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
        }*/

       // System.out.println("Additionals = "+additionals);
        if(!additionals.isEmpty()){
            positions.add(additionals);
        }

        return positions;
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
    private ArrayList<Integer> getAdditionalObjectsToBeMoved(List<UrbieAnimation>objects,
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
    }

    /******************************************************
     * get positions that additional objects will be moved to
     ******************************************************/
    private ArrayList<Point> getAdditionalPointsToBeMoved(List<UrbieAnimation>objects,
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
    }

    /*********************************************************************************
     * Return a list of elements positions relative to the cleared column
     *********************************************************************************/
    private ArrayList<Point> getPositionOfElementsRelatingToClearedColumn(List<UrbieAnimation>objects,
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


}
