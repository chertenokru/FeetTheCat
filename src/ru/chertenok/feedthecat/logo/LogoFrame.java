package ru.chertenok.feedthecat.logo;

import ru.chertenok.feedthecat.logo.LogoPanel;

import javax.swing.*;

/**
 * Created by 13th on 15.04.2017.
 */
public class LogoFrame extends JFrame {
    private LogoPanel p;
    public boolean end = false;

    public LogoFrame()  {
        super();
        p = new LogoPanel();
        add(p);
        setSize(800,600);
        setResizable(false);
        setLocationRelativeTo(null);
        // скрыть заголовок окна
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }



}

