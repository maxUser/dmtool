package frontend;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Scene3 {
    private Scene scene3;
    private Button scene1Button;

    public Scene3(){}

    public void setScene3(Stage window) {

        scene1Button = new Button("Scene 1");

        VBox layout = GenerateVBox.getVBox();
        layout.getChildren().addAll(scene1Button);

        scene1Button.setOnAction(e -> goToScene1(window));

        scene3 = new Scene(layout, 225, 275);
        window.setScene(scene3);
        window.setTitle("Scene 3");
        window.show();
    }

    public void goToScene1(Stage window) {
        Scene1 s1 = new Scene1();
        s1.setScene1(window);
    }
}
