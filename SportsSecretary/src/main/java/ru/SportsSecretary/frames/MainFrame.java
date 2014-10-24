package ru.SportsSecretary.frames;

import com.javaswingcomponents.calendar.JSCCalendar;
import ru.SportsSecretary.lesson.Lesson;
import ru.SportsSecretary.lesson.LessonType;
import ru.SportsSecretary.lesson.Property;
import ru.SportsSecretary.services.LessonService;
import ru.SportsSecretary.services.swing.CalendarService;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Основной фрейм приложения.
 */
public class MainFrame extends JFrame {

    /** Ширина окна */
    private static final int WIDTH = 1100;

    /** Высота окна */
    private static final int HEIGHT = 700;

    /** Расположение календаря */
    private static final int LOCATION_CALENDAR_X = 870;
    private static final int LOCATION_CALENDAR_Y = 10;

    /** Расположение выбора типа спорта */
    private static final int LOCATION_KIND_SPORT_X = 50;
    private static final int LOCATION_KIND_SPORT_Y = 50;

    /** Расположение характеристик спорта */
    private static final int LOCATION_PROPERTIES_X = 50;
    private static final int LOCATION_PROPERTIES_Y = 100;

    /** Наименование окна */
    private static final String TITLE = "SportSecretary";

    private static JPanel panel;

    public MainFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle(TITLE);
//        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel(){{
            setFocusable(true);
            setLayout(null);
//            setBackground(Color.LIGHT_GRAY);
        }};
        add(panel);

        JSCCalendar calendar = CalendarService.getCalendarComponent();
        calendar.setLocation(LOCATION_CALENDAR_X, LOCATION_CALENDAR_Y);
        calendar.setSize(220, 220);
        panel.add(calendar);

        JLabel kindSportLabel = new JLabel() {{
            setText("Вид спорта");
            setSize(100, 20);
            setLocation(LOCATION_KIND_SPORT_X, LOCATION_KIND_SPORT_Y);
        }};
        panel.add(kindSportLabel);

        JComboBox<LessonType> sportComboBox = new JComboBox<LessonType>() {{
            setModel(new DefaultComboBoxModel<>(LessonService.getLessonsTypeNames()));
            setLocation(LOCATION_KIND_SPORT_X + 110, LOCATION_KIND_SPORT_Y);
            setSize(250, 20);
        }};
        panel.add(sportComboBox);

        sportComboBox.addActionListener(e -> viewLesson((LessonType) sportComboBox.getSelectedItem()));
    }

    private static void viewLesson(LessonType type) {
        int propertyX = LOCATION_PROPERTIES_X;
        int propertyY = LOCATION_PROPERTIES_Y;


        Lesson lesson = new Lesson();
        Map<Property, Object> defaultProperties = new HashMap<>();

        for (Property property : type.getProperties()) {
            if(property.getName().equals("time"))
                defaultProperties.put(property, 60);
            if(property.getName().equals("avgSpeed"))
                defaultProperties.put(property, 10);
            if(property.getName().equals("maxSpeed"))
                defaultProperties.put(property, 20);
            if(property.getName().equals("calories"))
                defaultProperties.put(property, 200);
            if(property.getName().equals("distance"))
                defaultProperties.put(property, 12000);
        }

        for (Property property : type.getProperties()) {
            System.out.println(property);
            if (defaultProperties.containsKey(property)) {
                System.out.println(property);
                JLabel propertyLabel = new JLabel();
                propertyLabel.setText(property.getName());
                propertyLabel.setSize(100, 20);
                propertyLabel.setLocation(propertyX, propertyY);
                panel.add(propertyLabel);

                JLabel valueLabel = new JLabel();
                valueLabel.setText(String.valueOf(defaultProperties.get(property)));
                valueLabel.setSize(100, 20);
                valueLabel.setLocation(propertyX + 100, propertyY);
                panel.add(valueLabel);

                propertyY += 30;
            }
        }
    }

}
