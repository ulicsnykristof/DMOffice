package FXControl;

import Main.*;
import Main.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class login_control {

    @FXML
    Label login_version;
    @FXML
    ImageView login_close;
    @FXML
    Button bt_login;
    @FXML
    AnchorPane ap;
    @FXML
    CheckBox cb_user, cb_admin;
    @FXML
    TextField t_login;
    @FXML
    PasswordField t_pwd;

    @FXML
    private void initialize(){
        bt_login.setOnAction(e -> {

            if(cb_user.isSelected() && cb_admin.isSelected()){
                Alert.display("Error!", "You can only select one!");
            }else if(!cb_user.isSelected() && !cb_admin.isSelected()){
                Alert.display("Error!", "You have to select one!");
            }else if(cb_user.isSelected()){
                User u = new User(t_login.getText(), t_pwd.getText());
                if(u.PasswordCheckUser()){
                    MyStage.createNewStage(new Stage(), "../GUI/user_main.fxml");
                    MyStage.closeStage(bt_login);
                }else{
                    Alert.display("Error!", "Wrong username or password.");
                }
            }else if(cb_admin.isSelected()){
                User u = new User(t_login.getText(), t_pwd.getText());
                if(u.PasswordCheckAdmin()){
                    MyStage.createNewStage(new Stage(), "../GUI/admin_main.fxml");
                    MyStage.closeStage(bt_login);
                }else{
                    Alert.display("Error!", "Wrong username or password.");
                }
            }

            /*
            if(!userCheck(t_login.getText(), t_pwd.getText())){
                System.out.println("cannot login");
            }else{
                if(cb_user.isSelected()){
                    MyStage.createNewStage(new Stage(), "../FXML/user.fxml");
                }else if(cb_admin.isSelected()){
                    MyStage.createNewStage(new Stage(), "../FXML/admin.fxml");
                }else{
                    System.err.println("It not supposed to happen");
                }
                MyStage.closeStage(bt_login);
            }
            */
        });

        login_close.setOnMouseClicked(e -> MyStage.closeStage(login_close));
        login_version.setText(Version.get());
        /*
        cb_user.setOnMouseClicked(e -> cbCheck(cb_admin));

        cb_admin.setOnMouseClicked(e -> cbCheck(cb_user));
        */
    }


}
