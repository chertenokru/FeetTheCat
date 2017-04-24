package ru.chertenok.feedthecat.model;

import ru.chertenok.feedthecat.game.GameData;

import java.awt.*;
import java.util.Random;

/**
 * Created by 13th on 20-Apr-17.
 */
public class Bowl extends Sprite {
    public static final int STATUS_FULL = 0;
    public static final int STATUS_MIDDLE = 1;
    public static final int STATUS_EMPTY = 2;
    public static final int STATUS_COUNT = 3;
    private int position = 0;
    private int maxposition = GameData.catCount;
    private int step = 70;
    private int upStep = 70;


    private Font font;
    // счётчик
    private long num = 1;
    private Random rand = new Random();

    // картинка со спрайтами
    public static Image spriteImage;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
      }

    public int getPosition() {
        return position;
    }


    public void positionUp() {
        if (position > 0) {
            position--;}
            y = step * position + upStep;

    }

    public void positionDown() {
        if (position < maxposition-1) {
            position++;
            y = step * position +upStep;
        }

    }


    public void setEmpty(){
        switch (status) {
            case Bowl.STATUS_FULL: {
                setStatus(Bowl.STATUS_MIDDLE);
                break;
            }
            case Bowl.STATUS_MIDDLE: {
                setStatus(Bowl.STATUS_EMPTY);
                break;
            }
        }
    }

    public void setFull(){
        switch (status) {
            case Bowl.STATUS_EMPTY: {
                setStatus(Bowl.STATUS_MIDDLE);
                break;
            }
            case Bowl.STATUS_MIDDLE: {
                setStatus(Bowl.STATUS_FULL);
                break;
            }
        }
    }

    public boolean isEmpty(){
        return status == Bowl.STATUS_EMPTY;
    }



    public Bowl() {
        super();
        if (spriteImage == null ) spriteImage = loadImage("/ru/chertenok/feedthecat/images/bowl.png");
        spriteSizeX = 50;
        spriteSizeY = 26;

        // статусы-состояния
        pointImage = new Point[STATUS_COUNT][];
        // для каждого состояния прописываем координаты каждой стадии анимации

        pointImage[STATUS_MIDDLE] = new Point[1];
        pointImage[STATUS_MIDDLE][0]= new Point(1,0);
        pointImage[STATUS_EMPTY] = new Point[1];
        pointImage[STATUS_EMPTY][0]= new Point(2,0);
        pointImage[STATUS_FULL] = new Point[1];
        pointImage[STATUS_FULL][0]= new Point(0,0);

        // начальные статус
        status = 0;
        // случайная стадия анимации
        currentStage = rand.nextInt(pointImage[status].length);
        x = 0;
        y = 0;
        moveDX = 0;
        moveDY = 0;


    }

    // отрисовка
    public void draw(Graphics2D g ){
        // вычисляем координаты картинки что надо отрисовать
        int x1 = pointImage[status][currentStage].x*spriteSizeX;
        int y1 = pointImage[status][currentStage].y*spriteSizeY;
        //рисуем
        g.drawImage(spriteImage,x,y,x+spriteSizeX,y+spriteSizeY,x1,y1,x1+spriteSizeX,y1+spriteSizeY,null);

    }

    // обновление состояния
    public void updateStage(){

        long time = System.nanoTime();
        // проверяем чтоб не частить прошло ли положенное время задержки
        if ((time-timeLastSpriteUpdate) < GameData.stageDelay) return ;
        // следующая стадия анимации по кругу
        //if (currentStage < pointImage[status].length-1) currentStage ++; else currentStage = 0;

         switch (getStatus()) {
             case  Bowl.STATUS_FULL:{
                 setStatus(Bowl.STATUS_MIDDLE);
                 break;

         }
         case Bowl.STATUS_MIDDLE:{
             setStatus(Bowl.STATUS_EMPTY);
             break;

            }
            default: {
                 setStatus(Bowl.STATUS_FULL);
         }
        }
        // если ...
        // передвигаем
        x += moveDX;
        y += moveDY;
        // обновляем время обновления
        timeLastSpriteUpdate  = System.nanoTime();


    }

}
