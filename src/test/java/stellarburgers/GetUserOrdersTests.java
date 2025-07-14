package stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.pojo.User;
import stellarburgers.testdatagenerator.GetUserData;
import stellarburgers.teststeps.Steps;

import java.util.List;

import static org.hamcrest.Matchers.*;

public class GetUserOrdersTests extends SetUp{

    private User user;
    private String token;
    private List<String> ingredientsIds;

    @Before
    public void setUp() {
        user = GetUserData.getUser("userAllData");

        ValidatableResponse createUser = Steps.createTestUser(user);

        token = createUser.extract().path("accessToken").toString();

        ingredientsIds = Steps.getIngredientsIds(Steps.getRandomIngredients(Steps.getIngredientListData()));
    }

    @Test
    @DisplayName("Получить заказ авторизованного пользователя")
    public void getUserOrdersWithAuthorizationOneOrder() {

        ValidatableResponse createOrder1 = Steps.createOrder(ingredientsIds, token);
        int orderNumber1 = createOrder1.extract().path("order.number");

        Steps.getOrders(token)
                .statusCode(200)
                .assertThat()
                .body("orders.size()", equalTo(1))
                .body("orders.number", hasItem(orderNumber1))
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
    }

    @Test
    @DisplayName("Получить заказы авторизованного пользователя")
    public void getUserOrdersWithAuthorizationTwoOrders() {

        ValidatableResponse createOrder1 = Steps.createOrder(ingredientsIds, token);
        int orderNumber1 = createOrder1.extract().path("order.number");

        ValidatableResponse createOrder2 = Steps.createOrder(ingredientsIds, token);
        int orderNumber2 = createOrder2.extract().path("order.number");

        Steps.getOrders(token)
                .statusCode(200)
                .assertThat()
                .body("orders.size()", equalTo(2))
                .body("orders.number", hasItems(orderNumber1, orderNumber2))
                .body("total", notNullValue())
                .body("totalToday", notNullValue());
    }

    @Test
    @DisplayName("Получить заказы неавторизованного пользователя")
    public void getUserOrdersWithoutAuthorization() {

        Steps.createOrder(ingredientsIds, token);

        Steps.getOrders("")
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void cleanUp() {
        Steps.deleteTestUser(user)
                .statusCode(202);
    }
}
