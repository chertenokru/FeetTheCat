package ru.chertenok.feedthecat.logo;

import ru.chertenok.feedthecat.Main;
import ru.chertenok.feedthecat.model.DrawPanel;
import ru.chertenok.feedthecat.model.ImageData;
import ru.chertenok.feedthecat.ttf.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by 13th on 15.04.2017.
 */
public class LogoPanel extends DrawPanel {
    private ImageData id_del;

    public LogoPanel() {
        super(800,600);
        new Thread(this).start();
    }


    @Override
    public void run() {
        Image image = new BufferedImage(panelWidth, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(20, 10, panelWidth - 40, 30);
        Font font = new Font("TimesRoman", Font.PLAIN, 20);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        String s = "Идёт подготовка мозга пользователя к запуску программы...";
        g.setColor(Color.WHITE);
        g.drawString(s, (int) (image.getHeight(null) / 2 - fm.getStringBounds(s, g).getHeight() / 2), (int) (image.getHeight(null) / 2 + fm.getStringBounds(s, g).getWidth() / 2));

        imageList.add(new ImageData(image, 0, panelHeight - image.getHeight(null), image.getWidth(null), image.getHeight(null)));
        this.repaint();
        // прогресс бар
        for (int i = 0; i < image.getWidth(null)-40; i = i + 20) {
            g.setColor(Color.GREEN);
            g.fillRect(20,10,20+i,30);
            g.setColor(Color.WHITE);
            g.drawString(s,(int)(image.getWidth(null)/2 - fm.getStringBounds(s,g).getCenterX()) ,(int)( image.getHeight(null)/2 - fm.getStringBounds(s,g).getCenterY()+4));
            this.repaint();
            sleep(100);
        }

        g.dispose();
        sleep(100);

        ImageIcon imageOk = new ImageIcon(Main.class.getResource("/ru/chertenok/feedthecat/images/ok.png"));
        ImageData id = new ImageData(imageOk.getImage(), (panelWidth / 2 - imageOk.getIconWidth() / 2), (panelHeight / 2 - imageOk.getIconHeight() / 2),
                imageOk.getIconWidth(), imageOk.getIconHeight());

        imageList.add(id);
        this.repaint();
        sleep(100);
        //SoundEngine.playSoundByName("ok");
        // вращение ок
        for (int i = 1; i < 32; i++) {
            int old = id.width;
            if (i<16) {
                id.width = (id.image.getWidth(null) - ((id.image.getWidth(null) / 16) * i));
                id.x += (old - id.width )/2;
            }
            else {
                id.width = (id.width + (id.image.getWidth(null) / 16));
                id.x -= (id.width - old) / 2;
            }

            repaint();
            sleep(50);
        }

        imageOk = null;

        // стираем экран
        image = new BufferedImage(panelWidth, panelHeight + 10, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panelWidth, panelHeight);
        g.setColor(Color.BLUE);
        g.fillRect(0, panelHeight - 10, panelWidth, panelHeight);
        id_del = new ImageData(image, 0, -panelHeight, image.getWidth(null), image.getHeight(null));
        imageList.add(id_del);
        for (int i = 0; i < panelHeight+10; i +=10) {
            id_del.y += 10;
            repaint();
            sleep(50);
        }
        //g.dispose();
        ImageData.clearImageList(imageList);

        image = new BufferedImage(panelWidth, 800, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font customFont1 = CustomFonts.getCustomFont(0, Font.ITALIC | Font.BOLD, 70);
        Font customFont3 = CustomFonts.getCustomFont(2, 20);
        Font customFont2 = CustomFonts.getCustomFont(2, 60);
        Font customFont4 = CustomFonts.getCustomFont(3, 60);

        g.setFont(customFont4);
        s = "ИП* \"CHERTENOK.RU\"";
        g.setColor(Color.yellow);
        Rectangle r = CustomFonts.getTextCenterInImage(s,image,g);
        g.drawString(s, r.x-2,r.height-2);
        g.setColor(Color.blue);
        g.drawString(s, r.x,r.height);
        g.setColor(Color.yellow);
        font = font.deriveFont(12.0f);
        g.setFont(font);
        s = "* ИП - индивидуальный программист";
        g.setColor(Color.yellow);
        //g.drawString(s, (int) (image.getHeight(null) / 2 - fm.getStringBounds(s, g).getHeight() / 2), (int) (image.getHeight(null) / 2 + fm.getStringBounds(s, g).getWidth() / 2));
        r = CustomFonts.getTextCenterInImage(s,image,g);
        g.drawString(s, r.x, 150);

        g.setFont(customFont1);

        s = "ПРЕДСТАВЛЯЕТ";
        g.setColor(Color.WHITE);
        //g.drawString(s, (int) (image.getHeight(null) / 2 - fm.getStringBounds(s, g).getHeight() / 2), (int) (image.getHeight(null) / 2 + fm.getStringBounds(s, g).getWidth() / 2));
        g.drawString(s, 200, 350);
        g.setColor(Color.GREEN);
        //g.drawString(s, (int) (image.getHeight(null) / 2 - fm.getStringBounds(s, g).getHeight() / 2), (int) (image.getHeight(null) / 2 + fm.getStringBounds(s, g).getWidth() / 2));
        g.drawString(s, 200, 350+2);


        imageList.add(new ImageData(image, 0, 0, image.getWidth(null), image.getHeight(null)));
        repaint();
        sleep(3000);


        // стираем экран
        image = new BufferedImage(panelWidth, panelHeight + 10, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panelWidth, panelHeight);
        g.setColor(Color.BLUE);
        g.fillRect(0, panelHeight - 10, panelWidth, panelHeight);
        id_del = new ImageData(image, 0, -panelHeight, image.getWidth(null), image.getHeight(null));
        imageList.add(id_del);
        for (int i = 0; i < panelHeight+10; i +=10) {
            id_del.y += 10;
            repaint();
            sleep(50);
        }

        ImageData.clearImageList(imageList);
        repaint();
        //sleep(300);
        imageOk = new ImageIcon(Main.class.getResource("/ru/chertenok/feedthecat/images/logo1.png"));
        image = new BufferedImage(800,600,BufferedImage.TYPE_INT_ARGB);
        g = DrawPanel.initGraphics(image);
        g.drawImage(imageOk.getImage(),(panelWidth / 2 - imageOk.getIconWidth() / 2), (panelHeight / 2 - imageOk.getIconHeight() / 2),null);

        id = new ImageData(image, 0,0,
                image.getWidth(null), image.getHeight(null));

        g.setFont(customFont4);
        s = "ДЗ \"КОТО ДРОМ\"";
        g.setColor(Color.YELLOW);
        r = CustomFonts.getTextCenterInImage(s,image,g);
        g.drawString(s, r.x-2,100);
        g.setColor(Color.red);
        g.drawString(s, r.x,102);


        imageList.add(id);
        this.repaint();
        sleep(1300);

        for (int i = 1; i < panelHeight/3; i +=3) {
            r = getTopLevelAncestor().getBounds();

            r.y += i;

            r.height -= i*2;

            id.y -=i;
            getTopLevelAncestor().setBounds(r);
            repaint();
            sleep(50);


        }
        ImageData.clearImageList(imageList);
        getTopLevelAncestor(). setVisible(false);




//


    }

}
