package org.othello.ui;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.othello.logic.OthelloGame;
import org.othello.ui.components.OthelloScene;
import org.othello.ui.scenes.GameBoardPanel;
import org.othello.ui.scenes.MenuPanel;
import org.othello.ui.scenes.SettingPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Main class to wrap the application around, contains useful static fields such as resources paths and default
 * font used in the application.
 *
 * It also sets up the Look and Feel from a third-party library ("com.formdev.flatlaf") for the dark theme.
 *
 * @see javax.swing.JPanel
 * @see javax.swing.JFrame
 * @see javax.swing.JButton
 * @see javax.swing.JLabel
 */
public class Othello {

    /**
     * Othello Constants
     */
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
    public final static String MUSIC_PATH = "src\\main\\java\\org\\othello\\resources\\musics\\";
    public final static String IMAGE_PATH = "src\\main\\java\\org\\othello\\resources\\images\\";
    public final static String FONT_PATH = "src\\main\\java\\org\\othello\\resources\\fonts\\";
    public final static String DEFAULT_FONT_FILE = "OthelloMTStd.otf";
    public final static String DEFAULT_FONT_NAME = "Othello MT Std";
    public final static int DEFAULT_FONT_SIZE = 25;
    public static Font DEFAULT_FONT;

    /**
     * Main Othello game instance
     */
    OthelloGame board = new OthelloGame();

    static {
        FlatDarculaLaf.setup();
    }

    /**
     * Default constructor
     */
    public Othello() {
        JFrame frame = new JFrame();

        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setLayout(new CardLayout());
        frame.setBounds(100, 100, OthelloScene.WIDTH, OthelloScene.HEIGHT);
        frame.add(new MenuPanel(), "menu");
        frame.add(new SettingPanel(), "setting");
        frame.add(new GameBoardPanel(board), "game");
        frame.setTitle("Othello");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(Othello.IMAGE_PATH + "icon.png").getImage());
        frame.setVisible(true);
    }
}
