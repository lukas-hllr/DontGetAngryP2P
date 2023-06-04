package de.dhbw.dontgetangry.ui;

import de.dhbw.dontgetangry.ui.components.DicePanel;
import de.dhbw.dontgetangry.ui.components.FieldPanel;
import de.dhbw.dontgetangry.model.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;


public class GameWindow implements UserInterface {

    private UIEventListener listener;
    private JFrame frame;
    private Player mainPlayer;
    public static int field_size = 64;
    public static int space_size = 10;
    public static int window_size = 11 * field_size + 12 * space_size;

    public FieldPanel[] fields;
    public FieldPanel[][] final_fields;
    public FieldPanel[][] out_fields;
    public int[][] player_positions;

    private FieldPanel highlighted;

    private int highlightedPlayer;
    private DicePanel rollingDice;

    private JPanel turnPanel;
    private JButton stepForwardButton;
    private JButton stepBackButton;
    private JButton endTurnButton;

    public Color color_default = new Color(0, 0, 0);

    public Color color_player_0 = new Color(33, 155, 211);
    public Color color_player_1 = new Color(226, 9, 74);
    public Color color_player_2 = new Color(254, 204, 0);
    public Color color_player_3 = new Color(136, 186, 20);

    /**
     * Create the application.
     */
    public GameWindow(UIEventListener listener) {
        this.listener = listener;
        initialize();

        //startWindow = new StartWindow();
        //startWindow.show(true);

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, window_size + 405, window_size + 40);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel playfield = new JPanel();
        playfield.setLayout(null);
        playfield.setBorder(new LineBorder(new Color(0, 0, 0)));
        playfield.setBounds(0, 0, window_size + 5, window_size + 40);

        fields = new FieldPanel[40];
        final_fields = new FieldPanel[4][4];
        out_fields = new FieldPanel[4][4];
        player_positions = new int[4][4];

        int center = 6 * space_size + 5 * field_size;
        int start_top_left_x = 5 * space_size + 4 * field_size;
        int start_top_left_2_x = 7 * space_size + 6 * field_size;
        int offset_one_field = space_size + field_size;

        int start_top_left_right_y = space_size;

        for (int i = 0; i < 4; i++) {

            int offset = i * field_size + i * space_size;

            /*
             *        ╗
             *        ║
             */

            //top-right
            fields[i] = new FieldPanel(this);
            fields[i].setLocation(start_top_left_2_x, start_top_left_right_y + offset);

            /*
             *        ╗
             *        ║
             *         ══╗
             */

            //center-right-top
            fields[i + 5] = new FieldPanel(this);
            fields[i + 5].setLocation(start_top_left_2_x + offset_one_field + offset, start_top_left_x);

            /*
             *        ╗
             *        ║
             *         ══╗
             *
             *         ══╝
             */

            //center-right-bottom
            fields[i + 10] = new FieldPanel(this);
            fields[i + 10].setLocation(window_size - field_size - start_top_left_right_y - offset, start_top_left_2_x);

            /*
             *        ╗
             *        ║
             *         ══╗
             *
             *         ══╝
             *        ║
             *        ╝
             */

            //bottom-right
            fields[i + 15] = new FieldPanel(this);
            fields[i + 15].setLocation(start_top_left_2_x, start_top_left_2_x + offset_one_field + offset);

            /*
             *        ╗
             *        ║
             *         ══╗
             *
             *         ══╝
             *      ║ ║
             *      ╚ ╝
             */

            //bottom-left
            fields[i + 20] = new FieldPanel(this);
            fields[i + 20].setLocation(start_top_left_x, window_size - field_size - start_top_left_right_y - offset);

            /*
             *        ╗
             *        ║
             *         ══╗
             *
             *   ╚══   ══╝
             *      ║ ║
             *      ╚ ╝
             */

            //center-left-bottom
            fields[i + 25] = new FieldPanel(this);
            fields[i + 25].setLocation(start_top_left_x - offset_one_field - offset, start_top_left_2_x);

            /*
             *        ╗
             *        ║
             *   ╔══   ══╗
             *
             *   ╚══   ══╝
             *      ║ ║
             *      ╚ ╝
             */

            //center-left-top
            fields[i + 30] = new FieldPanel(this);
            fields[i + 30].setLocation(start_top_left_right_y + offset, start_top_left_x);

            /*
             *      ╔ ╗
             *      ║ ║
             *   ╔══   ══╗
             *
             *   ╚══   ══╝
             *      ║ ║
             *      ╚ ╝
             */

            //top-left
            fields[i + 35] = new FieldPanel(this);
            fields[i + 35].setLocation(start_top_left_x, start_top_left_x - offset_one_field - offset);

            //final-fields
            final_fields[0][i] = new FieldPanel(this);
            final_fields[0][i].setBackground(color_player_0);
            final_fields[0][i].setLocation(center, center - (4 - i) * offset_one_field);

            final_fields[1][i] = new FieldPanel(this);
            final_fields[1][i].setBackground(color_player_1);
            final_fields[1][i].setLocation(center + (4 - i) * offset_one_field, center);

            final_fields[2][i] = new FieldPanel(this);
            final_fields[2][i].setBackground(color_player_2);
            final_fields[2][i].setLocation(center, center + (4 - i) * offset_one_field);

            final_fields[3][i] = new FieldPanel(this);
            final_fields[3][i].setBackground(color_player_3);
            final_fields[3][i].setLocation(center - (4 - i) * offset_one_field, center);
        }

