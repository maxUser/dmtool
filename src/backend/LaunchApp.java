package backend;

import frontend.MainMenuView;
import frontend.Scene1;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class LaunchApp extends Application {

    public static void main(String[] args) { launch(args); }

    public void start(Stage primaryStage) throws ParseException, IOException {
        Model aModel = new Model();
        BackendController bc = new BackendController(aModel);
        MainMenuView mm = new MainMenuView(bc);
//        Scene1 s1 = new Scene1();


        /* Test data */
        //bc.makeTestMasterList();

        /* Populate masterList from JSON file */
        bc.populateMasterList();

        /* Launch window */
        mm.setMainMenuView(primaryStage);
//        s1.setScene1(primaryStage);
    }
}
