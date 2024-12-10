package org.cis1200.aakarsh2048;

import java.io.*;
import java.util.Random;
import java.util.Stack;

public class Game2048 {
    private int[][] board;
    private final int size;
    private int score;
    private final Stack<int[][]> undoStack;
    private final Stack<Integer> scoreStack = new Stack<>();
    private final Random random;

    // Constructor to initialize a new game
    public Game2048(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.undoStack = new Stack<>();
        this.random = new Random();
        this.score = 0;
        initializeBoard();
    }

    // Initializes the board with two random tiles
    private void initializeBoard() {
        addRandomTile();
        addRandomTile();
    }

    // Adds a random tile (2 or 4) to an empty cell
    private void addRandomTile() {
        int emptyCount = 0;
        // Count empty cells
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    emptyCount++;
                }
            }
        }

        if (emptyCount == 0) {
            return; // No empty cells available
        }

        int targetEmpty = random.nextInt(emptyCount);
        int currentEmpty = 0;

        // Find and update the target empty cell
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    if (currentEmpty == targetEmpty) {
                        board[i][j] = random.nextInt(10) < 9 ? 2 : 4;
                        return;
                    }
                    currentEmpty++;
                }
            }
        }
    }

    // Saves the current board state for undo
    private void saveState() {
        // Save the current board state
        int[][] boardCopy = new int[size][size];
        for (int row = 0; row < size; row++) {
            System.arraycopy(board[row], 0, boardCopy[row], 0, size);
        }
        undoStack.push(boardCopy);

        // Save the current score
        scoreStack.push(score);
    }

    // Save the game to a file
    public void saveGame(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the board
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    writer.write(board[i][j] + (j < size - 1 ? " " : ""));
                }
                writer.newLine(); // End of row
            }
            // Write the score
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    // Load the game from a file
    public void loadGame(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the board. if the file doesn't have any value then create new game
            for (int i = 0; i < size; i++) {
                String written = reader.readLine();
                if (written != null) {
                    String[] line = written.split(" ");
                    for (int j = 0; j < size; j++) {
                        board[i][j] = Integer.parseInt(line[j]);
                    }
                } else {
                    System.out.println("No previous game exists!");
                    new Game2048(4);
                    break;
                }

            }
            // Read the score
            score = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            //System.err.println("Error loading game: " + e.getMessage());

        }
    }

    // Undoes the last move
    public boolean undo() {
        if (undoStack.isEmpty() || scoreStack.isEmpty()) {
            return false; // Nothing to undo
        }

        // Restore the board
        board = undoStack.pop();

        // Restore the score
        score = scoreStack.pop();

        return true;
    }

    // Getter for the board
    public int[][] getBoard() {
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, size);
        }
        return copy;
    }

    // Core game logic: move tiles in a given direction
    public boolean move(char direction) {
        saveState();

        boolean moved = false;
        switch (direction) {
            case 'W':
                moved = moveUp();
                break;
            case 'A':
                moved = moveLeft();
                break;
            case 'S':
                moved = moveDown();
                break;
            case 'D':
                moved = moveRight();
                break;
            default:
                return false; // Invalid direction
        }

        if (moved) {
            if (!testingFlag) {
                addRandomTile(); // Add a new random tile if the board changed
            }
        } else {
            undoStack.pop(); // Undo stack should only keep valid moves
            scoreStack.pop(); // Remove associated score if move was invalid
        }

        return moved;
    }

    // Helper methods for moves

    // Moves tiles up and returns whether the board was changed
    private boolean moveUp() {
        boolean moved = false;

        for (int col = 0; col < size; col++) {
            int[] column = new int[size];
            for (int row = 0; row < size; row++) {
                column[row] = board[row][col];
            }

            int[] newColumn = mergeAndSlide(column);
            for (int row = 0; row < size; row++) {
                if (board[row][col] != newColumn[row]) {
                    moved = true;
                    board[row][col] = newColumn[row];
                }
            }
        }

        return moved;
    }

    // Moves tiles down and returns whether the board was changed
    private boolean moveDown() {
        boolean moved = false;

        for (int col = 0; col < size; col++) {
            int[] column = new int[size];
            for (int row = 0; row < size; row++) {
                column[row] = board[row][col];
            }

            reverseArray(column); // Reverse the column for downward movement
            int[] newColumn = mergeAndSlide(column);
            reverseArray(newColumn); // Reverse back after processing

            for (int row = 0; row < size; row++) {
                if (board[row][col] != newColumn[row]) {
                    moved = true;
                    board[row][col] = newColumn[row];
                }
            }
        }

        return moved;
    }

    // Moves tiles left and returns whether the board was changed
    private boolean moveLeft() {
        boolean moved = false;

        for (int row = 0; row < size; row++) {
            int[] newRow = mergeAndSlide(board[row]);
            if (!arraysEqual(board[row], newRow)) {
                moved = true;
                board[row] = newRow;
            }
        }

        return moved;
    }

    // Moves tiles right and returns whether the board was changed
    private boolean moveRight() {
        boolean moved = false;

        for (int row = 0; row < size; row++) {
            int[] reversedRow = board[row].clone();
            reverseArray(reversedRow); // Reverse the row for rightward movement
            int[] newRow = mergeAndSlide(reversedRow);
            reverseArray(newRow); // Reverse back after processing

            if (!arraysEqual(board[row], newRow)) {
                moved = true;
                board[row] = newRow;
            }
        }

        return moved;
    }

    // Helper method to slide and merge a single row or column
    private int[] mergeAndSlide(int[] line) {
        int[] result = new int[size];
        int index = 0;

        // Slide all non-zero tiles to the front
        for (int value : line) {
            if (value != 0) {
                result[index++] = value;
            }
        }

        // Merge adjacent tiles
        for (int i = 0; i < index - 1; i++) {
            if (result[i] == result[i + 1] && result[i] != 0) {
                result[i] *= 2;
                score += result[i]; // Add to the score
                result[i + 1] = 0; // Clear the merged tile
            }
        }

        // Slide again to remove gaps from merging
        int[] finalResult = new int[size];
        index = 0;
        for (int value : result) {
            if (value != 0) {
                finalResult[index++] = value;
            }
        }

        return finalResult;
    }

    // Helper method to reverse an array in place
    private void reverseArray(int[] array) {
        int start = 0, end = array.length - 1;
        while (start < end) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end--;
        }
    }

    // Helper method to check if two arrays are equal
    private boolean arraysEqual(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // Checks if the game is over
    public boolean isGameOver() {
        // Check if any moves are possible
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    return false; // empty cell
                }
                if (i > 0 && board[i][j] == board[i - 1][j]) {
                    return false; // Merge possible (up)
                }
                if (j > 0 && board[i][j] == board[i][j - 1]) {
                    return false; // Merge possible (left)
                }
            }
        }
        return true; // No moves possible
    }

    // Getter for the score
    public int getScore() {
        return score;
    }

    // ********************* FOR TESTING ONLY *********************
    public void setBoard(int[][] board) {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            newBoard[i] = board[i].clone();
        }
        this.board = newBoard;
    }

    private boolean testingFlag;

    public Game2048(int size, boolean testingMode) {
        this.size = size;
        this.board = new int[size][size];
        this.undoStack = new Stack<>();
        this.random = new Random();
        this.score = 0;
        initializeBoard();
        testingFlag = true;
    }
}
