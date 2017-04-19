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
    private String res = "/ru/chertenok/feedthecat/images/cat.png";
    private int spriteSizeX = 56;
    private int spriteSizeY = 62;
    private int currentStage;
    private long updateCount = 0;
    private long sumSpeed;
    private int speed;
    private Font font;
    private int status = 0;
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_RUN = 1;
    public static final int STATUS_WINNER = 2;
    public static final int STATUS_LOSER = 3;
    public static final int STATUS_COUNT = 4;

    private Point[][] pointImage;
    private Random rand = new Random();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {

        currentStage = rand.nextInt(pointImage[status].length-1);
        this.status = status;
        if (status == STATUS_RUN) moveDX = 5; else moveDX = 0;
    }

    public Cat() {
        ImageIcon ik = new ImageIcon(getClass().getResource(res));
        this.spriteImage = ik.getImage();
        spriteDelay = GameData.stageDelay;
        timeLastSpriteUpdate = System.nanoTime();
         font = CustomFonts.getCustomFont(2,12);
        // ствтусы
        pointImage = new Point[STATUS_COUNT][];


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

        pointImage[STATUS_RUN] = new Point[2];
        pointImage[STATUS_RUN][0]= new Point(0,0);
        pointImage[STATUS_RUN][1]= new Point(1,0);

        pointImage[STATUS_WINNER] = new Point[4];
        pointImage[STATUS_WINNER][0]= new Point(3,1);
        pointImage[STATUS_WINNER][1]= new Point(3,1);
        pointImage[STATUS_WINNER][2]= new Point(4,1);
        pointImage[STATUS_WINNER][3]= new Point(4,1);

        pointImage[STATUS_LOSER] = new Point[6];
        pointImage[STATUS_LOSER][0]= new Point(2,0);
        pointImage[STATUS_LOSER][1]= new Point(2,0);
        pointImage[STATUS_LOSER][2]= new Point(3,0);
        pointImage[STATUS_LOSER][3]= new Point(3,0);
        pointImage[STATUS_LOSER][4]= new Point(2,1);
        pointImage[STATUS_LOSER][5]= new Point(2,1);


        status = 0;

        currentStage = rand.nextInt(pointImage[status].length);

        x = 50;
        y = 50;
        moveDX = 0;
        moveDY = 0;

    }



    public void drawCat(Graphics2D g ){
        int x1 = pointImage[status][currentStage].x*spriteSizeX;
        int y1 = pointImage[status][currentStage].y*spriteSizeY;
        int z = 0;

        //g.setColor(new Color(255,255,255,0));
        //g.setComposite(AlphaComposite.Clear);
       // g.fillRect(x,y,x+spriteSizeX,y+spriteSizeY);
        g.setComposite(AlphaComposite.Src);
        g.drawImage(spriteImage,x,y,x+spriteSizeX,y+spriteSizeY,x1-z,y1,x1+spriteSizeX,y1+spriteSizeY,null);
        if (moveDX != 0 ) {
            g.setColor(Color.WHITE);
            g.draw3DRect(x+10, y + spriteSizeY, spriteSizeX-10, 5, true);
            if (  moveDX < 10) g.setColor(Color.RED);
            if (moveDX > 9 && moveDX < 17) g.setColor(Color.YELLOW);
            if (moveDX > 16) g.setColor(Color.GREEN);
            g.fillRect(x + 11, y + spriteSizeY + 1, spriteSizeX - 11,   5 - 1);
           g.setFont(font);
           g.drawString(Integer.toString(moveDX),x-5 ,y+spriteSizeY+5 );

        }

    }



    public void updateStage(){

        long time = System.nanoTime();
        if ((time-timeLastSpriteUpdate) < GameData.stageDelay) return;
        if (currentStage < pointImage[status].length-1) currentStage ++; else currentStage = 0;
     //   if (currentStage == 0) currentStage = 1; else currentStage = 0;
        x += moveDX;
        y += moveDY;
        if (currentStage == STATUS_RUN)
        {
            //updateCount++;
            //sumSpeed += moveDX;
            //speed = (int)( sumSpeed / updateCount);
        }
        timeLastSpriteUpdate  = System.nanoTime();
    }

}
