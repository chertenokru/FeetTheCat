package ru.chertenok.feedthecat.ttf;

import java.awt.*;
import java.io.IOException;

/**
 * Класс  прогружает заданые шрифты
 * и возвращает ссылку на них по запросу
 * шрифты должны находиться в этом же пакете
 */
public class CustomFonts {
    static String[] customFontsName = {"a_AlternaTitulB.TTF", "a_BremenDcFr.TTF", "a_LCDNovaObl.TTF", "a_CampusGrav.TTF", "a_AlternaSw.TTF"};
    static Font[] customFontsList = new Font[customFontsName.length];

/*
отказался в пользу прогрузки шрифта при первом обращении
    // блок статической инициализации
    static {
        String pathToFont = CustomFonts.class.getPackage().getName();
        pathToFont = "/"+pathToFont.replace('.','/')+"/";
        for (int i = 0; i < customFontsName.length; i++) {
            try {
                customFontsList[i] = Font.createFont(Font.TRUETYPE_FONT, CustomFonts.class.getResourceAsStream(pathToFont + customFontsName[i]));
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFontsList[i]);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/

     /** Загрузка и регистрация шрифта
     *  @param num номер в списке
     */
    private static void loadFont(int num) {
        String pathToFont = CustomFonts.class.getPackage().getName();
        pathToFont = "/" + pathToFont.replace('.', '/') + "/";
        try {
            customFontsList[num] = Font.createFont(Font.TRUETYPE_FONT, CustomFonts.class.getResourceAsStream(pathToFont + customFontsName[num]));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFontsList[num]);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Возвращает ссылку на шрифт с заданным номером, стилен и размером
     *
     * @param numberFont номер шрифта от 0 до кол-ва шрифтов - 1
     * @param fontStyle  что-то типа Font.ITALIC|Font.BOLD
     * @param fontSize   размер шрифта, н-р 15.0f
     * @return возвращает стандартный класс Font
     */
    public static Font getCustomFont(int numberFont, int fontStyle, float fontSize) {
        if (numberFont > customFontsList.length - 1) return null;
        // если шрифт не загружен, то загружаем его
        if (customFontsList[numberFont] == null) loadFont(numberFont);
        // возвращаем шрифт
        return customFontsList[numberFont].deriveFont(fontStyle, fontSize);
    }

    /**
     * Возвращает ссылку на шрифт с заданным номером и размером
     *
     * @param numberFont номер шрифта от 0 до кол-ва шрифтов - 1
     * @param fontSize   размер шрифта, н-р 15.0f
     * @return возвращает стандартный класс Font
     */
    public static Font getCustomFont(int numberFont, float fontSize) {
        return getCustomFont(numberFont,Font.PLAIN,fontSize);
    }

    /** Возвращает координаты х,y - для вывода по центру
     *  и width, heigth - для надписи
     * @param s - строка
     * @param image - картинка куда выводить
     * @param g - Graphics2D c установленным шрифтом
     * @return Rectangle
     */
    public static Rectangle getTextCenterInImage(String s,Image image, Graphics2D g){

        FontMetrics fm = g.getFontMetrics();

        return new Rectangle((int)(image.getWidth(null) / 2 - fm.getStringBounds(s,g).getWidth()/2 ),
                (int) (image.getHeight(null) / 2 - fm.getStringBounds(s, g).getHeight() ),
                (int)fm.getStringBounds(s,g).getWidth(),(int)fm.getStringBounds(s,g).getHeight());
    }

}

