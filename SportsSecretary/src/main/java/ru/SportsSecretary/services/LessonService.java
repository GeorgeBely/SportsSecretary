package ru.SportsSecretary.services;

import ru.SportsSecretary.lesson.LessonType;
import ru.SportsSecretary.lesson.Pushups;
import ru.SportsSecretary.lesson.Run;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Сервис для работы с упражнениями.
 */
public class LessonService {

    private static final Map<Class, LessonType> lessonMap = new HashMap<Class, LessonType>() {{
        put(Run.class, new Run());
        put(Pushups.class, new Pushups());
    }};

    public static Vector<LessonType> getLessonsTypeNames() {
        Vector<LessonType> vector = new Vector<>();
//        vector.add("Все виды спорта");

        vector.addAll(lessonMap.values().stream().map(type -> type).collect(Collectors.toList()));
        return vector;
    }

}
