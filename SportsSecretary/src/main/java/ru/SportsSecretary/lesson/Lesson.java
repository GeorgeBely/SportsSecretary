package ru.SportsSecretary.lesson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс описывающий упражнения.
 */
public class Lesson {

    private LessonType type;

    private Map<Property, Object> properties = new HashMap<>();

    private Date date;

    private String description;


    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public Map<Property, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<Property, Object> properties) {
        this.properties = properties;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
