package Database;

import Main.*;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static Connection connectToDatabase(){
        Connection con = null;
        String database = "";

        String url = null, user = null, password = null;

        try {
            InputStream is = Version.class.getResourceAsStream("../Other/config.txt");
            Reader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            int l = 0;
            while((line =((BufferedReader) r).readLine()) != null){
                l++;
                if(l == 3){ url = line; }
                if(l == 4){ user = line; }
                if(l == 5){ password = line; }
            }
            url = url.substring(5);
            user = user.substring(6);
            password = password.substring(10);

            r.close();
        }catch(IOException ex){
            System.err.println("Config file is missing");
        }


        //jdbc:mysql://127.0.0.1:3306/dmoffice?autoReconnect=true&useSSL=false
        //root
        //password

        try{
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database");
        }catch(SQLException ex){
            System.err.println("Error. Database unreachable.");
        }
        return con;
    }

    //////////////////// *** USER *** //////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Temporary solution, pls don't judge me
    public static String queryPwd(String uid){
        Connection conn = connectToDatabase();
        String pwd = "retek";
        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT user_pwd FROM users WHERE '" + uid + "' = user_uid";
            ResultSet r = stmt.executeQuery(sql);
            while(r.next()){
                pwd = r.getString("user_pwd");
            }

        }catch(SQLException ex){
            System.out.println("Error. Can't query from user");
        }
        return pwd;
    }

    public static boolean insertUser(User u){
        boolean valid = true;
        Connection conn = connectToDatabase();

        try{
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (user_uid, user_pwd, user_email, user_firstname, user_lastname," +
                    "user_position, user_access, user_registered, user_admin_registered) VALUES " +
                    "('" + u.getUid() + "','" + u.getPwd() + "','" + u.getEmail() + "','" + u.getFirstname() +
                    "','" + u.getLastname() + "','" + u.getPosition() + "','" + u.getAccess() + "','" + u.getRegdate() +
                    "','" + u.getRegby() + "');";
            stmt.executeUpdate(sql);

        }catch (SQLException ex){
            System.out.println("Error. Can't insert into user");
            valid = false;
        }

        return valid;
    }

    public static List<User> queryAllUser(){
        List<User> ul = new ArrayList<>();
        Connection conn = connectToDatabase();

        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users";
            ResultSet r = stmt.executeQuery(sql);

            while(r.next()){
                ul.add(new User(r.getInt("user_id"), r.getString("user_uid"),r.getString("user_email"),r.getString("user_firstname"),
                        r.getString("user_lastname"), r.getString("user_position"),r.getString("user_access"),
                        r.getString("user_registered"),r.getString("user_admin_registered")));
            }

        }catch(SQLException ex){
            System.out.println("Error. Can't query from user");
        }

        return ul;
    }

    public static boolean updateUser(User u, String new_uid, String new_email, String new_fn, String new_ln, String new_pos){
        boolean valid = true;
        Connection conn = connectToDatabase();
        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE users SET");
        if(!new_uid.equals("")){
            sb.append(" user_uid = ").append("'").append(new_uid).append("'");
            if(!new_email.equals("") || !new_fn.equals("") || !new_ln.equals("") || !new_pos.equals("")){
                sb.append(",");
            }
        }
        if(!new_email.equals("")){
            sb.append(" user_email = '").append(new_email).append("'");
            if(!new_fn.equals("") || !new_ln.equals("") || !new_pos.equals("")){
                sb.append(",");
            }
        }
        if(!new_fn.equals("")){
            sb.append(" user_firstname = '").append(new_fn).append("'");
            if(!new_ln.equals("") || !new_pos.equals("")){
                sb.append(",");
            }
        }
        if(!new_ln.equals("")){
            sb.append(" user_lastname = '").append(new_ln).append("'");
            if(!new_pos.equals("")){
                sb.append(",");
            }
        }
        if(!new_pos.equals("")){
            sb.append(" user_position = '").append(new_pos).append("'");
        }
        sb.append(" WHERE user_id =").append(u.getId());

        try{
            Statement stmt = conn.createStatement();

            String sql = sb.toString();
            System.out.println(sql);
            stmt.executeUpdate(sql);

        }catch (SQLException ex){
            System.err.println("Error. Can't update user");
            valid = false;
        }

        return valid;
    }

    public static boolean deleteUser(User u){
        boolean valid = true;
        Connection conn = connectToDatabase();

        try{
            Statement stmt = conn.createStatement();
            String sql = "DELETE from users WHERE user_id = " + u.getId()+ ";";
            stmt.executeUpdate(sql);

        }catch (SQLException ex){
            System.out.println("Error. Can't delete from user");
            valid = false;
        }

        return valid;
    }

    ///////////////////// *** PRODUCT *** //////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean insertProduct(Product p){
        boolean valid = true;
        Connection conn = connectToDatabase();

        try{
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO product(p_name, p_type, p_category, p_producer, p_distributor," +
                    "p_purchase_price, p_country, p_container, p_volume, p_alcohol, p_returnable) VALUES " +
                    "('" + p.getName() + "','" + p.getType() + "','" + p.getCategory() + "','" + p.getProducer() +
                    "','" + p.getDistributor() + "','" + p.getPrice() + "','" + p.getCountry() + "','" + p.getContainer() + "','" + p.getVolume() +
                    "','" + p.getAlcohol() + "','" + (p.getReturnable() ? 1 : 0) + "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);

        }catch (SQLException ex){
            System.err.println("Error. Can't insert into product");
            valid = false;
        }

        return valid;
    }

    public static List<Product> queryAllProduct(){
        List<Product> pl = new ArrayList<>();
        Connection conn = connectToDatabase();

        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM product";
            ResultSet r = stmt.executeQuery(sql);

            while(r.next()){
                pl.add(new Product(r.getInt("p_id"), r.getString("p_name"),r.getString("p_type"),r.getString("p_category"),
                        r.getString("p_producer"), r.getString("p_distributor"),r.getInt("p_purchase_price"), r.getString("p_country"),
                        r.getString("p_container"),r.getInt("p_volume"), r.getDouble("p_alcohol"), r.getBoolean("p_returnable")));
            }

        }catch(SQLException ex){
            System.out.println("Error. Can't query from user");
        }

        return pl;
    }

    public static boolean deleteProduct(Product p){
        boolean valid = true;
        Connection conn = connectToDatabase();

        try{
            Statement stmt = conn.createStatement();
            String sql = "DELETE from product WHERE p_id = " + p.getId()+ ";";
            stmt.executeUpdate(sql);
        }catch(SQLException ex){
            System.err.println("Error. Can't delete from product.");
            valid = false;
        }

        return valid;
    }

    ///////////////////// ORDER /////////////////////////

    public static boolean insertOrder(Order o){
        boolean valid = true;
        Connection conn = connectToDatabase();

        try{
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO orders (o_order_date, o_status, o_partner, o_price) VALUES " +
                    "('" + o.getDate() + "','" + o.getStatus() + "','" + o.getPartner() + "','" + o.getPrice() + "');";
            stmt.executeUpdate(sql);

        }catch (SQLException ex){
            System.err.println("Error. Can't insert into order");
            valid = false;
        }

        return valid;
    }

    public static boolean insertOrderPiece(OrderPiece op, int oid){
        boolean valid = true;
        Connection conn = connectToDatabase();

        try{
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO orders_h (p_id, quantity, o_id) VALUES " +
                    "('" + op.getProduct().getId() + "','" + op.getQuantity() + "','" + oid +  "');";
            stmt.executeUpdate(sql);

        }catch (SQLException ex){
            System.err.println("Error. Can't insert into order_h");
            valid = false;
        }

        return valid;
    }

    public static List<Integer> queryAllOid(){
        List<Integer> ol = new ArrayList<>();
        Connection conn = connectToDatabase();

        try{
            Statement stmt = conn.createStatement();
            String sql = "SELECT o_id FROM orders";
            ResultSet r = stmt.executeQuery(sql);

            while(r.next()){
                ol.add(r.getInt("o_id"));
            }

        }catch(SQLException ex){
            System.out.println("Error. Can't query from user");
        }

        return ol;
    }

}
