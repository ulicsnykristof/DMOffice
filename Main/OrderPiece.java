package Main;

import Database.Database;

import java.util.List;

public class OrderPiece {
    Product product;
    int quantity;

    public OrderPiece(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public OrderPiece(Product product, int quantity, int orderid){
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct(){return product;}
    public int getQuantity(){return quantity;}


    public int getPrice(){return product.price * quantity;}

    public boolean addToDatabase(int oid){return Database.insertOrderPiece(this, oid);}

}
