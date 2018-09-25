package news.hacker.omnify.hackernews.constants;

public class Users {


    private String name, email;

    Users() {
    }

    public Users(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }
}
