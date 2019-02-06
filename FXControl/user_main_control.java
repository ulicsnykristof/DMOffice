package FXControl;

import Main.*;
import Main.Alert;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class user_main_control {

    @FXML
    Label menu_version, menu_logout;
    @FXML
    ImageView user_main_close;
    @FXML
    AnchorPane user_main_products, user_main_order;



    @FXML
    private void initialize() {


        /* TOP RIGHT */
        user_main_close.setOnMouseClicked(e -> MyStage.closeStage(user_main_close));


        /* MENU */
        menu_version.setText(Version.get());
        menu_logout.setOnMouseClicked(e -> {
            boolean logout = Alert.displayChose("Logout", "Are you logging out?");
            if(logout){
                MyStage.createNewStage(new Stage(), "../GUI/login.fxml");
                MyStage.closeStage(user_main_close);
            }
        });

        /* MAIN */
        user_main_products.setOnMouseClicked(e -> {
            MyStage.createNewStage(new Stage(), "../GUI/user_products.fxml");
            MyStage.closeStage(user_main_close);
        });

        user_main_order.setOnMouseClicked(e -> {
            MyStage.createNewStage(new Stage(), "../GUI/user_order.fxml");
            MyStage.closeStage(user_main_close);
        });


    }

}
