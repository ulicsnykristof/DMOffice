package FXControl;

import Main.*;
import Main.Alert;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class user_order_control {
    Order new_order;
    List<OrderPiece> order_list = new ArrayList<>();
    List<Product> pl = Product.queryAllProduct();

    @FXML
    Label menu_version, menu_logout;
    @FXML
    ImageView user_products_close, user_products_back;
    @FXML
    ComboBox user_order_combo;
    @FXML
    ListView user_order_plist, user_order_olist;
    @FXML
    Button user_order_add, user_order_remove, user_order_clear, user_order_confirm, user_order_preview,
    user_order_all, user_order_stock;
    @FXML
    TextField user_order_qnt;

    @FXML
    private void initialize(){

        /* TOP RIGHT */
        user_products_close.setOnMouseClicked(e -> MyStage.closeStage(user_products_close));
        user_products_back.setOnMouseClicked(e -> {
            MyStage.createNewStage(new Stage(), "../GUI/user_main.fxml");
            MyStage.closeStage(user_products_close);
        });

        /* MENU */
        menu_version.setText(Version.get());
        menu_logout.setOnMouseClicked(e -> {
            boolean logout = Alert.displayChose("Logout", "Are you logging out?");
            if(logout){
                MyStage.createNewStage(new Stage(), "../GUI/login.fxml");
                MyStage.closeStage(user_products_close);
            }
        });

        /* MAIN */
        initializeComboBox();
        initializePlist();

        user_order_combo.setOnAction(e -> initializePlist());

        user_order_add.setOnAction(e -> {
            if(user_order_qnt.getText().equals("")){
                Alert.display("Warning!", "You have to fill the quantity field.");
            }else if(String.valueOf(user_order_qnt.getText()).matches("[0-9]")){
                Alert.display("Warning!", "Only numbers are allowed.");
            }else{
                if(order_list.isEmpty()){
                    user_order_combo.setDisable(true);
                }
                for (Product p: pl) {
                    if(p.getName().equals(user_order_plist.getSelectionModel().getSelectedItem())){
                        System.out.println(p.getName() + " " + Integer.parseInt(user_order_qnt.getText()));
                        order_list.add(new OrderPiece(p, Integer.parseInt(user_order_qnt.getText())));
                        user_order_olist.getItems().add(p.getName() + " " + user_order_qnt.getText());
                        break;
                    }
                }
            }
            user_order_olist.getSelectionModel().select(0);
        });

        user_order_remove.setOnAction(e -> {
            if(!user_order_olist.getItems().isEmpty()){
                int idx = user_order_olist.getSelectionModel().getSelectedIndex();
                order_list.remove(idx);
                user_order_olist.getItems().remove(idx);

                if(order_list.isEmpty()){
                    user_order_combo.setDisable(false);
                }
            }
        });

        user_order_clear.setOnAction(e -> {
            user_order_olist.getItems().clear();
            order_list.clear();
            user_order_combo.setDisable(false);
        });

        user_order_confirm.setOnAction(e -> {
            int price = 0;
            for (OrderPiece op: order_list) {
                price = price + op.getPrice();
                System.out.println(op.getPrice() + " " + op.getProduct().getPrice() + " " + op.getQuantity());
            }

            Order o = new Order(Date.today(), "order placed",
                    user_order_combo.getSelectionModel().getSelectedItem().toString(), price);

            if(!o.addToDatabase()){
                Alert.display("Error!", "Database problem.");
            }else{
                user_order_combo.setDisable(false);
            }

            for (OrderPiece op: order_list) {
                op.addToDatabase(o.getId2());
            }

            //MyStage.createNewStage(new Stage(), "../GUI/user_order_preview.fxml");
            createPreview();


        });
    }

    private void initializeComboBox(){
        List<String> partner_list = new ArrayList<>();

        boolean already_in;

        for (Product p: pl) {
            already_in = false;
            for (String partner: partner_list) {
                if(p.getDistributor().equals(partner)){
                    already_in = true;
                    break;
                }
            }
            if(!already_in){
                partner_list.add(p.getDistributor());
            }
        }

        for (String s: partner_list) {
            user_order_combo.getItems().add(s);
        }
        user_order_combo.getSelectionModel().select(0);
    }

    private void initializePlist(){
        user_order_plist.getItems().clear();
        for (Product p: pl) {
            if(user_order_combo.getSelectionModel().getSelectedItem().equals(p.getDistributor())){
                user_order_plist.getItems().add(p.getName());
            }
        }
    }

    private void createPreview(){
        Stage s = new Stage();

        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle("Preview");
        s.setMinWidth(400);
        s.setMinHeight(600);

        Label l = new Label("Something interesting");
        l.setPadding(new Insets(20,20,20,20));
        l.setTextAlignment(TextAlignment.LEFT);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().add(l);
        layout.setAlignment(Pos.TOP_LEFT);

        int total = 0;

        for (OrderPiece op: order_list) {
            Label nl = new Label(op.getProduct().getName() + " :  " + op.getQuantity() + " X " +
                    op.getProduct().getPrice());
            total = total + (op.getQuantity() * op.getProduct().getPrice());
            nl.setPadding(new Insets(10,10,10,10));
            nl.setTextAlignment(TextAlignment.LEFT);
            layout.getChildren().add(nl);
        }

        Label nl = new Label("Total:......................... " + total + " HUF");
        nl.setPadding(new Insets(10,10,10,10));
        nl.setTextAlignment(TextAlignment.LEFT);
        layout.getChildren().add(nl);



        Scene sc = new Scene(layout);
        s.setScene(sc);
        s.showAndWait();
    }


    /*
    new_order = new Order(Date.today(), "inprogress",
             String.valueOf(user_order_combo.getSelectionModel().getSelectedItem()), 0);
     */
}
