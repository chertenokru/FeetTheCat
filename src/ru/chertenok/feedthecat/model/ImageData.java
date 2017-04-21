package ru.chertenok.feedthecat.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 13th on 15.04.2017.
 */
public class ImageData {
    public Image image;
    public int x;
    public int y;
    public int width;
    public int heigth;
    public boolean visible = true;

    public ImageData(Image image, int x, int y, int width, int heigth) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }
    public static void clearImageList(Map<String,ImageData> maps){
        ImageData id;
        for (Map.Entry< String, ImageData > map: maps.entrySet()){
        map.getValue().image = null;
        }
        maps.clear();
    }
}
