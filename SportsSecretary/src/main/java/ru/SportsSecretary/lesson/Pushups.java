package ru.SportsSecretary.lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Класс упражнения отжимания.
 */
public class Pushups extends LessonType {

    /** Наименование упражнения */
    private static final String NAME = "PushUps";

    /**Наименование упражнения на русском */
    private static final String RUSSIAN_NAME = "Отжимания";


    /**
     * Список характеристик упражнения "отжимания".
     */
    private static final List<Property> defaultProperties = new ArrayList<Property>() {{
        add(new Property("Count", "Integer", "Количество"));
        add(new Property("Approaches", "Integer", "Подходов"));
        add(new Property("Approaches between time", "Integer", "Время между подходами"));
        add(new Property("Slope", "Integer", "Наклон"));
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
        return getName();
    }
}