        //inner-corners
        fields[4] = new FieldPanel(this);
        fields[4].setLocation(start_top_left_2_x, start_top_left_x);
        //10

        fields[14] = new FieldPanel(this);
        fields[14].setLocation(start_top_left_2_x, start_top_left_2_x);
        //11

        fields[24] = new FieldPanel(this);
        fields[24].setLocation(start_top_left_x, start_top_left_2_x);
        //01

        fields[34] = new FieldPanel(this);
        fields[34].setLocation(start_top_left_x, start_top_left_x);
        //00

        //outer-fields
        fields[9] = new FieldPanel(this);
        fields[9].setLocation(window_size - offset_one_field, start_top_left_x + offset_one_field);

        fields[19] = new FieldPanel(this);
        fields[19].setLocation(start_top_left_x + offset_one_field, window_size - offset_one_field);

        fields[29] = new FieldPanel(this);
        fields[29].setLocation(start_top_left_right_y, start_top_left_x + offset_one_field);

        fields[39] = new FieldPanel(this);
        fields[39].setLocation(start_top_left_x + offset_one_field, start_top_left_right_y);

        //out-fields
        for (int i = 0; i < 4; i++) {
            out_fields[0][i] = new FieldPanel(this);
            int y = start_top_left_right_y + offset_one_field * (i < 2 ? 0 : 1);
            out_fields[0][i].setLocation(window_size - (2 - (i % 2)) * offset_one_field, y);
            out_fields[0][i].setBackground(Player.BLUE.color);

            out_fields[1][i] = new FieldPanel(this);
            out_fields[1][i].setLocation(window_size - (2 - (i % 2)) * offset_one_field, window_size - (2 - (i < 2 ? 0 : 1)) * offset_one_field);
            out_fields[1][i].setBackground(Player.RED.color);

            out_fields[2][i] = new FieldPanel(this);
            out_fields[2][i].setLocation(start_top_left_right_y + offset_one_field * (i % 2), window_size - (2 - (i < 2 ? 0 : 1)) * offset_one_field);
            out_fields[2][i].setBackground(Player.YELLOW.color);

            out_fields[3][i] = new FieldPanel(this);
            out_fields[3][i].setLocation(start_top_left_right_y + offset_one_field * (i % 2), y);
            out_fields[3][i].setBackground(Player.GREEN.color);
        }

        //set-color
        fields[0].setBackground(color_player_0);
        fields[10].setBackground(color_player_1);
        fields[20].setBackground(color_player_2);
        fields[30].setBackground(color_player_3);


