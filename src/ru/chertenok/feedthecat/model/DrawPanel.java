package ru.chertenok.feedthecat.model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by 13th on 17-Apr-17.
 */
public abstract class DrawPanel extends JPanel implements Runnable {
    protected int panelWidth;
    protected int panelHeight;
    // стэк картинок для отрисовки
    protected java.util.List<ImageData> imageList = new ArrayList();
    // отрисованный стэк картинок
    protected BufferedImage background;
    // темповые для времени
    private long lastTime = System.nanoTime();
    private long _lastTime ;
    // текущий fps
    volatile  protected int fpsCount = 0;
    // fps
    volatile protected int fps = 0;
    private Rectangle repaintBound ;
    // FPS рисовать
    protected boolean drawFPS = true;
    protected int fpsPositionX = 100;
    protected int fpsPositionY = 10;
    protected Color fpsColor = Color.white;
    // ограничитель fps
    volatile protected int fpsMax = 60;
    volatile protected boolean fpsIsLimit = true;

    private Graphics2D g;
    volatile private int keyCode;
    volatile private boolean keyPressed;

    synchronized public int getKeyCode() {
        keyPressed = false;
        return keyCode;
    }

    synchronized public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        System.out.println("key "+ keyCode);
        keyPressed = true;
    }

    synchronized public boolean isKeyPressed() {
        return keyPressed;
    }


    public Rectangle getRepaintBound() {
        return repaintBound;
    }

    public void setRepaintBound(Rectangle repaintBound) {
        this.repaintBound = repaintBound;
        background  = new BufferedImage(repaintBound.width, repaintBound.height, BufferedImage.TYPE_INT_RGB);
        g = initGraphics(background);
    }

    // конструктор
    public DrawPanel(int x,int y) {
        super();
        panelWidth = x;
        panelHeight = y;
        repaintBound = new Rectangle(0,0,panelWidth,panelHeight);
        background = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_RGB);
        g = initGraphics(background);
        //setOpaque(true);
    }

    // начальная стэка
    protected void paintBackground() {
        // очистка
        g.setColor(Color.BLACK);
        g.fillRect(repaintBound.x, repaintBound.y, repaintBound.width, repaintBound.height);

        ImageData id;
        // отрисовываем стэк картинок
        for (int i = 0; i < imageList.size(); i++) {
            id = imageList.get(i);
            g.drawImage(id.image, id.x, id.y, id.width, id.heigth, null);
        }
        // выводим FPS
        if (drawFPS){
            g.setColor(fpsColor);
            g.drawString("FPS: "+fps,fpsPositionX,fpsPositionY);
        }

    }

    /** sleep
     * @param time
     */
    protected void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Извлекаем графикс и настраиваем его
     * @param image
     * @return
     */
    public static Graphics2D initGraphics(Image image){
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g;
    }

    /** отрисовка панели
     * @param graphics
     */
    @Override
    protected void paintComponent(Graphics graphics) {

        // считаем FPS
        _lastTime = System.nanoTime();
        if ( (_lastTime - lastTime)>= 1000000000)
        {
            fps = fpsCount;
            lastTime = _lastTime;
            fpsCount = 0;
        } else {
            // ограничение FPS на отрисовку
            if (fpsIsLimit && fpsCount > fpsMax ) return;

            fpsCount++;
        }

        // построение фона
        paintBackground();
        // отрисовка фона
        graphics.drawImage(background, repaintBound.x, repaintBound.y, repaintBound.width,repaintBound.height, null);
     //   super.paintChildren(graphics);

    }







}
