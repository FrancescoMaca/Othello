package org.othello.ui.components;

import org.othello.logic.board.CellState;
import org.othello.ui.Othello;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing graphically a {@code Cell} object.
 *
 * @see javax.swing.JButton
 */
public class OthelloCell extends JButton {

    /**
     * Cell's state graphics
     */
    private static ImageIcon whiteIcon = new ImageIcon(Othello.IMAGE_PATH + "skins\\0w.png");
    private static ImageIcon blackIcon = new ImageIcon(Othello.IMAGE_PATH + "skins\\0b.png");
    private static ImageIcon availableIcon = new ImageIcon(Othello.IMAGE_PATH + "skins\\0a.png");

    public OthelloCell() {

    }

    /**
     * Changes the white icon to the given one
     * @param img the new white icon
     */
    public static void setWhiteIcon(ImageIcon img) {
        whiteIcon = img;
    }

    /**
     * Changes the black icon to the given one
     * @param img the new black icon
     */
    public static void setBlackIcon(ImageIcon img) {
        blackIcon = img;
    }

    /**
     * Changes the available icon to the given one
     * @param img the new available icon
     */
    public static void setAvailableIcon(ImageIcon img) {
        availableIcon = img;
    }

    /**
     * @return the current white icon
     */
    public static ImageIcon getWhiteIcon() {
        return whiteIcon;
    }

    /**
     * @return the current black icon
     */
    public static ImageIcon getBlackIcon() {
        return blackIcon;
    }

    /**
     * @return the current available icon
     */
    public static ImageIcon getAvailableIcon() {
        return availableIcon;
    }

    /**
     * changes the state of the current instance
     * @param newState the new cell state
     */
    public void changeState(CellState newState) {
        if (this.getWidth() <= 0 || this.getHeight() <= 0) {
            return;
        }

        ImageIcon icon;

        icon = switch (newState) {
            case Black -> new ImageIcon(blackIcon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
            case White -> new ImageIcon(whiteIcon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
            case Available -> new ImageIcon(availableIcon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
            default -> null;
        };

        this.setIcon(icon);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
