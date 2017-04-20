package ru.chertenok.feedthecat.game;

import ru.chertenok.feedthecat.model.DrawPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** Основное окно приложения
 * Created by 13th on 17-Apr-17.
 */
public class GamesFrame extends JFrame {
    private DrawPanel p;





    public GamesFrame() throws HeadlessException {
        super();
        //p = new GamesPanel();
        p = new GamesPanel2();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                System.out.println(keyEvent.getKeyChar());
                super.keyReleased(keyEvent);
            }
        });

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
