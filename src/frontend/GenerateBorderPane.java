package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GenerateBorderPane {

    private VBox topMenu;
    private VBox centerMenu;
    private BorderPane borderPane;
    private GridPane gridPane;

    public GenerateBorderPane(){}

    public BorderPane getBorderPane() {
        topMenu = new VBox();
        topMenu.setPadding(new Insets(10,0,0,0));
        topMenu.setAlignment(Pos.CENTER);
        gridPane = GenerateGridPane.getGrid();
        centerMenu = new VBox();
        centerMenu.setPadding(new Insets(10, 10, 10, 10));
        borderPane = new BorderPane();
        borderPane.setLeft(gridPane);
        borderPane.setTop(topMenu);
        borderPane.setCenter(centerMenu);

        return borderPane;
    }

    public VBox getTopMenu() {
        return topMenu;
    }

    public VBox getCenterMenu() {
        return centerMenu;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
