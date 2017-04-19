package ru.chertenok.feedthecat.game;

import javax.swing.*;
import java.awt.*;

/** Основное окно приложения
 * Created by 13th on 17-Apr-17.
 */
public class GamesFrame extends JFrame {
    private GamesPanel p;




    public GamesFrame() throws HeadlessException {
        super();
        p = new GamesPanel();
        add(p);
        setSize(800, 620);
        setResizable(false);
        setLocationRelativeTo(null);
        // скрыть заголовок окна
        //setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
