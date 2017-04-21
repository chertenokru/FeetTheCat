package ru.chertenok.feedthecat.game;

import ru.chertenok.feedthecat.Main;
import ru.chertenok.feedthecat.model.Bowl;
import ru.chertenok.feedthecat.model.Cat;
import ru.chertenok.feedthecat.model.DrawPanel;
import ru.chertenok.feedthecat.model.ImageData;
import ru.chertenok.feedthecat.ttf.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by 13th on 17-Apr-17.
 */
public class GamesPanel2 extends DrawPanel {

    private BufferedImage backgroundFon;
    private Cat[] cats = new Cat[8];
    private Bowl bowl;
    private SettingPanel sp;
    private Rectangle _repaintBound;

    private ImageIcon imageBag;
    private ImageIcon imageHeart;
    // жизни
    private int heart = 3;

    private int marginTop = 30;

    private Rectangle strStartRect;


    public GamesPanel2() {
        super(800, 600);
        GameData.status = GameData.STATUS_WAIT;
        setLayout(new BorderLayout());


        _repaintBound = new Rectangle(0, 0, panelWidth, panelHeight);
        setRepaintBound(_repaintBound);
        buildBackground();
        // запускаем поток, который будет управлять всем происходящим ( - run)
        new Thread(this).start();
    }

    // фоновый слой с травой
    private void buildBackground() {
        backgroundFon = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_RGB);
        ImageIcon imageI = new ImageIcon(Main.class.getResource("/ru/chertenok/feedthecat/images/grass.jpg"));
        Graphics2D g = DrawPanel.initGraphics(backgroundFon);
        for (int i = 0; i <= panelWidth / imageI.getIconWidth(); i++)
            for (int j = 0; j <= panelHeight / imageI.getIconHeight(); j++) {
                g.drawImage(imageI.getImage(), i * imageI.getIconWidth(), j * imageI.getIconHeight(), null);
            }
        // добавляем в стэк отрисовки самым нижним слоем
        imageList.put("background", new ImageData(backgroundFon, 0, 0, backgroundFon.getWidth(), backgroundFon.getHeight()));

