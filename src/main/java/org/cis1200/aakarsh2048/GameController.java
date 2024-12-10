package org.cis1200.aakarsh2048;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private Game2048 model;
    private GameView view;

    public GameController(Game2048 model, GameView view) {
        this.model = model;
        this.view = view;

        // Set the back button action
        view.setBackActionListener(e -> handleBackAction());

        // Initialize game view with the current model state
        view.updateBoard(model.getBoard(), model.getScore());
    }

    private void handleBackAction() {
        model.saveGame("src/main/java/org/cis1200/aakarsh2048/savegame.txt");

        // Open the title page
        SwingUtilities.invokeLater(() -> {
            TitlePageView titlePage = new TitlePageView(
                    "src/main/java/org/cis1200/aakarsh2048/pexels-pixabay-163064.jpg"
            );
            titlePage.setVisible(true);
        });
        view.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        boolean moved;

        // Handle input for movement
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // Move Up
                moved = model.move('W');
                break;
            case KeyEvent.VK_A: // Move Left
                moved = model.move('A');
                break;
            case KeyEvent.VK_S: // Move Down
                moved = model.move('S');
                break;
            case KeyEvent.VK_D: // Move Right
                moved = model.move('D');
                break;
            case KeyEvent.VK_U: // Undo
                if (model.undo()) {
                    System.out.println("Undo successful!");
                    view.updateBoard(model.getBoard(), model.getScore()); // Update the UI for undo
                } else {
                    System.out.println("No moves to undo!");
                }
                return; // Skip further actions since undo doesn't involve movement
            default:
                // Ignore other keys
                return;
        }

        // If the board changed, notify the view and check game state
        if (moved) {
            view.updateBoard(model.getBoard(), model.getScore());

            if (model.isGameOver()) {
                view.showGameOverMessage();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // No-op
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // No-op
    }
}
