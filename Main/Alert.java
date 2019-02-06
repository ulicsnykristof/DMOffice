package Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {

    static boolean verify;

    public static void display(String title, String msg){
        Stage s = new Stage();

        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle(title);
        s.setMinWidth(250);
        s.setMinHeight(150);

        Label l = new Label(msg);
        l.setPadding(new Insets(20,20,20,20));
        l.setTextAlignment(TextAlignment.CENTER);
        Button close = new Button("OK");
        close.setOnAction(e -> s.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0,0,20,0));
        layout.getChildren().addAll(l, close);
        layout.setAlignment(Pos.CENTER);

        Scene sc = new Scene(layout);
        s.setScene(sc);
        s.showAndWait();
    }

    public static boolean displayChose(String title, String msg){
        verify = false;

        Stage s = new Stage();

        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle(title);
        s.setMinWidth(250);
        s.setMinHeight(150);

        Label l = new Label(msg);
        l.setPadding(new Insets(20,20,20,20));
        l.setTextAlignment(TextAlignment.CENTER);
        Button confirm = new Button("Confirm");
        confirm.setOnAction(e -> {
            verify = true;
            s.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> s.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0,0,20,0));
        layout.getChildren().addAll(l, confirm, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene sc = new Scene(layout);
        s.setScene(sc);
        s.showAndWait();

        return verify;
    }
}
