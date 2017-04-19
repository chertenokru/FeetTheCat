package ru.chertenok.feedthecat.game;

import javafx.scene.shape.*;
import ru.chertenok.feedthecat.Main;
import ru.chertenok.feedthecat.model.DrawPanel;
import ru.chertenok.feedthecat.model.Cat;
import ru.chertenok.feedthecat.model.ImageData;
import ru.chertenok.feedthecat.ttf.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by 13th on 17-Apr-17.
 */
public class GamesPanel extends DrawPanel {

    private BufferedImage backgroundFon;
    private Cat[] cats = new Cat[8];
    private JPanel userPanel;
    private ImageData id_welcome;
    private ImageData id_wait;
    private JButton b_start;
    private JComboBox cb_cats;
    private JComboBox cb_money;
    private JLabel l_userMoney;
    private JButton b_setting;
    private SettingPanel sp;




    public GamesPanel() {
        super(800, 600);
        GameData.status = GameData.STATUS_WAIT;


        setLayout(new BorderLayout());
        buildBackground();
        userPanel = new JPanel();
        //setOpaque(true);
        userPanel.setSize(800, 50);
        l_userMoney = new JLabel("У Вас денег :  "+ GameData.userMoney+"   ");
        l_userMoney.setForeground(Color.BLUE);
        userPanel.add(l_userMoney);
        String[] cat_value =  {"1","2","3","4","5","6"};
        int[] cat_int = {0,1,2,3,4,5,6};
        String[] money_value = {"1","5","20","50"};
        int[] money_int = {1,5,20,50};
        userPanel.add(new JLabel("котоЛошадь:  "));
        cb_cats = new JComboBox(cat_value);
        userPanel.add(cb_cats);
        cb_money = new JComboBox(money_value);
        userPanel.add(new JLabel("ставка:   "));
        userPanel.add(cb_money);
        b_start = new JButton("Start !");
        b_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // запуск
                if (GameData.status == GameData.STATUS_WAIT) {
                    GameData.userCatNum = cat_int[cb_cats.getSelectedIndex()];
                    GameData.userStavka = money_int[cb_money.getSelectedIndex()];
                    for (int i = 0; i < GameData.catCount; i++) {
                        cats[i].setStatus(Cat.STATUS_RUN);
                        b_start.setEnabled(false);
                        imageList.remove(id_welcome);
                        GameData.status = GameData.STATUS_RUN;

                    }
                }
                // продолжить
                if (GameData.status == GameData.STATUS_WIN) {
                    GameData.status = GameData.STATUS_WAIT;
                    b_start.setText("Start !");
                    imageList.add(id_welcome);
                    for (int i = 0; i < GameData.catCount; i++) {
                        cats[i].setX(50);
                        cats[i].setStatus(Cat.STATUS_WAIT);

                        sleep(10);
                    }
                    repaint();
                }



                }
        });
        userPanel.add(b_start);
        userPanel.add(new JLabel("      "));
        b_setting = new JButton("Настройка");
        b_setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (sp == null)sp = new SettingPanel();
                sp.setVisible(true);
                sp.setAlwaysOnTop(true);
            }
        });
        userPanel.add(b_setting);

