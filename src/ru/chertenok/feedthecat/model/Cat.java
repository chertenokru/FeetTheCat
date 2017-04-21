package ru.chertenok.feedthecat.model;

import com.sun.beans.editors.IntegerEditor;
import ru.chertenok.feedthecat.game.GameData;
import ru.chertenok.feedthecat.ttf.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created by 13th on 17-Apr-17.
 */
public class Cat extends Sprite {
    // константы статусов, для каждого своя анимация
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_RUN = 1;
    public static final int STATUS_WINNER = 2;
    public static final int STATUS_LOSER = 3;
    // кол-во статусов, почему-то тоже тут
    public static final int STATUS_COUNT = 4;
    private boolean needChangeSpeed = true;
    // картинка со спрайтами
    public static Image spriteImage;

    private Font font;
    // счётчик
    private long num = 1;
    private Random rand = new Random();

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        // при смене статуса, сбрасываем стадию анимации, рандомная чтоб не двигались все синхронно
        currentStage = rand.nextInt(pointImage[status].length-1);
        this.status = status;
        if (status == STATUS_RUN) {
            // если бег то придаём ускорение
            moveDX = rand.nextInt((GameData.maxSpeed - GameData.minSpeed) + 1) + GameData.minSpeed;
        } else
            // иначе наоборот тормозим
            moveDX = 0;
    }

    public Cat(boolean needChangeSpeed) {
        super();
        this.needChangeSpeed = needChangeSpeed;
        if (spriteImage == null ) spriteImage = loadImage("/ru/chertenok/feedthecat/images/cat.png");
        spriteSizeX = 56;
        spriteSizeY = 62;
        spriteDelay = GameData.stageDelay;
        timeLastSpriteUpdate = System.nanoTime();
        //шрифт для вывода скорости кота
         font = CustomFonts.getCustomFont(2,12);
        // статусы-состояния
        pointImage = new Point[STATUS_COUNT][];
        // для каждого состояния прописываем координаты каждой стадии анимации
        // STATUS_WAIT
        pointImage[STATUS_WAIT] = new Point[10];
        pointImage[STATUS_WAIT][0]= new Point(4,0);
        pointImage[STATUS_WAIT][1]= new Point(5,0);
        pointImage[STATUS_WAIT][2]= new Point(6,0);
        pointImage[STATUS_WAIT][3]= new Point(7,0);
        pointImage[STATUS_WAIT][4]= new Point(8,0);
        pointImage[STATUS_WAIT][5]= new Point(5,1);
        pointImage[STATUS_WAIT][6]= new Point(6,1);
        pointImage[STATUS_WAIT][7]= new Point(7,1);
        pointImage[STATUS_WAIT][8]= new Point(8,1);
        pointImage[STATUS_WAIT][9]= new Point(9,1);
        //STATUS_RUN
        pointImage[STATUS_RUN] = new Point[2];
        pointImage[STATUS_RUN][0]= new Point(0,0);
        pointImage[STATUS_RUN][1]= new Point(1,0);
        // STATUS_WINNER
        pointImage[STATUS_WINNER] = new Point[4];
        pointImage[STATUS_WINNER][0]= new Point(3,1);
        pointImage[STATUS_WINNER][1]= new Point(3,1);
        pointImage[STATUS_WINNER][2]= new Point(4,1);
        pointImage[STATUS_WINNER][3]= new Point(4,1);
        // STATUS_LOSER
        pointImage[STATUS_LOSER] = new Point[6];
        pointImage[STATUS_LOSER][0]= new Point(2,0);
        pointImage[STATUS_LOSER][1]= new Point(2,0);
        pointImage[STATUS_LOSER][2]= new Point(3,0);
        pointImage[STATUS_LOSER][3]= new Point(3,0);
        pointImage[STATUS_LOSER][4]= new Point(2,1);
        pointImage[STATUS_LOSER][5]= new Point(2,1);
        // начальные статус
        status = 0;
        // случайная стадия анимации
        currentStage = rand.nextInt(pointImage[status].length);
        x = 50;
        y = 50;
        moveDX = 0;
        moveDY = 0;

    }

    // отрисовка кота
    @Override
    public void draw(Graphics2D g ){
        // вычисляем координаты картинки что надо отрисовать
        int x1 = pointImage[status][currentStage].x*spriteSizeX;
        int y1 = pointImage[status][currentStage].y*spriteSizeY;
        //рисуем
        g.drawImage(spriteImage,x,y,x+spriteSizeX,y+spriteSizeY,x1,y1,x1+spriteSizeX,y1+spriteSizeY,null);
        // если кот движется то рисуем под ним полоску и скорость
        if (moveDX != 0 ) {
            g.setColor(Color.WHITE);
            g.drawRect(x+10, y + spriteSizeY, spriteSizeX-10, 5);
            if (  moveDX < 10) g.setColor(Color.RED);
            if (moveDX > 9 && moveDX < 17) g.setColor(Color.YELLOW);
            if (moveDX > 16) g.setColor(Color.GREEN);
            g.fillRect(x + 11, y + spriteSizeY + 1, spriteSizeX - 11,   5 - 1);
           g.setFont(font);
           g.drawString(Integer.toString(moveDX),x-5 ,y+spriteSizeY+5 );
        }

    }

    // обновление состояния кота
    public void updateStage(){

        long time = System.nanoTime();
        // проверяем чтоб не частить прошло ли положенное время задержки
        if ((time-timeLastSpriteUpdate) < GameData.stageDelay) return ;
        // следующая стадия анимации по кругу
        if (currentStage < pointImage[status].length-1) currentStage ++; else currentStage = 0;
        // если забег
        if (status == STATUS_RUN)
        {
            // и прошло GameData.freqRandom итераций, то генерим новую скорость для всех котов
            if ( needChangeSpeed && (num % GameData.freqRandom) == 0) {
                // новая скорость по горизонтали
                moveDX = rand.nextInt((GameData.maxSpeed - GameData.minSpeed) + 1) + GameData.minSpeed;
            }
            num++;
        }
        // передвигаем кота
        x += moveDX;
        y += moveDY;
        // обновляем время обновления
        timeLastSpriteUpdate  = System.nanoTime();


    }

}
