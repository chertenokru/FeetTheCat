package ru.chertenok.feedthecat.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 13th on 19-Apr-17.
 */
public class SettingPanel extends JFrame {
    JTextField tf_catCount;
    JTextField tf_minSpeed;
    JTextField tf_maxSpeed;
    JTextField tf_freqRandom;
    JTextField tf_spriteDelay;
    JTextField tf_sleepDelay;
    JTextField tf_fpsDelay;
    JButton b_ok;
    JButton b_cancel;
    GamesPanel gamesPanel;

    public SettingPanel(GamesPanel gamesPanel) {
        super();
        setTitle("Настройки игры");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10,10,10,10);
        constraints.anchor = GridBagConstraints.EAST;
        add(new JLabel("Количество котов: "), constraints);
        tf_catCount = new JTextField(5);
        tf_catCount.setText(Integer.toString(GameData.catCount));
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(tf_catCount, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(new JLabel("Min скорость :"), constraints);
        constraints.gridx = 1;
        tf_minSpeed = new JTextField(5);
        tf_minSpeed.setText(Integer.toString(GameData.minSpeed));
        add(tf_minSpeed, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel("Max скорость :"), constraints);
        constraints.gridx = 1;
        tf_maxSpeed = new JTextField(5);
        tf_maxSpeed.setText(Integer.toString(GameData.maxSpeed));
        add(tf_maxSpeed, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(new JLabel("Кол-во итераций до смены скорости: "), constraints);
        constraints.gridx = 1;
        tf_freqRandom = new JTextField(5);
        tf_freqRandom.setText(Integer.toString(GameData.freqRandom));
        add(tf_freqRandom, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(new JLabel("Задержка анимации и движения, нс: "), constraints);
        constraints.gridx = 1;
        tf_spriteDelay = new JTextField(7);
        tf_spriteDelay.setText(Integer.toString(GameData.stageDelay));
        add(tf_spriteDelay, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
//        add(new JLabel("Время сна управляющего потока, чтобы основной успел отрисоваться "), constraints);
//        constraints.gridx = 1;
//        tf_sleepDelay = new JTextField(5);
//        tf_sleepDelay.setText(Long.toString(GameData.sleepTime));
//        add(tf_sleepDelay, constraints);
//        constraints.gridx = 0;
        constraints.gridy = 6;
        add(new JLabel("Ограничить FPS до (0 - без ограничений ), к/c: "), constraints);
        constraints.gridx = 1;
        tf_fpsDelay = new JTextField(3);
        tf_fpsDelay.setText(Integer.toString(gamesPanel.getMaxFPS()));
        add(tf_fpsDelay, constraints);


        constraints.gridx = 0;
        constraints.gridy = 7;
        b_ok = new JButton("Применить");
        b_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // если применить, то меняем настройки
                try {
                    GameData.freqRandom = Integer.valueOf(tf_freqRandom.getText());
                } catch (Exception e) {
                }
                try {
                    GameData.catCount = Integer.valueOf(tf_catCount.getText());
                } catch (Exception e) {
                }
                try {
                    GameData.minSpeed = Integer.valueOf(tf_minSpeed.getText());
                } catch (Exception e) {
                }
                try {
                    GameData.maxSpeed = Integer.valueOf(tf_maxSpeed.getText());
                } catch (Exception e) {
                }
                try {
                    GameData.stageDelay = Integer.valueOf(tf_spriteDelay.getText());
                } catch (Exception e) {
                }
                try {
                    GameData.sleepTime = Integer.valueOf(tf_sleepDelay.getText());
                } catch (Exception e) {
                }
                 try {
                        gamesPanel.setMaxFPS( Integer.valueOf(tf_fpsDelay.getText()));
                    } catch (Exception e) {
                    }
            }
        });


        add(b_ok, constraints);
        constraints.gridx = 1;
        b_cancel = new JButton("Отмена");
        b_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        });
        add(b_cancel, constraints);
        this.pack();


    }
}
