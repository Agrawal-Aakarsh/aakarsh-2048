package org.cis1200.aakarsh2048;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Game2048Test {

    // *************** Core functionality JUnit test cases ***************
    @Test
    void testBoardInitialization() {
        Game2048 game = new Game2048(4, true);
        int[][] board = game.getBoard();

        // Count non-zero tiles (should be exactly 2)
        int nonZeroCount = 0;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell != 0) {
                    nonZeroCount++;
                }
            }
        }
        assertEquals(
                2, nonZeroCount,
                "The board should initialize with exactly two tiles."
        );
    }

    @Test
    void testMoveUp() {
        Game2048 game = new Game2048(4);
        int[][] board = {
            { 2, 0, 0, 2 },
            { 2, 0, 0, 2 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };

        game.setBoard(board);

        game.move('W');

        assertEquals(game.getBoard()[0][0], 4);
        assertEquals(game.getBoard()[0][3], 4);

    }

    @Test
    void testMoveLeft() {
        Game2048 game = new Game2048(4, true);
        int[][] board = {
            { 2, 2, 0, 0 },
            { 0, 0, 0, 0 },
            { 2, 0, 0, 2 },
            { 0, 0, 0, 0 }
        };
        game.setBoard(board);

        game.move('A');
        assertEquals(game.getBoard()[0][0], 4, "Row 1");
        assertEquals(game.getBoard()[2][0], 4, "Row 3");

        int[][] expected = {
            { 4, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 4, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        assertArrayEquals(
                expected, game.getBoard(),
                "Whole Board"
        );

    }

    @Test
    void testMoveRight() {
        Game2048 game = new Game2048(4, true);
        int[][] board = {
            { 2, 2, 0, 0 },
            { 0, 0, 0, 0 },
            { 2, 0, 0, 2 },
            { 0, 0, 0, 0 }
        };
        game.setBoard(board);

        game.move('D');
        assertEquals(game.getBoard()[0][3], 4, "Row 1");
        assertEquals(game.getBoard()[2][3], 4, "Row 3");

    }

    @Test
    void testUndo() {
        Game2048 game = new Game2048(4, true);
        int[][] initial = {
            { 2, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        game.setBoard(initial);

        game.move('D');

        game.undo();

        assertEquals(
                game.getBoard()[0][0], 2,
                "Only element was top left (0,0)"
        );
        assertArrayEquals(
                initial, game.getBoard(),
                "Whole board"
        );

    }

    @Test
    void testScoreCalculation() {
        Game2048 game = new Game2048(4);
        int[][] board = {
            { 2, 2, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        game.setBoard(board);

        game.move('A');
        assertEquals(
                4, game.getScore(),
                "Simple Score Check"
        );
    }

    @Test
    void testSaveAndLoadGame() {
        Game2048 game = new Game2048(4);
        int[][] board = {
            { 2, 2, 0, 0 },
            { 4, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        game.setBoard(board);
        game.saveGame("src/main/java/org/cis1200/aakarsh2048/savegame.txt");

        File file = new File("src/main/java/org/cis1200/aakarsh2048/savegame.txt");
        assertTrue(file.exists(), "The save file should be created successfully.");

        int[][] nB = new int[4][4];
        int score = 0;
        try (BufferedReader reader = new BufferedReader(
                new FileReader(
                        "src/main/java/org/cis1200/aakarsh2048/savegame.txt"
                )
        )) {
            // Read the board
            for (int i = 0; i < 4; i++) {
                String[] line = reader.readLine().split(" ");
                for (int j = 0; j < 4; j++) {
                    nB[i][j] = Integer.parseInt(line[j]);
                }
            }
            // Read the score
            score = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }

        assertArrayEquals(nB, board, "Test the board write");
        assertEquals(score, game.getScore(), "Test the score write");

        // LOAD Tests
        game.loadGame("src/main/java/org/cis1200/aakarsh2048/savegame.txt");
        assertArrayEquals(game.getBoard(), board, "Test the board in-game load");

    }

    @Test
    void testMaxTileValue() {
        Game2048 game = new Game2048(4, true);
        int[][] board = {
            { 0, 0, 0, 2048 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        game.setBoard(board);

        assertFalse(
                game.isGameOver(),
                "The game should not end just because a 2048 tile is present."
        );
    }

    // *************** More complex test cases ***************

    // Edge Case #1 (should automatically tell me if the game is still playable
    // without pressing
    // any moves
    @Test
    void testIsGameOver() {
        Game2048 game = new Game2048(4);
        int[][] board = {
            { 2, 4, 2, 4 },
            { 4, 2, 4, 2 },
            { 2, 4, 2, 4 },
            { 4, 2, 4, 2 }
        };
        game.setBoard(board);

        assertTrue(game.isGameOver(), "The game should be over if no moves are possible.");
    }

    // A little more comprehensive of the previous move tests
    @Test
    void testUndoRestoresBoardAndScore() {
        Game2048 game = new Game2048(4, true);
        int[][] initialBoard = {
            { 2, 2, 0, 0 },
            { 4, 0, 0, 4 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        game.setBoard(initialBoard);
        game.move('A');

        int[][] boardAfterMove = {
            { 4, 0, 0, 0 },
            { 8, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        assertArrayEquals(
                boardAfterMove, game.getBoard(),
                "The board should reflect the move left."
        );
        assertEquals(
                12, game.getScore(),
                "Score should update correctly after merging."
        );

        game.undo();
        assertArrayEquals(
                initialBoard, game.getBoard(),
                "The board should be restored to the initial state."
        );
        assertEquals(
                0, game.getScore(),
                "Score should be restored to the initial state."
        );
    }

    // Edge Case #2. My game should not turn this into {8, 0, 0, 0} in the first row
    @Test
    void testMergingOnlyOncePerRow() {
        Game2048 game = new Game2048(4, true);
        int[][] board = {
            { 2, 2, 2, 2 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        game.setBoard(board);

        game.move('A');

        int[][] expected = {
            { 4, 4, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 },
            { 0, 0, 0, 0 }
        };
        assertArrayEquals(
                expected, game.getBoard(),
                "whole board"
        );
        assertEquals(8, game.getScore(), "Score Check");
    }

    // Edge Case #3
    @Test
    void testNoMovementDoesNotAddTile() {
        Game2048 game = new Game2048(4, true);
        int[][] board = {
            { 2, 4, 2, 4 },
            { 4, 2, 4, 2 },
            { 2, 4, 2, 4 },
            { 4, 2, 4, 2 }
        };
        game.setBoard(board);

        assertFalse(
                game.move('A'),
                "No movement should occur when boards full"
        );
        assertArrayEquals(board, game.getBoard(), "The board should remain unchanged.");
        assertEquals(0, game.getScore(), "Score should remain unchanged.");
    }

    // *************** Console Testing ***************
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game2048 game = new Game2048(4);

        while (true) {
            printBoard(game.getBoard());
            System.out.println("Score: " + game.getScore());

            if (game.isGameOver()) {
                System.out.println("Game Over! No more moves possible.");
                break;
            }

            System.out.println("Enter move (W/A/S/D for direction, U to undo, Q to quit): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Q")) {
                System.out.println("Exiting game. Thanks for playing!");
                break;
            } else if (input.equals("U")) {
                if (game.undo()) {
                    System.out.println("Undo successful!");
                } else {
                    System.out.println("No moves to undo!");
                }
            } else if ("WASD".contains(input) && input.length() == 1) {
                char direction = input.charAt(0);
                if (!game.move(direction)) {
                    System.out.println("Invalid move! Try a different direction.");
                }
            } else {
                System.out.println("Invalid input! Please enter W/A/S/D, U, or Q.");
            }
        }

        scanner.close();
    }

    // Helper method to print the game board
    private static void printBoard(int[][] board) {
        System.out.println("Current Board:");
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print((cell == 0 ? "." : cell) + "\t");
            }
            System.out.println();
        }
    }
}
