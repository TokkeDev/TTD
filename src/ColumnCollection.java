import java.util.ArrayList;
import java.util.List;

/**
 * Made by Rasmus on 19/09/2016.
 */

public class ColumnCollection {
    private int round;
    private List<Column> columns;

    public ColumnCollection() {
        // Initialize column list
        this.columns = new ArrayList<>();
        // Add the 15 columns (passing their position)
        for (int i = 0; i < 15; i++) columns.add(new Column(i));
    }

    public int getSafe() {
        // Go through the columns and return the position of the first safe one
        for (Column column : columns) if (column.isSafe(round)) {
            return column.getColumnPosition();
        }
        // If none are safe, return -1
        return -1;
    }

    public void addToColumns(List<Integer> stepsTillBombs) {
        // Print the bomb map
        System.out.println("Bomb count: " + stepsTillBombs + " (round " + round + ")");
        // Go through the columns and add their respective steps until bombs
        for (int i = 0; i < 15; i++) columns.get(i).addToColumn(round, stepsTillBombs.get(i));
        // Increment the round number
        round++;
    }
}
