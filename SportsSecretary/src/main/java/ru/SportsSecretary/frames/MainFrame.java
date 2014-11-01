package ru.SportsSecretary.frames;

import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import ru.SportsSecretary.lesson.LessonType;
import ru.SportsSecretary.lesson.Property;
import ru.SportsSecretary.services.LessonService;
import ru.SportsSecretary.services.swing.CalendarService;
import ru.SportsSecretary.services.swing.GraphicsService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Основной фрейм приложения.
 */
public class MainFrame extends JFrame {

    /** Ширина окна */
    private static final int DEFAULT_FRAME_WIDTH = 1100;

    /** Высота окна */
    private static final int DEFAULT_FRAME_HEIGHT = 800;

    /** Расположение блока с календарём и записями занятий */
    private static final int DEFAULT_LOCATION_RIGHT_PANEL_X = DEFAULT_FRAME_WIDTH - 235;
    private static final int DEFAULT_LOCATION_RIGHT_PANEL_Y = 5;
    private static final int DEFAULT_WEIGHT_RIGHT_PANEL = 240;

    /** Размер календаря (В данный момент квадратный) */
    private static final int DEFAULT_SIZE_CALENDAR = 230;

    /** Расположение головного блока параметров */
    private static final int DEFAULT_WEIGHT_HEADER_PANEL = DEFAULT_LOCATION_RIGHT_PANEL_X;
    private static final int DEFAULT_HEIGHT_HEADER_PANEL = 400;

    /** Расположение блока графиков */
    private static final int DEFAULT_LOCATION_GRAPHICS_PANEL_X = 5;
    private static final int DEFAULT_LOCATION_GRAPHICS_PANEL_Y = DEFAULT_FRAME_HEIGHT - 400;
    private static final int DEFAULT_WEIGHT_GRAPHICS_PANEL = DEFAULT_LOCATION_RIGHT_PANEL_X;
    private static final int DEFAULT_HEIGHT_GRAPHICS_PANEL = 400;

    /** Расположение выбора типа спорта */
    private static final int LOCATION_KIND_SPORT_X = 50;
    private static final int LOCATION_KIND_SPORT_Y = 50;

    /** Расположение характеристик спорта */
    private static final int LOCATION_PROPERTIES_X = 50;
    private static final int LOCATION_PROPERTIES_Y = 100;

    /** Наименование окна */
    private static final String TITLE = "SportSecretary";


    private Container lessonContainer;
    private Container headerContainer;
    private Container graphicsContainer;
    private JPanel lessonPanel;
    private ChartPanel categoryPanel;
    private ChartPanel piePanel;

