package ru.chertenok.feedthecat.model;

import java.awt.*;

/**
 * Created by 13th on 17-Apr-17.
 */
public class Sprite {
    protected int imageCount;
    protected int x;
    protected int y;
    protected int moveDX;
    protected int moveDY;
    protected Image spriteImage;
    protected long spriteDelay = 0;
    protected long timeLastSpriteUpdate = 0;
    protected Point[] pointImage;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMoveDX() {
        return moveDX;
    }

    public void setMoveDX(int moveDX) {
        this.moveDX = moveDX;
    }

    public int getMoveDY() {
        return moveDY;
    }

    public void setMoveDY(int moveDY) {
        this.moveDY = moveDY;
    }
}
