package ru.SportsSecretary.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Контэйнер для объектов фреёма.
 * Реализует подобие адаптивной вёрстки.
 */
public class Container extends java.awt.Container {

    private JPanel panel;

    private int defaultX;
    private int defaultY;
    private int indentWidth;
    private int indentHeight;
    private boolean invertedX;
    private boolean invertedY;
    private Integer xMin;
    private Integer yMin;
    private Integer widthMin;
    private Integer heightMin;


    public Container(int x, int y, int width, int height, boolean invertedX, boolean invertedY, Integer xMin,
                     Integer yMin, Integer widthMin, Integer heightMin, java.awt.Container parentContainer) {
        this.defaultX = x;
        this.defaultY = y;
        this.xMin = xMin != null ? xMin : x;
        this.yMin = yMin != null ? yMin : y;
        this.widthMin = widthMin != null ? widthMin : width;
        this.heightMin = heightMin != null ? heightMin : height;
        this.invertedX = invertedX;
        this.invertedY = invertedY;
        this.indentWidth = parentContainer.getWidth() - width - x;
        this.indentHeight = parentContainer.getHeight() - height - y;
        this.panel = new JPanel() {{
            setLocation(0, 0);
            setSize(width, height);
        }};
        super.add(panel);

        setSize(width, height);
        setLocation(invertedX ? parentContainer.getWidth() - x : x, invertedY ? parentContainer.getHeight() - y : y);
    }

    public void resize() {
        java.awt.Container parentContainer = getParent();
        Integer newLocationX = defaultX;
        if (invertedX) {
            newLocationX = parentContainer.getWidth() - defaultX;
            if (newLocationX < xMin) {
                newLocationX = xMin;
            }
        }
        Integer newLocationY = defaultY;
        if (invertedY) {
            newLocationY = parentContainer.getHeight() - defaultY;
            if (newLocationY < yMin) {
                newLocationY = yMin;
            }
        }
        Integer newWidth = getWidth();
        if (getX() == xMin) {
            newWidth = parentContainer.getWidth() - xMin - indentWidth;
        }
        Integer newHeight = getHeight();
        if (getY() == yMin) {
            newHeight = parentContainer.getHeight() - yMin - indentHeight;
        }

        if (newWidth < widthMin || newHeight < heightMin) {
            setVisible(false);
        } else {
            setVisible(true);
            setSize(newWidth, newHeight);
            setLocation(newLocationX, newLocationY);
        }

        panel.setSize(getWidth(), getHeight());

        for (Component component : panel.getComponents()) {
            if (component instanceof Container)
                ((Container) component).resize();
        }
    }

    public Component add(Component comp) {
        return panel.add(comp);
    }

    public JPanel getPanel() {
        return panel;
    }
}
