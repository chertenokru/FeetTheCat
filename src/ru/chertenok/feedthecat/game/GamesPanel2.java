package ru.chertenok.feedthecat.game;

import ru.chertenok.feedthecat.Main;
import ru.chertenok.feedthecat.model.Bowl;
import ru.chertenok.feedthecat.model.Cat;
import ru.chertenok.feedthecat.model.DrawPanel;
import ru.chertenok.feedthecat.model.ImageData;
import ru.chertenok.feedthecat.ttf.CustomFonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by 13th on 17-Apr-17.
 */
public class GamesPanel2 extends DrawPanel {

    private BufferedImage backgroundFon;
    private Cat[] cats = new Cat[8];
    private Bowl bowl ;
    private JPanel userPanel;
    private ImageData id_welcome;
    private ImageData id_wait;
    private JButton b_start;
    private JComboBox cb_cats;
    private JComboBox cb_money;
    private JLabel l_userMoney;
    private JButton b_setting;
    private SettingPanel sp;
    volatile  private int userPanelHeight = 50;
    private Rectangle _repaintBound;
    private boolean isKeyPressed ;
    private int key;



    public GamesPanel2() {
        super(800, 600);
        GameData.status = GameData.STATUS_WAIT;
        setLayout(new BorderLayout());



        userPanel = new JPanel();
        //setOpaque(true);
        userPanel.setSize(panelWidth, userPanelHeight);
        l_userMoney = new JLabel("У Вас денег :  " + GameData.userMoney + "   ");
        l_userMoney.setForeground(Color.BLUE);
        userPanel.add(l_userMoney);
        String[] cat_value = {"1", "2", "3", "4", "5", "6"};
        int[] cat_int = {0, 1, 2, 3, 4, 5, 6};
        String[] money_value = {"1", "5", "20", "50"};
        int[] money_int = {1, 5, 20, 50};
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
        GamesPanel2 gp = this;
        b_setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
              //  if (sp == null) sp = new SettingPanel(gp);
                sp.setVisible(true);
                sp.setAlwaysOnTop(true);
            }
        });
        userPanel.add(b_setting);

        //add(userPanel, BorderLayout.SOUTH);

        userPanelHeight = userPanel.getHeight();
        _repaintBound = new Rectangle(0, 0, panelWidth, panelHeight - userPanelHeight);
        setRepaintBound(_repaintBound);
        buildBackground();
        // запускаем поток, который будет управлять всем происходящим ( - run)
        new Thread(this).start();
    }

    // фоновый слой с травой
    private void buildBackground() {
        backgroundFon = new BufferedImage(panelWidth, panelHeight - userPanelHeight, BufferedImage.TYPE_INT_RGB);
        ImageIcon imageI = new ImageIcon(Main.class.getResource("/ru/chertenok/feedthecat/images/grass.jpg"));
        Graphics2D g = DrawPanel.initGraphics(backgroundFon);
        for (int i = 0; i <= panelWidth / imageI.getIconWidth(); i++)
            for (int j = 0; j <= panelHeight / imageI.getIconHeight(); j++) {
                g.drawImage(imageI.getImage(), i * imageI.getIconWidth(), j * imageI.getIconHeight(), null);
            }
        // добавляем в стэк отрисовки самым нижним слоем
        imageList.add(new ImageData(backgroundFon, 0, 0, backgroundFon.getWidth(), backgroundFon.getHeight()));
        g.dispose();
        repaint();
    }


    @Override
    public void run() {


        fpsIsLimit = false;

        // инициализация котов
        for (int i = 0; i < GameData.catCount; i++) {
            cats[i] = new Cat();
            cats[i].setX(50);
            cats[i].setY(i * 70);
        }
        bowl = new Bowl();
        bowl.setX(700);
        bowl.setY(100);


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
            g.drawString("" + (i + 1), 20, 71 * i + 50);
        }
        imageList.add(new ImageData(image, 0, 0, panelWidth, panelHeight - userPanelHeight));
        g.dispose();

        // welcome сообщение
        image = new BufferedImage(_repaintBound.width, _repaintBound.height, BufferedImage.TYPE_INT_ARGB);
        g = DrawPanel.initGraphics(image);
        g.setFont(CustomFonts.getCustomFont(3, Font.ITALIC, 30));
        String s = "Укажите Вашу ставку и";
        // получаем размеры надписи и её координаты для надписи по центру экрана
        Rectangle rect = CustomFonts.getTextCenterInImage(s, image, g);
        g.drawString(s, rect.x, rect.y);
        s = "и \n выберите номер котолошади!";
        rect = CustomFonts.getTextCenterInImage(s, image, g);
        g.drawString(s, rect.x, rect.y + 80);
        // сохраняем ссылку на слой, чтоб его можно было удалять и добавлять в процессе игры
        id_welcome = new ImageData(image, 0, 0, _repaintBound.width, _repaintBound.height);
        imageList.add(id_welcome);
        g.dispose();

        // а на этом слое мы будем рисовать наших котиков
        image = new BufferedImage(_repaintBound.width, _repaintBound.height, BufferedImage.TYPE_INT_ARGB);
        g = DrawPanel.initGraphics(image);
        imageList.add(new ImageData(image, 0, 0, _repaintBound.width, _repaintBound.height));

        repaint();

        boolean f;
        long num = 0;
        imageList.remove(id_wait);
        // вечный игровой цикл
        while (true) {
            // нет победы
            f = false;

            // repaint();
            //sleep(GameData.sleepTime);
            if (!fpsIsLimit || (fpsIsLimit && fpsCount< fpsMax )) {

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
                    if (GameData.status == GameData.STATUS_RUN && cats[i].getX() > 636 + 50) {
                        // то забег закончен, есть победитель
                        GameData.status = GameData.STATUS_WIN;
                        // сохраняем его
                        GameData.winCatNum = i;
                        // ставим флаг, что победа в этом шаге цикла и потом сбрасываем его в начале цикла, чтобы не крутиться тут вечно
                        f = true;
                        break;
                    }

                    // обновляем котов (анимация + сдвиг координат )
                    cats[i].updateStage();
                    bowl.updateStage();
                    // отрисовка кота на нашем слое
                    cats[i].draw(g);
                    bowl.draw(g);
                }

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
        l_userMoney.setText("У Вас денег :" + GameData.userMoney);
        // меняем надпись на кнопке, можно было бы сделать надписью и картинкой в отдельном слое на игровом поле, но пока лень
        b_start.setText((GameData.winCatNum == GameData.userCatNum) ? "Вы выиграли, Нажмите для продолжения" : " Вы проиграли, Нажмите для продолжения");
        // включаем кнопку старта
        b_start.setEnabled(true);
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
        return (fpsIsLimit)? fpsMax: 0;
    }

    public void setMaxFPS(int fps) {
        fpsIsLimit = fps > 0;
        if (fps != 0) fpsMax = fps;

    }
}
