package stellarburgers.testdatagenerator;

import com.github.javafaker.Faker;
import stellarburgers.pojo.User;

public class GetUserData {

    private static final Faker faker = new Faker();
    private static String email;
    private static String password;
    private static String name;

    public static User getUser(String config) {

        switch (config) {
            case "userAllData":
                email = faker.internet().emailAddress();
                password = faker.internet().password();
                name = faker.name().username();
                break;
            case "userWithoutEmail":
                email = null;
                password = faker.internet().password();
                name = faker.name().username();
                break;
            case "userWithoutPassword":
                email = faker.internet().emailAddress();
                password = null;
                name = faker.name().username();
                break;
            case "userWithoutName":
                email = faker.internet().emailAddress();
                password = faker.internet().password();
                name = null;
                break;
            default:
                throw new IllegalArgumentException(String.format("Конфигурация %s пользовательских данных - не найдена", config));
        }

        return new User(email, password, name);
    }
}