//        userPanel.setBorder(BorderFactory.createBevelBorder(5));
        userPanel.setOpaque(true);
        add(userPanel, BorderLayout.SOUTH);

        new Thread(this).start();

    }

    private void buildBackground() {
        backgroundFon = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
        ImageIcon imageI = new ImageIcon(Main.class.getResource("/ru/chertenok/feedthecat/images/grass.jpg"));
        Graphics2D g = DrawPanel.initGraphics(backgroundFon);

        for (int i = 0; i <= panelWidth / imageI.getIconWidth(); i++)
            for (int j = 0; j <= panelHeight / imageI.getIconHeight(); j++) {
                g.drawImage(imageI.getImage(), i * imageI.getIconWidth(), j * imageI.getIconHeight(), null);
            }
        imageList.add(new ImageData(backgroundFon, 0, 0, backgroundFon.getWidth(), backgroundFon.getHeight()));
        BufferedImage image2 = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = DrawPanel.initGraphics(image2);
        g2.setFont(CustomFonts.getCustomFont(3, Font.ITALIC, 30));
        String s = "Минутку !";
        Rectangle rect = CustomFonts.getTextCenterInImage(s, image2, g2);
        g2.drawString(s, rect.x, rect.y);
        g2.drawString(s, rect.x, rect.y + 80);

        id_wait = new ImageData(image2, 0, 0, 800, 600);

        imageList.add(id_wait);
        repaint();


    }


    @Override
    public void run() {
        repaint();
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = DrawPanel.initGraphics(image);
        Random r = new Random();
        for (int i = 0; i < GameData.catCount; i++) {
            cats[i] = new Cat();
            cats[i].setX(50);
            cats[i].setY(i * 70);
            //   cats[i].setMoveDX(r.nextInt(8)+8);
            //    cats[i].drawCat(g);
        }
//        cats[1].setStatus(Cat.STATUS_RUN);
//        cats[4].setStatus(Cat.STATUS_RUN);
//        cats[5].setStatus(Cat.STATUS_RUN);
//        cats[2].setStatus(Cat.STATUS_LOSER);
//        cats[3].setStatus(Cat.STATUS_WINNER);
        imageList.add(new ImageData(image, 0, 0, 800, 600));
        repaint();


        BufferedImage image1 = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g1 = DrawPanel.initGraphics(image1);

        g1.setColor(Color.YELLOW);
        g1.drawRect(700, 2, 1, 500);
        g1.setColor(Color.WHITE);
        g1.drawRect(50, 2, 1, 500);
        g1.setFont(CustomFonts.getCustomFont(3, 30));
        for (int i = 0; i < GameData.catCount; i++) {
            g1.drawString(Integer.toString(i + 1), 20, 71 * i + 50);
        }
        imageList.add(new ImageData(image1, 0, 0, 800, 600));

        BufferedImage image2 = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = DrawPanel.initGraphics(image2);
        g2.setFont(CustomFonts.getCustomFont(3, Font.ITALIC, 30));
        String s = "Укажите Вашу ставку и";

        Rectangle rect = CustomFonts.getTextCenterInImage(s, image2, g2);
        g2.drawString(s, rect.x, rect.y);
        s = "и \n выберите номер котолошади!";
        rect = CustomFonts.getTextCenterInImage(s, image2, g2);
        g2.drawString(s, rect.x, rect.y + 80);

        id_welcome = new ImageData(image2, 0, 0, 800, 600);

        imageList.add(id_welcome);

        sleep(100);
        boolean f;
        int num = 0;
        imageList.remove(id_wait);
        while (true) {
            f = false;

           // repaint();
            sleep(5);
            //  g.fillRect(50,50,150,150);
            //  g.drawImage(ik.getImage(),0,0,null);
            g.setColor(new Color(255, 255, 255, 0));
            g.setComposite(AlphaComposite.Src);
            g.fillRect(0, 0, panelWidth, panelHeight);

            for (int i = 0; i < GameData.catCount; i++) {
                if (GameData.status == GameData.STATUS_RUN && cats[i].getX()>636+50) {
                    GameData.status = GameData.STATUS_WIN;
                    GameData.winCatNum = i;
                    f = true;
                    break;

                }

                cats[i].updateStage();
                if (cats[i].getStatus() == Cat.STATUS_RUN && (num % GameData.freqRandom) == 0)
                    cats[i].setMoveDX(r.nextInt((GameData.maxSpeed- GameData.minSpeed)+1) + GameData.minSpeed);
                cats[i].drawCat(g);  }
             repaint();
           // this.paintComponent(this.getGraphics());
            if (GameData.status == GameData.STATUS_WIN && f) updateWin();
            num++;
        }
    }

    private void updateWin() {
        //GameData.status = GameData.STATUS_WAIT;
        if (GameData.winCatNum == GameData.userCatNum) {
            GameData.userMoney += GameData.userStavka;
        } else {
            GameData.userMoney -= GameData.userStavka;

        }
        l_userMoney.setText("У Вас денег :"+ GameData.userMoney);
        b_start.setText((GameData.winCatNum == GameData.userCatNum)?"Вы выиграли, Нажмите для продолжения":" Вы проиграли, Нажмите для продолжения");
        b_start.setEnabled(true);
        for (int i = 0; i < GameData.catCount; i++) {
            if (i == GameData.winCatNum) {
                cats[i].setStatus(Cat.STATUS_WINNER);
            } else {
                cats[i].setStatus(Cat.STATUS_LOSER);
            }

        }
    }
}
