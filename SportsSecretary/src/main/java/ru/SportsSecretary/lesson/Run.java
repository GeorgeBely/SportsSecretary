package ru.SportsSecretary.lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Класс упражнения бег.
 */
public class Run extends LessonType {

    /** Наименование упражнения */
    private static final String NAME = "Run";

    /**Наименование упражнения на русском */
    private static final String RUSSIAN_NAME = "Бег";


    /**
     * Список характеристик упражнения бег.
     */
    private static final List<Property> defaultProperties = new ArrayList<Property>() {{
        add(new Property("time", "Integer", "Бег"));
        add(new Property("avgSpeed", "Integer", "Средняя скорость"));
        add(new Property("maxSpeed", "Integer", "Максимальная скорость"));
        add(new Property("calories", "Integer", "Калории"));
        add(new Property("distance", "Integer", "Дистанция"));
    }};

    public List<Property> getProperties() {
        return defaultProperties;
    }

    public String getName() {
        if (Locale.getDefault().equals(new Locale("ru", "RU")))
            return RUSSIAN_NAME;
        return NAME;
    }

    public String toString() {
        return RUSSIAN_NAME;
    }
}
