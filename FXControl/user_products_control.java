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

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;

public class user_products_control {
    int idx;
    List<Product> product_list;

    @FXML
    Label menu_version, menu_logout;
    @FXML
    ImageView user_products_close, user_products_back;
    @FXML
    Button user_product_export, user_product_import, user_product_select, user_product_remove;
    @FXML
    ListView user_product_list;
    @FXML
    Label user_product_id_label, user_product_name_label,user_product_type_label,user_product_category_label,
            user_product_producer_label,user_product_distributor_label,user_product_country_label,
            user_product_container_label, user_product_volume_label, user_product_alcohol_label, user_product_returnable_label;


    @FXML
    private void initialize() {


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
        initializeListView();

        user_product_select.setOnAction(e -> showSelectedUserdata());

        user_product_export.setOnAction(e -> exportToExcel());

        user_product_import.setOnAction(e -> {
            boolean success = true;
            List<Product> l = importFromExcel();
            for (Product p: l) {
                if(!p.addToDatabase()){
                    success = false;
                }
            }
            if(success){
                Alert.display("Success!", "Database updated successfully.");
                initializeListView();
            }else{
                Alert.display("Error!", "Can't update database.");
            }
        });

        user_product_remove.setOnAction(e -> {
            boolean delete = Alert.displayChose("Warning!", "Are you delete this user from database?");
            if(delete){
                delete = product_list.get(idx).delete();
                if(delete){
                    Alert.display("Success!", "You have successfully deleted the selected item.");
                    initializeListView();
                }else{
                    Alert.display("Error!", "You can't delete this item.");
                }
            }
        });

    }

    private List<Product> importFromExcel(){
        List<Product> pl = new ArrayList<>();
        try{
           FileInputStream fis = new FileInputStream(new File("D:\\Programs\\IntelliJ projects\\DMOffice\\src\\XLS\\product_input.xls"));
           HSSFWorkbook wb = new HSSFWorkbook(fis);

           Sheet sheet = wb.getSheetAt(0);

           int i = 0;
           for (Row row: sheet){
               if(i!=0){
                   String name="", type="", cat="", pro="", dis="", cou="", con="";
                   double vol = 0, alc = 0, pri = 0;
                   boolean ret = true;
                   for(Cell cell: row){
                       if(cell.getColumnIndex() == 0){name = cell.getStringCellValue();}
                       if(cell.getColumnIndex() == 1){type = cell.getStringCellValue();}
                       if(cell.getColumnIndex() == 2){cat = cell.getStringCellValue();}
                       if(cell.getColumnIndex() == 3){pro = cell.getStringCellValue();}
                       if(cell.getColumnIndex() == 4){dis = cell.getStringCellValue();}
                       if(cell.getColumnIndex() == 5){pri = cell.getNumericCellValue();}
                       if(cell.getColumnIndex() == 6){cou = cell.getStringCellValue();}
                       if(cell.getColumnIndex() == 7){con = cell.getStringCellValue();}
                       if(cell.getColumnIndex() == 8){vol = cell.getNumericCellValue();}
                       if(cell.getColumnIndex() == 9){alc = cell.getNumericCellValue();}
                       if(cell.getColumnIndex() == 10){ret = cell.getBooleanCellValue();}
                   }

                   Product p = new Product(name, type, cat, pro, dis, (int)pri, cou, con, (int)vol, alc, ret);
                   pl.add(p);
               }
               i++;
           }


        }catch(IOException ex){
            System.err.println("asdasddsasda");
        }




        return pl;
    }

    private void exportToExcel(){

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Workers");
        Row r = sheet.createRow(0);


        Cell id, name, type, cat, prod, dist, pri, country, cont, vol, alc, ret;
        id = r.createCell(0);
        id.setCellValue("ID");

        name = r.createCell(1);
        name.setCellValue("NAME");

        type = r.createCell(2);
        type.setCellValue("TYPE");

        cat = r.createCell(3);
        cat.setCellValue("CATEGORY");

        prod = r.createCell(4);
        prod.setCellValue("PRODUCER");

        dist = r.createCell(5);
        dist.setCellValue("DISTRIBUTOR");

        pri = r.createCell(6);
        pri.setCellValue("PRICE");

        country = r.createCell(7);
        country.setCellValue("COUNTRY");

        cont = r.createCell(8);
        cont.setCellValue("CONTAINER");

        vol = r.createCell(9);
        vol.setCellValue("VOLUME");

        alc = r.createCell(10);
        alc.setCellValue("ALCOHOL");

        ret = r.createCell(11);
        ret.setCellValue("RETURNABLE");


        int k = 1;
        for (Product u: product_list) {
            Row ro = sheet.createRow(k);
            Cell id1, name1, type1, cat1, prod1, dist1, pri1, country1, cont1, vol1, alc1, ret1;

            id1 = ro.createCell(0);
            id1.setCellValue(u.getId());

            name1 = ro.createCell(1);
            name1.setCellValue(u.getName());

            type1 = ro.createCell(2);
            type1.setCellValue(u.getType());

            cat1 = ro.createCell(3);
            cat1.setCellValue(u.getCategory());

            prod1 = ro.createCell(4);
            prod1.setCellValue(u.getProducer());

            dist1 = ro.createCell(5);
            dist1.setCellValue(u.getDistributor());

            pri1 = ro.createCell(6);
            pri1.setCellValue(u.getPrice());

            country1 = ro.createCell(7);
            country1.setCellValue(u.getCountry());

            cont1 = ro.createCell(8);
            cont1.setCellValue(u.getContainer());

            vol1 = ro.createCell(9);
            vol1.setCellValue(u.getVolume());

            alc1 = ro.createCell(10);
            alc1.setCellValue(u.getAlcohol());

            ret1 = ro.createCell(11);
            ret1.setCellValue(u.getReturnable());

            k++;
        }


        try{
            FileOutputStream fos = new FileOutputStream("../XLS/product_output.xls");
            ((HSSFWorkbook) wb).write(fos);
            fos.close();
        }catch(IOException ex){
            System.err.println("shit");
        }



    }

    private void initializeListView(){
        product_list = Product.queryAllProduct();
        user_product_list.getItems().clear();
        for (Product p: product_list) {
            user_product_list.getItems().add("#" + String.valueOf(p.getId()) + " " + p.getName());
        }
        //user_product_list.getSelectionModel().select(0);
        //showSelectedUserdata();
    }

    private void showSelectedUserdata(){
        idx = user_product_list.getSelectionModel().getSelectedIndex();
        user_product_id_label.setText(String.valueOf(product_list.get(idx).getId()));
        user_product_name_label.setText(product_list.get(idx).getName());
        user_product_type_label.setText(product_list.get(idx).getType());
        user_product_category_label.setText(product_list.get(idx).getCategory());
        user_product_producer_label.setText(product_list.get(idx).getProducer());
        user_product_distributor_label.setText(product_list.get(idx).getDistributor());
        user_product_country_label.setText(product_list.get(idx).getCountry());
        user_product_container_label.setText(product_list.get(idx).getContainer());
        user_product_volume_label.setText(String.valueOf(product_list.get(idx).getVolume()));
        user_product_alcohol_label.setText(String.valueOf(product_list.get(idx).getAlcohol()));
        user_product_returnable_label.setText((product_list.get(idx).getReturnable()) ? "Yes" : "Nope");
    }
}
