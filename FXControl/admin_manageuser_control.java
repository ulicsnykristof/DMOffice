package FXControl;

import Main.*;
import Main.Alert;
import Database.Database;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class admin_manageuser_control {

    List<User> user_list = User.queryAllUsers();
    int idx;

    @FXML
    Label menu_version, menu_logout, admin_moduser_uid_label, admin_moduser_email_label, admin_moduser_firstname_label,
            admin_moduser_lastname_label, admin_moduser_position_label, admin_moduser_access_label,
            admin_moduser_regdate_label, admin_moduser_regby_label;
    @FXML
    ImageView admin_moduser_close, admin_moduser_back, admin_moduser_uid_img, admin_moduser_email_img,
            admin_moduser_firstname_img, admin_moduser_lastname_img, admin_moduser_position_img;
    @FXML
    TextField admin_moduser_uid_text, admin_moduser_email_text, admin_moduser_firstname_text, admin_moduser_lastname_text,
            admin_moduser_position_text;
    @FXML
    Button admin_moduser_select, admin_moduser_confirm, admin_moduser_delete;
    @FXML
    ListView admin_moduser_list;


    @FXML
    private void initialize(){


        /* TOP RIGHT */
        admin_moduser_close.setOnMouseClicked(e -> MyStage.closeStage(admin_moduser_close));
        admin_moduser_back.setOnMouseClicked(e -> {
            MyStage.createNewStage(new Stage(), "../GUI/admin_main.fxml");
            MyStage.closeStage(admin_moduser_close);
        });


        /* MENU */
        menu_version.setText(Version.get());
        menu_logout.setOnMouseClicked(e -> {
            boolean logout = Alert.displayChose("Logout", "Are you logging out?");
            if(logout){
                MyStage.createNewStage(new Stage(), "../GUI/login.fxml");
                MyStage.closeStage(admin_moduser_close);
            }
        });

        /* MAIN */
        setAllTextInvisible();
        initializeListView();

        admin_moduser_select.setOnAction(e -> {
            if(admin_moduser_uid_text.isVisible() || admin_moduser_email_text.isVisible() || admin_moduser_firstname_text.isVisible() || admin_moduser_lastname_text.isVisible() || admin_moduser_uid_text.isVisible()) {
                Alert.display("Warning!", "You have unfinished task.");
            }else{
                showSelectedUserdata();
            }
        });

        admin_moduser_uid_img.setOnMouseClicked(e -> changeUserDataButton(admin_moduser_uid_text));
        admin_moduser_email_img.setOnMouseClicked(e -> changeUserDataButton(admin_moduser_email_text));
        admin_moduser_firstname_img.setOnMouseClicked(e -> changeUserDataButton(admin_moduser_firstname_text));
        admin_moduser_lastname_img.setOnMouseClicked(e -> changeUserDataButton(admin_moduser_lastname_text));
        admin_moduser_position_img.setOnMouseClicked(e -> changeUserDataButton(admin_moduser_position_text));

        admin_moduser_confirm.setOnAction(e -> {
            String[] asd = changeUserDateConfirm(admin_moduser_uid_text, admin_moduser_email_text,
                    admin_moduser_firstname_text, admin_moduser_lastname_text, admin_moduser_position_text);

            if (asd[0].equals("") && asd[1].equals("") && asd[2].equals("") && asd[3].equals("") && asd[4].equals("")) {
                Alert.display("Error!", "You are not allowed to do that.");
            }else{
                if (asd[5].equals("false")) {
                    Alert.display("Warning!", "You can't leave an input empty.");
                } else if (asd[5].equals("true")) {
                    user_list.get(idx).update(asd[0], asd[1], asd[2], asd[3], asd[4]);
                    Alert.display("Success!", "You successfully changed data.");
                    setAllTextInvisible();
                    user_list = User.queryAllUsers();
                    showSelectedUserdata();
                }
            }
        });

        admin_moduser_delete.setOnAction(e -> {
            if(admin_moduser_uid_text.isVisible() || admin_moduser_email_text.isVisible() || admin_moduser_firstname_text.isVisible() || admin_moduser_lastname_text.isVisible() || admin_moduser_uid_text.isVisible()){
                 Alert.display("Warning!", "You have unfinished task.");
            }else{
                boolean delete = Alert.displayChose("Warning!", "Are you delete this user from database?");
                if(delete){
                    user_list.get(idx).delete();
                    Alert.display("Success!","You have successfully deleted user.");
                    updateList();
                }
            }

        });
    }


    //Initialize ListView, showing the user_id and user_uid
    private void initializeListView(){
        for (User u: user_list) {
            admin_moduser_list.getItems().add("#" + String.valueOf(u.getId()) + " " + u.getUid());
        }
        admin_moduser_list.getSelectionModel().select(0);
        showSelectedUserdata();
    }

    //Showing the the selected user data
    private void showSelectedUserdata(){
        idx = admin_moduser_list.getSelectionModel().getSelectedIndex();
        admin_moduser_uid_label.setText(user_list.get(idx).getUid());
        admin_moduser_email_label.setText(user_list.get(idx).getEmail());
        admin_moduser_firstname_label.setText(user_list.get(idx).getFirstname());
        admin_moduser_lastname_label.setText(user_list.get(idx).getLastname());
        admin_moduser_position_label.setText(user_list.get(idx).getPosition());
        admin_moduser_access_label.setText(user_list.get(idx).getAccess());
        admin_moduser_regdate_label.setText(user_list.get(idx).getRegdate());
        admin_moduser_regby_label.setText(user_list.get(idx).getRegby());
    }

    //Initialize the changeUserDataButton
    private void changeUserDataButton(TextField text){
        if(text.isVisible()){
            if(!text.getText().equals("")){
                boolean v = Alert.displayChose("Warning!", "You have an unfinished task. Your " +
                        "changes will be lost. Continue?");
                if(v){
                    text.setVisible(false);
                    text.setText("");
                }
            }else{
                text.setVisible(false);
            }

        }else{
            text.setVisible(true);
        }
    }

    private String[] changeUserDateConfirm(TextField uid, TextField email, TextField fn, TextField ln, TextField pos){
        String[] ch = new String[6];
        ch[5] = "true";
        if(uid.isVisible()){
            if(uid.getText().equals("")){
                ch[5] = "false";
                ch[0] = "";
            }else{
                ch[0] = uid.getText();
            }
        }else{
            ch[0] = "";
        }

        if(email.isVisible()){
            if(email.getText().equals("")){
                ch[5] = "false";
                ch[1] = "";
            }else{
                ch[1] = email.getText();
            }
        }else{
            ch[1] = "";
        }

        if(fn.isVisible()){
            if(fn.getText().equals("")){
                ch[5] = "false";
                ch[2] = "";
            }else{
                ch[2] = fn.getText();
            }
        }else{
            ch[2] = "";
        }

        if(ln.isVisible()){
            if(ln.getText().equals("")){
                ch[5] = "false";
                ch[3] = "";
            }else{
                ch[3] = ln.getText();
            }
        }else{
            ch[3] = "";
        }

        if(pos.isVisible()){
            if(pos.getText().equals("")){
                ch[5] = "false";
                ch[4] = "";
            }else{
                ch[4] = pos.getText();
            }
        }else{
            ch[4] = "";
        }

        return ch;
    }

    private void setAllTextInvisible(){
        admin_moduser_uid_text.setText("");
        admin_moduser_uid_text.setVisible(false);
        admin_moduser_email_text.setText("");
        admin_moduser_email_text.setVisible(false);
        admin_moduser_firstname_text.setText("");
        admin_moduser_firstname_text.setVisible(false);
        admin_moduser_lastname_text.setText("");
        admin_moduser_lastname_text.setVisible(false);
        admin_moduser_position_text.setText("");
        admin_moduser_position_text.setVisible(false);
    }

    private void updateList(){
        user_list.clear();
        user_list = User.queryAllUsers();
        admin_moduser_list.getItems().clear();
        for (User u: user_list) {
            admin_moduser_list.getItems().add("#" + String.valueOf(u.getId()) + " " + u.getUid());
        }
        admin_moduser_list.getSelectionModel().select(0);
        showSelectedUserdata();
    }
}
