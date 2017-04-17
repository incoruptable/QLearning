import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Incoruptable on 4/16/2017.
 */
public class QLearning {

    private final static int BOARD_WIDTH = 20;
    private final static int BOARD_HEIGHT = 10;
    private final static int PENALTY_OUT_OF_BOUNDS = -50;
    private final static int PENALTY_BARRIER = -100;
    private final static int PENALTY_FOR_MOVING = -5;
    private final static int REWARD_GOAL = 500;
    private Random rand;
    private char[][] board;
    private double[][][] qTable;
    private List<Action> actions;

    public QLearning() {
        qTable = new double[BOARD_WIDTH][BOARD_HEIGHT][4];
        board = new char[BOARD_WIDTH][BOARD_HEIGHT];

        actions = new LinkedList<>();
        actions.add(Action.UP); //0
        actions.add(Action.RIGHT); //1
        actions.add(Action.DOWN); //2
        actions.add(Action.LEFT); //3

        rand = new Random();
        initializeBoard();
        printBoard();
        qLearning();
        printQTable();
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
                    qTable[i][j][k] = 0.0;
                }
            }
        }
    }

    private void qLearning() {
        int numIterations = 1000000;
        int currentX = 0;
        int currentY = BOARD_HEIGHT - 1;
        int futureX;
        int futureY;
        double exploreVal = .15;
        double alphaK = .1;
        double gamma = .97;

        for (int iterations = 0; iterations < numIterations; iterations++) {

            //Choose action
            int actionChoice;
            if (rand.nextDouble() < exploreVal) {

                //EXPLORE
                actionChoice = rand.nextInt(4);
            } else {

                //EXPLOIT
                actionChoice = 0;
                for (int candidate = 0; candidate < 4; candidate++) {
                    if (qTable[currentX][currentY][candidate] > qTable[currentX][currentY][actionChoice]) {
                        actionChoice = candidate;
                    }
                }
                if (actionChoice == 0) {
                    actionChoice = rand.nextInt(4);
                }
            }


            //Do action
            futureX = actions.get(actionChoice).getDeltaX() + currentX;
            futureY = actions.get(actionChoice).getDeltaY() + currentY;

            //Learn
            int rewardForAction = determineReward(futureX, futureY);
            double futureBestAction = determineFutureBestAction(futureX, futureY);
            qTable[currentX][currentY][actionChoice] = ((1 - alphaK) * qTable[currentX][currentY][actionChoice]) +
                    (alphaK * (rewardForAction + (gamma * futureBestAction)));

            if (!(futureX < 0 || futureX >= BOARD_WIDTH || futureY < 0 || futureY >= BOARD_HEIGHT)) {
                if (board[futureX][futureY] == 'G') {
                    currentX = 0;
                    currentY = BOARD_HEIGHT - 1;
                } else {
                    currentX = futureX;
                    currentY = futureY;
                }
            }
        }

    }

    private int determineReward(int futureX, int futureY) {
        if (futureX < 0 || futureX >= BOARD_WIDTH || futureY < 0 || futureY >= BOARD_HEIGHT) {
            return PENALTY_OUT_OF_BOUNDS;
        } else if (board[futureX][futureY] == '#') {
            return PENALTY_BARRIER;
        } else if (board[futureX][futureY] == 'G') {
            return REWARD_GOAL;
        } else {
            return PENALTY_FOR_MOVING;
        }
    }

    private double determineFutureBestAction(int futureX, int futureY) {
        int actionChoice = 0;
        if (futureX < 0 || futureX >= BOARD_WIDTH || futureY < 0 || futureY >= BOARD_HEIGHT) {
            return 0;
        }
        for (int i = 0; i < 4; i++) {
            if (qTable[futureX][futureY][i] > qTable[futureX][futureY][actionChoice]) {
                actionChoice = i;
            }
        }
        return qTable[futureX][futureY][actionChoice];
    }

    private void printBoard() {
        System.out.print("\nPRINTING BOARD:\n");
        for (int j = 0; j < BOARD_HEIGHT; j++) {
            for (int i = 0; i < BOARD_WIDTH; i++) {
                System.out.print(board[i][j]);
            }
            System.out.print("\n");
        }
    }

    private void printQTable() {
        System.out.print("\nPRINTING Q-TABLE:\n");
        for (int j = 0; j < BOARD_HEIGHT; j++) {
            for (int i = 0; i < BOARD_WIDTH; i++) {
                if (board[i][j] == '#') {
                    System.out.print('#');
                } else if (board[i][j] == 'S') {
                    System.out.print('S');
                } else if (board[i][j] == 'G') {
                    System.out.print('G');
                } else {
                    double bestValue = -2000000;
                    int bestChoice = -1;
                    for (int k = 0; k < 4; k++) {
                        if (qTable[i][j][k] > bestValue) {
                            bestValue = qTable[i][j][k];
                            bestChoice = k;
                        }
                    }
                    if (bestChoice == 0) {
                        System.out.print('^');
                    } else if (bestChoice == 1) {
                        System.out.print('>');
                    } else if (bestChoice == 2) {
                        System.out.print('v');
                    } else if (bestChoice == 3) {
                        System.out.print('<');
                    }
                }

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
