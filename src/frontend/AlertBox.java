package frontend;

import backend.TieGroup;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class AlertBox {
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label l1 = new Label();
        l1.setText(message);
        Button b1 = new Button("Close");
        b1.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(l1, b1);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("/images/style.css");
        window.setScene(scene);
        window.showAndWait();
    }

    public static void displayTies(String title, String message, ArrayList<TieGroup> tieList) {
        Stage window = new Stage();
//        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        String allTies = "";

        for (TieGroup tg : tieList) {
            for (String charName : tg.getTiedChars()) {
                allTies = allTies + charName;
            }
        }

        String strTies = "";
        for (TieGroup tg : tieList) {
            for (String charName : tg.getTiedChars()) {
                strTies += charName + " | ";
            }
            strTies += (tg.getInitValue() + "\n");
        }

        Label msg = new Label();
        msg.setText(message);
        Label ties = new Label();
        ties.setText(strTies);
        Button b1 = new Button("Close");
        b1.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(msg, ties, b1);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("/images/style.css");
        window.setScene(scene);
        window.showAndWait();
    }
}
