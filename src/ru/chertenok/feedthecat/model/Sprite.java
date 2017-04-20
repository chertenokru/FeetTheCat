package ru.chertenok.feedthecat.model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 13th on 17-Apr-17.
 */
public abstract class Sprite {
    // позиция отрисовки
    protected int x;
    protected int y;
    // движение
    protected int moveDX;
    protected int moveDY;
    // картинка со спрайтами
    protected Image spriteImage;
    // задержка между стадиями анимации
    protected long spriteDelay = 0;
    protected long timeLastSpriteUpdate = 0;
    // массив координат спрайтов в общей картинке [статус][стадия анимации]
    protected Point[][] pointImage;
    // картинка со спрайтами
    protected String res = "";
    // размер спрайта
    protected int spriteSizeX = 1;
    protected int spriteSizeY = 1;
    // текущая стадия анимации
    protected int currentStage;
    // статус состояний спрайта
    protected int status = 0;


    public Sprite(String spriteMapPath) {
        // грузим картинку со справйтами
        ImageIcon ik = new ImageIcon(getClass().getResource(spriteMapPath));
        this.spriteImage = ik.getImage();
        res = spriteMapPath;
    }

    abstract public void draw(Graphics2D g );

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
