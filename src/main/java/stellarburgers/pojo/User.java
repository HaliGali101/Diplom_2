package stellarburgers.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User {

    @Setter
    private String email;
    private final String password;
    private final String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

}
