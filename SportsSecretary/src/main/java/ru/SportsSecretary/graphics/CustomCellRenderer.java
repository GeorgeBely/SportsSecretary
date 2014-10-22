package ru.SportsSecretary.graphics;

import com.javaswingcomponents.calendar.JSCCalendar;
import com.javaswingcomponents.calendar.cellrenderers.CalendarCellRenderer;
import com.javaswingcomponents.calendar.cellrenderers.CellRendererComponentParameter;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class CustomCellRenderer extends JLabel implements CalendarCellRenderer {

    @Override
    public JComponent getCellRendererComponent(CellRendererComponentParameter parameterObject) {
        boolean isHoliday = parameterObject.isHoliday;
        boolean isWeekend = parameterObject.isWeekend;
        boolean isMouseOver = parameterObject.isMouseOver;
        boolean isToday = parameterObject.isToday;
        boolean isSelected = parameterObject.isSelected;
        boolean isSelectable = parameterObject.isAllowSelection();
        boolean hasKeyboardFocus = parameterObject.isHasFocus();
        boolean isCurrentMonth = parameterObject.isCurrentMonth;
        String text = parameterObject.getText();
        Date date = parameterObject.getDate();
        JSCCalendar calendar = parameterObject.getCalendar();

        setHorizontalAlignment(JLabel.CENTER);
        setIcon(null);
        setText(text);
        setOpaque(false);

        if (isSelectable) {
            setForeground(Color.BLACK);
        } else {
            setText(text);
            setForeground(Color.LIGHT_GRAY);
        }
        if (!isCurrentMonth) {
            setText("");
        }

//        Icon holidayIcon = null;
//        Icon weekendIcon = null;
//        Icon todayIcon = null;
//        try {
//            holidayIcon = new ImageIcon(ImageIO.read(new File("holiday.png")));
//            weekendIcon = new ImageIcon(ImageIO.read(new File("weekend.png")));
//            todayIcon = new ImageIcon(ImageIO.read(new File("today.png")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        if (isHoliday) {
//            Holiday holiday = calendar.getCalendarModel().getHolidayForDate(date);
//            if (holiday.getDescription().contains("Christmas")) {
//                setIcon(holidayIcon);
//                setText("");
//            }
//        }
//        if (isToday) {
//            setIcon(todayIcon);
//            setText("");
//        }
//        if (isWeekend) {
//            setIcon(weekendIcon);
//            setText("");
//        }

        if (isSelected) {
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        } else {
            setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        }
        if (hasKeyboardFocus) {
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }
        return this;
    }

    @Override
    public JComponent getHeadingCellRendererComponent(JSCCalendar calendar, String text) {
        setHorizontalAlignment(JLabel.CENTER);
        setText(text);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        setForeground(Color.BLACK);
        setIcon(null);
        return this;
    }
}

