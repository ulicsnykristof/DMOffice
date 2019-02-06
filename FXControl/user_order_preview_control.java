package FXControl;

import Main.*;
import Main.Alert;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class user_order_preview_control {

    List<OrderPiece> prev_list;

    @FXML
    Label user_order_pre_ln1, user_order_pre_ln2, user_order_pre_ln3, user_order_pre_ln4, user_order_pre_ln5;
    @FXML
    ImageView user_order_pre_close;


    @FXML
    private void initialize(){

        /* TOP RIGHT */
        user_order_pre_close.setOnMouseClicked(e -> MyStage.closeStage(user_order_pre_close));


        /* MAIN */






    }


}
