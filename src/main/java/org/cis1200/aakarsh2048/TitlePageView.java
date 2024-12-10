package org.cis1200.aakarsh2048;

import javax.swing.*;
import java.awt.*;

public class TitlePageView extends JFrame {
    private final String backgroundImagePath;

    public TitlePageView(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
        initUI();
    }

    private void initUI() {
        setTitle("2048 - Welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Draw the background image
                if (backgroundImagePath != null) {
                    ImageIcon bgImage = new ImageIcon(backgroundImagePath);
                    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }

                // Add a translucent black overlay
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title Label
        JLabel titleLabel = new JLabel("Aakarsh's 2048");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // New Game Button
        JButton newGameButton = createFilledButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());

        // Load Previous Game Button
        JButton loadGameButton = createFilledButton("Load Previous Game");
        loadGameButton.addActionListener(e -> loadPrevGame());

        // Instructions Button
        JButton instructionsButton = createFilledButton("Instructions");
        instructionsButton.addActionListener(e -> openInstructionsPage());

        // Add padding between elements
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(newGameButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(loadGameButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(instructionsButton);
        mainPanel.add(Box.createVerticalGlue());

        // Add the main panel to the frame
        setContentPane(mainPanel);
    }

    private void loadPrevGame() {
        SwingUtilities.invokeLater(() -> {
            Game2048 model = new Game2048(4);
            model.loadGame("src/main/java/org/cis1200/aakarsh2048/savegame.txt");
            GameView view = new GameView(4);
            GameController controller = new GameController(model, view);

            view.addKeyListener(controller);
            view.setFocusable(true);
            view.requestFocusInWindow();
        });
        this.dispose();
    }

    private void startNewGame() {
        SwingUtilities.invokeLater(() -> {
            Game2048 model = new Game2048(4);
            GameView view = new GameView(4);
            GameController controller = new GameController(model, view);

            view.addKeyListener(controller);
            view.setFocusable(true);
            view.requestFocusInWindow();
        });

        this.dispose();
    }

    private void openInstructionsPage() {
        String instructionsBackgroundPath =
                "src/main/java/org/cis1200/aakarsh2048/pexels-pixabay-163064.jpg";

        SwingUtilities.invokeLater(() -> {
            InstructionsView instructionsPage = new InstructionsView(instructionsBackgroundPath);
            instructionsPage.setVisible(true);
        });

        this.dispose();
    }

    // Utility method to create a filled button with consistent styling
    private JButton createFilledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false); // Removes focus ring
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    // Testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TitlePageView view = new TitlePageView(
                    "src/main/java/org/cis1200/aakarsh2048/pexels-pixabay-163064.jpg"
            );
            view.setVisible(true);
        });
    }
}
