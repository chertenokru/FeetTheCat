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
    protected java.util.List<ImageData> imageList = new ArrayList();
    protected BufferedImage background;
    private long lastTime = System.nanoTime();
    private long _lastTime ;
    volatile  protected int fpsCount = 0;
    volatile protected int fps = 0;
    private boolean drawFPS = true;

    public DrawPanel(int x,int y) {
        super();
        panelWidth = x;
        panelHeight = y;
        background = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
        setOpaque(true);
    }

    protected void paintBackground() {
        Graphics2D g = (Graphics2D) background.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLACK);
        Rectangle r = getVisibleRect();
        g.fillRect(r.x, r.y, r.width, r.height);
        ImageData id;

        for (int i = 0; i < imageList.size(); i++) {
            id = imageList.get(i);
            g.drawImage(id.image, id.x, id.y, id.width, id.heigth, null);
        }
        if (drawFPS){
            g.setColor(Color.WHITE);
            g.drawString(Integer.toString(fps),40,10 );
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

    @Override
    protected void paintComponent(Graphics graphics) {
        _lastTime = System.nanoTime();
        if ( (_lastTime - lastTime)>= 1000000000)
        {
            fps = fpsCount;
            lastTime = _lastTime;
            fpsCount = 0;
        } else {
            fpsCount++;
        }
        if (fpsCount >60 ) return;

        paintBackground();
        graphics.drawImage(background, 0, 0, null);
        super.paintChildren(graphics);

    }







}
