/**
 * Made by Rasmus on 19/09/2016.
 */
public class TTD {

    private WebConnection webConnection;        // The webconnection to TT
    private ColumnCollection columnCollection;  // The column collection holding the columns
    private int position;                       // The position of the cursor

    public TTD() {
        // Initialize the column collection
        this.columnCollection = new ColumnCollection();
        // Initialize the web connection
        webConnection = new WebConnection();
        // Connect to TT
        webConnection.connect();
    }

    public void destroy() {
        // Loop 10,000 times (unreachable)
        for (int i = 0; i < 10000; i++) {
            // Update the column collection from the web connection
            webConnection.update(columnCollection);
            // Go to a safe position (safe spot retrieved fro column collection)
            this.position = webConnection.moveFromTo(this.position, columnCollection.getSafe());
        }
    }
}
