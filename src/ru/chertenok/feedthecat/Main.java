package ru.chertenok.feedthecat;

import ru.chertenok.feedthecat.game.GamesFrame;
import ru.chertenok.feedthecat.logo.LogoFrame;

import javax.swing.*;

/**
 *  Демо проект:
 *  - отработка простейшего графического движка на базе класса DrawPanel
 *  - работа со внешними шрифтами CustomFonts
 *  - отработка зачаточного механизма анимации - Sprite, Cat
 *
 *  GitHub:
 *  https://github.com/chertenokru/FeetTheCat
 *
 */
public class Main {

    public static void main(String[] args) {
   // первичный пример работы с DrawPanel, признан не актуальным, сокращенная заставка перенесена в игровой класс
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
