package org.othello.ui.components;

import org.othello.ui.Othello;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Custom JPanel from where all Othello's windows are inherited. This class provides basic functionalities and
 * sets up the custom fonts and basic window size.
 */
public class OthelloScene extends JPanel {

    // This static field creates the font on the user's machine and registers it as a valid font.
    // If the font cannot be loaded, the default font becomes "SanSerif" (possible graphics discrepancies
    // can happen)
    static {
        try {
            GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(Othello.FONT_PATH + Othello.DEFAULT_FONT_FILE)));
            Othello.DEFAULT_FONT = new Font("Othello MT Std", Font.PLAIN, Othello.DEFAULT_FONT_SIZE);

        } catch (FontFormatException | IOException e) {
            Othello.DEFAULT_FONT = new Font("SanSerif", Font.PLAIN, Othello.DEFAULT_FONT_SIZE);
        }
    }

    /**
     * default constructor
     */
    protected OthelloScene() {

    }

    /**
     * Sets the default and primary settings of the window (minimum size, current size and sets the layout to
     * null).
     */
    protected final void setup() {

        this.setMinimumSize(new Dimension(Othello.WIDTH, Othello.HEIGHT));
        this.setSize(Othello.WIDTH, Othello.HEIGHT);
        this.setLayout(null);
        this.setVisible(true);
    }

    /**
     * Create a dialog from the current window locking the focus to it.
     * @param title the dialog's title
     * @param message the message to display
     * @param background the image to render in the background
     * @param isYesNo flag for the dialog's button layout
     */
    protected final void createDialog(String title, String message, Image background, boolean isYesNo) {
        final int width = 500;
        final int height = 200;

        JDialog di_finished = new JDialog();
        di_finished.setIconImage(new ImageIcon(Othello.IMAGE_PATH + "exit_icon.png").getImage());
        di_finished.setBounds(
                super.getParent().getLocationOnScreen().x + (super.getParent().getWidth() / 2) - (width / 2),
                super.getParent().getLocationOnScreen().y + (super.getParent().getHeight() / 2) - (height / 2),
                width,
                height
        );
        di_finished.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        di_finished.setTitle(title);
        di_finished.setResizable(false);
        di_finished.setAlwaysOnTop(true);
        di_finished.setLayout(new BorderLayout());

        JPanel dialog = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(background, 0, 0, width - 15, height - 40, this);
            }
        };

        JLabel lb_message = new JLabel("<html>" + message + "</html>");
        lb_message.setFont(Othello.DEFAULT_FONT);
        lb_message.setForeground(Color.red);
        lb_message.setAutoscrolls(true);
        lb_message.setBackground(Color.black);
        lb_message.setVerticalAlignment(SwingConstants.TOP);
        lb_message.setHorizontalAlignment(SwingConstants.LEFT);
        lb_message.setBounds(40, 20, width - 50, height - 30);

        if (isYesNo) {
            OthelloButton bt_yes = new OthelloButton();
            bt_yes.setText("Yes, I do");
            bt_yes.setForeground(Color.black);
            bt_yes.setBounds(350, 110, 130, 45);
            bt_yes.setVerticalAlignment(SwingConstants.BOTTOM);
            bt_yes.addActionListener((e) -> {
                di_finished.dispose();
                System.exit(0);
            });

            OthelloButton bt_no = new OthelloButton();
            bt_no.setText("No, stay");
            bt_no.setForeground(Color.black);
            bt_no.setBounds(210, 110, 130, 45);
            bt_no.setVerticalAlignment(SwingConstants.BOTTOM);
            bt_no.addActionListener((e) -> di_finished.setVisible(false));

            dialog.add(bt_yes);
            dialog.add(bt_no);
        }
        else {
            OthelloButton bt_ok = new OthelloButton();
            bt_ok.setText("Ok");
            bt_ok.setForeground(Color.black);
            bt_ok.setBounds(350, 110, 130, 45);
            bt_ok.setVerticalAlignment(SwingConstants.BOTTOM);
            bt_ok.addActionListener((e) -> di_finished.setVisible(false));

            dialog.add(bt_ok);
        }

        dialog.add(lb_message);

        di_finished.add(dialog);
        di_finished.setVisible(true);
    }
}
