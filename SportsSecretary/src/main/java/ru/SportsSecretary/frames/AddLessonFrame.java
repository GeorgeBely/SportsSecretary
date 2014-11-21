package ru.SportsSecretary.frames;

import ru.SportsSecretary.lesson.Lesson;
import ru.SportsSecretary.lesson.LessonType;
import ru.SportsSecretary.lesson.Property;
import ru.SportsSecretary.services.LessonService;
import ru.SportsSecretary.swing.Container;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;
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
    private static final int DEFAULT_HEIGHT_VALUES_PANEL = 300;

    /** Расположение блока описание */
    private static final int DEFAULT_LOCATION_DESCRIPTION_X = 5;
    private static final int DEFAULT_LOCATION_DESCRIPTION_Y = 400;
    private static final int DEFAULT_HEIGTH_DESCRIPTION = 140;


    private Container valuesContainer;
    private AddLessonFrame thisFrame;
    private JComboBox<LessonType> valuesComboBox;
    private JTextArea descriptionText;

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
        thisFrame = this;

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

        valuesComboBox = new JComboBox<LessonType>() {{
            setSize(250, 20);
            setModel(new DefaultComboBoxModel<>(LessonService.getLessonsTypeNames()));
            addActionListener(e -> initValuesContainer(panel, (LessonType) getSelectedItem()));
        }};
        valuesComboBox.setLocation(getWidth()/2 - valuesComboBox.getWidth()/2, 5);
        panel.add(valuesComboBox);

        initValuesContainer(panel, null);

        descriptionText = new JTextArea() {{
            setLineWrap(true);
            setWrapStyleWord(true);
        }};
        JScrollPane scrollPane = new JScrollPane() {{
            setViewportView(descriptionText);
            setVisible(true);
            setLocation(DEFAULT_LOCATION_DESCRIPTION_X, DEFAULT_LOCATION_DESCRIPTION_Y);
            setSize(thisFrame.getWidth() - 10, DEFAULT_HEIGTH_DESCRIPTION);
        }};
        panel.add(scrollPane);

        JButton okButton = new JButton() {{
            setText("OK");
            setSize(getText().length() * 5 + 100, 30);
            setLocation(thisFrame.getWidth() / 2 - getWidth() / 2, thisFrame.getHeight() - 60);
            addActionListener(e -> addLesson((LessonType) valuesComboBox.getSelectedItem()));
        }};
        panel.add(okButton);
    }

    /**
     * Инициализация верхней панели выбора типа упражений и другие параметры.
     */
    private void initValuesContainer(JPanel panel, LessonType lessonType) {
        if (valuesContainer != null)
            valuesContainer.removeAll();

        valuesContainer = new Container(DEFAULT_LOCATION_VALUES_X, DEFAULT_LOCATION_VALUES_Y,
                DEFAULT_WIDTH_VALUES_PANEL, DEFAULT_HEIGHT_VALUES_PANEL, false, false, null, null,
                DEFAULT_WIDTH_VALUES_PANEL/2, DEFAULT_HEIGHT_VALUES_PANEL/2, this);
        panel.add(valuesContainer);


        if (lessonType == null)
            return;

        for (Property property : lessonType.getProperties()) {
            JLabel propertyName = new JLabel() {{
                setText(property.toString() + ": ");
                setSize(getText().length() * 5 + 100, 20);
            }};
            JPanel valuePanel = new JPanel() {{
                setLocation(0,0);
                setSize(propertyName.getWidth() + 100, 20);
            }};
            valuePanel.add(propertyName);
            if ("Number".equals(property.getType())) {
                JTextField numberField = new JTextField() {{
                    setPreferredSize(new Dimension(100, 20));
                    setName(property.getName());
                }};
                valuePanel.add(numberField);
            } else if ("Text".equals(property.getType())) {
                JTextArea text = new JTextArea() {{
                    setSize(100, 50);
                }};
                valuePanel.add(text);
            } else if ("Selected".equals(property.getType())) {
                JComboBox<String> valuesComboBox = new JComboBox<String>() {{
                    setSize(150, 20);
                    setModel(new DefaultComboBoxModel<>(new Vector<>(property.getValues())));
                }};
                valuePanel.add(valuesComboBox);
            }

            valuesContainer.add(valuePanel);

        }

        valuesContainer.resize();
    }

    public void resizeFrame() {
        valuesContainer.resize();
    }

    public void addLesson(LessonType lessonType) {
        if (lessonType == null)
            return;

        Lesson lesson = new Lesson();
        Map<Property, Object> properties = new HashMap<>();

        for (Component component : valuesContainer.getPanel().getComponents()) {
            for (Component valueComponent : ((JPanel) component).getComponents()) {
                Property property = lessonType.getPropertyByCode(valueComponent.getName());
                if (valueComponent instanceof JTextField) {
                    properties.put(property, ((JTextField) valueComponent).getText());
                } else if (valueComponent instanceof JTextArea) {
                    properties.put(property, ((JTextArea) valueComponent).getText());
                } else if (valueComponent instanceof JComboBox) {
                    properties.put(property, ((JComboBox) valueComponent).getSelectedItem());
                }
            }
        }
        lesson.setProperties(properties);
        lesson.setDescription(descriptionText.getText());



        dispose();
    }
}
