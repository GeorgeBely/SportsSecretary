package ru.SportsSecretary.services.swing;

import com.javaswingcomponents.calendar.JSCCalendar;
import com.javaswingcomponents.calendar.cellrenderers.CalendarCellRenderer;
import com.javaswingcomponents.calendar.cellrenderers.CellRendererComponentParameter;
import com.javaswingcomponents.calendar.listeners.CalendarSelectionEventType;
import com.javaswingcomponents.calendar.model.DayOfWeek;
import com.javaswingcomponents.calendar.plaf.darksteel.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Сервис для работы с компонентом календарь.
 */
public class CalendarService {


    /**
     * Создаёт компонент календарь.
     *
     * @return календарь.
     */
    public static JSCCalendar getCalendarComponent() {
        JSCCalendar calendar = new JSCCalendar() {{
            setUI(DarkSteelCalendarUI.createUI(this));
            getCalendarModel().setFirstDayOfWeek(DayOfWeek.MONDAY);
            setCalendarCellRenderer(getCustomCellRenderer());
        }};

        calendar.addCalendarSelectionListener(calendarSelectionEvent -> {
            List<Date> selectedDates = calendarSelectionEvent.getSelectedDates();
            CalendarSelectionEventType selectionEventType = calendarSelectionEvent.getCalendarSelectionEventType();
            switch (selectionEventType) {
                case DATE_REMOVED: {
                    System.out.println("A date has been removed. Date=" + selectedDates.get(0));
                    break;
                }
                case DATE_SELECTED: {
                    System.out.println("A date has been selected. Date=" + selectedDates.get(0));
                    break;
                }
                case DATES_SELECTED: {
                    System.out.println("Dates have been selected. Date=" + Arrays.toString(selectedDates.toArray()));
                    break;
                }
                case DATES_CLEARED: {
                    System.out.println("All dates have been cleared. Date=" + Arrays.toString(selectedDates.toArray()));
                    break;
                }
                case DISPLAY_DATE_CHANGED: {
                    System.out.println("Display date moved.");
                    break;
                }
            }
        });

        return calendar;
    }

    public static CustomCellRenderer getCustomCellRenderer() {
        return new CustomCellRenderer();
    }
}


/**
 * Класс описывающий ячейки календаря.
 * Переопределяет класс DarkSteelCalendarCellRenderer;
 */
class CustomCellRenderer extends JLabel implements CalendarCellRenderer {

    /**
     * Описание ячеек дат.
     */
    @Override
    public JComponent getCellRendererComponent(CellRendererComponentParameter parameterObject) {
        DarkSteelCalendarCellRenderer defaultCell = (DarkSteelCalendarCellRenderer)
                new DarkSteelCalendarCellRenderer().getCellRendererComponent(parameterObject);

        setHorizontalAlignment(defaultCell.getHorizontalAlignment());
        setIcon(defaultCell.getIcon());
        setText(parameterObject.getText());
        setOpaque(defaultCell.isOpaque());
        setForeground(defaultCell.getForeground());
        setBorder(defaultCell.getBorder());
        setBackground(defaultCell.getBackground());

        if (parameterObject.isCurrentMonth) {
            if (parameterObject.isWeekend || parameterObject.isHoliday) {
                setForeground(new Color(51, 142, 237));
                setOpaque(true);
            }

            if (parameterObject.isToday) {
                setForeground(new Color(237, 142, 62));
                setOpaque(true);
            }
        }
        return this;
    }

    /**
     * Описание шапки.
     */
    @Override
    public JComponent getHeadingCellRendererComponent(JSCCalendar calendar, String text) {
        return new DarkSteelCalendarCellRenderer().getHeadingCellRendererComponent(calendar, text);
    }
}