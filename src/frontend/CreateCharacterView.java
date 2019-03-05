package frontend;

import backend.BackendController;
import backend.CharacterType;
import backend.Character;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class CreateCharacterView {

    /**
     * Popup
     * Made static because it is a popup window
     * I think this might be arbitrary
     * Could give it a constructor like MasterListView
     */
    @SuppressWarnings("Duplicates")
    public static void createCharacter(BackendController bc) {
        // this shit must be declared within method because it's static
        Stage window = new Stage();
        Scene createCharacterScene;
        Button createButton, closeButton;
        Label added, notAdded, nameLabel, descriptionLabel, acLabel, typeLabel;
        TextField nameField, acField, descriptionField;

        window.initModality(Modality.APPLICATION_MODAL);

        /* GridPane */
        GridPane grid = GenerateGridPane.getGrid();

        /* elements within grid */
        // buttons
        createButton = new Button("Add Character");
        closeButton = new Button("Close");
        // labels
        added = new Label("Character added!");
        notAdded = new Label("Not added");
        nameLabel = new Label("Name:");
        descriptionLabel = new Label("Description:");
        acLabel = new Label("AC:");
        typeLabel = new Label("Type:");
        // textfields
        nameField = new TextField();
        nameField.setPromptText("Name");
        acField = new TextField();
        acField.setPromptText("Armour Class");
//        descriptionField = new TextField();
//        descriptionField.setPromptText("Provide flavourful text describing the character");
//        descriptionField.setMinHeight(250);
        // choicebox
        ChoiceBox<CharacterType> chooseType = new ChoiceBox<>();
        chooseType.getItems().add(CharacterType.PLAYER);
        chooseType.getItems().add(CharacterType.NONPLAYER);
        chooseType.getSelectionModel().select(0);

        /* UI layouts */
        BorderPane borderPane = new BorderPane();

        /* insert elements into gridpane */
        int base_col = 0;
        int base_row = 0;
        GridPane.setConstraints(nameLabel, base_col, base_row);
        GridPane.setConstraints(typeLabel, base_col, base_row+1);
        GridPane.setConstraints(acLabel, base_col, base_row+2);
//        GridPane.setConstraints(descriptionLabel, base_col, 3);
        GridPane.setConstraints(nameField, base_col+1, base_row);
        GridPane.setConstraints(chooseType, base_col+1, base_row+1);
        GridPane.setConstraints(acField, base_col+1, base_row+2);
//        GridPane.setConstraints(descriptionField, 1, 3);
        GridPane.setConstraints(added, base_col+5, base_row);
        GridPane.setConstraints(notAdded, base_col+5, base_row);
        GridPane.setConstraints(createButton, base_col+4, base_row);
        GridPane.setConstraints(closeButton, base_col+4, base_row+1);
        grid.getChildren().addAll(nameField, chooseType, createButton, closeButton, acField, nameLabel, acLabel, typeLabel);

        /* set action for buttons */
        /**
         * Add new character to model.masterList
         * Attributes of character are obtained from user
         * Character name cannot be empty
         */
        createButton.setOnAction(e -> {
            String newName = nameField.getText();
            boolean acIsInt = isInt(acField, "Invalid value entered for character's armour class");
            CharacterType newType = chooseType.getValue();
            Character newCharacter = new Character(newType);
            if (acIsInt == true) {
                int newAC = Integer.parseInt(acField.getText());
                newCharacter.setAC(newAC);
            } else {
                acField.setText("");
            }

            if (nameField.getText().equals("") || acIsInt == false) {
                // character not added
                grid.getChildren().remove(added);
                grid.getChildren().remove(notAdded);
                labelFade(notAdded);
                grid.getChildren().add(notAdded);
            } else {
                // character added
                grid.getChildren().remove(added);
                grid.getChildren().remove(notAdded);
                labelFade(added);
                grid.getChildren().add(added);
                newCharacter.setName(newName);
                bc.addCharacter(newCharacter);
                nameField.setText("");
                acField.setText("");
            }
        });
        closeButton.setOnAction(e -> window.close());

        /* set window/scene */
        createCharacterScene = new Scene(grid, 650, 400);
        createCharacterScene.getStylesheets().add("/images/style.css");
        window.setScene(createCharacterScene);
        window.setTitle("Character Creation");
        window.show();
    }

    private static void labelFade(Label aLabel) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.0), aLabel);
        fadeTransition.setFromValue(1.5);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    @SuppressWarnings("Duplicates")
    private static boolean isInt(TextField input, String message) {
        try {
            int someInt = Integer.parseInt(input.getText());
            return true;
        } catch (NumberFormatException x) {
            AlertBox.display("Invalid Input", message);
            return false;
        }
    }

}
