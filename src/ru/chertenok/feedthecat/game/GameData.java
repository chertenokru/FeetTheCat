package ru.chertenok.feedthecat.game;

/**
 * Created by 13th on 18-Apr-17.
 */
public class GameData {
    //    const
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_RUN = 1;
    public static final int STATUS_WIN = 2;
    // статус игры
    public static int status = STATUS_WAIT;
    // выбранный кот пользователем
    public static int userCatNum = 0;
    // выигравший кот
    public static int winCatNum = 0;
    // счёт юзера
    public static int userMoney = 200;
    // ставка юзера
    public static int userStavka = 0;
    // итераций до смены скорости
    public static int freqRandom = 5;
    // макс спиид
    public static int maxSpeed = 20;
    // мин спид
    public static int minSpeed = 5;
    // кол-во котов (1-8)
    public static int catCount = 6;
    // задержка в наносекундах между анимацией и движением (те самые инерации)
    volatile public  static  int stageDelay = 200000000;
}
