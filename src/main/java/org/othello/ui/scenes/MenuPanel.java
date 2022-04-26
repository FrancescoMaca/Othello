package org.othello.ui.scenes;

import org.othello.logic.music.MusicLoader;
import org.othello.ui.Othello;
import org.othello.ui.components.OthelloButton;
import org.othello.ui.components.OthelloScene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuPanel extends OthelloScene {

    /**
     * Image resources
     */
    private final Image background = new ImageIcon(Othello.IMAGE_PATH + "components\\wallpaper.gif").getImage();
    private final MusicLoader musicLoader;

    /**
     * Default constructor
     */
    public MenuPanel() {
        musicLoader = MusicLoader.getInstance();
        musicLoader.loadMusic(Othello.MUSIC_PATH + "menu.wav");
        musicLoader.setVolume(1.f);
        musicLoader.start();

        setup();

        initializeComponents();
    }

    /**
     * Initializes window's components
     */
    private void initializeComponents() {

        OthelloButton bt_play = new OthelloButton();
        bt_play.setText("Play");
        bt_play.setBounds(90, 195, 185, 65);
        bt_play.setVerticalAlignment(SwingConstants.BOTTOM);
        bt_play.setForeground(Color.black);
        bt_play.addActionListener(this::bt_playCallback);

        OthelloButton bt_settings = new OthelloButton();
        bt_settings.setText("Settings");
        bt_settings.setBounds(90, 275, 185, 65);
        bt_settings.setVerticalAlignment(SwingConstants.BOTTOM);
        bt_settings.setForeground(Color.black);
        bt_settings.addActionListener(this::bt_settingCallback);

        OthelloButton bt_exit = new OthelloButton();
        bt_exit.setText("Exit");
        bt_exit.setBounds(90, 355, 185, 65);
        bt_exit.setVerticalAlignment(SwingConstants.BOTTOM);
        bt_exit.setForeground(Color.black);
        bt_exit.addActionListener(this::bt_exitCallback);

        JLabel lb_title = new JLabel("Othello");
        lb_title.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 75));
        lb_title.setBounds(100, 35, 330 ,115);
        lb_title.setForeground(Color.white);

        this.add(lb_title);
        this.add(bt_play);
        this.add(bt_settings);
        this.add(bt_exit);
    }

    private void bt_playCallback(ActionEvent e) {
        if (this.getParent().getLayout() instanceof CardLayout) {
            ((CardLayout)this.getParent().getLayout()).show(this.getParent(), "game");
        }
    }

    private void bt_settingCallback(ActionEvent e) {
        if (this.getParent().getLayout() instanceof CardLayout) {
            ((CardLayout)this.getParent().getLayout()).show(this.getParent(), "setting");

            musicLoader.loadMusic(Othello.MUSIC_PATH + "settings.wav");
            musicLoader.start();
        }
    }

    private void bt_exitCallback(ActionEvent e) {
        Image img = new ImageIcon(Othello.IMAGE_PATH + "background_dialog.png").getImage();

        createDialog("Exiting",
                "Are you sure you want to exit?",
                img,
                true
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
