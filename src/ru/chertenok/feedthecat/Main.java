package ru.chertenok.feedthecat;

import ru.chertenok.feedthecat.game.GamesFrame;
import ru.chertenok.feedthecat.logo.LogoFrame;

import javax.swing.*;

/**
 * Created by 13th on 15.04.2017.
 */
public class Main {

    public static void main(String[] args) {
    // заставка
//          JFrame f = new LogoFrame();
//        while (f.isVisible() )  {
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        // приложение
        new GamesFrame();

    }
}
