package org.othello.ui.components;

import org.othello.ui.Othello;

import javax.swing.*;
import java.awt.*;

/**
 * A custom control class that implements the JButton functionalities adding game-oriented
 * features such as custom graphics and different text font.
 *
 * @see javax.swing.JButton
 */
public class OthelloButton extends JButton {

    private Image img = new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutNormal.png").getImage() ;
    private Image img_prs = new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutPressed.png").getImage();

    /**
     * Default constructor, it initializes the options of the {@code JButton} to its own.
     */
    public OthelloButton() {
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setFont(Othello.DEFAULT_FONT);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setForeground(new Color(0xA07023));
    }

    /**
     * Changes the button graphics.
     * @param buttonNormal graphics for the button released state
     * @param buttonPressed graphics for the button pressed state
     */
    public void changeStyle(Image buttonNormal, Image buttonPressed) {
        img = buttonNormal;
        img_prs = buttonPressed;
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (this.getModel().isPressed()) {
            g.drawImage(img_prs, 0, 0, this.getWidth(), this.getHeight(), this);
            this.setVerticalAlignment(SwingConstants.BOTTOM);
        }
        else {
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            this.setVerticalAlignment(SwingConstants.CENTER);
        }

        super.paintComponent(g);
    }
}
