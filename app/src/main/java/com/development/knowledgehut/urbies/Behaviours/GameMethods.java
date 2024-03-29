package com.development.knowledgehut.urbies.Behaviours;


import android.graphics.Bitmap;
import android.graphics.Point;

import com.development.knowledgehut.urbies.DrawableObjects.UrbieAnimation;
import com.development.knowledgehut.urbies.Objects.MatchedDetails;
import com.development.knowledgehut.urbies.Objects.ObjectPathCreator;
import com.development.knowledgehut.urbies.Objects.Obstacles;
import com.development.knowledgehut.urbies.Screens.Assets;
import com.development.knowledgehut.urbies.Screens.Urbies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.CEMENT;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.GLASS;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.NONE;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.WOODEN;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieType.BABY;

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
                case GOBSTOPPER:
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
     * Given a location, find where this location is within the objects and return
     * the relevant object index. If not found return -1
     ********************************************************************************/
    public int findSpecifiedLocation(List<UrbieAnimation> objects, int location){
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i).getLocation() == location){
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
                temp = Assets.whiteChocolate;
                break;
            case GOBSTOPPER_BOMB:
                temp = Assets.gobstopperBomb;
                break;
            case GOBSTOPPER:
                temp = Assets.gobstopper;
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
            type = BABY;
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
        } else if (bitmap == Assets.whiteChocolate) {
            type = Urbies.UrbieType.WHITE_CHOCOLATE;
        } else if (bitmap == Assets.gobstopper) {
            type = Urbies.UrbieType.GOBSTOPPER;
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

            if (tilePos.get(i).equals(position)) {
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
     * returns a list of empty tile locations
     ********************************************************************************/
    private ArrayList<Integer>getListOfEmptyTiles(ArrayList<Integer>reference){
        ArrayList<Integer>result = new ArrayList<>();

        for(int i = 0; i < reference.size(); i++){
            if(reference.get(i) == -3){
                result.add(i);
            }
        }
        return result;
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

        if (!emptyTiles.isEmpty()) {
            Collections.sort(emptyTiles, Collections.<Integer>reverseOrder());
        }

        return emptyTiles;
    }

    /********************************************************************************
     *
     ********************************************************************************/
    private int isRowBlocked(ArrayList<Integer> map, int width) {
        //boolean blocked = false;
        int found = -1;

        for (int i = 0; i < map.size(); i = i + width) {
            int count;
            if (map.get(i) == -2) {
                count = 1;
                for (int j = 1; j < width; j++) {
                    if (map.get(i + j) == -2) {
                        count++;
                    } else {
                        count = 0;
                    }
                }
                if (count == width) {
                    //blocked = true;
                    found = i;
                    break;
                }
            }
        }

        return found;
    }



    /********************************************************************************
     *
     ********************************************************************************/
    private int getReverseLocationOfY(ArrayList<Point> tileLoc, int y, int width) {
        int result = -1;

        ArrayList<Integer> yLocations = new ArrayList<>();
        ArrayList<Integer> reverse = new ArrayList<>();

        for(int i = 0; i < tileLoc.size(); i = i + width){
            yLocations.add(tileLoc.get(i).y);
        }

        for(int i = 0; i < yLocations.size(); i++){
            reverse.add(-yLocations.get(i));
        }

        Collections.sort(reverse);

        if(yLocations.contains(y)){
            result = reverse.get(yLocations.indexOf(y));
        }

        return result;

    }



    /********************************************************************************
     *
     ********************************************************************************/
    public ArrayList<ObjectPathCreator>replaceObjects(List<UrbieAnimation> objects,
                                                      ArrayList<Integer> matches,
                                                      ArrayList<Obstacles> obstacles, int width,
                                                      ArrayList<Point> tilePos,
                                                      ArrayList<Integer> map,
                                                      ArrayList<Integer> matchesOffScreen){

        ArrayList<ObjectPathCreator>objectPathCreators =  new ArrayList<>();
        ArrayList<Integer> obstacleLocations = new ArrayList<>();
        ArrayList<Integer> glassLocations = new ArrayList<>();
        ArrayList<Integer> reference;
        ArrayList<Integer> emptyTiles;
        ArrayList<Integer> availableTiles = new ArrayList<>();

        System.out.println("REPLACE OBJECTS");

        if (!obstacles.isEmpty()) {
            for (int i = 0; i < obstacles.size(); i++) {
                if (!obstacles.get(i).isVisible() && obstacles.get(i).getDestroyCounter() > 0) {
                    obstacleLocations.add(obstacles.get(i).getLocation());
                } else if (obstacles.get(i).getStatus() == GLASS) {
                    glassLocations.add(obstacles.get(i).getLocation());
                }
            }
        }

        emptyTiles = getEmptyTiles(map, tilePos, objects);

        reference = tilesWithoutMatches(objects, tilePos, map, obstacleLocations, glassLocations, emptyTiles);

        if(!emptyTiles.isEmpty()) {
            for (int i = 0; i < emptyTiles.size(); i++) {
                int blockage = isRowBlocked(reference, width);
                if(blockage == -1 || (blockage > -1 && emptyTiles.get(i) < blockage)){
                    availableTiles.add(emptyTiles.get(i));
                }
            }
        }

        System.out.println("Available Tiles = "+availableTiles);

        if(availableTiles.isEmpty()){
            matchesOffScreen.addAll(matches);
            matches.clear();
        }
        else if(availableTiles.size() == matches.size()){

            for(int i = 0; i < matches.size(); i++) {
                ObjectPathCreator o = new ObjectPathCreator();
                o.setElement(matches.get(i));
                Point pos = tilePos.get(availableTiles.get(i));
                int loc = getReverseLocationOfY(tilePos, pos.y, width);
                if(loc != -1){
                    o.setPosition(new Point(pos.x, loc));
                }
                else {
                    o.setPosition(new Point(pos.x, -200));
                }
                o.addToPath(findLine(o.getPosition().x, o.getPosition().y, pos.x, pos.y));
                int futureLoc = findLocationByPosition(pos, tilePos);
                if(futureLoc != -1) {
                    o.setFutureElement(futureLoc);
                }
                //o.print();

                objectPathCreators.add(o);
            }
        }
        else if(availableTiles.size() < matches.size()){
            System.out.println("The available tiles are < matches size");
        }
        else if(availableTiles.size() > matches.size()){
            System.out.println("The available tiles are > matches size");

            Collections.sort(availableTiles, Collections.<Integer>reverseOrder());

            ArrayList<Integer>entrancePoints = new ArrayList<>();

            for(int j = 0; j < width; j++){
                if(reference.get(j) == -3){
                    entrancePoints.add(j);
                }
            }

            availableTiles = orderListByColumnWithEntrancesLast(availableTiles, entrancePoints, width);
           // System.out.println("entrancePoints = "+entrancePoints);
            System.out.println("availableTiles = "+availableTiles);

            for(int i = 0; i < availableTiles.size(); i++) {

                System.out.println("reference = "+reference);
                PositionList positionList = addNewObjects(reference, map, availableTiles.get(i),  entrancePoints, tilePos, width);

                int y = tilePos.get(availableTiles.get(i)).y;
                int loc = getReverseLocationOfY(tilePos, y, width);

                if (loc == -1) {
                    loc = -200;
                }

                Point point = new Point(positionList.getPositionAt(0).x, loc);
                positionList.setPositionAt(point, 0);

                ObjectPathCreator o = new ObjectPathCreator();
                o.setElement(positionList.getLocation_id());

                int sx = positionList.getPositionAt(0).x;
                int sy = positionList.getPositionAt(0).y;

                for (int q = 1; q < positionList.getPosition().size(); q++) {

                    ArrayList<Point> getPath = findLine(sx, sy, positionList.getPosition().get(q).x, positionList.getPosition().get(q).y);
                    o.addToPath(getPath);

                    sx = positionList.getPosition().get(q).x;
                    sy = positionList.getPosition().get(q).y;
                }

                o.setPosition(o.getPath().get(o.getPath().size() - 1));
                int futureLoc = findLocationByPosition(o.getPosition(), tilePos);
                if(futureLoc != -1) {
                    o.setFutureElement(futureLoc);
                }
                //o.print();
                objectPathCreators.add(o);


                reference.set(availableTiles.get(i),availableTiles.get(i));
            }
        }

        return objectPathCreators;
    }



    /********************************************************************************
     *
     ********************************************************************************/
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


    /********************************************************************************
     *
     ********************************************************************************/
    private boolean isThereMoreObjectsToMove(ArrayList<Integer>pathwayTileValues, int empty,
                                             int matched){

        System.out.println("pathwayTileValues = "+pathwayTileValues);
        for(int i = 0; i < pathwayTileValues.size(); i++){
            if(pathwayTileValues.get(i) != empty && pathwayTileValues.get(i) != matched){
                return true;
            }
        }

        return false;
    }


    /********************************************************************************
     *
     ********************************************************************************/
    private ArrayList<PositionList> myTest(ArrayList<Integer>reference, ArrayList<Integer>entrance,
                        int width, ArrayList<Integer>map, ArrayList<Point>tilePos,
                        ArrayList<Integer>missingObjects)
    {
        boolean moreObjectsToMove = false;
        ArrayList<PositionList>positionLists = new ArrayList<>();

        do {
            int entryPoint = getBestEntryPoint(entrance, width, missingObjects.get(0));

            //get the correct ordering of missing objects
            missingObjects = orderByFurthestFromEntry(missingObjects, entryPoint, width);

            //establish the route between entrance and first missing object
            LinkedList<int[]> pathway = getPathway(reference, map, entryPoint, missingObjects.get(0), width);

            //add the positions for any objects in the same column as entrance (above only) to the end of the route
            pathway.addAll(getObjectsAboveEntryPoint(reference, entryPoint, width));

            //if all values in the pathway are -3 or -5 then there are no more moves to make
            ArrayList<Integer> pathwayTileValues = new ArrayList<>();
            for (int i = 0; i < pathway.size(); i++) {
                int[] element;
                element = pathway.get(i);
                int position = (element[0] * width) + element[1];
                pathwayTileValues.add(reference.get(position));
            }
            moreObjectsToMove = isThereMoreObjectsToMove(pathwayTileValues, -3, -5);

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            //delete when happy
            System.out.println("noMoreObjectsToMove = " + moreObjectsToMove);
            //System.out.println("myTest = ");
            /*for (Object object : pathway) {
                int[] element = (int[]) object;
                System.out.println("path = [" + element[0] + "] [" + element[1] + "]");
            }*/
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

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
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    //delete when happy
                    /*System.out.println(position);
                    for (int j = 0; j < temp.length; j++) {
                        System.out.println("temp = " + temp[j][0] + ": " + temp[j][1]);

                    }*/
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                    //I believe that the code goes here for positionList - see below
                    //update reference arraylist
                    positionLists.add(convertPathwayToPositionList(pathway,tilePos, width, position));

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

            for (int i = missingObjects.size() - 1; i >= 0; i--) {
                if (reference.get(missingObjects.get(i)) != -3 && reference.get(missingObjects.get(i)) != -5) { //or -5 in some situations
                    missingObjects.remove(i);
                }
            }

            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            //delete when happy
            System.out.println("MissingObjects = " + missingObjects);
            //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        } while (!missingObjects.isEmpty() && moreObjectsToMove);

        for(int i = 0; i < positionLists.size(); i++){
            System.out.println("location id = " + positionLists.get(i).getLocation_id());
            System.out.println("positions = " + positionLists.get(i).getPosition());
        }
        return positionLists;
    }

    /********************************************************************************
     *
     ********************************************************************************/
    private PositionList convertPathwayToPositionList(LinkedList<int[]> pathway,
                                                      ArrayList<Point>tilePos, int width, int loc){
        PositionList positionList = new PositionList(loc);

        for (int i = pathway.size() - 1; i >= 0; i--) {
            int[] element;
            element = pathway.get(i);
            int position = (element[0] * width) + element[1];
            positionList.setPosition(tilePos.get(position));
        }

        return positionList;
    }

    private Point convertPositionToPoint(int []position, ArrayList<Point>tilePos, int width){

        int placement = (position[0] * width) + position[1];
        Point point = tilePos.get(placement);

        return point;
    }
    /********************************************************************************
     *
     ********************************************************************************/
    private ArrayList<Point> convertPathwayToObjectPathCreator(LinkedList<int[]> pathway,
                                                                ArrayList<Point>tilePos, int width, int loc){

        ArrayList<Point>paths = new ArrayList<>();

        for (int i = pathway.size() - 1; i >= 0; i--) {
            int[] element;
            element = pathway.get(i);
            int position = (element[0] * width) + element[1];
            paths.add(tilePos.get(position));
        }

//        ObjectPathCreator objectPathCreator = new ObjectPathCreator();
//        objectPathCreator.setElement(loc);
//        objectPathCreator.addToPath(paths);

        return paths;
    }

    /********************************************************************************
     *
     ********************************************************************************/
    private ArrayList<int[]> getObjectsAboveEntryPoint(ArrayList<Integer>reference, int entryPoint, int width){
        ArrayList<int[]>additionals = new ArrayList<>();
        int num = entryPoint - width;

        while (num >= 0){
            if(reference.get(num)==-2){
                break;
            }
            else if(reference.get(num) == -3 || reference.get(num) == -5 || reference.get(num) >=0){
                int[]t = new int[2];
                t[0] = num / width;
                t[1] = num % width;
                additionals.add(t);
            }
            num = num - width;
        }
        return additionals;
    }

    /********************************************************************************
     *
     ********************************************************************************/
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


    //called if a path can be established between matchElement and empty tiles / matched(i). E.g. there is a
    //broken obstacle or free urb

    /********************************************************************************
     *exits out of loop on first condition of true
     ********************************************************************************/
    public int isRowBlocked(ArrayList<Integer> map, int width, ArrayList<Integer> obstacleLocations) {
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
                if (count == width) {
                    blocked = true;
                    found = i;
                    break;
                }
            }
        }

        return found;
    }


    private int[] convertToCells(int position, int width){
        int[] cells = new int[2];

        cells[0] = position / width;
        cells[1] = position % width;

        return cells;
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

    private ArrayList<Integer> moveDownByColumn(ArrayList<Integer>reference,
                                                int width,
                                                ArrayList<Point> tilePos,
                                                ArrayList<ObjectPathCreator>objectPathCreators){

        for(int a = width -1; a >=0; a--) {
            ArrayList<Integer> columnResults = columnEntriesEmpty(width, reference, a);

            if(!columnResults.isEmpty()){
                System.out.println(columnResults);

                if(!columnResults.isEmpty()) {
                    int location = columnResults.get(0) - width;

                    while (!columnResults.isEmpty()) {
                        if (location >= 0) {
                            if (reference.get(location) >= 0) {

                                ObjectPathCreator creatorTest = new ObjectPathCreator();
                                creatorTest.setElement(reference.get(location));

                                Collections.swap(reference, columnResults.get(0), location);

                                LinkedList<int[]> pathway = new LinkedList<>();
                                pathway.add(convertToCells(columnResults.get(0), width));
                                pathway.add(convertToCells(location, width));

                                creatorTest.addToPath(convertPathwayToObjectPathCreator(pathway, tilePos, width, location));
                                int futureLoc = findLocationByPosition(creatorTest.getPath().get(creatorTest.getPath().size() - 1), tilePos);
                                if(futureLoc != -1) {
                                    creatorTest.setFutureElement(futureLoc);
                                }
                                creatorTest.print();

                                objectPathCreators.add(creatorTest);
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


    /********************************************************
     Updated method to move remaining objects within tile map
     ********************************************************/
    public ArrayList<ObjectPathCreator> moveRemainingObjects(List<UrbieAnimation> objects, ArrayList<Integer> matches,        ArrayList<Obstacles> obstacles,
                                                             int width, ArrayList<Point> tilePos, ArrayList<Integer> map,
                                                             ArrayList<Integer> matchesOffScreen,
                                                             ArrayList<Integer> entrance){
        ArrayList<Integer> emptyTiles;
        ArrayList<Integer> reference;
        ArrayList<Integer> obstacleLocations = new ArrayList<>();
        ArrayList<Integer> glassLocations = new ArrayList<>();
        ArrayList<Integer> nearMatchObstacles;
        ArrayList<Integer> brokenObstacleLocations = new ArrayList<>();
        ArrayList<ObjectPathCreator>objectPathCreators =  new ArrayList<>();

        boolean isPopulated = false;

        if(!matchesOffScreen.isEmpty())isPopulated = true;

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

        //Identify any empty tiles
        emptyTiles = getEmptyTiles(map, tilePos, objects);
        System.out.println("Empty tiles = " + emptyTiles);


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
                            brokenObstacleLocations.add(i);
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

        //if there are no longer any obstacles remove entrance
        if (obstacleLocations.isEmpty() && !entrance.isEmpty()) {
            entrance.clear();
        }
        System.out.println("Entrance = "+entrance);


        ////////////////////////////////////////////////////////////////////////////////////////////
        //2. Does the matched list contain a urb submerged in GLASS? - if so remove glass urb from match list
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
            System.out.println("Matches = " + matches);
        }
        reference = tileStatus(matches, map, obstacleLocations, glassLocations, emptyTiles);

        System.out.println("reference = "+reference);
        System.out.println("brokenObstacles = "+brokenObstacleLocations);
        System.out.println("emptyTiles = " + emptyTiles);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //3. Start looping through matches, considering any broken obstacles and empty tiles
        ////////////////////////////////////////////////////////////////////////////////////////////
        reference = moveDownByColumn(reference, width, tilePos, objectPathCreators);
        printReference(reference, width);

        for(int j = 0; j < objectPathCreators.size(); j++){
            objectPathCreators.get(j).print();
        }

        if(!entrance.isEmpty()) {
            System.out.println("There are entrances available");
            ArrayList<Integer>newMatches = new ArrayList<>();

            do {
                newMatches.clear();
                newMatches = getMatchedTiles(reference);
                System.out.println(newMatches);

                int entryPoint = getBestEntryPoint(entrance, width, newMatches.get(0));
                int start = getStartOfEntryPointColumn(reference, entryPoint, width, newMatches.get(0));

                LinkedList<int[]> pathway = getPathway(reference, map, start, newMatches.get(0), width);

                for(int i = 0; i < pathway.size(); i++){
                    System.out.println(pathway.get(i)[0] + ", " + pathway.get(i)[1]);
                }
                printPathway(pathway, reference, width, tilePos,objectPathCreators);
                printReference(reference, width);

                newMatches.clear();
                newMatches = getMatchedTiles(reference);

            } while (moreToMove(reference, newMatches, entrance, width));
        }

        //Identify any empty tiles
        emptyTiles = getListOfEmptyTiles(reference);
        if(isPopulated){
            if(!matchesOffScreen.equals(emptyTiles)){
                matchesOffScreen.clear();
                matchesOffScreen.addAll(emptyTiles);
            }
        }

        System.out.println("matches offscreen" + matchesOffScreen);
        return objectPathCreators;
    }

    private boolean hasOccupiedTile(int element, ArrayList<Integer>reference, int width, ArrayList<Integer>entrance){
        boolean result = false;
        int num = element;

        while(num >= 0){
            if(reference.get(num) >= 0){
                result = true;
                break;
            }
            if(reference.get(num) == -2 && entrance.isEmpty()){
                result = false;
                break;
            }
            num = num - width;
        }

        return result;
    }

    private boolean moreToMove(ArrayList<Integer>reference, ArrayList<Integer>matches, ArrayList<Integer>entrance, int width){
        boolean moreToMove = true;

        for(int i = 0; i < matches.size(); i++){
            int entryPoint = getBestEntryPoint(entrance, width, matches.get(i));
            int start = getStartOfEntryPointColumn(reference, entryPoint, width, matches.get(i));

            if(matches.get(i) < width || !hasOccupiedTile(matches.get(i),reference, width, entrance) || start == -1){
                moreToMove = false;
            }
            else {
                moreToMove = true;
                break;
            }
        }
        return moreToMove;
    }


    private void printPathway(LinkedList<int[]>pathway,
                              ArrayList<Integer>reference,
                              int width,
                              ArrayList<Point>tilePos,
                              ArrayList<ObjectPathCreator>objectPathCreator)
    {
        for (int k = 0; k < pathway.size(); k++) {
            int[] element;
            element = pathway.get(k);
            int position = (element[0] * width) + element[1];
            int relative_position = reference.get(position);

            if (reference.get(position) != -3 && reference.get(position) != -5) {
                ArrayList<Point> temp = new ArrayList<>();
                for (int j = 0; j < k + 1; j++) {
                    Point point = convertPositionToPoint(pathway.get(j), tilePos, width);
                    temp.add(point);
                }

                boolean foundInList = false;
                int listItem = -1;
                for(int i = 0; i < objectPathCreator.size(); i++){
                    if(objectPathCreator.get(i).getElement() == relative_position){
                        foundInList = true;
                        listItem = i;
                        break;
                    }
                }
                if(foundInList){
                    objectPathCreator.get(listItem).printPath();
                    for(int i = temp.size() - 1; i >= 0; i--){
                        System.out.println("testing: " + temp.get(i));
                        for(int j = 0; j < objectPathCreator.get(listItem).getPath().size(); j++){
                            //Point point = convertPositionToPoint(temp.get(i), tilePos, width);
                            if(objectPathCreator.get(listItem).getPath().get(j).equals(temp.get(i))){
                                temp.remove(i);
                                break;
                            }
                        }
                    }

                    objectPathCreator.get(listItem).addPathAt(0, temp);
                    objectPathCreator.get(listItem).print();
                }
                else {
                    ObjectPathCreator creatorTest = new ObjectPathCreator();
                    creatorTest.setElement(relative_position);
                    creatorTest.addToPath(temp);
                    objectPathCreator.add(creatorTest);
                    objectPathCreator.get(objectPathCreator.size() - 1).print();
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

    private ArrayList<Integer>getMatchedTiles(ArrayList<Integer>reference){
        ArrayList<Integer>matchedTiles = new ArrayList<>();

        for(int i = 0; i < reference.size(); i++){
            if(reference.get(i) == -5 || reference.get(i) == -3){
                matchedTiles.add(i);
            }
        }
        Collections.sort(matchedTiles, Collections.<Integer>reverseOrder());
        return matchedTiles;
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
    /*************************************************
     to act as a replacement for listOfObjectsToMoveDown
     *************************************************/
    public ArrayList<ObjectPathCreator> separateTheMadness(
            List<UrbieAnimation> objects, ArrayList<Integer> matches, ArrayList<Obstacles> obstacles,
            int width, ArrayList<Point> tilePos, ArrayList<Integer> map,
            ArrayList<Integer> matchesOffScreen,
            ArrayList<Integer> entrance
    ) {

        ArrayList<Integer> obstacleLocations = new ArrayList<>();
        ArrayList<Integer> glassLocations = new ArrayList<>();
        ArrayList<Integer> nearMatchObstacles;
        ArrayList<Integer> brokenObstacleLocations = new ArrayList<>();
        ArrayList<ObjectPathCreator>objectPathCreators =  new ArrayList<>();
        ArrayList<ObjectPathCreator>downPathCreators =  new ArrayList<>();
        ArrayList<Integer> moveDownList = new ArrayList<>();
        ArrayList<Point> positions = new ArrayList<>();
        ArrayList<Integer> emptyTiles;
        ArrayList<Integer> reference;
        ArrayList<PositionList>positionLists = new ArrayList<>();
        boolean isPopulated = false;

        if(!matchesOffScreen.isEmpty())isPopulated = true;

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

        //Identify any empty tiles
        emptyTiles = getEmptyTiles(map, tilePos, objects);
        System.out.println("Empty tiles = " + emptyTiles);

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
                            brokenObstacleLocations.add(i);
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

        //if there are no longer any obstacles remove entrance
        if (obstacleLocations.isEmpty() && !entrance.isEmpty()) {
            entrance.clear();
        }
        System.out.println("Entrance = "+entrance);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //2. Does the matched list contain a urb submerged in GLASS? - if so remove glass urb from match list
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
            System.out.println("Matches = " + matches);
        }
        reference = tileStatus(matches, map, obstacleLocations, glassLocations, emptyTiles);

        System.out.println("reference = "+reference);
        System.out.println("brokenObstacles = "+brokenObstacleLocations);
        System.out.println("emptyTiles = " + emptyTiles);

        ////////////////////////////////////////////////////////////////////////////////////////////
        //3. Start looping through matches, considering any broken obstacles and empty tiles
        ////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<Point> tempPosition = new ArrayList<>();
        ArrayList<Integer>down = new ArrayList<>();
        ArrayList<Integer>tempMatchRemove = new ArrayList<>();

        if((!emptyTiles.isEmpty() && !entrance.isEmpty()) || (!entrance.isEmpty() && !matches.isEmpty())){
            boolean matchBelowObstacles = false;
            System.out.println("matches = "+matches);
            for(int i = matches.size() - 1; i >= 0; i--){
                if(matches.get(i) >= entrance.get(0) + width){
                    emptyTiles.add(matches.get(i));
                    tempMatchRemove.add(matches.get(i));
                    matches.remove(i);
                    matchBelowObstacles = true;
                }
            }

            if (!emptyTiles.isEmpty() || matchBelowObstacles) {
                ArrayList<PositionList>pList = myTest(reference,entrance, width, map, tilePos, emptyTiles);
                for(int j = 0; j < pList.size(); j++) {
                    ObjectPathCreator o = new ObjectPathCreator();
                    o.setElement(pList.get(j).getLocation_id());
                    down.add(o.getElement());
                    int sx = pList.get(j).getPositionAt(0).x;
                    int sy = pList.get(j).getPositionAt(0).y;

                    for(int k = 0; k < pList.get(j).getPosition().size(); k++){
                        ArrayList<Point> getPath = findLine(sx, sy, pList.get(j).getPosition().get(k).x,
                                pList.get(j).getPosition().get(k).y);
                        o.addToPath(getPath);

                        sx = pList.get(j).getPosition().get(k).x;
                        sy = pList.get(j).getPosition().get(k).y;
                    }

                    o.setPosition(o.getPath().get(o.getPath().size() - 1));
                    int futureLoc = findLocationByPosition(o.getPath().get(o.getPath().size() - 1), tilePos);
                    if(futureLoc != -1) {
                        o.setFutureElement(futureLoc);
                    }
                    //o.print();
                    objectPathCreators.add(o);
                }

            }

            for(int i = emptyTiles.size() -1; i >= 0; i--){
                for(int j = 0; j < objectPathCreators.size(); j++){
                    if(objectPathCreators.get(j).getFutureElement() == emptyTiles.get(i)){
                        emptyTiles.remove(i);
                        break;
                    }
                }
            }
        }

        if(!matches.isEmpty()){

            System.out.println("matches = "+matches);

            for (int i = 0; i < matches.size(); i++) {
            int startingPoint = matches.get(i);
           // else {

                //Identify the valid positions that objects will be able to drop down to
                int num = startingPoint - width;
                tempPosition.add(tilePos.get(startingPoint));

                while (num >= 0) {
                    if(getPositionListById(num, positionLists) == -1) {
                        if (map.get(num) == 1) {

                            int urb_num = findBitmapByMapLocation(objects, tilePos, num);

                            if (urb_num > -1) {
                                if (objects.get(urb_num).getStatus() == NONE) {
                                    tempPosition.add(tilePos.get(num));
                                } else if (objects.get(urb_num).getStatus() == CEMENT || objects.get(urb_num).getStatus() == WOODEN) {
                                    tempPosition.remove(tempPosition.size() - 1);
                                    break;
                                }
                            }
                        }
                    }
                    num = num - width;
                }

                //Identify the objects that will be moved down
                int counter = 0;

                num = startingPoint - width;


                while (num >= 0) {
                    if(getPositionListById(num, positionLists) == -1) {
                        int urb_num = findBitmapByMapLocation(objects, tilePos, num);
                        if (urb_num > -1) {
                            if (map.get(num) == 1 && !matches.contains(num) &&
                                    !moveDownList.contains(num) &&
                                    objects.get(urb_num).getStatus() == NONE &&
                                    (down.isEmpty() || !down.contains(num))) {
                                moveDownList.add(num);
                                positions.add(tempPosition.get(counter));
                                ObjectPathCreator o = new ObjectPathCreator();
                                o.setElement(num);
                                o.setPosition(objects.get(urb_num).getPosition());
                                o.addToPath(findLine(o.getPosition().x, o.getPosition().y, tempPosition.get(counter).x, tempPosition.get(counter).y));

                                int futureLoc = findLocationByPosition(tempPosition.get(counter), tilePos);
                                if(futureLoc != -1) {
                                    o.setFutureElement(futureLoc);
                                }
                                downPathCreators.add(o);
                                int pos = findLocationByPosition(tempPosition.get(counter), tilePos);
                                Collections.swap(reference, num, pos);
                                tempPosition.remove(counter);
                            } else {
                                //there is a blockage so add matches element to off-screen,
                                //it will not drop back down
                                if (obstacleLocations.contains(num)) {
                                    matchesOffScreen.add(findObjectByPosition(matches.get(i), objects));
                                    break;
                                }
                            }
                        }
                    }
                    num = num - width;
                }
                tempPosition.clear();
                emptyTiles.clear();
                brokenObstacleLocations.clear();
            }
        }

        if(!downPathCreators.isEmpty()){
            objectPathCreators.addAll(downPathCreators);
        }

        //Identify any empty tiles
        emptyTiles = getEmptyTiles(map, tilePos, objects);
        if(isPopulated){
            if(!matchesOffScreen.equals(emptyTiles)){
                matchesOffScreen.clear();
                matchesOffScreen.addAll(emptyTiles);
            }
        }


        /*if(!tempMatchRemove.isEmpty()){
            matches.addAll(tempMatchRemove);
            Collections.sort(matches, Collections.<Integer>reverseOrder());
        }*/
        System.out.println("Empty tiles = " + emptyTiles);


        return objectPathCreators;
    }

    private ArrayList<Point> findLine(int x0, int y0, int x1, int y1){
        ArrayList<Point>spritePath = new ArrayList<>();

        int sy = (y0 < y1) ? 1 : -1;
        int sx = (x0 < x1) ? 1 : -1;


        while(true) {
            Point p = new Point(x0, y0);

            spritePath.add(p);

            if (x0 == x1 && y0 == y1) {
                break;
            }

            if(y0 != y1 && x0 != x1){
                break;
            }

            if (x0 == x1) {
                if(y0 != y1) y0 = y0 + sy;

            }
            if (y0 == y1) {
                if(x0 != x1) x0 = x0 + sx;
            }
        }
        return spritePath;
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


    /**************************************************************************************************
     finds the shortest path without obstacles for adding new objects to a tile map
     **************************************************************************************************/
    private PositionList addNewObjects(ArrayList<Integer> reference, ArrayList<Integer>map,
                                                  int positionToGoTo, ArrayList<Integer> entrance,
                                                  ArrayList<Point>tilePos, int width){

        PositionList positionList = new PositionList(positionToGoTo);

        ArrayList<ArrayList<int[]>> pathway = new ArrayList<>();

        //get the relevant entry point
        int entryPoint = -1;

        if (!entrance.isEmpty()) {
            if (entrance.size() == 1) {
                entryPoint = entrance.get(0);
            } else {
                for (int t = 0; t < entrance.size(); t++) {
                    if (entrance.get(t) % width == positionToGoTo % width) {
                        entryPoint = entrance.get(t);
                        break;
                    }
                }
            }
        }
        System.out.println("My Entry Point = " + entryPoint);

        //get shortest path
        ArrayList<Integer> blockedPositions = blockedPositions(reference,  width);
        int[][] arrayWasteLand = convertArrayListTo2DArray(blockedPositions);

        PathFinding path = new PathFinding();

        pathway.add(path.getPath(positionToGoTo, map.size() / width, width, (entryPoint / width),
                (entryPoint % width), positionToGoTo / width, positionToGoTo % width,
                arrayWasteLand));


        //print out pathway
        for (int j = 0; j < pathway.size(); j++) {
            System.out.println("===============================");
            for (int k = 0; k < pathway.get(j).size(); k++) {
                System.out.println("pathway = [" + pathway.get(j).get(k)[0] + "][ " + pathway.get(j).get(k)[1] + "]");
            }
        }

        for (int j = 0; j < pathway.size(); j++) {
            for (int k = pathway.get(j).size() - 1; k >= 0; k--) {
                int position = (pathway.get(j).get(k)[0] * width) + pathway.get(j).get(k)[1];
                positionList.setPosition(tilePos.get(position));
            }
        }

        return positionList;
    }

    /**************************************************************************************************
     Find the shortest path to fill empty tiles following a broken solid obstacle
     **************************************************************************************************/
    private ArrayList<PositionList> fillEmptyTiles(ArrayList<Integer> matches, int width, ArrayList<Integer> map,
                                ArrayList<Integer> reference, ArrayList<Integer> entrance,
                                ArrayList<Point> tilePos, ArrayList<Integer> emptyTiles,
                                ArrayList<ArrayList<int[]>> pathway, int i) {

        ArrayList<Integer> tail = new ArrayList<>();
        ArrayList<Integer> storeUnusedPositions = new ArrayList<>();
        ArrayList<PositionList> positionList = new ArrayList<>();

        PathFinding path = new PathFinding();

        System.out.println("THERE ARE CURRENTLY BROKEN TILES DUE TO MATCH AND THIS MATCH " + matches.get(i) + " IS ON THE SAME COLUMN");

        //get the relevant entry point
        int entryPoint = -1;

        if (!entrance.isEmpty()) {
            if (entrance.size() == 1) {
                entryPoint = entrance.get(0);
            }
            else {
                for (int t = 0; t < entrance.size(); t++) {
                    if (entrance.get(t) % width == matches.get(i) % width) {
                        entryPoint = entrance.get(t);
                        break;
                    }
                }
            }
        }
        System.out.println("My Entry Point = " + entryPoint);

        //get all the valid objects in the same column above entryPoint
        int num = entryPoint - width;
        while (num >= 0) {
            if (reference.get(num) == num) {
                tail.add(num);
            }
            num = num - width;
        }

        //get shortest path
        ArrayList<Integer> blockedPositions = irrelevantPositions(reference, entryPoint, width);
        int[][] arrayWasteLand = convertArrayListTo2DArray(blockedPositions);

        pathway.add(path.getPath(emptyTiles.get(0), map.size() / width, width, (entryPoint / width),
                (entryPoint % width), emptyTiles.get(0) / width, emptyTiles.get(0) % width,
                arrayWasteLand));


        //print out pathway
        for (int j = 0; j < pathway.size(); j++) {
            System.out.println("===============================");
            for (int k = 0; k < pathway.get(j).size(); k++) {
                System.out.println("pathway = [" + pathway.get(j).get(k)[0] + "][ " + pathway.get(j).get(k)[1] + "]");
            }
        }

        //position lists to store positions of elements
        positionList.add(new PositionList(entryPoint));
        for (int z = 0; z < tail.size(); z++) {
            positionList.add(new PositionList(tail.get(z)));
        }

        int currentPosition;
        int previousPosition;

        for (int j = 0; j < pathway.size(); j++) {
            for (int k = 1; k < pathway.get(j).size(); k++) {
                previousPosition = (pathway.get(j).get(k - 1)[0] * width) + pathway.get(j).get(k - 1)[1];
                currentPosition = (pathway.get(j).get(k)[0] * width) + pathway.get(j).get(k)[1];

                //add to storeUnused if empty tile or matched tile
                if (reference.get(currentPosition) == -3 || reference.get(currentPosition) == -5) {
                    if (!storeUnusedPositions.contains(previousPosition)) {
                        storeUnusedPositions.add(previousPosition);
                    }
                }

                //add to path list if swapping with an occupied tile
                else if (reference.get(currentPosition) >= 0) {
                    Collections.swap(reference, currentPosition, previousPosition);
                    int p = getPositionListById(currentPosition, positionList);

                    if(p == -1){
                        positionList.add(new PositionList(currentPosition));
                        positionList.get(positionList.size() - 1).setPosition(tilePos.get(previousPosition));
                    }
                    else {
                        positionList.get(p).setPosition(tilePos.get(previousPosition));
                    }

                    //add storeUnused to path list
                    if (!storeUnusedPositions.isEmpty()) {
                        for (int m = storeUnusedPositions.size() - 1; m >= 0; m--) {
                            if(p > -1) {
                                Collections.swap(reference, reference.indexOf(currentPosition), storeUnusedPositions.get(m));
                                positionList.get(p).setPosition(tilePos.get(storeUnusedPositions.get(m))); //the position list for current position not currentPosListNum
                            }
                        }
                        storeUnusedPositions.clear();
                    }

                    //if entryPoint move the other elements in the same column (original column)
                    //following the path of entryPoint
                    if (currentPosition == entryPoint) {

                        //move down the others in the column
                        if (!tail.isEmpty()) {
                            int head = currentPosition;
                            for (int t = 0; t < tail.size(); t++) {

                                int distance = reference.indexOf(head) - reference.indexOf(tail.get(t));

                                while (distance > 1) {
                                    if (distance >= width) {

                                        if (!tail.contains(reference.get(reference.indexOf(tail.get(t)) + width))) {
                                            Collections.swap(reference, reference.indexOf(tail.get(t)), reference.indexOf(tail.get(t)) + width);
                                            positionList.get(t + 1).setPosition(tilePos.get(reference.indexOf(tail.get(t))));
                                        }

                                        distance = distance - width;
                                    }

                                    if (distance < width && distance > 1) {

                                        if (!tail.contains(reference.get(reference.indexOf(tail.get(t)) + 1))) {
                                            Collections.swap(reference, reference.indexOf(tail.get(t)), reference.indexOf(tail.get(t)) + 1);
                                            positionList.get(t + 1).setPosition(tilePos.get(reference.indexOf(tail.get(t))));
                                        }
                                        distance = distance - 1;
                                    }
                                }
                                head = tail.get(t);
                            }
                        }
                    }
                }
                System.out.println("reference = " + reference);
                for (int g = 0; g < positionList.size(); g++) {
                    System.out.println("Element " + positionList.get(g).getLocation_id() + "= " + positionList.get(g).getPosition());
                }
            }
        }
        return positionList;
    }

    /**************************************************************************************************
     returns the position int the array of the location id searched for
     **************************************************************************************************/
    private int getPositionListById(int current, ArrayList<PositionList>positionListArrayList){
        for(int i = 0; i < positionListArrayList.size(); i++){
            if(positionListArrayList.get(i).getLocation_id() == current){
                return i;
            }
        }
        return -1;
    }


    /**************************************************************************************************
     gets the current status of the tile map when passed to main method
     **************************************************************************************************/
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


    private ArrayList<Integer>tilesWithoutMatches(List<UrbieAnimation>objects, ArrayList<Point>tileLoc,
                                                  ArrayList<Integer>map, ArrayList<Integer>obstacleLocations,
                                                  ArrayList<Integer>glassLocations, ArrayList<Integer>emptyTiles){

        ArrayList<Integer> reference = new ArrayList<>();

        for(int i = 0; i < map.size(); i++){
            if (map.get(i) == 0) {
                reference.add(-1);
            } else if (map.get(i) == 1 && obstacleLocations.contains(i)) {
                reference.add(-2);
            } else if (map.get(i) == 1 && emptyTiles.contains(i)) {
                reference.add(-3);
            } else if (map.get(i) == 1 && glassLocations.contains(i)) {
                reference.add(-4);
            }  else {
                reference.add(objects.get(findBitmapByMapLocation(objects, tileLoc, i)).getLocation());
            }
        }

        return reference;
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
    //TODO: Problem may occur here. For some reason a match is included that is under cement. location 19 (cement), 13 and 17 status is none
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


    /***************************************************************************
     Returns an multi-dimensional array of the cell positions from irrelevantPositions
     ***************************************************************************/
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

    /***************************************************************************
     Returns an array list of cell positions, each pair in the array list corresponds to
     y,x cell values based on the list provided. Only empty tiles are excluded from
     the returning list of blocked positions
     ***************************************************************************/
    private ArrayList<Integer> blockedPositions(ArrayList<Integer>reference, int width){
        ArrayList<Integer> values = new ArrayList<>();

        int x = 0;
        int y = 0;

        for(int i = 0; i < reference.size(); i++){
            if(reference.get(i) != -3){
                values.add(y);
                values.add(x);
            }

            x++;

            if(x == width){
                x = 0;
                y++;
            }
        }

        System.out.println("blocked positions = " + values);
        return values;
    }

    /***************************************************************************
     Returns an array list of cell positions, each pair in the array list corresponds to
     y,x cell values based on the list provided
     ***************************************************************************/
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


    private ArrayList<Integer> irrelevantPositions2(ArrayList<Integer>reference, ArrayList<Integer> entrance,
                                                   int width) {

        ArrayList<Integer> values = new ArrayList<>();
        ArrayList<Integer> entryCol = new ArrayList<>();

        for(int i = 0; i < entrance.size(); i++){
            entryCol.add(entrance.get(i) % width);
        }

        int x = 0;
        int y = 0;

        for(int i = 0; i < reference.size(); i++){
            if(reference.get(i) == -2 || reference.get(i) == -4){
                y = i / width;
                x = i % width;
                values.add(y);
                values.add(x);
            }
            else {

                if (!entryCol.contains(i % width) && i < entrance.get(0)) {
                    y = i / width;
                    x = i % width;

                    values.add(y);
                    values.add(x);
                }

            }
        }

        System.out.println("not Relevant2 = " + values);
        return values;
    }

    public void resetVisualisePotentialMatch(List<UrbieAnimation> objects, ArrayList<Integer> possibleMatches) {
        int duration = new Random().nextInt(12000) + 3001;

        if (!possibleMatches.isEmpty()) {
            for (int loop = 0; loop < possibleMatches.size(); loop++) {
                switch (objects.get(possibleMatches.get(loop)).getType()) {
                    case BABY:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.baby);
                        objects.get(possibleMatches.get(loop)).setFPS(10);
                        break;
                    case NERD:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.nerd);
                        objects.get(possibleMatches.get(loop)).setFPS(10);
                        break;
                    case PIGTAILS:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.pigtails);
                        objects.get(possibleMatches.get(loop)).setFPS(10);
                        break;
                    case PAC:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.pac);
                        objects.get(possibleMatches.get(loop)).setFPS(10);
                        break;
                    case LADY:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.lady);
                        objects.get(possibleMatches.get(loop)).setFPS(10);

                        break;
                    case PUNK:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.punk);
                        objects.get(possibleMatches.get(loop)).setFPS(10);

                        break;
                    case ROCKER:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.rocker);
                        objects.get(possibleMatches.get(loop)).setFPS(10);

                        break;
                    case GIRL_NERD:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.nerd_girl);
                        objects.get(possibleMatches.get(loop)).setFPS(10);
                        break;
                    case WHITE_CHOCOLATE:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.whiteChocolate);
                        objects.get(possibleMatches.get(loop)).setFPS(30);
                        duration = new Random().nextInt(1200) + 3001;
                        break;
                    case GOBSTOPPER:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.gobstopper);
                        objects.get(possibleMatches.get(loop)).setFPS(30);
                        duration = new Random().nextInt(1200) + 3001;
                        break;
                    case STRIPE_HORIZONTAL:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.stripe_h);
                        objects.get(possibleMatches.get(loop)).setFPS(30);
                        duration = new Random().nextInt(1200) + 3001;
                        break;
                    case STRIPE_VERTICAL:
                        objects.get(possibleMatches.get(loop)).setBitmap(Assets.stripe_v);
                        objects.get(possibleMatches.get(loop)).setFPS(30);
                        duration = new Random().nextInt(1200) + 3001;
                        break;
                }

                objects.get(possibleMatches.get(loop)).setFrameDuration(duration);
            }
        }
    }


    public int getSlope(float x1, float y1, float x2, float y2) {
        Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));
        if (angle > 45 && angle <= 135)
            // top
            return 1;
        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)
            // left
            return 2;
        if (angle < -45 && angle >= -135)
            // down
            return 3;
        if (angle > -45 && angle <= 45)
            // right
            return 4;
        return 0;
    }


    /***********************************************************************************************
        Given a list of available tiles and the unblocked (free from obstacles) columns, arrange the
        order of available tiles so that the placement of the new objects do not block other
        available tiles from getting to their destination
     SHOULD THIS NOT BE ORDER BY FURTHEST AWAY FROM ENTRY
     ***********************************************************************************************/
    private ArrayList<Integer>orderListByColumnWithEntrancesLast(ArrayList<Integer>availableTiles, ArrayList<Integer>entranceList, int width){
        int[][] mArray = new int[availableTiles.size()][2];

        ArrayList<Integer>entryColumns = new ArrayList<>();
        ArrayList<Integer>temp = new ArrayList<>();
        ArrayList<Integer>sorted = new ArrayList<>();

        for(int i = 0; i < entranceList.size(); i++){
            entryColumns.add(entranceList.get(i) % width);
        }

        for(int i = 0; i < availableTiles.size(); i++){
            mArray[i][0] = availableTiles.get(i);
            mArray[i][1] = availableTiles.get(i) % width;
        }

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < mArray.length; j++) {
                if (mArray[j][1] == i) {
                    if(entryColumns.contains(mArray[j][1])){
                        temp.add(mArray[j][0]);
                    }
                    else sorted.add(mArray[j][0]);
                }
            }
        }

        sorted.addAll(temp);

        System.out.println(sorted);

        availableTiles.clear();
        availableTiles = sorted;

        return availableTiles;
    }

    /***************************************************************************
     Orders a list, firstly by the row distance of entryPoint and the freeTiles
     then by the column distance of the entryPoint and the freeTiles
     so that the furthest away from specified value
     (in a 2D list, by column)is first with the closest last
     ***************************************************************************/
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

}
