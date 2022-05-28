package Model;

/**
 * user model
 * @author Isabelle Matthews
 */

public class user {
    private int userID;
    private String userName;
    private String password;


    public user(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }


    //USER ID
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
//USERNAME
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
//PASSWORD
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return (userName);
    }
}

