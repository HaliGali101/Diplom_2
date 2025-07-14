package stellarburgers.pojo;
import lombok.Getter;

@Getter
public class Authorize {

    private final String email;
    private final String password;

    public Authorize(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
