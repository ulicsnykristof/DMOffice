package Main;

import Database.Database;
import java.util.ArrayList;
import java.util.List;

public class User {
    int id;
    String uid;
    String pwd;
    String email;
    String firstname;
    String lastname;
    String access;
    String position;
    String reg_by;
    String reg_date;

    public User(String uid, String pwd){
        this.uid = uid;
        this.pwd = pwd;
    }

    public User(String uid, String pwd, String email, String firstname, String lastname, String access,
                String position, String reg_by, String reg_date){
        this.uid = uid;
        this.pwd = pwd;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.access = access;
        this.position = position;
        this.reg_by = reg_by;
        this.reg_date = reg_date;
    }

    public User(int id, String uid, String email, String firstname, String lastname, String access,
                String position, String reg_by, String reg_date){
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.access = access;
        this.position = position;
        this.reg_by = reg_by;
        this.reg_date = reg_date;
    }


    public void addToDatabase(){
        Database.insertUser(this);
    }

    public static List<User> queryAllUsers(){
        return Database.queryAllUser();
    }

    public boolean uidAlreadyExits(){
        boolean ae = false;
        List<User> ul = queryAllUsers();
        for (User u: ul){
            if(u.getUid().equals(this.uid)){
                ae = true;
                break;
            }
        }
        return ae;
    }

    public void update(String new_uid, String new_email, String new_fn, String new_ln, String new_pos){
        Database.updateUser(this, new_uid, new_email, new_fn, new_ln, new_pos);
    }

    public void delete(){
        Database.deleteUser(this);
    }

    public boolean PasswordCheckUser(){
        boolean valid = false;
        List<User> ul = Database.queryAllUser();
        for (User u: ul) {
            if(u.getUid().equals(this.uid) && u.getPosition().equals("User")){
                if(Database.queryPwd(this.uid).equals(this.pwd)){
                    valid = true;
                    break;
                }
            }
        }
        return valid;
    }

    public boolean PasswordCheckAdmin(){
        boolean valid = false;
        List<User> ul = Database.queryAllUser();
        for (User u: ul) {
            if(u.getUid().equals(this.uid) && u.getPosition().equals("Admin")){
                System.out.println(u.getUid() + " " + this.uid + " " + u.getAccess());
                if(Database.queryPwd(this.uid).equals(this.pwd)){
                    valid = true;
                    break;
                }
            }
        }
        return valid;
    }

    @Override
    public String toString(){
        return uid + " " + pwd + " " + email + " " + firstname + " " + lastname + " " + access + " " +
                " " + position + " " + reg_by + " " + reg_date;
    }

    public int getId(){ return id; }
    public String getUid(){ return uid; }
    public String getPwd(){ return pwd; }
    public String getEmail(){ return email; }
    public String getFirstname(){ return firstname; }
    public String getLastname(){ return lastname; }
    public String getAccess(){ return access; }
    public String getPosition(){ return position; }
    public String getRegby(){ return reg_by; }
    public String getRegdate(){ return reg_date; }
}
