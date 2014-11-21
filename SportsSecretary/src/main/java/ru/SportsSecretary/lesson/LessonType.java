package ru.SportsSecretary.lesson;

import java.util.List;

/**
 * Класс описывающий тип упражнения.
 */
public abstract class LessonType {

    private List<Property> properties;

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public String getName() {
        return "";
    }

    /**
     * Возвращает характеристику по коду.
     *
     * @param code код характеристики
     * @return характеристику, или <code>null</code>, если характеристики с таким кодом нет.
     */
    public Property getPropertyByCode(String code) {
        if (properties == null || code == null)
            return null;

        for (Property property : properties) {
            if (code.equals(property.getName()))
                return property;
        }
        return null;
    }

}

