package frontend;

import backend.BackendController;
import backend.Character;

import backend.CharacterAlphaSortComparator;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class MasterListView {

    private BackendController bc;
    private ListView<String> masterListView;

    @SuppressWarnings("Duplicates")
    public void setMasterListView(Stage window, BackendController backendController) {
        bc = backendController;
        masterListView = new ListView<>();
        bc.doubleClickForDetails(masterListView);

        /* elements within BorderPane */
        Button mmvButton, populateButton, deleteButton, detailsButton, combatButton;
        Label titleLabel;

        /* buttons */
        mmvButton = new Button("Main Menu");
        populateButton = new Button("Populate");
        deleteButton = new Button("Delete");
        detailsButton = new Button("Details");
        combatButton = new Button("Combat");
        deleteButton.setDisable(true);
        detailsButton.setDisable(true);
        combatButton.setDisable(true);

        /* labels */
        titleLabel = new Label("Choose your character(s)!");

        /* BorderPane */
        GenerateBorderPane generateBorderPane = new GenerateBorderPane();
        BorderPane borderPane = generateBorderPane.getBorderPane();

        /* set elements within BorderPane */
        GridPane.setConstraints(populateButton,0,0);
        GridPane.setConstraints(detailsButton,0,1);
        GridPane.setConstraints(combatButton,0,2);
        GridPane.setConstraints(deleteButton,0,3);
        GridPane.setConstraints(mmvButton, 0, 4);
        generateBorderPane.getTopMenu().getChildren().addAll(titleLabel);
        generateBorderPane.getGridPane().getChildren().addAll(populateButton, mmvButton, deleteButton, detailsButton, combatButton);
        generateBorderPane.getCenterMenu().getChildren().addAll(masterListView);

        /* set action for buttons */
        mmvButton.setOnAction(e -> goToMainMenuView(window));
        populateButton.setOnAction(e -> {
            ArrayList<Character> sortedList = bc.getMasterListCopy();
            Collections.sort(sortedList, new CharacterAlphaSortComparator()); // sort alphabetically
            masterListView.getItems().clear();
            for(Character c : sortedList) {
                masterListView.getItems().add(c.getName());
            }

            populateButton.setDisable(true);
            deleteButton.setDisable(false);
            detailsButton.setDisable(false);
            combatButton.setDisable(false);
        });
        deleteButton.setOnAction(e -> {
            boolean confirmation = ConfirmBox.display("Confirm Delete", "Are you sure you want to delete selected character(s)?");
            if (confirmation) {
                deleteSelected();
            }
        });
        detailsButton.setOnAction(e -> {
            ObservableList<String> selected = masterListView.getSelectionModel().getSelectedItems();
            if (selected.size() != 1) {
                AlertBox.display("Selection Error", "Please only select 1 character\nOther characters can be selected separately");
            } else {
                try {
                    CharacterDetailView.display(bc.viewDetails(selected), bc);
                } catch (IOException x) {
                    x.printStackTrace();
                }
            }
        });
        combatButton.setOnAction(e -> {
            ObservableList<String> selected = masterListView.getSelectionModel().getSelectedItems();
            bc.populateCombatList(createCombat(selected));
            if (bc.getCombatListCopy().size() < 2) {
                AlertBox.display("Selection Error", "Please select at least 2 characters for combat");
            } else {
                try {
                    goToCombatView();
                } catch(IOException x) {
                    x.printStackTrace();
                }
            }
            for (Character c : bc.getCombatList()) {
                c.setInitiative(0);
            }
        });

        /* set scene/window */
        masterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Scene masterListScene = new Scene(borderPane, 350, 475);
        masterListScene.getStylesheets().add("/images/style.css");

        window.setTitle("Character Master List");
        window.setScene(masterListScene);
        window.show();
    }

    private void goToMainMenuView(Stage window) {
        MainMenuView mmv = new MainMenuView(bc);
        mmv.setMainMenuView(window);
    }

    private void goToCombatView() throws IOException {
        CombatView.setCombatView(bc);
    }

    /**
     * @param selected
     * @return ArrayList of all characters selected for combat
     */
    private ArrayList<Character> createCombat(ObservableList<String> selected) {
        ArrayList<Character> tempA = new ArrayList<>();
        for (String listC : selected) {
            for (Character arrC : bc.getMasterListCopy()) {
                if (listC.equals(arrC.getName())) {
                    tempA.add(arrC);
                }
            }
        }
        return tempA;
    }

    /**
     * add comment
     */
    private void deleteSelected() {
        ArrayList<Character> tempList = new ArrayList<>();
        tempList.addAll(bc.getMasterListCopy());
        ObservableList<String> selected;
        selected = masterListView.getSelectionModel().getSelectedItems();
        for (String listC : selected) {
            for (Character arrC : tempList) {
                if (listC.equals(arrC.getName())) {
                    // remove character from masterList
                    bc.removeCharacter(arrC);
                }
            }
        }
        masterListView.getItems().clear();
        for (Character c : bc.getMasterListCopy()) {
            // refresh ListView
            masterListView.getItems().add(c.getName());
        }
    }
}
