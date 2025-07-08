package stellarBurgers;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import stellarBurgers.pojo.User;
import stellarBurgers.pojo.ingredientsList.IngredientData;
import stellarBurgers.testDataGenerator.GetUserData;
import stellarBurgers.testSteps.Steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.emptyString;

public class OrdersTests extends SetUp {

    private List<IngredientData> ingredientListTestData;

    @Before
    public void getIngredientListForTest() {
        ingredientListTestData = Steps.getRandomIngredients(Steps.getIngredientListData());
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithAuthorize() {
        User user = GetUserData.getUser("userAllData");
        String token = Methods.postCreateUser(user)
                .statusCode(200)
                .extract().path("accessToken").toString();

        List<String> idsList  = Steps.getIngredientsIds(ingredientListTestData);

        Steps.createOrder(idsList, token)
                .statusCode(200)
                .assertThat()
                .body("name", notNullValue())
                .body("name", not(emptyString()))
                .body("order.number", notNullValue())
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthorize() {
        List<String> idsList  = Steps.getIngredientsIds(ingredientListTestData);

        Steps.createOrder(idsList,"")
                .statusCode(200)
                .assertThat()
                .body("name", notNullValue())
                .body("name", not(emptyString()))
                .body("order.number", notNullValue())
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients() {
        List<String> ingredientListTestData = new ArrayList<>();
        Steps.createOrder(ingredientListTestData,"")
                .statusCode(400)
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderInvalidateIngredientsHash() {
        List<String> idsList  = Steps.getIngredientsIds(ingredientListTestData);
        idsList.add(String.valueOf(new Random().nextInt(10)));

        Steps.createOrder(idsList,"")
                .statusCode(500);
    }

}
