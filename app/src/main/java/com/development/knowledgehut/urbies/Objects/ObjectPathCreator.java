package com.development.knowledgehut.urbies.Objects;

import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;


public class ObjectPathCreator {
    private ArrayList<Integer> elements = new ArrayList<>();
    private ArrayList<Point> positions = new ArrayList<>();
    private ArrayList<ArrayList<Path>> paths = new ArrayList<>();

    public void addToElements(int element){
        elements.add(element);
    }

    public void addAllElements(ArrayList<Integer>values){
        elements.addAll(values);
    }
    public ArrayList<Integer> getElements(){
        return elements;
    }

    public void addToPositions(Point point){
        positions.add(point);
    }

    public void addAllPositions(ArrayList<Point>pos){
        positions.addAll(pos);
    }

    public ArrayList<Point> getPositions(){
        return positions;
    }

    public void addToPath(ArrayList<Path>path){
        paths.add(path);
    }

    public ArrayList<ArrayList<Path>> getPaths(){
        return paths;
    }

}

   /*
 Things I need to return
 1. A list of remaining objects that need to be moved down
 2. A list of positions that the remaining objects need to be moved to
 3. A list of paths of how those objects are moved
 4. A list of future objects that will be moved in to replace absent objects (empty and/or matched objects)
 5. A list of positions that the replaced objects need to be moved to
 6. A list of paths of how those objects are to be moved


 */