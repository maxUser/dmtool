package frontend;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import backend.Model;
import backend.BackendController;

public class Test extends Application {

    private Stage window;
    private Scene mainMenu, scene2, accessScene, noAccessScene;
    private Button b1;
    private boolean result;
    private boolean verifyLogin;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        /**
         * Populate test list
         */
        Model model = new Model();
        BackendController c = new BackendController(model);
        c.makeTestMasterList();

        window = primaryStage;

        Label labelMainMenu = new Label("Test Menu");
        Label labelAccessGranted = new Label("Welcome, Maxi");
        Label labelAccessDenied = new Label("Access denied");
        TextField input = new TextField();
        input.setPromptText("Write stuff");
        String userInput = input.getText();
        Label printUserInput = new Label(userInput);

        /**
         * Buttons
         */
        b1 = new Button();
        b1.setText("Print master list");
        b1.setOnAction(e -> c.printMasterList());

        Button toScene2 = new Button("Other window");
        toScene2.setOnAction(e -> window.setScene(scene2));

        Button backToMain = new Button("Back");
        backToMain.setOnAction(e -> window.setScene(mainMenu));

        Button popup = new Button("AlertBox");
        popup.setOnAction(e -> AlertBox.display("Title goes here", "message goes here"));

        Button confirm = new Button("ConfirmBox");
        confirm.setOnAction(e -> {
            result = ConfirmBox.display("Title goes here", "do you?");
            System.out.println(result);
        });

        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        /**
         * Confirm exit and save before exiting
         */
        window.setOnCloseRequest(e -> {
            // when user hits the X, ConfirmBox will now display
            e.consume();
            closeProgram();
        });
        Button close = new Button("Exit");
        close.setOnAction(e -> closeProgram());

        /**
         * window/scene layout
         */
        /* BorderPane */
        HBox topMenu = new HBox();
        topMenu.getChildren().addAll(labelMainMenu);

        VBox leftMenu = new VBox();
        leftMenu.getChildren().addAll(toScene2, b1, popup, close);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(leftMenu);

        /* GridPane */
        GridPane gridPane = GenerateGridPane.getGrid();

        Label nameLabel = new Label("Username:");
        TextField nameInput = new TextField();
        loginButton.setOnAction(e -> {
            verifyLogin = isValid(nameInput);
            if(verifyLogin == true) {
                window.setScene(accessScene);
            } else {
                window.setScene(noAccessScene);
            }
        });
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameInput, 1, 0);

        Label passLabel = new Label("Password:");
        TextField passInput = new TextField();
        passInput.setPromptText("Password");
        GridPane.setConstraints(passLabel, 0, 1);
        GridPane.setConstraints(passInput, 1, 1);
        GridPane.setConstraints(backToMain, 2, 2);

        gridPane.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton, backToMain);

        VBox accessVbox = new VBox();
        accessVbox.getChildren().addAll(labelAccessGranted);
        VBox noAccessVbox = new VBox();
        noAccessVbox.getChildren().addAll(labelAccessDenied);

        /* instantiate scenes */
        mainMenu = new Scene(borderPane, 300, 300);
        scene2 = new Scene(gridPane, 500, 600);
        accessScene = new Scene(accessVbox);
        noAccessScene = new Scene(noAccessVbox);

        /* set main window */
        window.setScene(mainMenu);
        window.setTitle("Initiator");
        window.show();
    }

    public void closeProgram() {
        Boolean answer = ConfirmBox.display("tItLe", "Are you sure you want to exit?");
        if (answer) {
            System.out.println("Save state");
            window.close();
        }
    }

    public boolean isValid(TextField userName) {
        if (userName.getText().equals("maxi")) {
            return true;
        } else {
            return false;
        }
    }
 }
