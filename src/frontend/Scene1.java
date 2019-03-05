package frontend;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Scene1 {
    private Scene scene1;
    private Button scene2Button;

    public Scene1(){}

    public void setScene1(Stage window) {

        scene2Button = new Button("Scene 2");

        VBox layout = GenerateVBox.getVBox();
        layout.getChildren().addAll(scene2Button);

        scene2Button.setOnAction(e -> goToScene2(window));

        scene1 = new Scene(layout, 225, 275);
        window.setScene(scene1);
        window.setTitle("Scene 1");
        window.show();
    }

    public void goToScene2(Stage window) {
        Scene2 s2 = new Scene2();
        s2.setScene2(window);
    }

}
