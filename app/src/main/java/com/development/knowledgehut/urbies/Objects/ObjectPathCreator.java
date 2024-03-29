package com.development.knowledgehut.urbies.Objects;

import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.LinkedList;


public class ObjectPathCreator {
    private int element;
    private Point position;
    private ArrayList<Point> paths = new ArrayList<>();
    private int futureElement;

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

    public void addPathAt(int index, ArrayList<Point>path){
        paths.addAll(index, path);
    }

    public void addToPath(ArrayList<Point>path){
        paths.addAll(path);
    }

    public void printPath(){
        System.out.println(element);
        for(int i = 0; i < paths.size(); i++){
            System.out.println("[" + paths.get(i) + "][" + paths.get(i) + "]");
        }
    }


    public ArrayList<Point> getPath(){
        return paths;
    }

    public void setFutureElement(int e){
        this.futureElement = e;
    }

    public int getFutureElement(){
        return this.futureElement;
    }

    public void print(){
        System.out.println("element = "+element);
        System.out.println("position = " +position );
        System.out.println("start & end path " + paths.get(0) + ", " + paths.get(paths.size() -1));
        System.out.println("future location = "+ futureElement);

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