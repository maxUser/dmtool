package frontend;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label l1 = new Label();
        l1.setText(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        HBox layout = GenerateHBox.getHBox();
        layout.getChildren().addAll(l1, yesButton, noButton);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("/images/style.css");
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
