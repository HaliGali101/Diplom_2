package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.pojo.User;
import stellarburgers.testdatagenerator.GetUserData;
import stellarburgers.teststeps.Steps;

import static org.hamcrest.CoreMatchers.*;

public class AuthorizeTests extends SetUp {

    private User user;

    @Before
    public void setUp() {
        user = GetUserData.getUser("userAllData");
        Steps.createTestUser(user);
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void authorizeUserPositiveTest() {
        Steps.authorizeTestUser(user.getEmail(), user.getPassword())
                .statusCode(200)
                .assertThat().body("success", equalTo(true))
                .assertThat().body("user.email", equalTo(user.getEmail()))
                .assertThat().body("user.name", equalTo(user.getName()))
                .assertThat().body("accessToken", startsWith("Bearer "))
                .assertThat().body("refreshToken", notNullValue());
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void authorizeUserWithIncorrectLogin() {
        Steps.authorizeTestUser(user.getEmail() + "1", user.getPassword())
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void authorizeUserWithIncorrectPassword() {
        Steps.authorizeTestUser(user.getEmail() + "1", user.getPassword())
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void cleanUp() {
        Steps.deleteTestUser(user)
                .statusCode(202);
    }

}