        imageBag = new ImageIcon(Main.class.getResource("/ru/chertenok/feedthecat/images/bag1.png"));
        g.drawImage(imageBag.getImage(), 700, 480, null);
        imageHeart = new ImageIcon(Main.class.getResource("/ru/chertenok/feedthecat/images/heart1.png"));
        g.dispose();
        repaint();


    }


    @Override
    public void run() {


        fpsIsLimit = false;

        // инициализация котов
        for (int i = 0; i < GameData.catCount; i++) {
            cats[i] = new Cat(false);
            cats[i].setX(50);
            cats[i].setY(i * 70 + marginTop);
        }

        bowl = new Bowl();
        bowl.setX(730);
        bowl.setY(70 + marginTop);
        bowl.setStatus(Bowl.STATUS_EMPTY);


        BufferedImage image = new BufferedImage(_repaintBound.width, _repaintBound.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = DrawPanel.initGraphics(image);
        // линия финиша
        g.setColor(Color.YELLOW);
        g.drawRect(700, 2, 1, 500);
        // линия старта
        g.setColor(Color.WHITE);
        g.drawRect(50, 2, 1, 500);
        // рисуем номера котов
        g.setFont(CustomFonts.getCustomFont(3, 30));
        for (int i = 0; i < GameData.catCount; i++) {
            g.drawString("" + (i + 1), 20, 71 * i + marginTop + 50);
        }
        imageList.put("gameDate", new ImageData(image, 0, 0, panelWidth, panelHeight));
        g.dispose();

        // welcome сообщение
        image = new BufferedImage(_repaintBound.width, _repaintBound.height, BufferedImage.TYPE_INT_ARGB);
        g = DrawPanel.initGraphics(image);
        g.setFont(CustomFonts.getCustomFont(3, Font.ITALIC, 20));
        String s = "Используйте клавиши (W,UP) и (S,DOWN) ";
        // получаем размеры надписи и её координаты для надписи по центру экрана
        Rectangle rect = CustomFonts.getTextCenterInImage(s, image, g);
        g.drawString(s, rect.x, rect.y + 40);
        s = "для управления миской";
        // получаем размеры надписи и её координаты для надписи по центру экрана
        rect = CustomFonts.getTextCenterInImage(s, image, g);
        g.drawString(s, rect.x, rect.y);
        s = "и \n (Пробел) - наполнить миску";
        rect = CustomFonts.getTextCenterInImage(s, image, g);
        g.drawString(s, rect.x, rect.y + 80);
        // сохраняем ссылку на слой, чтоб его можно было удалять и добавлять в процессе игры
        imageList.put("welcome", new ImageData(image, 0, 0, _repaintBound.width, _repaintBound.height));
        g.dispose();

        // а на этом слое мы будем рисовать наших котиков
        image = new BufferedImage(_repaintBound.width, _repaintBound.height, BufferedImage.TYPE_INT_ARGB);
        g = DrawPanel.initGraphics(image);
        imageList.put("game", new ImageData(image, 0, 0, _repaintBound.width, _repaintBound.height));

        repaint();
        sleep(20);

        int keycode;
        Font font = CustomFonts.getCustomFont(3, Font.BOLD, 40);
        Font font1 = CustomFonts.getCustomFont(3, Font.BOLD, 42);
        g.setFont(font);
        String strStart = "START !";
        strStartRect = CustomFonts.getTextCenterInImage(strStart, image, g);

        boolean f;
        long num = 0;
        Point click;

        //imageList.remove(id_wait);
        // вечный игровой цикл
        while (true) {
            // нет победы
            f = false;

            // repaint();
            //sleep(GameData.sleepTime);
            if (!fpsIsLimit || (fpsIsLimit && fpsCount < fpsMax)) {

                // стираем старый экран с котиками, для этого создаём прозрачный цвет
                g.setColor(new Color(255, 255, 255, 0));
                // и говорим что при отрисовке важнее цвет которым рисуем, чем тот на котором рисуем, иначе стирать не будет
                g.setComposite(AlphaComposite.Src);
                // стираем
                g.fillRect(0, 0, _repaintBound.width, _repaintBound.height);
            }
            // контролируем наших котиков, не добежал ли кто до финиша
            for (int i = 0; i < GameData.catCount; i++) {
                // если забег и кот преодалел финишную линию
                if (GameData.status == GameData.STATUS_RUN && cats[i].getX() > 700) {

                    if (bowl.getPosition() == i && !bowl.isEmpty()) {
                        bowl.setEmpty();
                    } else {
                        heart--;
                        if (heart < 0) {
                            //финиш
                            GameData.status = GameData.STATUS_WIN;
                            for (int j = 0; j < GameData.catCount; j++) {
                                cats[j].setStatus(Cat.STATUS_LOSER);
                                strStart = "RESTART";
                                f = true;
                                break;
                            }
                        }

                    }

                    cats[i].setX(50);

                }
                // обновляем котов (анимация + сдвиг координат )
                cats[i].updateStage();
                // отрисовка кота на нашем слое
                cats[i].draw(g);
            }
            if (isKeyPressed()) {
                keycode = getKeyCode();
                switch (keycode) {
                    case 38:
                    case 87: {
                        bowl.positionUp();
                        break;
                    }
                    case 40:
                    case 83: {
                        bowl.positionDown();

                        break;
                    }
                    case 32: {
                        bowl.setFull();
                    }
                }
            }

            bowl.draw(g);

            for (int j = 0; j < heart; j++) {
                g.drawImage(imageHeart.getImage(), 580 + j * 35, 20, null);
            }
            g.setFont(font);

            //System.out.println(" mouse - "+getMouseMoveXY().x+" rect "+(50+strStartRect.width));
            if (50 < getMouseMoveXY().x && (strStartRect.width + 50) > getMouseMoveXY().x &&
                    550 > getMouseMoveXY().y && (550 - strStartRect.height) < getMouseMoveXY().y
                    ) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }

            if (isMouseClicked()) {
                click = getMouseClickedXY();

                //  запуск игры
                if (50 < click.x && (strStartRect.width + 50) > click.x &&
                        550 > click.y && (550 - strStartRect.height) < click.y
                        ) {

                    if (GameData.status == GameData.STATUS_WAIT) {
                        for (int i = 0; i < GameData.catCount; i++) {
                            cats[i].setStatus(Cat.STATUS_RUN);
                        }
                        imageList.get("welcome").visible = false;
                        GameData.status = GameData.STATUS_RUN;
                    }
                    // продолжить
                    if (GameData.status == GameData.STATUS_WIN) {
                        GameData.status = GameData.STATUS_WAIT;
                        imageList.get("welcome").visible = true;
                        for (int i = 0; i < GameData.catCount; i++) {
                            cats[i].setX(50);
                            cats[i].setStatus(Cat.STATUS_WAIT);
                        }
                        heart = 3;
                        strStart = "START !";
                    }


                }
            }

            g.drawString(strStart, 50, 550);

            // repaint();
            // отрисовываем весь стэк слоёв
            try {
                EventQueue.invokeAndWait(() -> paintComponent(getGraphics()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            // this.paintComponent(this.getGraphics());
            // если победа, то сбрасываем всё и обрабатываем победу
            if (GameData.status == GameData.STATUS_WIN && f) updateWin();
        }
    }

    // обработка победы

    private void updateWin() {
        // обновляем счёт пользователя
        if (GameData.winCatNum == GameData.userCatNum) {
            GameData.userMoney += GameData.userStavka;
        } else {
            GameData.userMoney -= GameData.userStavka;

        }
        // выводим на экран
        // меняем надпись на кнопке, можно было бы сделать надписью и картинкой в отдельном слое на игровом поле, но пока лень
        // включаем кнопку старта
        // меняем состояние котов (вид картинки анимации)
        for (int i = 0; i < GameData.catCount; i++) {
            if (i == GameData.winCatNum) {
                // победитель
                cats[i].setStatus(Cat.STATUS_WINNER);
            } else {
                // лузеры
                cats[i].setStatus(Cat.STATUS_LOSER);
            }

        }
    }

    public int getMaxFPS() {
        //если включено ограничение, то вернем его, иначе 0
        return (fpsIsLimit) ? fpsMax : 0;
    }

    public void setMaxFPS(int fps) {
        fpsIsLimit = fps > 0;
        if (fps != 0) fpsMax = fps;

    }
}
