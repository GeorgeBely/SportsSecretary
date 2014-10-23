package ru.SportsSecretary.lesson;

import java.util.Date;

/**
 * Класс описывающий упражнения.
 */
public abstract class Lesson {

    private Date date;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

