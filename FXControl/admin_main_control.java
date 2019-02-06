package FXControl;

import Main.*;
import Main.Alert;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class admin_main_control {

    @FXML
    Label menu_version, menu_logout;
    @FXML
    ImageView admin_main_close;
    @FXML
    AnchorPane admin_main_register, admin_main_manage;


    @FXML
    private void initialize(){
        admin_main_close.setOnMouseClicked(e -> MyStage.closeStage(admin_main_close));
        menu_version.setText(Version.get());

        admin_main_register.setOnMouseClicked(e -> {
            MyStage.createNewStage(new Stage(), "../GUI/admin_reguser.fxml");
            MyStage.closeStage(admin_main_close);
        });

        admin_main_manage.setOnMouseClicked(e -> {
            MyStage.createNewStage(new Stage(), "../GUI/admin_manageuser.fxml");
            MyStage.closeStage(admin_main_close);
        });

        menu_logout.setOnMouseClicked(e -> {
            boolean logout = Alert.displayChose("Logout", "Are you logging out?");
            if(logout){
                MyStage.createNewStage(new Stage(), "../GUI/login.fxml");
                MyStage.closeStage(admin_main_close);
            }
        });

    }
}
