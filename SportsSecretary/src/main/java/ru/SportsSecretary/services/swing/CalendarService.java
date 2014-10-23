package ru.SportsSecretary.services.swing;

import com.javaswingcomponents.calendar.JSCCalendar;
import com.javaswingcomponents.calendar.cellrenderers.CalendarCellRenderer;
import com.javaswingcomponents.calendar.cellrenderers.CellRendererComponentParameter;
import com.javaswingcomponents.calendar.listeners.CalendarSelectionEventType;
import com.javaswingcomponents.calendar.model.DayOfWeek;
import com.javaswingcomponents.calendar.plaf.darksteel.*;
import com.javaswingcomponents.framework.painters.configurationbound.BlankPainter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * Сервис для работы с компонентом календарь.
 */
public class CalendarService {


    public static JSCCalendar getCalendarComponent() {
        JSCCalendar calendar = new JSCCalendar() {{
            setUI(DarkSteelCalendarUI.createUI(this));
            setCalendarCellRenderer(getCustomCellRenderer());
            setLocation(10, 10);
            setSize(200, 200);
            getCalendarModel().setFirstDayOfWeek(DayOfWeek.MONDAY);
        }};

        calendar.addCalendarSelectionListener(calendarSelectionEvent -> {
            java.util.List<Date> selectedDates = calendarSelectionEvent.getSelectedDates();
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

        return calendar;
    }

    public static CustomCellRenderer getCustomCellRenderer() {
        return new CustomCellRenderer();
    }

    /**
     * If you are still not happy with the level of customisation the
     * CalendarCellRenderer
     * gives you, you may change the UI directly. Each UI will have different properties
     * It is recommended that you check the javadoc for each ui to discover what getters
     * and setters are available.
     * In this example will will tweak the DarkSteelCalendarUI
     * @param calendar
     */
    public static void howToChangeTheAppearanceOfTheOverallUI(JSCCalendar calendar) throws IOException {
//All current calendarUI's are subclasses of of StandardFormatCalendarUI.
//A StandardFormatCalendarUI comprises the following pieces
//1. A month and year panel located at the top of the component
//2. A cell panel located below the month and year panel containing the cells
//3. The cell panel contains a background painter
//4. The month and year panel contains a background painter
//Each of the above classes including the StandardFormatCalendarUI
//can be tweaked or replaced to create your ideal calendar.
        DarkSteelCalendarUI calendarUI = (DarkSteelCalendarUI) calendar.getUI();
        DarkSteelMonthAndYearPanel monthAndYearPanel = (DarkSteelMonthAndYearPanel)
                calendarUI.getMonthAndYearPanel();
        DarkSteelCellPanel cellPanel = (DarkSteelCellPanel) calendarUI.getCellPanel();
        DarkSteelCellPanelBackgroundPainter cellPanelBackgroundPainter =
                (DarkSteelCellPanelBackgroundPainter) cellPanel.getBackgroundPainter();
//The DarkSteelCalendarUI does not make use of a backgroundPainter on the
//monthAndYearPanel.
//that is why the blankPainter was assigned. However any painter could be
// applied in its place.
        BlankPainter blankPainter = (BlankPainter)
                monthAndYearPanel.getBackgroundPainter();
//we will change the font on the month and year panel to black, bold and
//slightly larger
        Font newFont = monthAndYearPanel.getDateLabel().getFont()
                .deriveFont(Font.BOLD).deriveFont(16f);
        monthAndYearPanel.getDateLabel().setForeground(Color.BLACK);
        monthAndYearPanel.getDateLabel().setFont(newFont);

//these images will replace the standard images used for the buttons with
//larger red images
        ImageIcon nextMonth = new ImageIcon(ImageIO.read(new File("demoNextMonth.png")));
        ImageIcon nextYear = new ImageIcon(ImageIO.read(new File("demoNextYear.png")));
        ImageIcon prevMonth = new ImageIcon(ImageIO.read(new File("demoPrevMonth.png")));
        ImageIcon prevYear = new ImageIcon(ImageIO.read(new File("demoPrevYear.png")));
        ImageIcon rolloverNextMonth = new
                ImageIcon(ImageIO.read(new File("demoRolloverNextMonth.png")));
        ImageIcon rolloverNextYear = new
                ImageIcon(ImageIO.read(new File("demoRolloverNextYear.png")));
        ImageIcon rolloverPrevMonth = new
                ImageIcon(ImageIO.read(new File("demoRolloverPrevMonth.png")));
        ImageIcon rolloverPrevYear = new
                ImageIcon(ImageIO.read(new File("demoRolloverPrevYear.png")));
        monthAndYearPanel.getIncrementMonthButton().setIcon(nextMonth);
        monthAndYearPanel.getIncrementYearButton().setIcon(nextYear);
        monthAndYearPanel.getDecrementMonthButton().setIcon(prevMonth);
        monthAndYearPanel.getDecrementYearButton().setIcon(prevYear);
        monthAndYearPanel.getIncrementMonthButton().setRolloverIcon(rolloverNextMonth);
        monthAndYearPanel.getIncrementYearButton().setRolloverIcon(rolloverNextYear);
        monthAndYearPanel.getDecrementMonthButton().setRolloverIcon(rolloverPrevMonth);
        monthAndYearPanel.getDecrementYearButton().setRolloverIcon(rolloverPrevYear);
//we will remove the selectedIcon
        monthAndYearPanel.getIncrementMonthButton().setPressedIcon(null);
        monthAndYearPanel.getIncrementYearButton().setPressedIcon(null);
        monthAndYearPanel.getDecrementMonthButton().setPressedIcon(null);
        monthAndYearPanel.getDecrementYearButton().setPressedIcon(null);
//we will tweak the colours on the cellPanel background painter.
        cellPanelBackgroundPainter.setHeadingBackgroundEndGradientColor(Color.WHITE);
        cellPanelBackgroundPainter.setHeadingBackgroundEndGradientColor(Color.RED);
//there are far too many options to tweak but the ones used above should
//create a nice festive calendar.
    }




}


/**
 * Класс переопределяющий описывание ячейки календаря.
 */
class CustomCellRenderer extends JLabel implements CalendarCellRenderer {

    @Override
    public JComponent getCellRendererComponent(CellRendererComponentParameter parameterObject) {

        DarkSteelCalendarCellRenderer defaultCell = (DarkSteelCalendarCellRenderer)
                new DarkSteelCalendarCellRenderer().getCellRendererComponent(parameterObject);

        setHorizontalAlignment(defaultCell.getHorizontalAlignment());
        setIcon(defaultCell.getIcon());
        setText(defaultCell.getText());
        setOpaque(defaultCell.isOpaque());
        setForeground(defaultCell.getForeground());
        setBorder(defaultCell.getBorder());
        setBackground(defaultCell.getBackground());


        if (parameterObject.isCurrentMonth) {
//        boolean isHoliday = parameterObject.isHoliday;
//        boolean isMouseOver = parameterObject.isMouseOver;
//        boolean isToday = parameterObject.isToday;
//        boolean isSelected = parameterObject.isSelected;
//        boolean isSelectable = parameterObject.isAllowSelection();
//        boolean hasKeyboardFocus = parameterObject.isHasFocus();
//        String text = parameterObject.getText();
//        Date date = parameterObject.getDate();
//        JSCCalendar calendar = parameterObject.getCalendar();
//
//        setHorizontalAlignment(JLabel.CENTER);
//        setIcon(null);
//        setText(text);
//        setOpaque(false);
//

        if (parameterObject.isWeekend) {
            setForeground(new Color(65, 237, 214));
            setOpaque(true);
//            setBorder(BorderFactory.createLineBorder(new Color(65, 237, 214)));
        }


//        if (isSelectable) {
//            setForeground(Color.BLACK);
//        } else {
//            setText(text);
//            setForeground(Color.LIGHT_GRAY);
//        }
//        if (!isCurrentMonth) {
//            setText("");
//        }

//        Icon holidayIcon = null;
//        Icon weekendIcon = null;
//        Icon todayIcon = null;
//        try {
//            holidayIcon = new ImageIcon(ImageIO.read(new File("/home/georg/1.png")));
//            weekendIcon = new ImageIcon(ImageIO.read(new File("/home/georg/2.png")));
//            todayIcon = new ImageIcon(ImageIO.read(new File("/home/georg/3.png")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        if (isHoliday) {
//            Holiday holiday = calendar.getCalendarModel().getHolidayForDate(date);
//            if (holiday.getDescription().contains("Christmas")) {
//                setIcon(holidayIcon);
//                setText("");
//            }
//            setBackground(Color.CYAN);
//            setBorder(BorderFactory.createLineBorder(Color.WHITE));
//        }
//        if (isToday) {
//            setIcon(todayIcon);
//            setText("");
//            setForeground(Color.GREEN);
//            setBorder(BorderFactory.createLineBorder(Color.WHITE));
//        }
//

//        if (isSelected) {
//            setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        } else {
//            setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
//        }
//        if (hasKeyboardFocus) {
//            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//        }
        }
        return this;
    }

    @Override
    public JComponent getHeadingCellRendererComponent(JSCCalendar calendar, String text) {
        DarkSteelCalendarCellRenderer defaultHeading = (DarkSteelCalendarCellRenderer)
                new DarkSteelCalendarCellRenderer().getHeadingCellRendererComponent(calendar, text);
        return defaultHeading;

//        setHorizontalAlignment(JLabel.CENTER);
//        setText(text);
//        setOpaque(false);
//        setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
//        setForeground(Color.BLACK);
//        setIcon(null);
//        return this;
    }
}