package FXControl;

import Main.*;
import Main.Alert;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class admin_reguser_control {

    public static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", Pattern.CASE_INSENSITIVE);
    public static final Pattern NAME = Pattern.compile("^[a-zA-Z]", Pattern.CASE_INSENSITIVE);

    @FXML
    Label menu_version, menu_logout;
    @FXML
    ImageView admin_reguser_close, admin_reguser_back;
    @FXML
    TextField admin_reguser_uid, admin_reguser_email, admin_reguser_firstname, admin_reguser_lastname, admin_reguser_position;
    @FXML
    ComboBox admin_reguser_access;
    @FXML
    Button admin_reguser_regbutton, admin_reguser_clearbutton;


    @FXML
    private void initialize(){

        /* TOP RIGHT */
        admin_reguser_close.setOnMouseClicked(e -> MyStage.closeStage(admin_reguser_close));
        admin_reguser_back.setOnMouseClicked(e -> {
            MyStage.createNewStage(new Stage(), "../GUI/admin_main.fxml");
            MyStage.closeStage(admin_reguser_close);
        });


        /* MENU */
        menu_version.setText(Version.get());
        menu_logout.setOnMouseClicked(e -> {
            boolean logout = Alert.displayChose("Logout", "Are you logging out?");
            if(logout){
                MyStage.createNewStage(new Stage(), "../GUI/login.fxml");
                MyStage.closeStage(admin_reguser_close);
            }
        });

        /* MAIN */

        setChoiceBox();

        admin_reguser_regbutton.setOnAction(e -> {
            regButton();
            clearButton();
        });

        admin_reguser_clearbutton.setOnAction(e -> clearButton());
    }


    ///////////////////////
    private String checkInputIsValid(List<String> l){
        StringBuilder sb = new StringBuilder();
        Matcher m;

        //if(l.get(0).equals("")) sb.append("UID\n");
        if(!l.get(1).equals("")){                       //EMAIL
            m = EMAIL.matcher(l.get(1));
            if(!m.find()) sb.append("EMAIL\n");
        }
        if(!l.get(2).equals("")){                       //FIRST NAME
            m = NAME.matcher(l.get(2));
            if(!m.find()) sb.append("FIRST NAME\n");
        }
        if(!l.get(3).equals("")){                       //LAST NAME
            m = NAME.matcher(l.get(3));
            if(!m.find()) sb.append("LAST NAME\n");
        }
        //if(l.get(4).equals("")) sb.append("POSITION\n");  // POSITION

        return sb.toString();
    }

    private String checkInputIsEmpty(List<String> l){
        StringBuilder sb = new StringBuilder();

        if(l.get(0).equals("")) sb.append("UID\n");  // UID
        if(l.get(1).equals("")) sb.append("EMAIL\n");  // EMAIL
        if(l.get(2).equals("")) sb.append("FIRST NAME\n");  // FIRST NAME
        if(l.get(3).equals("")) sb.append("LAST NAME\n");  // LAST NAME
        if(l.get(4).equals("")) sb.append("POSITION\n");  // POSITION

        return sb.toString();
    }

    private List<String> readInputs(){
        List<String> l = new ArrayList<>();
        l.add(admin_reguser_uid.getText());
        l.add(admin_reguser_email.getText());
        l.add(admin_reguser_firstname.getText());
        l.add(admin_reguser_lastname.getText());
        l.add(admin_reguser_position.getText());
        return l;
    }

    private void regButton(){
        boolean isvalid=false, isnotempty=false;
        int st;

        if(checkInputIsValid(readInputs()).equals("")) isvalid = true;
        if(checkInputIsEmpty(readInputs()).equals("")) isnotempty = true;

        String msg = "";

        if(isvalid && isnotempty){
            User u = new User(readInputs().get(0), "user", readInputs().get(1), readInputs().get(2),
                    readInputs().get(3), admin_reguser_access.getSelectionModel().getSelectedItem().toString(),
                    readInputs().get(4), "watmate", Date.today());
            u.addToDatabase();
            Alert.display("Success!", "You have registered the user successfully.");

        }else if(!isvalid && isnotempty){
            msg = "The following inputs are wrong:\n\n" + checkInputIsValid(readInputs());
        }else if(isvalid && !isnotempty){
            msg = "The following inputs are missing:\n\n" + checkInputIsEmpty(readInputs());
        }else{
            msg = "The following inputs are wrong:\n\n" + checkInputIsValid(readInputs()) + "\n" +
                    "The following inputs are missing:\n\n" + checkInputIsEmpty(readInputs());
        }
        if(!msg.equals("")) Alert.display("Warning!", msg);
    }

    private void clearButton(){
        admin_reguser_uid.setText("");
        admin_reguser_email.setText("");
        admin_reguser_firstname.setText("");
        admin_reguser_lastname.setText("");
        admin_reguser_position.setText("");
    }

    ///////////////////////
    private void setChoiceBox(){
        admin_reguser_access.getItems().addAll("User", "Admin");
        admin_reguser_access.getSelectionModel().select("User");
    }
}
