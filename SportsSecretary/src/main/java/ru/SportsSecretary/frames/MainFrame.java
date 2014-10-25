package ru.SportsSecretary.frames;

import ru.SportsSecretary.lesson.Lesson;
import ru.SportsSecretary.lesson.LessonType;
import ru.SportsSecretary.lesson.Property;
import ru.SportsSecretary.services.swing.CalendarService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Основной фрейм приложения.
 */
public class MainFrame extends JFrame {

    /** Ширина окна */
    private static final int FRAME_WIDTH = 400;

    /** Высота окна */
    private static final int FRAME_HEIGHT = 400;

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


    Container video;


    Container lessonContainer;
    JPanel lessonPanel;

    public MainFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(screenSize.width / 2 - FRAME_WIDTH / 2, screenSize.height / 2 - FRAME_HEIGHT / 2);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(TITLE);
//        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel() {{
            setFocusable(true);
            setLayout(null);
            setBackground(Color.LIGHT_GRAY);
        }};
        add(panel);


        video = new Container() {{
            setSize(100, 100);
            setLocation(10, 10);
            setBackground(Color.BLUE);
        }};
        JPanel p = new JPanel() {{
            setLocation(10, 10);
            setSize(30, 20);
            setBackground(Color.CYAN);
        }};
        panel.add(video);
        video.add(p, BorderLayout.CENTER);

        lessonContainer = new Container() {{
            setSize(240, FRAME_HEIGHT);
            setLocation(FRAME_WIDTH - 240, 0);
            setBackground(Color.LIGHT_GRAY);
        }};
        panel.add(lessonContainer);


//
//        JPanel dPanel = new JPanel(){{
//            setPreferredSize(new Dimension(100, 100));
//            setMinimumSize(new Dimension(100, 100));
//            setMaximumSize(new Dimension(100, 100));
//            setBackground(Color.RED);
//        }};
//        add(dPanel, BorderLayout.EAST);


//        panel = new JPanel(){{
//            setPreferredSize(new Dimension(FRAME_WIDTH - 230, FRAME_HEIGHT));
//            setMinimumSize(new Dimension(FRAME_WIDTH - 230, FRAME_HEIGHT));
//            setMaximumSize(new Dimension(FRAME_WIDTH - 230, FRAME_HEIGHT));
//            setFocusable(true);
//            setLayout(null);
//            setBackground(Color.LIGHT_GRAY);
//        }};
//        add(panel, BorderLayout.WEST);
//
//        lessonPanel = new JPanel() {{
//            setPreferredSize(new Dimension(220, FRAME_HEIGHT));
//            setBackground(Color.WHITE);
//            setLayout(null);
//            setVisible(true);
//        }};
//        add(lessonPanel, BorderLayout.EAST);

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizedFrame();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        init();
    }

    /**
     * Инициализация компонентов.
     */
    private void init() {
        lessonContainer.add(CalendarService.getCalendarComponent(new Rectangle(10, 10, 220, 220)));

        lessonPanel = new JPanel() {{
            setSize(lessonContainer.getWidth() - 10, lessonContainer.getHeight() - 245);
            setLocation(5, 240);
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }};
        lessonContainer.add(lessonPanel);
//        JLabel kindSportLabel = new JLabel() {{
//            setText("Вид спорта");
//            setSize(100, 20);
//            setLocation(LOCATION_KIND_SPORT_X, LOCATION_KIND_SPORT_Y);
//        }};
//        panel.add(kindSportLabel);
//
//        JComboBox<LessonType> sportComboBox = new JComboBox<LessonType>() {{
//            setModel(new DefaultComboBoxModel<>(LessonService.getLessonsTypeNames()));
//            setLocation(LOCATION_KIND_SPORT_X + 110, LOCATION_KIND_SPORT_Y);
//            setSize(250, 20);
//            addActionListener(e -> viewLesson((LessonType) getSelectedItem(), panel));
//        }};
//        panel.add(sportComboBox);

    }

    /**
     * Действия при изминении окна.
     */
    private void resizedFrame() {
        lessonContainer.setLocation(getWidth() - 240, 0);
        lessonContainer.setSize(lessonContainer.getWidth(), getHeight());
        lessonPanel.setSize(lessonPanel.getWidth(), getHeight() - 245);
    }

    private static void viewLesson(LessonType type, JPanel panel) {
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
            if (defaultProperties.containsKey(property)) {
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

        panel.setVisible(false);
        panel.setVisible(true);
    }

}
