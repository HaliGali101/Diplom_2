package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import stellarburgers.pojo.User;
import stellarburgers.testdatagenerator.GetUserData;
import stellarburgers.teststeps.Steps;

import static org.hamcrest.CoreMatchers.*;

public class CreateUserTests extends SetUp {

    private User user;
    private boolean isUser;

    @Test
    @DisplayName("Создать уникального пользователя")
    public void createUserPositive() {
        isUser = true;

        user = GetUserData.getUser("userAllData");

        Steps.createTestUser(user)
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(user.getEmail()))
                .assertThat().body("user.name", equalTo(user.getName()))
                .assertThat().body("accessToken", startsWith("Bearer "))
                .assertThat().body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Создать пользователя, который уже зарегистрирован")
    public void createUserDuplicate() {
        isUser = true;

        user = GetUserData.getUser("userAllData");

        Steps.createTestUser(user)
                .statusCode(200);

        Methods.postCreateUser(user)
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создать пользователя и не заполнив поле Эл.почта")
    public void createUserWithoutEmail() {
        isUser = false;

        user = GetUserData.getUser("userWithoutEmail");

        Steps.createTestUser(user)
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создать пользователя и не заполнив поле Пароль")
    public void createUserWithoutPassword() {
        isUser = false;

        user = GetUserData.getUser("userWithoutPassword");

        Steps.createTestUser(user)
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создать пользователя и не заполнив поле Имя")
    public void createUserWithoutName() {
        isUser = false;

        user = GetUserData.getUser("userWithoutName");
        Steps.createTestUser(user)
                .statusCode(403)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void cleanUp() {
        if(isUser) {
            Steps.deleteTestUser(user)
                    .statusCode(202);
        }
    }

}
