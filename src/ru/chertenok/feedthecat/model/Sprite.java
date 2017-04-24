package ru.chertenok.feedthecat.model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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

    public int getSpriteSizeX() {
        return spriteSizeX;
    }

    public int getSpriteSizeY() {
        return spriteSizeY;
    }

    public Sprite() {
         }

    public Image loadImage(String spriteMapPath){
            // грузим картинку со справйтами
            ImageIcon ik = new ImageIcon(getClass().getResource(spriteMapPath));
            BufferedImage bf = new BufferedImage(ik.getIconWidth(),ik.getIconHeight(),BufferedImage.TYPE_INT_ARGB);
            bf.getGraphics().drawImage(ik.getImage(),0,0,ik.getIconWidth(),ik.getIconHeight(),null);
            ik = null;
            res = spriteMapPath;
            return  bf;

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
