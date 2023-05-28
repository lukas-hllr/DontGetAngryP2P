package de.dhbw.dontgetangry;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;

public class GameWindow implements IView {

    private JFrame frame;
    public static int field_size = 64;
    public static int space_size = 10;
    public static int window_size = 11 * field_size + 12 * space_size;

    public FieldPanel[] fields;
    public FieldPanel[][] final_fields;
    public FieldPanel[][] out_fields;
    public int[][] player_positions;

    private final DicePanel dice = new DicePanel(true);

    public Color color_default = new Color(0, 0, 0);

    public Color color_player_0 = new Color(33, 155, 211);
    public Color color_player_1 = new Color(226, 9, 74);
    public Color color_player_2 = new Color(254, 204, 0);
    public Color color_player_3 = new Color(136, 186, 20);

    /**
     * Create the application.
     */
    public GameWindow() {
        initialize();
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
            fields[i] = new FieldPanel();
            fields[i].setLocation(start_top_left_2_x, start_top_left_right_y + offset);

            /*
             *        ╗
             *        ║
             *         ══╗
             */

            //center-right-top
            fields[i + 5] = new FieldPanel();
            fields[i + 5].setLocation(start_top_left_2_x + offset_one_field + offset, start_top_left_x);

            /*
             *        ╗
             *        ║
             *         ══╗
             *
             *         ══╝
             */

            //center-right-bottom
            fields[i + 10] = new FieldPanel();
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
            fields[i + 15] = new FieldPanel();
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
            fields[i + 20] = new FieldPanel();
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
            fields[i + 25] = new FieldPanel();
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
            fields[i + 30] = new FieldPanel();
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
            fields[i + 35] = new FieldPanel();
            fields[i + 35].setLocation(start_top_left_x, start_top_left_x - offset_one_field - offset);

            //final-fields
            final_fields[0][i] = new FieldPanel();
            final_fields[0][i].setBackground(color_player_0);
            final_fields[0][i].setLocation(center, center - (4 - i) * offset_one_field);

            final_fields[1][i] = new FieldPanel();
            final_fields[1][i].setBackground(color_player_1);
            final_fields[1][i].setLocation(center + (4 - i) * offset_one_field, center);

            final_fields[2][i] = new FieldPanel();
            final_fields[2][i].setBackground(color_player_2);
            final_fields[2][i].setLocation(center, center + (4 - i) * offset_one_field);

            final_fields[3][i] = new FieldPanel();
            final_fields[3][i].setBackground(color_player_3);
            final_fields[3][i].setLocation(center - (4 - i) * offset_one_field, center);
        }

        //inner-corners
        fields[4] = new FieldPanel();
        fields[4].setLocation(start_top_left_2_x, start_top_left_x);
        //10

        fields[14] = new FieldPanel();
        fields[14].setLocation(start_top_left_2_x, start_top_left_2_x);
        //11

        fields[24] = new FieldPanel();
        fields[24].setLocation(start_top_left_x, start_top_left_2_x);
        //01

        fields[34] = new FieldPanel();
        fields[34].setLocation(start_top_left_x, start_top_left_x);
        //00

        //outer-fields
        fields[9] = new FieldPanel();
        fields[9].setLocation(window_size - offset_one_field, start_top_left_x + offset_one_field);

        fields[19] = new FieldPanel();
        fields[19].setLocation(start_top_left_x + offset_one_field, window_size - offset_one_field);

        fields[29] = new FieldPanel();
        fields[29].setLocation(start_top_left_right_y, start_top_left_x + offset_one_field);

        fields[39] = new FieldPanel();
        fields[39].setLocation(start_top_left_x + offset_one_field, start_top_left_right_y);

        //out-fields
        for (int i = 0; i < 4; i++) {
            out_fields[0][i] = new FieldPanel();
            int y = start_top_left_right_y + offset_one_field * (i < 2 ? 0 : 1);
            out_fields[0][i].setLocation(window_size - (2 - (i % 2)) * offset_one_field, y);
            out_fields[0][i].setBackground(Player.BLUE.color);

            out_fields[1][i] = new FieldPanel();
            out_fields[1][i].setLocation(window_size - (2 - (i % 2)) * offset_one_field, window_size - (2 - (i < 2 ? 0 : 1)) * offset_one_field);
            out_fields[1][i].setBackground(Player.RED.color);

            out_fields[2][i] = new FieldPanel();
            out_fields[2][i].setLocation(start_top_left_right_y + offset_one_field * (i % 2), window_size - (2 - (i < 2 ? 0 : 1)) * offset_one_field);
            out_fields[2][i].setBackground(Player.YELLOW.color);

            out_fields[3][i] = new FieldPanel();
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
        dice.setLocation(center, center);
        playfield.add(dice);


        frame.getContentPane().add(playfield);

        JPanel p = new JPanel();
        p.setBounds(window_size, 0, 400 + 5, window_size + 40);
        p.add(new JLabel("Hallo"));
        frame.getContentPane().add(p);

        setPosition(Player.BLUE, 0, 6);

        frame.setVisible(true);
    }

    @Override
    public void setPosition(Player player, int character, int position) {
        //reset

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
        // TODO Auto-generated method stub

    }

    @Override
    public void setDice(int r) {
        // TODO Auto-generated method stub

    }
}
