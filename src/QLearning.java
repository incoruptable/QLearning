/**
 * Created by Incoruptable on 4/16/2017.
 */
public class QLearning {

    private final static int BOARD_WIDTH = 20;
    private final static int BOARD_HIGHTH = 10;
    private char[][] board;

    public QLearning() {
        board = new char[BOARD_WIDTH][BOARD_HIGHTH];
        initializeBoard();
        printBoard(this.board);
    }

    public static void main(String[] args) {
        QLearning qLearning = new QLearning();
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HIGHTH; j++) {
                if (i == 7) {
                    if (j == 3 || j == 4) {
                        board[i][j] = '.';
                    } else {
                        board[i][j] = '#';
                    }
                } else {
                    board[i][j] = '.';
                }
            }
        }
    }

    private void printBoard(char[][] board) {
        for (int j = 0; j < BOARD_HIGHTH; j++) {
            for (int i = 0; i < BOARD_WIDTH; i++) {
                System.out.print(board[i][j]);
            }
            System.out.print("\n");
        }
    }


}
