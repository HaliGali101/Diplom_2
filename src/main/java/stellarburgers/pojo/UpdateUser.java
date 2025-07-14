package stellarburgers.pojo;

import lombok.Getter;

@Getter
public class UpdateUser {

    private final String email;
    private final String name;

    public UpdateUser(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
