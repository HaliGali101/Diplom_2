package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import stellarburgers.pojo.UpdateUser;
import stellarburgers.pojo.User;
import stellarburgers.testdatagenerator.GetUpdateUserData;
import stellarburgers.testdatagenerator.GetUserData;
import stellarburgers.teststeps.Steps;

import static org.hamcrest.CoreMatchers.equalTo;

public class UpdateUserDataTests extends SetUp{

    private boolean isUser;
    private User user;

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void updateUserPositive() {
        isUser = true;

        user = GetUserData.getUser("userAllData");
        ValidatableResponse response = Steps.createTestUser(user)
                .statusCode(200);

        String token = response.extract().path("accessToken").toString();

        UpdateUser updateUser = GetUpdateUserData.getUpdateUserData();
        Steps.updateUserData(token, updateUser)
                .statusCode(200);

        user.setEmail(updateUser.getEmail());
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void updateUserWithoutAuthorizeNegative() {
        isUser = true;

        user = GetUserData.getUser("userAllData");

        Steps.createTestUser(user)
                .statusCode(200);

        Steps.updateUserData("", GetUpdateUserData.getUpdateUserData())
                .statusCode(401)
                .assertThat().body("success", equalTo(false))
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @After
    public void cleanUp() {
        if(isUser) {
            Steps.deleteTestUser(user);
        }
    }

}
