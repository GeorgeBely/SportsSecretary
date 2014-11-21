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
import ru.SportsSecretary.swing.Container;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.*;

/**
 * Основной фрейм приложения.
 */
public class MainFrame extends JFrame {

    /** Ширина окна */
    private static final int DEFAULT_FRAME_WIDTH = 1100;

    /** Высота окна */
    private static final int DEFAULT_FRAME_HEIGHT = 800;

    /** Расположение блока с календарём и записями занятий */
    private static final int DEFAULT_LOCATION_RIGHT_PANEL_Y = 5;
    private static final int DEFAULT_WIDTH_RIGHT_PANEL = 240;
    private static final int DEFAULT_HEIGHT_RIGHT_PANEL = DEFAULT_FRAME_HEIGHT;
    private static final int DEFAULT_LOCATION_RIGHT_PANEL_INVERT_X = DEFAULT_WIDTH_RIGHT_PANEL + 5;

    /** Размер календаря (В данный момент квадратный) */
    private static final int DEFAULT_SIZE_CALENDAR = 230;

    /** Расположение головного блока параметров */
    private static final int DEFAULT_WIDTH_HEADER_PANEL = DEFAULT_FRAME_WIDTH - DEFAULT_LOCATION_RIGHT_PANEL_INVERT_X;
    private static final int DEFAULT_HEIGHT_HEADER_PANEL = 400;

    /** Расположение блока графиков */
    private static final int DEFAULT_LOCATION_GRAPHICS_PANEL_X = 5;
    private static final int DEFAULT_WIDTH_GRAPHICS_PANEL = DEFAULT_FRAME_WIDTH - DEFAULT_LOCATION_RIGHT_PANEL_INVERT_X - DEFAULT_LOCATION_GRAPHICS_PANEL_X;
    private static final int DEFAULT_HEIGHT_GRAPHICS_PANEL = 400;
    private static final int DEFAULT_LOCATION_GRAPHICS_PANEL_Y = DEFAULT_FRAME_HEIGHT - DEFAULT_HEIGHT_GRAPHICS_PANEL - 5;

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
    private JScrollPane lessonPane;
    private JTable lessonTable;
//    private ChartPanel categoryPanel;
//    private ChartPanel piePanel;

    public MainFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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
        initValues();
    }

    /**
     * Инициализация компонентов.
     */
    private void initComponent() {
        JPanel panel = new JPanel() {{
            setFocusable(true);
            setLayout(null);
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
        lessonContainer = new Container(DEFAULT_LOCATION_RIGHT_PANEL_INVERT_X, DEFAULT_LOCATION_RIGHT_PANEL_Y,
                DEFAULT_WIDTH_RIGHT_PANEL, DEFAULT_HEIGHT_RIGHT_PANEL, true, false, DEFAULT_LOCATION_RIGHT_PANEL_INVERT_X /2,
                null, DEFAULT_WIDTH_RIGHT_PANEL /2, DEFAULT_HEIGHT_RIGHT_PANEL/2, this);
        panel.add(lessonContainer);
        lessonContainer.getPanel().setLayout(null);

        lessonContainer.add(CalendarService.getCalendarComponent(new Rectangle(5, 0, lessonContainer.getWidth() - 10,
                lessonContainer.getWidth() - 10)));

        lessonTable = new JTable() {{
            setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            ((DefaultTableModel) getModel()).setColumnIdentifiers(new String[]{"Время", "Вид спорта", "Значение"});
        }};
        lessonPane = new JScrollPane(lessonTable) {{
            setSize(lessonContainer.getWidth() - 10, lessonContainer.getHeight() - DEFAULT_SIZE_CALENDAR - 15);
            setLocation(5, DEFAULT_SIZE_CALENDAR + 5);
        }};

        lessonContainer.add(lessonPane);

    }

    /**
     * Инициализация верхней панели выбора типа упражений и другие параметры.
     */
    private void initHeaderContainer(JPanel panel) {
        headerContainer = new Container(0, 0, DEFAULT_WIDTH_HEADER_PANEL, DEFAULT_HEIGHT_HEADER_PANEL, false, false,
                null, null, DEFAULT_WIDTH_HEADER_PANEL /2, DEFAULT_HEIGHT_HEADER_PANEL/2, this);
        panel.add(headerContainer);
        headerContainer.getPanel().setLayout(null);


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

        JButton addButton = new JButton() {{
            setText("Добавить задачу");
            setLocation(500, 50);
            setSize(200, 30);
            addActionListener(e -> EventQueue.invokeLater(AddLessonFrame::new));
        }};
        headerContainer.add(addButton);
    }

    private void initGraphicsContainer(JPanel panel) {
        graphicsContainer = new Container(DEFAULT_LOCATION_GRAPHICS_PANEL_X, DEFAULT_LOCATION_GRAPHICS_PANEL_Y,
                DEFAULT_WIDTH_GRAPHICS_PANEL, DEFAULT_HEIGHT_GRAPHICS_PANEL, false, true, null,
                DEFAULT_LOCATION_GRAPHICS_PANEL_Y/2, DEFAULT_WIDTH_GRAPHICS_PANEL /2, DEFAULT_HEIGHT_GRAPHICS_PANEL/2, this);
        panel.add(graphicsContainer);
        graphicsContainer.getPanel().setLayout(null);

        TimeSeriesCollection dataSet = new TimeSeriesCollection() {{
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

        Container categoryContainer = new Container(450, 0, 450, graphicsContainer.getHeight() - 10,
                true, false, 0, null, 0, 0, graphicsContainer);
        categoryContainer.getPanel().setLayout(null);
        ChartPanel categoryPanel = new ChartPanel(GraphicsService.getTimeSeriesComponent("График отношение веса",
                "Дата тренировки", "вес", dataSet, this.getBackground())) {{
            setLocation(0, 0);
            setSize(categoryContainer.getSize());
        }};
        categoryContainer.add(categoryPanel);
        graphicsContainer.add(categoryContainer);


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
        Container pieContainer = new Container(0, 0, categoryContainer.getX() - 10,
                graphicsContainer.getHeight() - 10, false, false, null, null, 0, 0, graphicsContainer);
        pieContainer.getPanel().setLayout(null);
        ChartPanel piePanel = new ChartPanel(GraphicsService.getPieComponent("Время затраченное на тренировки", data, this.getBackground())) {{
            setLocation(0, 0);
            setSize(pieContainer.getSize());
        }};
        pieContainer.add(piePanel);
        graphicsContainer.add(pieContainer);
    }

    /**
     * Действия при изминении размера окна.
     */
    private void resizeFrame() {
        graphicsContainer.resize();
        lessonContainer.resize();

//        categoryPanel.setLocation(graphicsContainer.getWidth() - 460, 0);
//        categoryPanel.setSize(categoryPanel.getWidth(), graphicsContainer.getHeight() - 10);
//        piePanel.setSize(categoryPanel.getX() - 10, graphicsContainer.getHeight() - 10);


        lessonPane.setSize(lessonPane.getWidth(), getHeight() - DEFAULT_SIZE_CALENDAR - DEFAULT_LOCATION_RIGHT_PANEL_Y - 5);
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

                propertyY += 20;
            }
        }

//        panel.setVisible(false);
//        panel.setVisible(true);
    }

    private void initValues() {

    }

}
