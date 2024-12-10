package org.cis1200.aakarsh2048;

import javax.swing.*;
import java.awt.*;

public class InstructionsView extends JFrame {
    private String backgroundImagePath;

    public InstructionsView(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
        initUI();
    }

    private void initUI() {
        setTitle("2048 Instructions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window on the screen

        // Create the main panel with a custom layout
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // Draw the background image
                if (backgroundImagePath != null) {
                    ImageIcon bgImage = new ImageIcon(backgroundImagePath);
                    g.drawImage(
                            bgImage.getImage(), 0, 0, getWidth(),
                            getHeight(), this
                    );
                }

                // Add a translucent black overlay
                g2d.setColor(new Color(0, 0, 0, 150));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Create the title label
        JLabel titleLabel = new JLabel("How to Play 2048!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create the instructions text
        JTextArea instructionsText = new JTextArea();
        instructionsText.setText("""
                     2048 Instructions:
                    \s
                     - Use W to move UP.
                     - Use A to move LEFT.
                     - Use S to move DOWN.
                     - Use D to move RIGHT.
                     - Press U to UNDO your last move.
                    \s
                     Combine tiles with the same value to reach 2048!
                \s""");
        instructionsText.setFont(new Font("Arial", Font.PLAIN, 18));
        instructionsText.setForeground(Color.WHITE);
        instructionsText.setOpaque(false);
        instructionsText.setEditable(false);
        instructionsText.setFocusable(false);
        instructionsText.setLineWrap(true);
        instructionsText.setWrapStyleWord(true);

        JButton backButton = createFilledButton("Back");
        backButton.addActionListener(e -> openHomePage());

        // Create a scroll pane for the instructions (in case the text is long)
        JScrollPane scrollPane = new JScrollPane(instructionsText);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add components to the main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Add title at the top
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Add scroll pane in the center

        // Create a panel for the back button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Ensure background is transparent
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Add button panel at the bottom

        // Add the main panel to the frame
        setContentPane(mainPanel);
    }

    private void openHomePage() {
        String instructionsBackgroundPath =
                "src/main/java/org/cis1200/aakarsh2048/pexels-pixabay-163064.jpg";

        SwingUtilities.invokeLater(() -> {
            TitlePageView titlePageView = new TitlePageView(instructionsBackgroundPath);
            titlePageView.setVisible(true); // Show the instructions page
        });

        this.dispose();
    }

    private JButton createFilledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setForeground(Color.WHITE); // Text color
        button.setBackground(new Color(70, 130, 180)); // Steel blue background
        button.setFocusPainted(false); // Removes focus ring
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(true); // Ensures the background color is applied
        button.setBorderPainted(false); // Hides button border for a flat look
        return button;
    }

    // Testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InstructionsView view = new InstructionsView(
                    "src/main/java/org/cis1200/aakarsh2048/pexels-pixabay-163064.jpg"
            );
            view.setVisible(true);
        });
    }
}
