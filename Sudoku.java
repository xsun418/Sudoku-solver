
import java.util.Scanner;
import java.util.StringTokenizer;

public class Sudoku {

    // Two dimensional array used to store partially-filled board and solution 
    private int[][] board = new int[9][9];

    public void enterBoard() {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        // In each row input values are separated by a space
        for (int i = 0; i < 9; i++) {
            System.out.print("Enter the row " + i + " : ");
            line = scanner.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, " ");
            for (int j = 0; j < 9; j++) {
                String token = tokenizer.nextToken();
                //Missing cells may be input as a '.' character or as a '0' character
                if (token.equals(".")) {
                    board[i][j] = 0;
                } else {
                    board[i][j] = Integer.valueOf(token);
                }
            }
        }
    }

    public boolean fillBoard() {
        int x = -1;
        int y = -1;
        // find empty cell
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {
                if (board[i][j] == 0) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        if (x == -1) {
            return true;
        }
        // try all numbers 1-9 
        for (int k = 1; k < 10; k++) {
            board[x][y] = k;
            // validate
            if (conflicts(x, y)) {
                // backtracking for invalid value
                board[x][y] = 0;
            } else {
                if (fillBoard()) {
                    return true;
                } else {
                    // backtracking if no solution found
                    board[x][y] = 0;
                }
            }
        }

        return false;
    }

    public void printBoard() {
        System.out.println(" -----------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) {
                    System.out.print("| ");
                }
                System.out.print(board[i][j] + " ");
            }
            System.out.print("| ");
            System.out.println("");
            if ((i + 1) % 3 == 0) {
                System.out.println(" -----------------------");
            }
        }
    }

    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
        sudoku.enterBoard();

        if (sudoku.fillBoard()) {
            // print Board with solution 
            sudoku.printBoard();
        } else {
            System.out.println("There are no solution");
        }
    }

    public boolean conflicts(int x, int y) {
        int value = board[x][y];
        // horizontal conflict       
        for (int i = 0; i < 9; i++) {
            if (i != y && board[x][i] != 0 && board[x][i] == value) {
                return true;
            }
        }
        // vertical conflict
        for (int i = 0; i < 9; i++) {
            if (i != x && board[i][y] != 0 && board[i][y] == value) {
                return true;
            }
        }

        // block conflict
        int xSubgrid = x / 3;
        int ySubgrid = y / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (xSubgrid * 3 + i != x || ySubgrid * 3 + j != y) {
                    if (board[xSubgrid * 3 + i][ySubgrid * 3 + j] != 0
                            && board[xSubgrid * 3 + i][ySubgrid * 3 + j] == value) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
