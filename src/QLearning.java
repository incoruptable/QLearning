import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Incoruptable on 4/16/2017.
 */
public class QLearning {

    private final static int BOARD_WIDTH = 20;
    private final static int BOARD_HEIGHT = 10;
    private Random rand;
    private char[][] board;
    private int[][][] qTable;
    private List<Action> actions;

    public QLearning() {
        qTable = new int[BOARD_WIDTH][BOARD_HEIGHT][4];
        board = new char[BOARD_WIDTH][BOARD_HEIGHT];

        actions = new LinkedList<>();
        actions.add(Action.UP);
        actions.add(Action.RIGHT);
        actions.add(Action.DOWN);
        actions.add(Action.LEFT);

        rand = new Random();
        initializeBoard();
        printBoard(this.board);
        qLearning();
    }

    public static void main(String[] args) {
        QLearning qLearning = new QLearning();
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
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
        board[0][BOARD_HEIGHT - 1] = 'S';
        board[BOARD_WIDTH - 1][0] = 'G';

        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                for (int k = 0; k < 4; k++) {
                    qTable[i][j][k] = 0;
                }
            }
        }
    }

    private void qLearning() {
        int numIterations = 1000;
        int currentX = 0;
        int currentY = BOARD_HEIGHT - 1;
        double exploreVal = .05;

        for (int iterations = 0; iterations < numIterations; iterations++) {

            //Choose action
            Action action;
            if (rand.nextDouble() < exploreVal) {
                int actionChoice = rand.nextInt(4);
                action = actions.get(actionChoice);
            } else {

            }
        }

    }

    private void printBoard(char[][] board) {
        for (int j = 0; j < BOARD_HEIGHT; j++) {
            for (int i = 0; i < BOARD_WIDTH; i++) {
                System.out.print(board[i][j]);
            }
            System.out.print("\n");
        }
    }

    public enum Action {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

        private final int deltaX;
        private final int deltaY;

        Action(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        public int getDeltaX() {
            return deltaX;
        }

        public int getDeltaY() {
            return deltaY;
        }
    }


}
