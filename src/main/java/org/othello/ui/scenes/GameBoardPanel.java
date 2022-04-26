package org.othello.ui.scenes;

import org.othello.logic.OthelloGame;

import org.othello.logic.board.Cell;
import org.othello.logic.board.GameState;
import org.othello.logic.board.CellState;
import org.othello.logic.board.GameBoard;

import org.othello.ui.Othello;
import org.othello.ui.components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoardPanel extends OthelloScene {

    /**
     * Images resources
     */
    private final Image background = new ImageIcon(Othello.IMAGE_PATH + "components\\background.jpg").getImage();
    private final Image panelBackground = new ImageIcon(Othello.IMAGE_PATH + "components\\menusPortrait.png").getImage();

    /**
     * Main game objects:
     * board: an instance of the Othello game
     * pn_board: the main board container
     * bt_cells: all the graphics instances of the cells
     */
    private final OthelloGame board;
    private final JPanel pn_board = new JPanel();
    private final OthelloCell[][] bt_cells = new OthelloCell[GameBoard.SIZE][GameBoard.SIZE];

    /**
     * Global variables that are updated during the game execution
     */
    private JLabel lb_blackPointsValue;
    private JLabel lb_whitePointsValue;
    private JLabel lb_currentTurnValue;
    private JLabel lb_currentStateValue;
    private JButton bt_pvp;
    private JButton bt_pve;
    JSlider sl_difficulty;

    /**
     * Flag signaling if the player is against an AI or another player.
     */
    private boolean isPvp = true;

    /**
     * Default constructor, it needs a OthelloGame to work.
     * @param src the game from which the UI will update
     */
    public GameBoardPanel(OthelloGame src) {
        board = src;

        setup();

        initializeComponents();
    }

    /**
     * Initializes all the window's components.
     */
    private void initializeComponents() {
        int BOARD_SIZE = 550;

        pn_board.setLayout(new GridLayout(GameBoard.SIZE, GameBoard.SIZE));
        pn_board.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        pn_board.setBounds((this.getWidth() / 2) - (BOARD_SIZE / 2),
                (this.getHeight() / 2) - (BOARD_SIZE / 2),
                BOARD_SIZE,
                BOARD_SIZE
        );

        for (int i = 0; i < GameBoard.SIZE; i++) {
            for (int j = 0; j < GameBoard.SIZE; j++) {
                bt_cells[i][j] = new OthelloCell();
                bt_cells[i][j].setSize(pn_board.getWidth() / GameBoard.SIZE, pn_board.getHeight() / GameBoard.SIZE);
                bt_cells[i][j].setName(String.valueOf(i * GameBoard.SIZE + j));
                bt_cells[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 1, false));
                bt_cells[i][j].setBackground(new Color(0x007D36));
                bt_cells[i][j].addActionListener(this::bt_cellClicked);

                int res = board.tryMove(i, j);

                if (res > 0) {
                    board.getCell(i, j).setState(CellState.Available);
                    bt_cells[i][j].changeState(CellState.Available);
                }
                else {
                    bt_cells[i][j].changeState(board.getCell(i, j).getState());
                }

                pn_board.add(bt_cells[i][j]);
            }
        }

        JLabel lb_title = new JLabel("Othello");
        lb_title.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 75));
        lb_title.setBounds(925, 15, 450, 150);
        lb_title.setForeground(Color.black);

        JLabel lb_subTitle = new JLabel("the original");
        lb_subTitle.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 25));
        lb_subTitle.setBounds(1075, 45, 450, 150);
        lb_subTitle.setForeground(Color.black);

        OthelloButton bt_back = new OthelloButton();
        bt_back.changeStyle(new ImageIcon(Othello.IMAGE_PATH + "components\\backNormal.png").getImage(),
                            new ImageIcon(Othello.IMAGE_PATH + "components\\backPressed.png").getImage());
        bt_back.setBounds(20, 20, 60, 60);
        bt_back.addActionListener(this::bt_backCallback);
        bt_back.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));

        JPanel pn_menu = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(panelBackground, 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_menu.setOpaque(false);
        pn_menu.setBounds(925, 150, 325, 500);

        JLabel lb_menuTitle = new JLabel("Game Settings");
        lb_menuTitle.setBounds(45, 25, 300, 40);
        lb_menuTitle.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 35));
        lb_menuTitle.setForeground(Color.black);

        JLabel lb_mode = new JLabel("Mode");
        lb_mode.setFont(Othello.DEFAULT_FONT);
        lb_mode.setBounds(20, 60, 100, 40);
        lb_mode.setForeground(Color.black);

        bt_pve = new JButton("Player vs Engine");
        bt_pve.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 20));
        bt_pve.setBounds(25, 90, 200, 40);
        bt_pve.setForeground(Color.black);
        bt_pve.setBackground(new Color(0x007D36));
        bt_pve.setHorizontalAlignment(SwingConstants.CENTER);
        bt_pve.setVerticalAlignment(SwingConstants.BOTTOM);
        bt_pve.setBorder(BorderFactory.createLineBorder(Color.black, 3, false));
        bt_pve.addActionListener(this::bt_modeChangedToAICallback);

        bt_pvp = new JButton("Player vs Player");
        bt_pvp.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 20));
        bt_pvp.setBounds(45, 140, 200, 40);
        bt_pvp.setForeground(Color.black);
        bt_pvp.setBackground(new Color(0x007D36));
        bt_pvp.setHorizontalAlignment(SwingConstants.CENTER);
        bt_pvp.setVerticalAlignment(SwingConstants.BOTTOM);
        bt_pvp.setOpaque(false);
        bt_pvp.setBorder(BorderFactory.createLineBorder(Color.black, 3, false));
        bt_pvp.addActionListener(this::bt_modeChangedToPlayerCallback);

        JLabel lb_difficulty = new JLabel("AI difficulty");
        lb_difficulty.setFont(Othello.DEFAULT_FONT);
        lb_difficulty.setBounds(20, 215, 200, 50);
        lb_difficulty.setForeground(Color.black);

        sl_difficulty = new JSlider(SwingConstants.HORIZONTAL);
        sl_difficulty.setMaximum(10);
        sl_difficulty.setMinimum(1);
        sl_difficulty.setValue(3);
        sl_difficulty.setBounds(15, 250, 280, 40);
        sl_difficulty.setSnapToTicks(true);
        sl_difficulty.setPaintTicks(true);
        sl_difficulty.setPaintLabels(true);
        sl_difficulty.setBackground(Color.black);
        sl_difficulty.setForeground(Color.black);

        for(int i = 1; i <= sl_difficulty.getMaximum(); i++) {
            JLabel lb_aiValue = new JLabel(Integer.toString(i));
            lb_aiValue.setForeground(new Color(Color.HSBtoRGB(
                    (sl_difficulty.getMaximum() - i * (1f / sl_difficulty.getMaximum()))/3f,
                    1f,
                    1f)
                    )
            );
            lb_aiValue.setFont(Othello.DEFAULT_FONT);
            lb_aiValue.setBounds(-10 + (29 * i - 1), 285, 50, 30);
            pn_menu.add(lb_aiValue);
        }

        JButton bt_endgame = new JButton("End game");
        bt_endgame.setBounds(10, 350, 175, 40);
        bt_endgame.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 20));
        bt_endgame.setBounds(175, 440, 130, 40);
        bt_endgame.setForeground(Color.black);
        bt_endgame.setBackground(new Color(0xA10000));
        bt_endgame.setHorizontalAlignment(SwingConstants.CENTER);
        bt_endgame.setVerticalAlignment(SwingConstants.BOTTOM);
        bt_endgame.setOpaque(false);
        bt_endgame.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        bt_endgame.addActionListener(this::bt_reset);

        JButton bt_startGame = new JButton("Start game");
        bt_startGame.setBounds(15, 440, 145, 40);
        bt_startGame.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 20));
        bt_startGame.setForeground(Color.black);
        bt_startGame.setBackground(new Color(0x007D36));
        bt_startGame.setHorizontalAlignment(SwingConstants.CENTER);
        bt_startGame.setVerticalAlignment(SwingConstants.BOTTOM);
        bt_startGame.setOpaque(false);
        bt_startGame.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        bt_startGame.addActionListener(this::bt_startGame);

        pn_menu.add(bt_startGame);
        pn_menu.add(bt_endgame);
        pn_menu.add(lb_menuTitle);
        pn_menu.add(lb_mode);
        pn_menu.add(bt_pve);
        pn_menu.add(bt_pvp);
        pn_menu.add(lb_difficulty);
        pn_menu.add(sl_difficulty);

        JPanel pn_scores = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(panelBackground, 0, 0, getWidth(), getHeight(), this);
            }
        };
        pn_scores.setOpaque(false);
        pn_scores.setBounds(30, 150, 325, 500);

        JLabel lb_whitePoints = new JLabel("White: ");
        lb_whitePoints.setFont(Othello.DEFAULT_FONT);
        lb_whitePoints.setBounds(25, 130, 200, 50);
        lb_whitePoints.setForeground(Color.black);

        JLabel lb_blackPoints = new JLabel("Black: ");
        lb_blackPoints.setFont(Othello.DEFAULT_FONT);
        lb_blackPoints.setBounds(25, 100, 200, 50);
        lb_blackPoints.setForeground(Color.black);

        lb_whitePointsValue = new JLabel(String.valueOf(board.getWhiteScore()));
        lb_whitePointsValue.setFont(Othello.DEFAULT_FONT);
        lb_whitePointsValue.setBounds(125, 130, 200, 50);
        lb_whitePointsValue.setForeground(Color.black);

        lb_blackPointsValue = new JLabel(String.valueOf(board.getBlackScore()));
        lb_blackPointsValue.setFont(Othello.DEFAULT_FONT);
        lb_blackPointsValue.setBounds(125, 100, 200, 50);
        lb_blackPointsValue.setForeground(Color.black);

        JLabel lb_points = new JLabel("Points");
        lb_points.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 30));
        lb_points.setBounds(20, 65, 200, 50);
        lb_points.setForeground(Color.black);

        JLabel lb_currentTurn = new JLabel("Next to play: ");
        lb_currentTurn.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 30));
        lb_currentTurn.setBounds(20, 165, 250, 50);
        lb_currentTurn.setForeground(Color.black);

        JLabel lb_currentState = new JLabel("Game state: ");
        lb_currentState.setFont(new Font(Othello.DEFAULT_FONT_NAME, Font.PLAIN, 30));
        lb_currentState.setBounds(20, 265, 250, 50);
        lb_currentState.setForeground(Color.black);

        lb_currentTurnValue = new JLabel();
        lb_currentTurnValue.setBounds(230, 170, 30, 30);
        lb_currentTurnValue.setIcon(
                (board.getTurn() == CellState.Black) ?
                    new ImageIcon(OthelloCell.getBlackIcon().getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)) :
                    new ImageIcon(OthelloCell.getWhiteIcon().getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))
        );

        lb_currentStateValue = new JLabel("Not started");
        lb_currentStateValue.setFont(Othello.DEFAULT_FONT);
        lb_currentStateValue.setVerticalTextPosition(SwingConstants.CENTER);
        lb_currentStateValue.setBounds(55, 300, 240, 50);
        lb_currentStateValue.setForeground(new Color(0xA10000));
        lb_currentStateValue.setBorder(BorderFactory.createLineBorder(Color.red, 3));

        pn_scores.add(lb_currentStateValue);
        pn_scores.add(lb_currentState);
        pn_scores.add(lb_currentTurnValue);
        pn_scores.add(lb_currentTurn);
        pn_scores.add(lb_whitePointsValue);
        pn_scores.add(lb_blackPointsValue);
        pn_scores.add(lb_points);
        pn_scores.add(lb_whitePoints);
        pn_scores.add(lb_blackPoints);

        this.add(lb_subTitle);
        this.add(lb_title);
        this.add(pn_scores);
        this.add(pn_menu);
        this.add(bt_back);
        this.add(pn_board);
    }

    private void bt_cellClicked(ActionEvent e) {

        if (!(e.getSource() instanceof OthelloCell)) {
            return;
        }

        if (!board.hasStarted()) {
            createDialog("Start a game!",
                    "You need to start a game before playing!",
                    new ImageIcon(Othello.IMAGE_PATH + "components\\background_dialog.png")
                            .getImage()
                            .getScaledInstance(500, 200, Image.SCALE_SMOOTH),
                    false
            );
        }

        OthelloCell source = ((OthelloCell) e.getSource());

        int buttonIndex = Integer.parseInt(source.getName());

        int x = buttonIndex / GameBoard.SIZE;
        int y = buttonIndex % GameBoard.SIZE;

        boolean validMove = true;

        if (!board.getCell(x, y).isFree()) {
            validMove = false;
        }

        if (board.getTurn() == CellState.Black || isPvp) {
            board.setCell(x, y);
            updateCells();
        }

        boolean gameState = isGameEnded();

        if (!gameState && !isPvp && validMove) {
            Thread ai = new Thread(() -> {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

                Cell aiMove = board.getBestMove(sl_difficulty.getValue());

                board.setCell(aiMove.getX(), aiMove.getY());
                updateCells();
                isGameEnded();
            });

            ai.start();
        }
    }

    private void bt_backCallback(ActionEvent e) {
        if (this.getParent().getLayout() instanceof CardLayout) {
            ((CardLayout)this.getParent().getLayout()).show(this.getParent(), "menu");
        }
    }

    private void bt_modeChangedToAICallback(ActionEvent e) {
        if (isPvp) {
            bt_pve.setBounds(bt_pve.getX() + 20, bt_pve.getY(), bt_pve.getWidth(), bt_pve.getHeight());
            bt_pve.setBorder(BorderFactory.createLineBorder(Color.black, 4));
            bt_pvp.setBounds(bt_pvp.getX() - 20, bt_pvp.getY(), bt_pvp.getWidth(), bt_pvp.getHeight());
            bt_pvp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            isPvp = false;
        }

    }

    private void bt_modeChangedToPlayerCallback(ActionEvent e) {
        if (!isPvp) {
            bt_pve.setBounds(bt_pve.getX() - 20, bt_pve.getY(), bt_pve.getWidth(), bt_pve.getHeight());
            bt_pve.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            bt_pvp.setBounds(bt_pvp.getX() + 20, bt_pvp.getY(), bt_pvp.getWidth(), bt_pvp.getHeight());
            bt_pvp.setBorder(BorderFactory.createLineBorder(Color.black, 4));
            isPvp = true;
        }
    }

    private void bt_reset(ActionEvent e) {
        board.reset();
        updateCells();

        lb_currentStateValue.setText("Not started");
        lb_currentStateValue.setForeground(new Color(0xA10000));

    }

    private void bt_startGame(ActionEvent e) {
        board.start();

        lb_currentStateValue.setText("Started...");
        lb_currentStateValue.setForeground(Color.black);
    }

    private void updateCells() {
        // updating all button states
        for(int i = 0; i < GameBoard.SIZE; i++) {
            for(int j = 0; j < GameBoard.SIZE; j++) {

                // If the cell isn't occupied
                if (!board.getCell(i, j).isFree()) {
                    bt_cells[i][j].changeState(board.getCell(i, j).getState());
                    continue;
                }

                if (board.tryMove(i, j) > 0) {
                    bt_cells[i][j].changeState(CellState.Available);
                    board.getCell(i, j).setState(CellState.Available);
                }
                else {
                    bt_cells[i][j].changeState(CellState.Empty);
                    board.getCell(i, j).setState(CellState.Empty);
                }
            }
        }

        lb_whitePointsValue.setText(String.valueOf(board.getWhiteScore()));
        lb_blackPointsValue.setText(String.valueOf(board.getBlackScore()));
        lb_currentTurnValue.setIcon(
                (board.getTurn() == CellState.Black) ?
                        new ImageIcon(OthelloCell.getBlackIcon().getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)) :
                        new ImageIcon(OthelloCell.getWhiteIcon().getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH))
        );
    }

    private boolean isGameEnded() {
        GameState state = board.getState();

        if (state != GameState.Unfinished) {

            if (state == GameState.BlackWin) {
                lb_currentStateValue.setText("Black wins!");
            }
            else if (state == GameState.WhiteWin) {
                lb_currentStateValue.setText("White wins!");
            }
            else {
                lb_currentStateValue.setText("It's a draw!");
            }

            createDialog("Game Ended",
                    "The game has ended! Please restart the game to play again!",
                    new ImageIcon(Othello.IMAGE_PATH + "background_dialog.png")
                            .getImage()
                            .getScaledInstance(500, 200, Image.SCALE_SMOOTH),
                    false
            );

            return true;
        }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
