import java.util.ArrayList;
import java.util.HashMap;

public class User {
    protected String name;
    protected String password;
    protected String role;
    public static HashMap<String,String> userRoles=new HashMap<>();
    public static HashMap<String,String> userPasswords=new HashMap<>();
    public static ArrayList<User>allUsers=new ArrayList<>();

    public User(String name,String password,String role){
        this.password=password;
        this.name=name;
        this.role=role;

    }

    public static void createAccount(String name,String password,String confirmPassword,String role){
        User current=new User(name,password,role);
        allUsers.add(current);
        userRoles.put(name,role);
        userPasswords.put(name,password);


    }
    public static boolean login(String name,String password){
        if(!userPasswords.containsKey(name))return false;
        return userPasswords.get(name).equals(password);
    }
    public void deleteAccount(String password){
        if (this.password.equals(password)) {
            String name=this.name;
            allUsers.remove(this);
            userRoles.remove(name);
            userPasswords.remove(name);
        }
    }

    public static HashMap<String, String> getUserRoles() {
        return userRoles;
    }

    public static void setUserRoles(HashMap<String, String> userRoles) {
        User.userRoles = userRoles;
    }

    public static HashMap<String, String> getUserPasswords() {
        return userPasswords;
    }

    public static void setUserPasswords(HashMap<String, String> userPasswords) {
        User.userPasswords = userPasswords;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> allUsers) {
        User.allUsers = allUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
