package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class GenerateHBox {
    public static HBox getHBox() {
        HBox hbox = new HBox(15);
        hbox.setPadding(new Insets(10,10,10,10));
        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }
}
