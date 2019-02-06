package Main;

import Database.Database;
import java.util.ArrayList;
import java.util.List;

public class Product {

    int id;
    String name;
    String type;
    String category;
    String producer;
    String distributor;
    int price;
    String country;
    String container;
    int volume;
    double alcohol;
    boolean returnable;

    public Product (String name, String type, String category, String producer, String distributor, int price,
                    String country, String container, int volume, double alcohol, boolean returnable){
        this.name = name;
        this.type = type;
        this.category = category;
        this.producer = producer;
        this.distributor = distributor;
        this.price = price;
        this.country = country;
        this.container = container;
        this.volume = volume;
        this.alcohol = alcohol;
        this.returnable = returnable;
    }

    public Product (int id, String name, String type, String category, String producer, String distributor, int price, String country,
                    String container, int volume, double alcohol, boolean returnable) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.category = category;
        this.producer = producer;
        this.distributor = distributor;
        this.price = price;
        this.country = country;
        this.container = container;
        this.volume = volume;
        this.alcohol = alcohol;
        this.returnable = returnable;
    }


    public int getId(){return id;}
    public String getName(){return name;}
    public String getType(){return type;}
    public String getCategory(){return category;}
    public String getProducer(){return producer;}
    public String getDistributor(){return distributor;}
    public int getPrice() {return price;}
    public String getCountry(){return country;}
    public String getContainer(){return container;}
    public int getVolume(){return volume;}
    public double getAlcohol(){return alcohol;}
    public boolean getReturnable(){return returnable;}

    public boolean addToDatabase(){return Database.insertProduct(this);}

    public static List<Product> queryAllProduct(){return Database.queryAllProduct();}

    public boolean delete(){return Database.deleteProduct(this);}

}
