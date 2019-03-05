package frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class GenerateVBox {
    public static VBox getVBox() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }
}
