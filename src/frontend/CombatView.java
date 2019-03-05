package frontend;

import backend.*;
import backend.Character;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CombatView {

    /**
     * These variable are altered within lambda statements
     * therefore they must be global and static
     */
    private static int roundNum = 1;
    private static int d4Dice = 0;
    private static int d6Dice = 0;
    private static int d8Dice = 0;
    private static int d10Dice = 0;
    private static int d12Dice = 0;
    private static int d20Dice = 0;
    private static int d4num = 0;
    private static int d6num = 0;
    private static int d8num = 0;
    private static int d10num = 0;
    private static int d12num = 0;
    private static int d20num = 0;
    private static int finalListViewSize = 0;
//    private static int downIndA = 0;
//    private static int downIndB = 0;

    /**
     * Popup
     * Only allowing 1 combat at a time (otherwise need multiple combatLists)
     */
    @SuppressWarnings("Duplicates")
    public static void setCombatView(BackendController bc) throws IOException {
        /* window info */
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram(window, bc);
        });

        /* random shit */
        finalListViewSize = bc.getCombatListCopy().size();
        Random rand = new Random();

        /* image info */
        // arrows
        ImageView leftArrow = bc.getImage("src/images/leftArrow.png");
        ImageView rightArrow = bc.getImage("src/images/rightArrow.png");
        ImageView upArrow = bc.getImage("src/images/upCarrot.png");
        ImageView downArrow = bc.getImage("src/images/downCarrot.png");
        // checkmark
        ImageView checkmarkImage = bc.getImage("src/images/checkmark.png");

        /* create elements within UI layouts */
        Button closeButton, detailsButton, nextButton, prevButton, sortButton, duplicateButton, d4Button, d6Button, d8Button, d10Button, d12Button, d20Button, calculateTotalButton, initButton, closeEditInitButton, deleteButton, upButton, downButton;
        Label acLabel, initValueLabel, roundLabel, charNameLabel, charAC, charType, roundNumberLabel, totalLabel, totalDisplayLabel, initLabel;
        TextField d4input, d6input, d8input, d10input, d12input, d20input, initField;

        // buttons
        closeButton = new Button("Close");
        detailsButton = new Button("Details");
        nextButton = new Button("", rightArrow);
        prevButton = new Button("", leftArrow);
        sortButton = new Button("Sort");
        duplicateButton = new Button("Duplicate");
        calculateTotalButton = new Button("Roll");
        initButton = new Button("Init:"); initButton.setPadding(Insets.EMPTY); initButton.setVisible(false);
        closeEditInitButton = new Button("", checkmarkImage);
        deleteButton = new Button("Delete");
        upButton = new Button("", upArrow);
        downButton = new Button("", downArrow);

        // textfields
        initField = new TextField(); initField.setMaxWidth(38);

        // Die rolling elements
        d4Button = new Button("d4");
        d6Button = new Button("d6");
        d8Button = new Button("d8");
        d10Button = new Button("d10");
        d12Button = new Button("d12");
        d20Button = new Button("d20");
        d4input = new TextField("0");
        d4input.setMaxWidth(35);
        d6input = new TextField("0");
        d6input.setMaxWidth(35);
        d8input = new TextField("0");
        d8input.setMaxWidth(35);
        d10input = new TextField("0");
        d10input.setMaxWidth(35);
        d12input = new TextField("0");
        d12input.setMaxWidth(35);
        d20input = new TextField("0");
        d20input.setMaxWidth(35);

        // labels
        charNameLabel = new Label();
        acLabel = new Label("AC:"); acLabel.setVisible(false);
        charAC = new Label();
        charType = new Label();
        roundLabel = new Label("Round:");
        roundNumberLabel = new Label("1");
        totalLabel = new Label("Total:");
        totalDisplayLabel = new Label("0");
        initValueLabel = new Label();
        initLabel = new Label("Init:");


        ListView<String> combatListView = new ListView<>();
        combatListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        combatListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue, oldValue, newValue) -> {
            // this is for displaying details from items highlighted on the list
            // ie. when scrolling through the list, the details displayed will reflect the item highlighted
            acLabel.setVisible(true);
            initButton.setVisible(true);
            charNameLabel.setText(newValue);
            for (Character c : bc.getCombatListCopy()) {
                if (c.getName().equals(newValue)) {
                    charAC.setText(Integer.toString(c.getAC()));
                    charType.setText(c.getType().toString());
                    initValueLabel.setText(Integer.toString(c.getInitiative()));
                }
            }
        });

        /* populate combatListView */
        combatListView.getItems().clear();
        for(Character c : bc.getCombatListCopy()) {
            combatListView.getItems().add(c.getName());
        }
        bc.doubleClickForDetails(combatListView);

        /* UI layouts */
        BorderPane borderPane = new BorderPane();           // master container
        // leftMenu
        VBox leftMenu = GenerateVBox.getVBox();             // combatList
        leftMenu.setPadding(new Insets(10, 0, 10, 10));
        // topMenu
        HBox topMenu = new HBox();                          // buttons
        topMenu.setSpacing(5);
        topMenu.setPadding(new Insets(10,10,0,10));
        // centreMenu
        GridPane centreMenu = GenerateGridPane.getGrid();   // character details
        RowConstraints row0 = new RowConstraints(15);
        GridPane.setConstraints(charNameLabel, 0, 0);
        RowConstraints row1 = new RowConstraints(10);
        GridPane.setConstraints(charType, 0, 1);
        RowConstraints row2 = new RowConstraints(10);
        GridPane.setConstraints(acLabel, 0, 3);
        GridPane.setConstraints(charAC, 1, 3);
        GridPane.setConstraints(initButton, 0, 2);
        GridPane.setConstraints(initLabel, 0, 2);
        GridPane.setConstraints(initValueLabel, 1, 2);
        GridPane.setConstraints(initField, 1, 2);
        GridPane.setConstraints(closeEditInitButton, 2, 2);
        centreMenu.getRowConstraints().addAll(row0, row1, row2);
        // rightMenu
        GridPane rightMenu = GenerateGridPane.getGrid();    // round counter/die roller
        GridPane.setConstraints(roundLabel, 0, 0);
        GridPane.setConstraints(roundNumberLabel, 1, 0);
        GridPane.setConstraints(d4Button, 0, 2);
        GridPane.setConstraints(d6Button, 0, 3);
        GridPane.setConstraints(d8Button, 0, 4);
        GridPane.setConstraints(d10Button, 0, 5);
        GridPane.setConstraints(d12Button, 0, 6);
        GridPane.setConstraints(d20Button, 0, 7);
        GridPane.setConstraints(d4input, 1, 2);
        GridPane.setConstraints(d6input, 1, 3);
        GridPane.setConstraints(d8input, 1, 4);
        GridPane.setConstraints(d10input, 1, 5);
        GridPane.setConstraints(d12input, 1, 6);
        GridPane.setConstraints(d20input, 1, 7);
        GridPane.setConstraints(calculateTotalButton, 0, 8);
        GridPane.setConstraints(totalLabel, 0, 9);
        GridPane.setConstraints(totalDisplayLabel, 1, 9);

        /* set UI layouts within BorderPane */
        borderPane.setLeft(leftMenu);
        borderPane.setTop(topMenu);
        borderPane.setCenter(centreMenu);
        borderPane.setRight(rightMenu);

        /* set elements within UI layouts */
        leftMenu.getChildren().addAll(combatListView);
        topMenu.getChildren().addAll(prevButton, nextButton, upButton, downButton, deleteButton, duplicateButton, sortButton, closeButton);
        centreMenu.getChildren().addAll(acLabel, charAC, charNameLabel, charType, initButton, initValueLabel);
        rightMenu.getChildren().addAll(roundLabel, roundNumberLabel, d4Button, d6Button, d8Button, d10Button, d12Button, d20Button, d4input, d6input, d8input, d10input, d12input, d20input, calculateTotalButton, totalLabel, totalDisplayLabel);

        /* set actions for buttons */
        upButton.setOnAction(e -> {
            try {
                int upIndA = combatListView.getSelectionModel().getSelectedIndex();
                int upIndB = upIndA - 1;

                if (upIndA == 0) {
                    // swap top char with bottom char
                    upIndB = bc.getCombatListCopy().size() - 1;
                }

                Collections.swap(bc.getCombatList(), upIndA, upIndB);
                combatListView.getItems().clear();
                for (Character c : bc.getCombatList()) {
                    combatListView.getItems().add(c.getName());
                }

                if (upIndA == 0) {
                    combatListView.getSelectionModel().select(bc.getCombatListCopy().size()-1);
                } else {
                    combatListView.getSelectionModel().select(upIndA-1);
                }
            } catch (ArrayIndexOutOfBoundsException i) {
                AlertBox.display("Selection Error", "You must select a character to move");
            }
        });
        downButton.setOnAction(e -> {
            try {
                int downIndA = combatListView.getSelectionModel().getSelectedIndex();
                int downIndB = downIndA + 1;

                if (downIndA == bc.getCombatListCopy().size()-1) {
                    // swap bottom char with top char
                    downIndB = 0;
                }

                Collections.swap(bc.getCombatList(), downIndA, downIndB);
                combatListView.getItems().clear();
                for (Character c : bc.getCombatList()) {
                    combatListView.getItems().add(c.getName());
                }

                if (downIndA == bc.getCombatListCopy().size()-1) {
                    combatListView.getSelectionModel().select(0);
                } else {
                    combatListView.getSelectionModel().select(downIndA + 1);
                }
            } catch (ArrayIndexOutOfBoundsException i) {
                AlertBox.display("Selection Error", "You must select a character to move");
            }
        });
        sortButton.setOnAction(e -> {
            ArrayList<Character> sortedList = bc.getCombatListCopy();
            Collections.sort(sortedList, new CharacterInitSortComparator()); // sort by initiative
            combatListView.getItems().clear();
            for (Character c : sortedList) {
                combatListView.getItems().add(c.getName());
            }

            // detect tie and display popup
            TieGroup aTieGroup;
            ArrayList<TieGroup> tieList = new ArrayList<>();
            int curr;
            int next;
            for (int i = 0; i < sortedList.size()-1; i++) {
                curr = sortedList.get(i).getInitiative();
                next = sortedList.get(i+1).getInitiative();
                if (curr == next) {
                    // tie detected, create new TieGroup
                    aTieGroup = new TieGroup(sortedList.get(i).getInitiative());
                    int j = i; // start index
                    while (sortedList.get(i).getInitiative() == sortedList.get(i+1).getInitiative()) {
                        if (i == sortedList.size()-2) {
                            break;
                        }
                        i++; // end index
                    }
//                    System.out.println("Start: " + j);
//                    System.out.println("End: " + i);
                    for (int k = j; k <= i; k++) {
                        aTieGroup.addCharacter(sortedList.get(k).getName());
                    }
                    tieList.add(aTieGroup);
                }
            }
            for (TieGroup tg : tieList) {
                tg.printTieGroup();
            }


        });
        deleteButton.setOnAction(e -> {
            int index = combatListView.getSelectionModel().getSelectedIndex();
            combatListView.getItems().remove(index);
            finalListViewSize--;
        });
        initButton.setOnAction(e -> {
            centreMenu.getChildren().removeAll(initButton, initValueLabel);
            centreMenu.getChildren().addAll(initField, closeEditInitButton, initLabel);
        });
        closeEditInitButton.setOnAction(e -> {
            try {
                String charName = combatListView.getSelectionModel().getSelectedItem();
                Character selectedChar = bc.getCharacterFromNameCombat(charName);
                String newInit = initField.getText();
                int newInitInt = Integer.parseInt(newInit);
                initValueLabel.setText(newInit);
                selectedChar.setInitiative(newInitInt);
            } catch (NumberFormatException x) {
                AlertBox.display("Invalid Input", "Please enter a digit (i.e. 11)");
            }
            initField.setText("0");
            centreMenu.getChildren().removeAll(initField, closeEditInitButton, initLabel);
            centreMenu.getChildren().addAll(initButton, initValueLabel);
        });
        duplicateButton.setOnAction(e -> {
            // this is a bitch, apologies to future me
            int numExistingChars = 1;
            boolean isNPC = true;
            String charName = combatListView.getSelectionModel().getSelectedItem();
            charName = charName.replaceAll("\\d*$", "");
            Character temp = new Character();

            for (Character c : bc.getCombatListCopy()) {
                // iterate through list to determine how many characters with that name are already in the combat and make copy
                String cName = c.getName().replaceAll("\\d*$", "");
                if (cName.equals(charName)) {
                    if (c.getType() == CharacterType.PLAYER) {
                        AlertBox.display("Duplication Error", "You cannot duplicate Player Characters. You can only duplicate Nonplayer Characters.");
                        isNPC = false;
                    }
                    numExistingChars++;
                    temp = c;
                }
            }
            if (isNPC) {
                Character dupeChar = new Character(temp);
                String newName = dupeChar.getName().replaceAll("\\d*$", "");
                newName = newName + numExistingChars;
                dupeChar.setName(newName);
                bc.addCharacterToCombatList(dupeChar);              // add to combatList
                combatListView.getItems().add(dupeChar.getName());  // add to combatListView
                finalListViewSize++;
            }
        });
        closeButton.setOnAction(e -> closeProgram(window, bc));
        nextButton.setOnAction(e -> {
            int selectionIndex = combatListView.getSelectionModel().getSelectedIndex();
            if (selectionIndex == -1) {
                // if nothing selected, select first entry
                combatListView.getSelectionModel().selectFirst();
            } else if (selectionIndex == finalListViewSize-1) {
                combatListView.getSelectionModel().selectFirst();
                roundNum++;
                roundNumberLabel.setText(Integer.toString(roundNum));
            } else {
                combatListView.getSelectionModel().selectNext();
            }
        });
        prevButton.setOnAction(e -> {
            int selectionIndex = combatListView.getSelectionModel().getSelectedIndex();
            if (selectionIndex == -1) {
                combatListView.getSelectionModel().selectLast();
            } else if (selectionIndex == 0) {
                combatListView.getSelectionModel().selectLast();
                if (roundNum > 1) {
                    roundNum--;
                }
                roundNumberLabel.setText(Integer.toString(roundNum));
            } else {
                combatListView.getSelectionModel().selectPrevious();
            }
        });
        detailsButton.setOnAction(e -> {
            ObservableList<String> selected = combatListView.getSelectionModel().getSelectedItems();
            if (selected.size() != 1) {
                AlertBox.display("Selection Error", "Please only select 1 character\nOther characters can be selected separately");
            } else {
                try {
                    CharacterDetailView.display(bc.viewDetails(selected), bc);
                } catch(IOException x) {
                    x.printStackTrace();
                }
            }
        });
        d4Button.setOnAction(e -> incrementDieAmount("d4", d4input));
        d6Button.setOnAction(e -> incrementDieAmount("d6", d6input));
        d8Button.setOnAction(e -> incrementDieAmount("d8", d8input));
        d10Button.setOnAction(e -> incrementDieAmount("d10", d10input));
        d12Button.setOnAction(e -> incrementDieAmount("d12", d12input));
        d20Button.setOnAction(e -> incrementDieAmount("d20", d20input));
        calculateTotalButton.setOnAction(e -> {
            int total = 0;
            boolean intChecker = isInt(d4input, d4input.getText());
            if (intChecker) {
                d4num = Integer.parseInt(d4input.getText());
            }
            intChecker = isInt(d6input, d6input.getText());
            if (intChecker) {
                d6num = Integer.parseInt(d6input.getText());
            }
            intChecker = isInt(d8input, d8input.getText());
            if (intChecker) {
                d8num = Integer.parseInt(d8input.getText());
            }
            intChecker = isInt(d10input, d10input.getText());
            if (intChecker) {
                d10num = Integer.parseInt(d10input.getText());
            }
            intChecker = isInt(d12input, d12input.getText());
            if (intChecker) {
                d12num = Integer.parseInt(d12input.getText());
            }
            intChecker = isInt(d20input, d20input.getText());
            if (intChecker) {
                d20num = Integer.parseInt(d20input.getText());
            }
            for (int i = 0; i < d4num; i++) {
                total = total + rand.nextInt(4) + 1;
            }
            for (int i = 0; i < d6num; i++) {
                total = total + rand.nextInt(6) + 1;
            }
            for (int i = 0; i < d8num; i++) {
                total = total + rand.nextInt(8) + 1;
            }
            for (int i = 0; i < d10num; i++) {
                total = total + rand.nextInt(10) + 1;
            }
            for (int i = 0; i < d12num; i++) {
                total = total + rand.nextInt(12) + 1;
            }
            for (int i = 0; i < d20num; i++) {
                total = total + rand.nextInt(20) + 1;
            }
            totalDisplayLabel.setText(Integer.toString(total));
            /* clear field and variable values after roll occurs */
            d4Dice = 0; d6Dice= 0; d8Dice = 0; d10Dice = 0; d12Dice = 0; d20Dice = 0;
            d4input.setText("0"); d6input.setText("0"); d8input.setText("0"); d10input.setText("0"); d12input.setText("0"); d20input.setText("0");
        });

        /* set window/scene */
        Scene combatScene = new Scene(borderPane, 700, 425);
        combatScene.getStylesheets().add("/images/style.css");
        window.setScene(combatScene);
        window.setTitle("Combat Window");
        window.show();
    }

    public static void closeProgram(Stage window, BackendController bc) {
        bc.getCombatList().clear();
        window.close();
    }

    private static void incrementDieAmount(String die, TextField dieDisplay) {
        if (die.equals("d4")) {
            d4Dice++;
            dieDisplay.setText(Integer.toString(d4Dice));
        } else if (die.equals("d6")) {
            d6Dice++;
            dieDisplay.setText(Integer.toString(d6Dice));
        } else if (die.equals("d8")) {
            d8Dice++;
            dieDisplay.setText(Integer.toString(d8Dice));
        } else if (die.equals("d10")) {
            d10Dice++;
            dieDisplay.setText(Integer.toString(d10Dice));
        } else if (die.equals("d12")) {
            d12Dice++;
            dieDisplay.setText(Integer.toString(d12Dice));
        } else if (die.equals("d20")) {
            d20Dice++;
            dieDisplay.setText(Integer.toString(d20Dice));
        }
    }

    @SuppressWarnings("Duplicates")
    private static boolean isInt(TextField input, String message) {
        try {
            int initiative = Integer.parseInt(input.getText());
            return true;
        } catch (NumberFormatException x) {
            AlertBox.display("Invalid Input", "Please enter a number (i.e. 11)");
            return false;
        }
    }

    private void increment() {

    }
}
