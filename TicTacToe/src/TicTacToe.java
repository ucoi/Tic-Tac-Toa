import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;

    // Constructor
    TicTacToe() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(Color.BLUE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(Color.RED);
        textfield.setForeground(Color.GRAY);
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(125, 125, 125));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        title_panel.add(textfield);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);

        firstTurn();
    }

    // Determine which player goes first
    public void firstTurn() {
        try {
            Thread.sleep(1000); // Add delay for better UX
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("X turn");
        } else {
            player1_turn = false;
            textfield.setText("O turn");
        }
    }

    // Handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (buttons[i].getText().equals("")) { // Only allow moves on empty buttons
                    if (player1_turn) {
                        buttons[i].setText("X");
                        buttons[i].setForeground(Color.RED);
                        player1_turn = false;
                        textfield.setText("O turn");
                        check();
                    } else {
                        buttons[i].setText("O");
                        buttons[i].setForeground(Color.BLUE);
                        player1_turn = true;
                        textfield.setText("X turn");
                        check();
                    }
                }
            }
        }
    }

    // Check for winning conditions or a draw
    public void check() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (checkThree(buttons[i * 3], buttons[i * 3 + 1], buttons[i * 3 + 2])) {
                declareWinner(i * 3, i * 3 + 1, i * 3 + 2);
                return;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (checkThree(buttons[i], buttons[i + 3], buttons[i + 6])) {
                declareWinner(i, i + 3, i + 6);
                return;
            }
        }

        // Check diagonals
        if (checkThree(buttons[0], buttons[4], buttons[8])) {
            declareWinner(0, 4, 8);
            return;
        }
        if (checkThree(buttons[2], buttons[4], buttons[6])) {
            declareWinner(2, 4, 6);
            return;
        }

        // Check for a draw
        if (isDraw()) {
            textfield.setText("It's a draw!");
            disableButtons();
        }
    }

    // Helper method to check if three buttons have the same non-empty text
    private boolean checkThree(JButton b1, JButton b2, JButton b3) {
        return b1.getText().equals(b2.getText()) && b2.getText().equals(b3.getText()) && !b1.getText().equals("");
    }

    // Declare the winner
    private void declareWinner(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for (JButton button : buttons) {
            button.setEnabled(false); // Disable all buttons
        }

        String winner = buttons[a].getText();
        textfield.setText(winner + " wins!");
    }

    // Check if the game is a draw
    private boolean isDraw() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    // Disable all buttons
    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    // Main method to run the game
    public static void main(String[] args) {
        new TicTacToe();
    }
}
