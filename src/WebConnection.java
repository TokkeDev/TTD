import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.ArrayList;

public class WebConnection {

    private DomElement columns;
    private HtmlPage page;
    private int currentLength;

    public static void main(String[] args) {
        new TTD().destroy();
    }

    public void connect() {
        // Initialize web client
        WebClient webClient = new WebClient();

        // Return "TTD" on game win
        webClient.setPromptHandler((page1, s) -> "TTD");

        // Don't stop on exceptions caused by Martin's ill formed code
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        try {
            // Get the web page
            page = webClient.getPage("http://terminalterror-martinzg.rhcloud.com/");

            // Wait for the page UI to get ready
            Thread.sleep(2000);

        } catch (Exception ignored) {
        }

        // Print the title of the web page
        System.out.println(page.getTitleText() + "\n");

        // Execute javascript to start game
        page.executeJavaScript("newGame()");

        // Get the columns div
        columns = page.getElementById("game").getLastElementChild();
    }

    public void update(ColumnCollection columnCollection) {
        // Sleep until the UI changed and the game has moved on
        while (columns.getFirstElementChild().getTextContent().length() == currentLength) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
        }

        // Initialize the list holding the steps until next bomb for each column
        ArrayList<Integer> values = new ArrayList<>();

        // For each column
        for (DomElement element : columns.getChildElements()) {
            String text = element.getTextContent();
            try {
                // add the steps until the next bomb
                values.add(Integer.parseInt(text.substring(text.length() - 1, text.length())));
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                // or add zero, if a "!" is seen or if an exception is thrown
                values.add(0);
            }
        }

        // Add the bomb list to the column collection
        columnCollection.addToColumns(values);

        // Update the current length variable
        currentLength = columns.getFirstElementChild().getTextContent().length();
    }

    public int moveFromTo(int start, int end) {
        // Declare javascript string
        String javaScript = null;

        // If start < end, we move right and make the javascript that
        if (start < end) javaScript = "var event = {key:\"ArrowRight\"};" + "keyRight(event)";

        // If start > end, we move left and make the javascript that
        if (start > end) javaScript = "var event = {key:\"ArrowLeft\"};" + "keyLeft(event)";

        // For every move we need to make, make it
        for (int i = 0; i < Math.abs(start - end); i++) page.executeJavaScript(javaScript);

        // Return the final position (if start = end, everything still works)
        return end;
    }
}
