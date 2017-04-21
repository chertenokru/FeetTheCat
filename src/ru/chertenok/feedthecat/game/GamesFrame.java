package ru.chertenok.feedthecat.game;

import ru.chertenok.feedthecat.model.DrawPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** Основное окно приложения
 * Created by 13th on 17-Apr-17.
 */
public class GamesFrame extends JFrame {
    private DrawPanel p;





    public GamesFrame() throws HeadlessException {
        super();
        // выбираем какую игру (панель) создавать
        choiceGame();
    }

    private void RunGamePanel() {
        add(p);
        setSize(800, 620);
        setResizable(false);
        setLocationRelativeTo(null);
        // скрыть заголовок окна
        //setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void choiceGame() {
        JFrame choice = new JFrame("Выберите игру");
        choice.setUndecorated(true);
        choice.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints cont = new GridBagConstraints();
        cont.anchor = GridBagConstraints.CENTER;
        cont.gridx = 0;
        cont.gridwidth = 2;
        cont.gridy = 0;
        cont.insets = new Insets(40,20,20,40);
        JButton b_game1 = new JButton("Игра 1: КотоДром        ");
        b_game1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                choice.setVisible(false);
                p = new GamesPanel();
                RunGamePanel();
            }
        });
        JButton b_game2 = new JButton("Игра 2: Покорми котиков");

        b_game2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                choice.setVisible(false);
                p = new GamesPanel2();
                RunGamePanel();
            }
        });
        choice.getContentPane().add(b_game1,cont);
        cont.gridy = 1;
        choice.getContentPane().add(b_game2,cont);
        choice.pack();

        choice.setResizable(false);
        choice.setLocationRelativeTo(null);
        choice.setVisible(true);
    }
}
