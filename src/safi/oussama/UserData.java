package safi.oussama;

public class UserData {

    private static UserData INSTANCE;
    private User user;

    public UserData() {
    }

    public UserData getInstance(){

        if(INSTANCE == null) {
            INSTANCE = new UserData();
        }
        return INSTANCE;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
