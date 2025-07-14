package stellarburgers.testdatagenerator;

import com.github.javafaker.Faker;
import stellarburgers.pojo.UpdateUser;

public class GetUpdateUserData {

    private static final Faker faker = new Faker();

    public static UpdateUser getUpdateUserData() {
        String email = faker.internet().emailAddress();
        String name = faker.name().username();

        return new UpdateUser(email, name);
    }
}
