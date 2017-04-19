package ru.chertenok.feedthecat.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;

/** Класс  прогружает простейшее воспроизведение звуков
 *  на базе JAVAFX по псевдонимам
 *  звуковые файлы должны находиться в этом же пакете
 */
public class SoundEngine {
    private static HashMap<String,String> soundNames = new HashMap<>();
    private static MediaPlayer currentPlayer = null;

    /**Добавляем в список воспроизведения
     * @param soundName наименование для воспроизведения
     * @param fileName имя связанного файла
     */
    public static void addSound(String soundName,String fileName) {
        String pathToSound = SoundEngine.class.getPackage().getName();
        pathToSound = "/" + pathToSound.replace('.', '/') + "/";
        soundNames.put(soundName, pathToSound + fileName);
    }

    /** Воспроизвести звук по имени,
     * воспрозводит только один звук
     * @param name имя из списка воспроизведения
     */
    public static void playSoundByName(String name){
        playSound( soundNames.get(name));
    }


    /** Внунтренния реализация playSoundByName
     * @param file
     */
    private static void playSound(String file) {
        Media sound = new Media(SoundEngine.class.getResource(file).toString());
        stopSound();
        currentPlayer = new MediaPlayer(sound);
        currentPlayer.play();
    }

    /** Остановить воспроизведение      */
    public static void stopSound()
    {
        if (currentPlayer != null)
        {
            currentPlayer.stop();
            currentPlayer.dispose();
            currentPlayer = null;
        }
    }

}
