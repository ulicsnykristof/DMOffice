package Main;

import Database.Database;

import java.util.List;

public class Order {
    int id;
    String date;
    String status;
    String partner;
    int price;

    public Order(int id, String date, String status, String partner, int price){
        this.id = id;
        this.date = date;
        this.status = status;
        this.partner = partner;
        this.price = price;
    }

    public Order(String date, String status, String partner, int price){
        this.date = date;
        this.status = status;
        this.partner = partner;
        this.price = price;
    }

    public int getId(){return id;}
    public String getDate(){return date;}
    public String getStatus(){return status;}
    public String getPartner(){return partner;}
    public int getPrice(){return price;}

    public void setDate(String date){this.date = date;}
    public void setStatus(String status){this.status = status;}
    public void setPartner(String partner){this.partner = partner;}
    public void setPrice(int price){this.price = price;}

    public boolean addToDatabase(){return Database.insertOrder(this);}
    public int getId2(){
        List<Integer> il = Database.queryAllOid();
        int lidx = 0;
        for (Integer i: il) {
            lidx = i;
        }
        return lidx;
    }
}
