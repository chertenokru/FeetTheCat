package ru.chertenok.feedthecat.game;

/**
 * Created by 13th on 18-Apr-17.
 */
public class GameData {
    //    enum {
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_RUN = 1;
    public static final int STATUS_WIN = 2;
    public static int status = STATUS_WAIT;
    public static int userCatNum = 0;
    public static int winCatNum = 0;
    public static int userMoney = 100;
    public static int userStavka = 0;
    public static int freqRandom = 5;
    public static int maxSpeed = 20;
    public static int minSpeed = 5;
    public static int catCount = 6;
    volatile public  static  int stageDelay = 200000000;
    public static long sleepTime = 20;
}
