package frontend;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class GenerateGridPane {

    public static GridPane getGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        return gridPane;
    }
}
