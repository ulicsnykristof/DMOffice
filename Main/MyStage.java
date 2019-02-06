package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class MyStage {

    public static void createNewStage(Stage s, String res) {
        try {
            FXMLLoader loader = new FXMLLoader(MyStage.class.getResource(res));
            Parent p = (Parent) loader.load();
            Scene sc = new Scene(p);
            sc.getStylesheets().add(MyStage.class.getResource("../GUI/style.css").toExternalForm());
            s.setScene(sc);
            s.initStyle(StageStyle.UNDECORATED);
            s.show();
        }catch(IOException ex){
            System.err.println("FXML load problem bro");
        }
    }

    public static void closeStage(Node n){
        Stage s = (Stage) n.getScene().getWindow();
        s.close();
    }

}

