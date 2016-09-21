import java.util.BitSet;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.function.BooleanSupplier;

/**
 * Made by Rasmus on 19/09/2016.
 */
public class Column {
    private int columnPosition; // The position of the column (0-14)
    private BitSet bombSet;     // Set holding the positions of the bombs for the column

    public Column(int columnPosition) {
        this.columnPosition = columnPosition;   // Set position
        bombSet = new BitSet();                 // Initialize the set
    }

    public void addToColumn(int currentRound, int stepsTillBomb) {
        // If there is an incoming bomb, add its location to the bomb set
        if (stepsTillBomb != 0) bombSet.set(currentRound + stepsTillBomb);
    }

    // Return the opposite of whether there is a bomb on the location
    public boolean isSafe(int round) {
        return !bombSet.get(round);
    }

    public int getColumnPosition() {
        return columnPosition;
    }
}
