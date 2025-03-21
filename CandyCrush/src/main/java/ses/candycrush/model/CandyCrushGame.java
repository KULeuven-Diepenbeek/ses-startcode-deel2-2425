package ses.candycrush.model;

import java.util.*;

public class CandyCrushGame {
    private int score;
    private final int[][] speelbord;

    private final Random random = new Random();

    public CandyCrushGame(int rows, int columns) {
        this(rows, columns, 0);
    }

    public CandyCrushGame(int rows, int columns, int score) {
        this.score = score;
        speelbord = new int[rows][columns];
    }

    public int getCandyAt(int row, int col) {
        return speelbord[row][col];
    }

    public int[] getSize() {
        return new int[] { speelbord.length, speelbord[0].length };
    }

    private int[] previousSelected = null;

    public boolean isSelected(int row, int col) {
        if (previousSelected == null) return false;
        return row == previousSelected[0] && col == previousSelected[1];
    }

    /**
     * Update the game state by selecting the candy on the given position.
     * If no candy was selected, this becomes the selected candy.
     * If another candy was already selected, both are switched (if they're adjacent).
     */
    public void selectCandy(int row, int col) {
        if (!hasCandyAt(row, col)) {
            previousSelected = null;
            return;
        }
        if (!hasAnySelected()) {
            previousSelected = new int[] {row, col};
        } else {
            var oldCandy = speelbord[row][col];
            speelbord[row][col] = speelbord[previousSelected[0]][previousSelected[1]];
            speelbord[previousSelected[0]][previousSelected[1]] = oldCandy;
            previousSelected = new int[] {row, col};
        }
    }

    public void start() {
    }

    private boolean hasCandyAt(int row, int col) {
        return getCandyAt(row, col) != 0;
    }

    public boolean hasAnySelected() {
        return previousSelected != null;
    }

    public void setCandyAt(int row, int col, int candy) {
        speelbord[row][col] = candy;
    }

    public int score() {
        return score;
    }

    public boolean isValidPosition(int row, int column) {
        int[] size = getSize();
        return 0 <= row && row < size[0] && 0 <= column && column < size[1];
    }

    public Collection<int[]> getPositions() {
        ArrayList<int[]> result = new ArrayList<>();
        int[] size = getSize();
        for (int row = 0; row < size[0]; row++) {
            for (int col = 0; col < size[1]; col++) {
               result.add(new int[] { row, col });
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        var size = getSize();
        for (int row = 0; row < size[0]; row++) {
            for (int col = 0; col < size[1]; col++) {
                result.append(Util.candyToString(getCandyAt(row, col)));
            }
            result.append("\n");
        }
        return result.toString();
    }

}
