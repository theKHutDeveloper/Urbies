package com.development.knowledgehut.urbies;


import android.graphics.Point;

import org.junit.Test;

import java.util.ArrayList;

public class PathMovement {

    @Test
    public void testLine()throws Exception{
        ArrayList<int[]>result = findLine(100, 210, 105, 210);
        for(int i = 0; i < result.size(); i++){
            System.out.println(result.get(i)[0]);
            System.out.println(result.get(i)[1]);
        }
        assert(true);
    }

    private ArrayList<int[]> findLine(int x0, int y0, int x1, int y1){
        ArrayList<int[]>spritePath = new ArrayList<>();

        int sy = (y0 < y1) ? 1 : -1;
        int sx = (x0 < x1) ? 1 : -1;


        while(true) {
            int[] c = new int[2];
            c[0] = x0;
            c[1] = y0;
            spritePath.add(c);
            if (x0 == x1 && y0 == y1) {
                break;
            }

            if (x0 == x1) {
                y0 = y0 + sy;

            }
            if (y0 == y1) {
                x0 = x0 + sx;
            }

            if(y0 != y1 && x0 != x1){
                break;
            }
        }
        return spritePath;
    }
}
