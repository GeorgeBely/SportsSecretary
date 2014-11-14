package ru.SportsSecretary.frames;

import ru.SportsSecretary.lesson.LessonType;
import ru.SportsSecretary.lesson.Property;
import ru.SportsSecretary.lesson.Pushups;
import ru.SportsSecretary.services.LessonService;
import ru.SportsSecretary.swing.Container;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Vector;

public class AddLessonFrame extends JFrame {

    /** Ширина окна */
    private static final int DEFAULT_FRAME_WIDTH = 400;

    /** Высота окна */
    private static final int DEFAULT_FRAME_HEIGHT = 600;

    /** Наименование окна */
    private static final String TITLE = "AddLesson";

    /** Расположение блока с атрибутами занятий */
    private static final int DEFAULT_LOCATION_VALUES_X = 0;
    private static final int DEFAULT_LOCATION_VALUES_Y = 100;
    private static final int DEFAULT_WIDTH_VALUES_PANEL = 400;
    private static final int DEFAULT_HEIGHT_VALUES_PANEL = 430;


    private Container valuesContainer;

    public AddLessonFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - DEFAULT_FRAME_WIDTH / 2, screenSize.height / 2 - DEFAULT_FRAME_HEIGHT / 2);
        setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        setTitle(TITLE);
        setVisible(true);

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
        }};
        add(panel);

        JComboBox<LessonType> valuesComboBox = new JComboBox<LessonType>() {{
            setSize(250, 20);
            setModel(new DefaultComboBoxModel<>(LessonService.getLessonsTypeNames()));
        }};
        valuesComboBox.setLocation(getWidth()/2 - valuesComboBox.getWidth()/2, 5);
        panel.add(valuesComboBox);

        initValuesContainer(panel, new Pushups());

        JButton okButton = new JButton() {{
            setText("OK");
            setSize(getText().length() * 5 + 100, 30);
        }};
        okButton.setLocation(getWidth()/2 - okButton.getWidth()/2, getHeight() - 60);
        panel.add(okButton);
    }

    /**
     * Инициализация верхней панели выбора типа упражений и другие параметры.
     */
    private void initValuesContainer(JPanel panel, LessonType lessonType) {
        if (lessonType == null)
            return;

        valuesContainer = new Container(DEFAULT_LOCATION_VALUES_X, DEFAULT_LOCATION_VALUES_Y,
                DEFAULT_WIDTH_VALUES_PANEL, DEFAULT_HEIGHT_VALUES_PANEL, false, false, null, null,
                DEFAULT_WIDTH_VALUES_PANEL/2, DEFAULT_HEIGHT_VALUES_PANEL/2, this);
        panel.add(valuesContainer);

        JPanel propertiesPanel = new JPanel() {{
            setLocation(0, 0);
            setSize(valuesContainer.getWidth(), valuesContainer.getHeight());
        }};
        valuesContainer.add(propertiesPanel);

        for (Property property : lessonType.getProperties()) {
            JLabel propertyName = new JLabel() {{
                setText(property.toString() + ": ");
                setSize(100, 20);
            }};
            propertiesPanel.add(propertyName);
            if ("Number".equals(property.getType())) {
                JTextField numberField = new JTextField() {{
                    setPreferredSize(new Dimension(100, 20));
                }};
                propertiesPanel.add(numberField);
            } else if ("Text".equals(property.getType())) {
                JTextArea text = new JTextArea() {{
                    setSize(100, 50);
                }};
                propertiesPanel.add(text);
            } else if ("Selected".equals(property.getType())) {
                JComboBox<String> valuesComboBox = new JComboBox<String>() {{
                    setSize(150, 20);
                    setModel(new DefaultComboBoxModel<>(new Vector<>(property.getValues())));
                }};
                propertiesPanel.add(valuesComboBox);
            }


        }
    }

    public void resizeFrame() {
        valuesContainer.resize(this);
    }
}
