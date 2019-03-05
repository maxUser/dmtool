package frontend;

import backend.BackendController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainMenuView {

    private BackendController bc;
    private Button gotoCharacterCreation, masterListButton, closeButton;

    public MainMenuView(BackendController backendController) {
        bc = backendController;
    }

    public void setMainMenuView(Stage window) {
        window.setOnCloseRequest(e -> {
            try {
                e.consume();
                closeProgram(window);
            } catch (IOException i) {
                i.printStackTrace();
            }
        });
        GridPane grid = GenerateGridPane.getGrid();

        gotoCharacterCreation = new Button("Create New Character");
        masterListButton = new Button("Character List");
        closeButton = new Button("Quit");


        GridPane.setConstraints(gotoCharacterCreation, 0,0);
        GridPane.setConstraints(masterListButton, 0,1);
        GridPane.setConstraints(closeButton, 0, 5);

        grid.getChildren().addAll(gotoCharacterCreation, masterListButton, closeButton);

        /* set button actions */
        gotoCharacterCreation.setOnAction(e -> CreateCharacterView.createCharacter(bc));
        masterListButton.setOnAction(e -> goToMasterListView(window));
        closeButton.setOnAction(e -> {
            try {
                closeProgram(window);
            } catch (IOException i) {
                i.printStackTrace();
            }
        });

        Scene mainMenuScene;
        mainMenuScene = new Scene(grid, 225,225);
        mainMenuScene.getStylesheets().add("/images/style.css");

        window.setTitle("Main Menu");
        window.setScene(mainMenuScene);
        window.show();
    }

    public void goToMasterListView(Stage window) {
        MasterListView mlv = new MasterListView();
        mlv.setMasterListView(window, bc);
    }

    public void closeProgram(Stage window) throws IOException {
        bc.saveMasterList(); // save state
        window.close();
    }

}
