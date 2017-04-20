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

    private Font font;
    // счётчик
    private long num = 1;
    private Random rand = new Random();


    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
      }


    public Bowl() {
        super("/ru/chertenok/feedthecat/images/bowl.png");
        spriteSizeX = 56;
        spriteSizeY = 62;

        // статусы-состояния
        pointImage = new Point[STATUS_COUNT][];
        // для каждого состояния прописываем координаты каждой стадии анимации

        pointImage[STATUS_MIDDLE] = new Point[1];
        pointImage[STATUS_MIDDLE][0]= new Point(0,0);
        pointImage[STATUS_EMPTY] = new Point[1];
        pointImage[STATUS_EMPTY][0]= new Point(0,0);
        pointImage[STATUS_FULL] = new Point[1];
        pointImage[STATUS_FULL][0]= new Point(0,0);

        // начальные статус
        status = 0;
        // случайная стадия анимации
        currentStage = rand.nextInt(pointImage[status].length);
        x = 50;
        y = 50;
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
        if (currentStage < pointImage[status].length-1) currentStage ++; else currentStage = 0;
        // если ...
        // передвигаем
        x += moveDX;
        y += moveDY;
        // обновляем время обновления
        timeLastSpriteUpdate  = System.nanoTime();


    }

}
