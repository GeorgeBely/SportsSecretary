package ru.SportsSecretary.frames;

import com.javaswingcomponents.calendar.JSCCalendar;
import com.javaswingcomponents.calendar.listeners.CalendarSelectionEventType;
import com.javaswingcomponents.calendar.plaf.darksteel.DarkSteelCalendarUI;
import ru.SportsSecretary.graphics.CustomCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Основной фрейм приложения.
 */
public class MainFrame extends JFrame {

    /** Ширина окна */
    private static final int WIDTH = 900;

    /** Высота окна */
    private static final int HEIGHT = 535;

    /** Наименование окна */
    private static final String TITLE = "SportSecretary";

    public MainFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle(TITLE);
//        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final JPanel panel = new JPanel(){{
            setFocusable(true);
            setLayout(null);
//            setBackground(Color.LIGHT_GRAY);
        }};
        add(panel);


        JSCCalendar calendar2 = new JSCCalendar();
        calendar2.setUI(DarkSteelCalendarUI.createUI(calendar2));
        calendar2.setLocation(230, 10);
        calendar2.setSize(200, 200);
        panel.add(calendar2);

        JSCCalendar calendar = new JSCCalendar() {{
            setUI(DarkSteelCalendarUI.createUI(this));
            setCalendarCellRenderer(new CustomCellRenderer());
            setLocation(10, 10);
            setSize(200, 200);
        }};
        panel.add(calendar);

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

    }

}
