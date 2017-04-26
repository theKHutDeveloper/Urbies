package com.development.knowledgehut.urbies.Objects;

import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;


public class ObjectPathCreator {
    private int element;
    private Point position;
    private ArrayList<Point> paths = new ArrayList<>();

    public void setElement(int element){
        this.element = element;
    }

    public int getElement(){
        return this.element;
    }

    public void setPosition(Point point){
        this.position = point;
    }

    public Point getPosition(){return this.position;}


    public void addToPath(ArrayList<Point>path){
        paths.addAll(path);
    }

    public ArrayList<Point> getPath(){
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