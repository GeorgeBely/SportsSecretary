package ru.SportsSecretary.frames;

import com.javaswingcomponents.calendar.JSCCalendar;
import ru.SportsSecretary.lesson.Lesson;
import ru.SportsSecretary.services.swing.CalendarService;

import javax.swing.*;
import java.awt.*;

/**
 * Основной фрейм приложения.
 */
public class MainFrame extends JFrame {

    /** Ширина окна */
    private static final int WIDTH = 1100;

    /** Высота окна */
    private static final int HEIGHT = 700;

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

        JSCCalendar calendar = CalendarService.getCalendarComponent();
        calendar.setLocation(870, 10);
        calendar.setSize(220, 220);
        panel.add(calendar);

        JLabel kindSportLabel = new JLabel() {{
            setText("Вид спорта");
            setSize(100, 20);
            setLocation(50, 50);
        }};
        panel.add(kindSportLabel);

        JComboBox<Lesson> sportComboBox = new JComboBox<Lesson>() {{
            setSize(250, 20);
            setLocation(160, 50);
        }};
        panel.add(sportComboBox);
    }

}