    public MainFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        setLocation(screenSize.width / 2 - DEFAULT_FRAME_WIDTH / 2, screenSize.height / 2 - DEFAULT_FRAME_HEIGHT / 2);
        setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        setTitle(TITLE);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                resizeFrame();
            }
            public void componentMoved(ComponentEvent e) {}
            public void componentShown(ComponentEvent e) {}
            public void componentHidden(ComponentEvent e) {}
        });

        initComponent();
    }

    /**
     * Инициализация компонентов.
     */
    private void initComponent() {
        JPanel panel = new JPanel() {{
            setFocusable(true);
            setLayout(null);
//            setBackground(Color.LIGHT_GRAY);
        }};
        add(panel);

        initLessonContainer(panel);
        initHeaderContainer(panel);
        initGraphicsContainer(panel);
    }

    /**
     * Инициализация боковой панели. Календарь и список задач.
     */
    private void initLessonContainer(JPanel panel) {
        lessonContainer = new Container() {{
            setSize(DEFAULT_WEIGHT_RIGHT_PANEL, DEFAULT_FRAME_HEIGHT);
            setLocation(DEFAULT_LOCATION_RIGHT_PANEL_X, DEFAULT_LOCATION_RIGHT_PANEL_Y);
        }};
        panel.add(lessonContainer);

        lessonContainer.add(CalendarService.getCalendarComponent(new Rectangle(0, 0, DEFAULT_SIZE_CALENDAR, DEFAULT_SIZE_CALENDAR)));

        lessonPanel = new JPanel() {{
            setSize(lessonContainer.getWidth() - 10, lessonContainer.getHeight() - 245);
            setLocation(0, 230);
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }};
        lessonContainer.add(lessonPanel);

    }

    /**
     * Инициализация верхней панели выбора типа упражений и другие параметры.
     */
    private void initHeaderContainer(JPanel panel) {
        headerContainer = new Container() {{
            setSize(DEFAULT_WEIGHT_HEADER_PANEL, DEFAULT_HEIGHT_HEADER_PANEL);
            setLocation(0, 0);
            setBackground(Color.BLUE);
        }};
        panel.add(headerContainer);


        JLabel kindSportLabel = new JLabel() {{
            setText("Вид спорта");
            setSize(100, 20);
            setLocation(LOCATION_KIND_SPORT_X, LOCATION_KIND_SPORT_Y);
            setBackground(Color.CYAN);
        }};
        headerContainer.add(kindSportLabel);

        JComboBox<LessonType> sportComboBox = new JComboBox<LessonType>() {{
            setModel(new DefaultComboBoxModel<>(LessonService.getLessonsTypeNames()));
            setLocation(LOCATION_KIND_SPORT_X + 110, LOCATION_KIND_SPORT_Y);
            setSize(250, 20);
            addActionListener(e -> viewLesson((LessonType) getSelectedItem()));
        }};
        headerContainer.add(sportComboBox);
    }

    private void initGraphicsContainer(JPanel panel) {
        graphicsContainer = new Container() {{
            setLocation(DEFAULT_LOCATION_GRAPHICS_PANEL_X, DEFAULT_LOCATION_GRAPHICS_PANEL_Y);
            setSize(DEFAULT_WEIGHT_GRAPHICS_PANEL, DEFAULT_HEIGHT_GRAPHICS_PANEL);
        }};
        panel.add(graphicsContainer);

        DefaultPieDataset data = new DefaultPieDataset() {{
            setValue("трицепс", 44);
            setValue("гантеля", 36);
            setValue("прес", 26);
            setValue("бег", 20);
            setValue("растяжка", 19);
            setValue("отжимания", 14);
            setValue("присидания", 14);
            setValue("подтягивания", 12);
            setValue("жим лёжа", 11);
            setValue("прочие", 4);
        }};
        piePanel = new ChartPanel(GraphicsService.getPieComponent("Время затраченное на тренировки", data, this.getBackground())) {{
            setLocation(0, 0);
            setSize(450, 350);
        }};
        graphicsContainer.add(piePanel);

        TimeSeriesCollection dataset = new TimeSeriesCollection() {{
            addSeries(new TimeSeries("Гонтеля") {{
                add(new Minute(0, 0, 7, 12, 2012), 50);
                add(new Minute(30, 12, 7, 2, 2013), 70);
                add(new Minute(15, 14, 7, 12, 2013), 80);
                add(new Minute(new Date()), 100);
            }});
            addSeries(new TimeSeries("Штанга") {{
                add(new Minute(0, 3, 7, 12, 2012), 5);
                add(new Minute(30, 9, 7, 2, 2013), 12);
                add(new Minute(15, 10, 7, 12, 2013), 15);
                add(new Minute(new Date()), 18);
            }});
        }};

        categoryPanel = new ChartPanel(GraphicsService.getTimeSeriesComponent("График отношение веса",
                                       "Дата тренировки", "вес", dataset, this.getBackground())) {{
            setLocation(450, 0);
            setSize(450, 350);
        }};
        graphicsContainer.add(categoryPanel);
    }

    /**
     * Действия при изминении размера окна.
     */
    private void resizeFrame() {
        graphicsContainer.setSize(lessonContainer.getX(), graphicsContainer.getHeight());
        if (getHeight() - DEFAULT_HEIGHT_GRAPHICS_PANEL < DEFAULT_LOCATION_GRAPHICS_PANEL_Y) {
            if (getHeight() - DEFAULT_LOCATION_GRAPHICS_PANEL_Y < DEFAULT_HEIGHT_GRAPHICS_PANEL/2) {
                graphicsContainer.setVisible(false);
            } else {
                graphicsContainer.setVisible(true);
                graphicsContainer.setSize(graphicsContainer.getWidth(), getHeight() - DEFAULT_HEIGHT_GRAPHICS_PANEL);
            }
        } else {
            graphicsContainer.setLocation(0, getHeight() - DEFAULT_HEIGHT_GRAPHICS_PANEL);
        }


        lessonContainer.setLocation(getWidth() - 235, 5);
        lessonContainer.setSize(lessonContainer.getWidth(), getHeight());


        categoryPanel.setLocation(graphicsContainer.getWidth() - 460, 0);
        categoryPanel.setSize(categoryPanel.getWidth(), graphicsContainer.getHeight() - 10);
        piePanel.setSize(categoryPanel.getX() - 10, graphicsContainer.getHeight() - 10);


        lessonPanel.setSize(lessonPanel.getWidth(), getHeight() - DEFAULT_SIZE_CALENDAR - DEFAULT_LOCATION_RIGHT_PANEL_Y - 5);


    }

    private void viewLesson(LessonType type) {
        int propertyX = LOCATION_PROPERTIES_X;
        int propertyY = LOCATION_PROPERTIES_Y;


//        Lesson lesson = new Lesson();
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
                headerContainer.add(propertyLabel);

                JLabel valueLabel = new JLabel();
                valueLabel.setText(String.valueOf(defaultProperties.get(property)));
                valueLabel.setSize(100, 20);
                valueLabel.setLocation(propertyX + 100, propertyY);
                headerContainer.add(valueLabel);

                propertyY += 30;
            }
        }

//        panel.setVisible(false);
//        panel.setVisible(true);
    }

}
