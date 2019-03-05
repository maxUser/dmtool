package frontend;

import backend.BackendController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import backend.Character;

import java.io.IOException;

public class CharacterDetailView {

    /**
     * Popup
     * Multiple CharacterDetailViews can be open at once
     */
    public static void display(Character c, BackendController bc) throws IOException {
        Stage window = new Stage();
        Label name, type, cName, cType, cAC, ACLabel;
        Button closeButton, ACButton, closeEditACButton;
        TextField ACField;

        /* image info */
        ImageView checkmarkImage = bc.getImage("src/images/checkmark.png");

        /* GridPane */
        GridPane grid = GenerateGridPane.getGrid();

        /* elements within GridPane */
        name = new Label("Name:");
        type = new Label("Type:");
        cName = new Label(c.getName());
        cType = new Label(c.getType().toString());
        cAC = new Label(Integer.toString(c.getAC()));
        ACLabel = new Label("AC:");
        closeButton = new Button("Close");
        ACButton = new Button("AC:");
        closeEditACButton = new Button("", checkmarkImage);
        ACField = new TextField(); ACField.setMaxWidth(38);

        /* set actions for buttons */
        closeButton.setOnAction(e -> window.close());
        ACButton.setOnAction(e -> {
            grid.getChildren().removeAll(cAC, ACButton);
            grid.getChildren().addAll(ACField, closeEditACButton, ACLabel);
        });
        closeEditACButton.setOnAction(e -> {
            try {
                String newAC = ACField.getText();
                int newACInt = Integer.parseInt(newAC);
                cAC.setText(newAC);
                c.setAC(newACInt);
            } catch (NumberFormatException x) {
                AlertBox.display("Invalid Input", "Please enter a number (i.e. 11)");
            }
            grid.getChildren().removeAll(ACField, closeEditACButton, ACLabel);
            grid.getChildren().addAll(ACButton, cAC);
        });

        // editing
        GridPane.setConstraints(ACField, 1, 3);
        GridPane.setConstraints(closeEditACButton, 2, 3);
        GridPane.setConstraints(ACLabel, 0, 3);
        // labels
        GridPane.setConstraints(name, 0, 1);
        GridPane.setConstraints(type, 0, 2);
        GridPane.setConstraints(ACButton, 0, 3);
        // values
        GridPane.setConstraints(cName, 1, 1);
        GridPane.setConstraints(cType, 1, 2);
        GridPane.setConstraints(cAC, 1, 3);
        // misc
        GridPane.setConstraints(closeButton, 0, 7);

        grid.getChildren().addAll(name, type, ACButton, cName, cType, cAC, closeButton);

        /* set scene/window */
        Scene scene = new Scene(grid, 350, 325);
        scene.getStylesheets().add("/images/style.css");
        window.setTitle("Character Details");
        window.setScene(scene);
        window.show();
    }

}
