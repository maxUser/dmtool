package frontend;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class newScene {

    private Scene s1;
    private Label l1;
    private Button b1;
    CreateCharacterView x;

    public newScene() {}

    public void start(Stage primaryStage) {
        x  = new CreateCharacterView();
        StackPane vb = new StackPane();
        l1 = new Label("K");
        b1 = new Button("Back");
//        b1.setOnAction(e -> x.start(primaryStage));
        vb.getChildren().addAll(l1, b1);
        s1 = new Scene(vb, 200, 200);

        primaryStage.setScene(s1);
        primaryStage.setTitle("New Scene");
        primaryStage.show();

    }
}
