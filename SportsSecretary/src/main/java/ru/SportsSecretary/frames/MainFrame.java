package ru.SportsSecretary.frames;

import com.javaswingcomponents.calendar.JSCCalendar;
import com.javaswingcomponents.calendar.plaf.darksteel.DarkSteelCalendarUI;
import ru.SportsSecretary.services.swing.CalendarService;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Основной фрейм приложения.
 */
public class MainFrame extends JFrame {

    /** Ширина окна */
    private static final int WIDTH = 450;

    /** Высота окна */
    private static final int HEIGHT = 225;

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

        JSCCalendar calendar2 = new JSCCalendar(TimeZone.getDefault(), Locale.getDefault());
        calendar2.setUI(DarkSteelCalendarUI.createUI(calendar2));
        calendar2.setLocation(230, 10);
        calendar2.setSize(200, 200);
        panel.add(calendar2);

        panel.add(CalendarService.getCalendarComponent());

    }

}