        for (FieldPanel field : fields) {
            playfield.add(field);
        }
        for (int i = 0; i < final_fields.length; i++) {
            for (int j = 0; j < final_fields[i].length; j++) {
                playfield.add(final_fields[i][j]);
                playfield.add(out_fields[i][j]);
            }
        }


        //dice
        rollingDice = new DicePanel(listener);
        rollingDice.setLocation(center, center);
        playfield.add(rollingDice);


        frame.getContentPane().add(playfield);

        JPanel controlPanel = new JPanel();
        controlPanel.setBounds(window_size, 0, 400 + 5, window_size);
        controlPanel.setLayout(new BorderLayout(0, 0));


        endTurnButton = new JButton("End turn");
        endTurnButton.addActionListener(e -> listener.onEndTurnByUI());
        controlPanel.add(endTurnButton, BorderLayout.SOUTH);

        JPanel movementTurnPanel = new JPanel();
        controlPanel.add(movementTurnPanel, BorderLayout.CENTER);
        movementTurnPanel.setLayout(new BorderLayout(0, 0));

        JPanel movementPanel = new JPanel();
        movementTurnPanel.add(movementPanel, BorderLayout.SOUTH);

        stepBackButton = new JButton("Step backward");
        stepBackButton.addActionListener(e -> listener.onSetPositionByUI(highlightedPlayer, false));
        movementPanel.add(stepBackButton);

        stepForwardButton = new JButton("Step forward");
        stepForwardButton.addActionListener(e -> listener.onSetPositionByUI(highlightedPlayer, true));
        movementPanel.add(stepForwardButton);

        turnPanel = new JPanel();
        movementTurnPanel.add(turnPanel, BorderLayout.CENTER);

        frame.getContentPane().add(controlPanel);
        frame.setVisible(false);
    }

    public void fieldClicked(FieldPanel field){
        int player = field.getPlayerOfType(mainPlayer);
        if(player != -1){
            field.highlight(true);
            if(highlighted != null){
                highlighted.highlight(false);
            }
            highlighted = field;
            highlightedPlayer = player;
        }
    }

    @Override
    public void setPosition(Player player, int character, int position) {

        if(position < 40 && position >= 0){
            switch (player){
                case BLUE -> position += 0;
                case RED -> position += 10;
                case YELLOW -> position += 20;
                case GREEN -> position += 30;
            }
            if(position > 39){
                position -= 40;
            }
        }

        int old_pos = player_positions[player.id][character];
        if (old_pos > 39) {
            final_fields[player.id][old_pos - 40].setPlayer(player, character, false);
        } else if (old_pos < 0) {
            out_fields[player.id][(old_pos * -1) - 1].setPlayer(player, character, false);
        } else {
            fields[old_pos].setPlayer(player, character, false);
        }

        if (position > 39) {
            final_fields[player.id][position - 40].setPlayer(player, character, true);
        } else if (position < 0) {
            out_fields[player.id][(position * -1) - 1].setPlayer(player, character, true);
        } else {
            fields[position].setPlayer(player, character, true);
        }

        player_positions[player.id][character] = position;
    }

    @Override
    public void setTurn(Player player) {
        turnPanel.setBackground(player.color);

        if(player == mainPlayer){
            rollingDice.setEnabled(true);
            stepBackButton.setEnabled(true);
            stepForwardButton.setEnabled(true);
            endTurnButton.setEnabled(true);
        } else {
            rollingDice.setEnabled(false);
            stepBackButton.setEnabled(false);
            stepForwardButton.setEnabled(false);
            endTurnButton.setEnabled(false);
        }

    }

    @Override
    public void setDice(int r) {
        rollingDice.rollDice(r);
    }

    @Override
    public void setWinner(Player player) {
        JOptionPane.showMessageDialog(frame,
                player + " won!",
                "Winner!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void startGame(List<Player> players) {
        this.mainPlayer = players.get(0);
        System.out.println(mainPlayer);
        for (Player player: players) {
            for (int i = 0; i < 4; i++) {
                setPosition(player, i, i-4);
            }
        }
        this.frame.setVisible(true);
    }

    @Override
    public void error(String message) {
        JOptionPane.showMessageDialog(frame,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
