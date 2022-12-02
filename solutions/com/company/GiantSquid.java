package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Cell {
    int number;
    boolean marked = false;

    Cell(int number) {
        // the `this.` is very important here.
        // without it, this does nothing!
        this.number = number;
    }
}

class Board {
    static final int size = 5;  // each board is 5x5
    boolean won = false;

    Cell[] grid;

    Board() {
        grid = new Cell[size * size];
    }

    Board(Board board) {
        grid = board.grid.clone();
    }

    void initCell(int x, int y, Cell number) {
        grid[x + size * y] = number;
    }

    Cell getCell(int x, int y) {
        return grid[x + size * y];
    }

    boolean won() {
        // check all rows horizontally
        for (int y = 0; y < size; y++) {
            boolean allMarked = true;
            for (int x = 0; x < size; x++) {
                Cell cell = getCell(x, y);

                if (!cell.marked) {
                    allMarked = false;
                    break;
                }
            }
            if (allMarked) {
                return true;
            }
        }

        // check all rows vertically
        for (int x = 0; x < size; x++) {
            boolean allMarked = true;
            for (int y = 0; y < size; y++) {
                Cell cell = getCell(x, y);

                if (!cell.marked) {
                    allMarked = false;
                    break;
                }
            }
            if (allMarked) {
                return true;
            }
        }

        return false;
    }

    int getScore(int winningNumber) {
        int sumOfUnmarkedCells = 0;
        for (Cell cell : grid) {
            if (!cell.marked) {
                sumOfUnmarkedCells += cell.number;
            }
        }
        return sumOfUnmarkedCells * winningNumber;
    }
}

public class GiantSquid {
    public static void main(String[] args) {
        try {
            File file = new File("giant_squid_input");

            BufferedReader reader = new BufferedReader(new FileReader(file));

            // parse the numbers to draw
            String firstLine = reader.readLine();
            String[] numbersToDraw = firstLine.split(",");

            // skip the newline
            reader.readLine();

            // parse the 5x5 boards
            List<Board> boards = new ArrayList<>();
            Board currentBoard = new Board();
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                String[] boardNumbers = line.split(" ");
                int x = 0;
                for (String boardNumber : boardNumbers) {
                    if (boardNumber.length() != 0) {
                        currentBoard.initCell(x, y % 5, new Cell(Integer.parseInt(boardNumber)));
                        x += 1;
                    }
                }
                y += 1;

                if (y % 5 == 0) {
                    // each board is 5 lines tall
                    boards.add(new Board(currentBoard));

                    // advance to the next board
                    currentBoard = new Board();

                    reader.readLine(); // skip the newline
                }
            }

            boolean firstHalfDone = false;
            int mostRecentWinningBoardScore = 0;
            for (String drawnNumberString :
                    numbersToDraw) {
                int drawnNumber = Integer.parseInt(drawnNumberString);

                for (Board board :
                        boards) {
                    if (board.won) {
                        continue;
                    }

                    for (Cell cell : board.grid) {
                        if (cell.number == drawnNumber) {
                            cell.marked = true;
                        }
                    }

                    if (board.won()) {
                        board.won = true;

                        mostRecentWinningBoardScore = board.getScore(drawnNumber);

                        if (!firstHalfDone) {
                            System.out.println("First half answer: " + mostRecentWinningBoardScore);
                            firstHalfDone = true;
                        }
                    }
                }
            }

            System.out.println("Second half answer: " + mostRecentWinningBoardScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
