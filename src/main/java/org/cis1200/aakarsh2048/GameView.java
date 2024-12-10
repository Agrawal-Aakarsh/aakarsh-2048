package org.cis1200.aakarsh2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JFrame {
    private JLabel[][] tiles;
    private JLabel scoreLabel;
    private JButton backButton;
    private ActionListener backActionListener;

    public GameView(int size) {
        this.tiles = new JLabel[size][size];
        this.scoreLabel = new JLabel("Score: 0");
        initUI(size);
    }

    private void initUI(int size) {
        setTitle("2048 Game");
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(size, size));
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                tiles[row][col] = new JLabel("", SwingConstants.CENTER);
                tiles[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                tiles[row][col].setOpaque(true);
                boardPanel.add(tiles[row][col]);
            }
        }

        add(scoreLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        // Back button
        backButton = new JButton("Back & Save Current Game State");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);

        // Add components to the layout
        add(scoreLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);

        // Back button listener
        backButton.addActionListener(e -> {
            if (backActionListener != null) {
                backActionListener.actionPerformed(e);
            }
            this.dispose();
        });

        setLocationRelativeTo(null);
    }

    public void setBackActionListener(ActionListener listener) {
        this.backActionListener = listener;
    }

    public void updateBoard(int[][] board, int score) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                int value = board[row][col];
                tiles[row][col].setText(value == 0 ? "" : String.valueOf(value));
                tiles[row][col].setBackground(getTileColor(value));
            }
        }
        scoreLabel.setText("Score: " + score);
    }

    // CHATGPT Generated Coloring
    private Color getTileColor(int value) {
        switch (value) {
            case 2:
                return new Color(255, 255, 204); // Light Yellow
            case 4:
                return new Color(255, 255, 153); // Pale Yellow
            case 8:
                return new Color(255, 204, 153); // Light Orange
            case 16:
                return new Color(255, 153, 102); // Orange
            case 32:
                return new Color(255, 102, 102); // Light Red
            case 64:
                return new Color(255, 51, 51); // Bright Red
            case 128:
                return new Color(204, 0, 0); // Darker Red
            case 256:
                return new Color(153, 0, 0); // Deep Red
            case 512:
                return new Color(102, 0, 0); // Burgundy
            case 1024:
                return new Color(51, 0, 0); // Almost Black Red
            case 2048:
                return new Color(0, 0, 0); // Black
            default:
                return Color.LIGHT_GRAY; // Default for 0 or unknown values
        }
    }

    public void showGameOverMessage() {
        JOptionPane.showMessageDialog(
                this,
                "Game Over!", "2048", JOptionPane.INFORMATION_MESSAGE
        );
    }
}
