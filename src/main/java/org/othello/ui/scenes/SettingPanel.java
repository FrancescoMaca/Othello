package org.othello.ui.scenes;

import org.othello.logic.music.MusicLoader;
import org.othello.ui.Othello;
import org.othello.ui.components.OthelloCell;
import org.othello.ui.components.OthelloButton;
import org.othello.ui.components.OthelloScene;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingPanel extends OthelloScene {

    /**
     * Main images and {@code MusicLoader} instance
     */
    private final Image background = new ImageIcon(Othello.IMAGE_PATH + "components\\background.jpg").getImage();
    private final Image panelBackground = new ImageIcon(Othello.IMAGE_PATH + "components\\menusPortrait.png").getImage();;
    private final MusicLoader musicLoader;

    /**
     * Default constructor
     */
    public SettingPanel() {
        musicLoader = MusicLoader.getInstance();

        setup();

        initializeComponents();
    }

    /**
     * Initializes all the panel's components.
     */
    private void initializeComponents() {
        OthelloButton bt_back2 = new OthelloButton();
        bt_back2.changeStyle(new ImageIcon(Othello.IMAGE_PATH + "components\\backNormal.png").getImage(),
                new ImageIcon(Othello.IMAGE_PATH + "components\\backPressed.png").getImage());
        bt_back2.setBounds(20, 20, 60, 60);
        bt_back2.addActionListener(this::bt_backCallback);
        bt_back2.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));

        JLabel lb_title = new JLabel("Othello");
        lb_title.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 75));
        lb_title.setBounds(925, 15, 450, 150);
        lb_title.setForeground(Color.black);

        JLabel lb_subtitle = new JLabel("the original");
        lb_subtitle.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 25));
        lb_subtitle.setBounds(1075, 45, 450, 150);
        lb_subtitle.setForeground(Color.black);

        JPanel pn_settings = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(panelBackground, 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_settings.setOpaque(true);
        pn_settings.setBounds(50, 300, 400, 350);

        JLabel lb_settings = new JLabel("Settings");
        lb_settings.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 45));
        lb_settings.setForeground(Color.black);
        lb_settings.setBounds(30, 30, 200, 50);

        JLabel lb_volume = new JLabel("Volume");
        lb_volume.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 35));
        lb_volume.setForeground(Color.black);
        lb_volume.setBounds(30, 230, 150, 50);

        JSlider sl_sfx = new JSlider(SwingConstants.HORIZONTAL);
        sl_sfx.setMaximum(100);
        sl_sfx.setMinimum(0);
        sl_sfx.setValue(100);
        sl_sfx.setBounds(95, 285, 275, 30);
        sl_sfx.setBackground(new Color(0x007D36));
        sl_sfx.setForeground(Color.black);
        sl_sfx.addChangeListener(this::sl_volumeChangedCallback);

        OthelloButton bt_mute = new OthelloButton();
        bt_mute.changeStyle(new ImageIcon(Othello.IMAGE_PATH + "components\\muted.png").getImage(),
                new ImageIcon(Othello.IMAGE_PATH + "components\\muted_clicked.png").getImage());
        bt_mute.setBounds(30, 275, 50, 50);
        bt_mute.addActionListener(this::bt_muteCallback);

        JPanel pn_instagram = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(Othello.IMAGE_PATH + "instagram.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_instagram.setBounds(30, 135, 40, 40);

        JLabel lb_lnkMacaluso = new JLabel("@franky_maca");
        lb_lnkMacaluso.setFont(Othello.DEFAULT_FONT);
        lb_lnkMacaluso.setForeground(Color.black);
        lb_lnkMacaluso.setBounds(80, 105, 200, 30);

        JLabel lb_lnkVeritti = new JLabel("@emmaaveritti");
        lb_lnkVeritti.setFont(Othello.DEFAULT_FONT);
        lb_lnkVeritti.setForeground(Color.black);
        lb_lnkVeritti.setBounds(80, 145, 200, 30);

        JLabel lb_lnkBlancuzzi = new JLabel("@patrick_blancuzzi");
        lb_lnkBlancuzzi.setFont(Othello.DEFAULT_FONT);
        lb_lnkBlancuzzi.setForeground(Color.black);
        lb_lnkBlancuzzi.setBounds(80, 185, 250, 30);

        pn_settings.add(lb_lnkMacaluso);
        pn_settings.add(lb_lnkVeritti);
        pn_settings.add(lb_lnkBlancuzzi);
        pn_settings.add(pn_instagram);
        pn_settings.add(lb_volume);
        pn_settings.add(lb_settings);
        pn_settings.add(bt_mute);
        pn_settings.add(sl_sfx);

        JPanel pn_currentSkins = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(panelBackground, 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_currentSkins.setBounds(50, 100, 400, 175);

        JPanel pn_currentBlackSkin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(OthelloCell.getBlackIcon().getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_currentBlackSkin.setBounds(30, 40, 100, 100);

        JPanel pn_currentAvailableSkin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(OthelloCell.getAvailableIcon().getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_currentAvailableSkin.setBounds(150, 40, 100, 100);

        JPanel pn_currentWhiteSkin = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                g.drawImage(OthelloCell.getWhiteIcon().getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_currentWhiteSkin.setBounds(270, 40, 100, 100);

        JLabel lb_currentBlack = new JLabel("[black]");
        lb_currentBlack.setFont(Othello.DEFAULT_FONT);
        lb_currentBlack.setForeground(Color.black);
        lb_currentBlack.setBounds(40, 10, 100, 40);

        JLabel lb_currentWhite = new JLabel("[white]");
        lb_currentWhite.setFont(Othello.DEFAULT_FONT);
        lb_currentWhite.setForeground(Color.black);
        lb_currentWhite.setBounds(280, 10, 100, 40);

        JLabel lb_currentAvailable = new JLabel("[available]");
        lb_currentAvailable.setFont(Othello.DEFAULT_FONT);
        lb_currentAvailable.setForeground(Color.black);
        lb_currentAvailable.setBounds(135, 10, 150, 40);

        pn_currentSkins.add(lb_currentWhite);
        pn_currentSkins.add(lb_currentAvailable);
        pn_currentSkins.add(lb_currentBlack);
        pn_currentSkins.add(pn_currentBlackSkin);
        pn_currentSkins.add(pn_currentAvailableSkin);
        pn_currentSkins.add(pn_currentWhiteSkin);

        JPanel pn_shop = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(panelBackground, 0, 0, getWidth(), getHeight(), this);
            }
        };

        pn_shop.setOpaque(true);
        pn_shop.setBounds(500, 175, 700, 325);

        JPanel pn_defaultSkin = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(new ImageIcon(Othello.IMAGE_PATH + "skins\\skinBackground0.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_defaultSkin.setBounds(40, 30, 125, 200);
        pn_defaultSkin.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        JPanel pn_Skin1 = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(new ImageIcon(Othello.IMAGE_PATH + "skins\\skinBackground1.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_Skin1.setBounds(205, 30, 125, 200);
        pn_Skin1.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        JPanel pn_Skin2 = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(new ImageIcon(Othello.IMAGE_PATH + "skins\\skinBackground2.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_Skin2.setBounds(370, 30, 125, 200);
        pn_Skin2.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        JPanel pn_Skin3 = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(new ImageIcon(Othello.IMAGE_PATH + "skins\\skinBackground3.png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_Skin3.setBounds(535, 30, 125, 200);
        pn_Skin3.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        OthelloButton bt_selectDefault = new OthelloButton();
        bt_selectDefault.setName("0");
        bt_selectDefault.setText("Select");
        bt_selectDefault.setForeground(Color.black);
        bt_selectDefault.changeStyle(new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutNormal.png").getImage(),
                new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutPressed.png").getImage());
        bt_selectDefault.setBounds(40, 250, 125, 40);
        bt_selectDefault.addActionListener(this::bt_selectSkinCallback);

        OthelloButton bt_selectSkin1 = new OthelloButton();
        bt_selectSkin1.setName("1");
        bt_selectSkin1.setText("Select");
        bt_selectSkin1.setForeground(Color.black);
        bt_selectSkin1.changeStyle(new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutNormal.png").getImage(),
                new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutPressed.png").getImage());
        bt_selectSkin1.setBounds(205, 250, 125, 40);
        bt_selectSkin1.addActionListener(this::bt_selectSkinCallback);

        OthelloButton bt_selectSkin2 = new OthelloButton();
        bt_selectSkin2.setName("2");
        bt_selectSkin2.setText("Select");
        bt_selectSkin2.setForeground(Color.black);
        bt_selectSkin2.changeStyle(new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutNormal.png").getImage(),
                new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutPressed.png").getImage());
        bt_selectSkin2.setBounds(370, 250, 125, 40);
        bt_selectSkin2.addActionListener(this::bt_selectSkinCallback);

        OthelloButton bt_selectSkin3 = new OthelloButton();
        bt_selectSkin3.setName("3");
        bt_selectSkin3.setText("Select");
        bt_selectSkin3.setForeground(Color.black);
        bt_selectSkin3.changeStyle(new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutNormal.png").getImage(),
                new ImageIcon(Othello.IMAGE_PATH + "components\\buttonLayoutPressed.png").getImage());
        bt_selectSkin3.setBounds(535, 250, 125, 40);
        bt_selectSkin3.addActionListener(this::bt_selectSkinCallback);

        pn_shop.add(bt_selectSkin3);
        pn_shop.add(bt_selectSkin2);
        pn_shop.add(bt_selectSkin1);
        pn_shop.add(bt_selectDefault);
        pn_shop.add(pn_defaultSkin);
        pn_shop.add(pn_Skin1);
        pn_shop.add(pn_Skin2);
        pn_shop.add(pn_Skin3);

        this.add(pn_shop);
        this.add(pn_currentSkins);
        this.add(pn_settings);
        this.add(lb_title);
        this.add(lb_subtitle);
        this.add(bt_back2);
    }

    private void bt_selectSkinCallback(ActionEvent e) {
        if (e.getSource() instanceof OthelloButton) {
            OthelloButton source = (OthelloButton) e.getSource();

            OthelloCell.setBlackIcon(new ImageIcon(Othello.IMAGE_PATH + "skins\\" + source.getName() + "b.png"));
            OthelloCell.setWhiteIcon(new ImageIcon(Othello.IMAGE_PATH + "skins\\" + source.getName() + "w.png"));
            OthelloCell.setAvailableIcon(new ImageIcon(Othello.IMAGE_PATH + "skins\\" + source.getName() + "a.png"));

            this.repaint();
        }
    }

    private void sl_volumeChangedCallback(ChangeEvent e) {

        if (e.getSource() instanceof JSlider) {
            JSlider source = (JSlider) e.getSource();

            musicLoader.setVolume(source.getValue() / (float)source.getMaximum());
        }
    }

    private void bt_muteCallback(ActionEvent e) {
        musicLoader.setVolume(0);
    }

    private void bt_backCallback(ActionEvent e) {
        if (this.getParent().getLayout() instanceof CardLayout) {
            ((CardLayout)this.getParent().getLayout()).show(this.getParent(), "menu");

            musicLoader.loadMusic(Othello.MUSIC_PATH + "menu.wav");
            musicLoader.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
