package frontend;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Scene2 {

    private Scene scene2;
    private Button scene3Button, scene1Button;

    public Scene2(){}

    public void setScene2(Stage window) {
        scene3Button = new Button("Scene 3");
        scene1Button = new Button("Scene 1");

        VBox layout = GenerateVBox.getVBox();
        layout.getChildren().addAll(scene1Button, scene3Button);

        scene3Button.setOnAction(e -> goToScene3(window));
        scene1Button.setOnAction(e -> goToScene1(window));

        scene2 = new Scene(layout, 225, 275);
        window.setScene(scene2);
        window.setTitle("Scene 2");
        window.show();
    }

    public void goToScene3(Stage window) {
        Scene3 s3 = new Scene3();
        s3.setScene3(window);
    }

    public void goToScene1(Stage window) {
        Scene1 s1 = new Scene1();
        s1.setScene1(window);
    }
}
