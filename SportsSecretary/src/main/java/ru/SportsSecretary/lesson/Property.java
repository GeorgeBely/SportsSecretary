package ru.SportsSecretary.lesson;

import java.util.List;
import java.util.Locale;

public class Property {

    private String name;
    private String type;
    private String russianName;
    private Integer position;
    private List<String> values;

    public Property(String name, String type, String russianName) {
        this.name = name;
        this.type = type;
        this.russianName = russianName;
    }

    public Property(String name, String type, String russianName, List<String> values) {
        this.name = name;
        this.type = type;
        this.russianName = russianName;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRussianName() {
        return russianName;
    }

    public void setRussianName(String russianName) {
        this.russianName = russianName;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public boolean equals(Property property) {
        return property != null && property.getName().equals(name);
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String toString() {
        if (Locale.getDefault().equals(new Locale("ru", "RU")))
            return russianName;
        return name;
    }

}
