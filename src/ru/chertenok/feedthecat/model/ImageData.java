package ru.chertenok.feedthecat.model;

import java.awt.*;

/**
 * Created by 13th on 15.04.2017.
 */
public class ImageData {
    public Image image;
    public int x;
    public int y;
    public int width;
    public int heigth;

    public ImageData(Image image, int x, int y, int width, int heigth) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }
    public static void clearImageList(java.util.List<ImageData> ids){
        ImageData id;
        while (ids.size()>0){
            id = ids.get(0);
            ids.remove(0);
            id.image = null;
        }
    }
}
