package ru.SportsSecretary.start;

import ru.SportsSecretary.frames.MainFrame;

import javax.swing.*;

/**
 * Стартующий класс.
 */
public class Start {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

}
