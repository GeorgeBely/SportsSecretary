package ru.SportsSecretary.services.swing;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;
import java.util.Locale;

/**
 * Сервис для работы с компонентами графиков.
 */
public class GraphicsService {

    /**
     * Создание компонента графика "Пирог"
     *
     * @param name       наименование графика
     * @param data       данные
     * @param background цвет панели графика
     * @return компонент графика "Пирог".
     */
    public static JFreeChart getPieComponent(String name, DefaultPieDataset data, Color background) {
        JFreeChart chart = ChartFactory.createPieChart(name, data);
        chart.setBackgroundPaint(background);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setExplodePercent(new Integer(0), 0.20);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}({2})"));
        plot.setBackgroundPaint(background);
        plot.setLabelLinkStyle(PieLabelLinkStyle.STANDARD);

        String noDataMessage = "No data available";
        if (Locale.getDefault().equals(new Locale("ru", "RU")))
            noDataMessage = "Нет данных";
        plot.setNoDataMessage(noDataMessage);

        int greenCount = 0;
        int redCount = 0;
        for (Object keyObj : data.getKeys()) {
            plot.setSectionPaint(keyObj.toString(), new Color(redCount, greenCount, 255));
            if (greenCount < 205)
                greenCount += 50;
            else if (redCount < 205)
                redCount += 50;
        }

        return chart;
    }

    /**
     * Создание компонента графика "График изминения значений по датам"
     *
     * @param name       наименование графика
     * @param nameX      наименование значения по абсцисе
     * @param nameY      наименование значения
     * @param data       данные
     * @param background цвет панели графика
     * @return компонент графика "График изминения значений по датам".
     */
    public static JFreeChart getTimeSeriesComponent(String name, String nameX, String nameY,
                                                    TimeSeriesCollection data, Color background) {
        JFreeChart chartCategory = ChartFactory.createTimeSeriesChart(name, nameX, nameY, data);
        chartCategory.setBackgroundPaint(background);

        return chartCategory;
    }
}
