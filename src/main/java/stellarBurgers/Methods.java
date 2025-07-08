package stellarBurgers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarBurgers.pojo.Authorize;
import stellarBurgers.pojo.Order;
import stellarBurgers.pojo.UpdateUser;
import stellarBurgers.pojo.User;

import static io.restassured.RestAssured.given;

public class Methods {

    private static final String createUserPath = "/api/auth/register";
    private static final String loginUserPath = "/api/auth/login";
    private static final String updateUserPath = "/api/auth/user";
    private static final String createOrdersPath = "/api/orders";
    private static final String getIngredientsPath = "/api/ingredients";
    private static final String getOrdersPath = "/api/orders";

    @Step("Создать пользователя")
    public static ValidatableResponse postCreateUser(User user) {

        return given()
                .header("Content-type", "application/json")
                .when()
                .body(user)
                .log().all()
                .post(createUserPath)
                .then()
                .log().all();

    }

    @Step("Удалить пользователя")
    public static ValidatableResponse deleteUser(String accessToken) {

        return given()
                .header("Authorization", accessToken)
                .when()
                .log().all()
                .delete(updateUserPath)
                .then()
                .log().all();

    }

    @Step("Авторизовать пользователя")
    public static ValidatableResponse postLoginUser(Authorize auth) {

        return given()
                .header("Content-type", "application/json")
                .when()
                .body(auth)
                .log().all()
                .post(loginUserPath)
                .then()
                .log().all();

    }

    public static ValidatableResponse patchUpdateUser(String token,
                                                      UpdateUser updateUserData) {

        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .body(updateUserData)
                .log().all()
                .patch(updateUserPath)
                .then()
                .log().all();

    }

    public static ValidatableResponse postCreateOrder(String token, Order order) {

        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .body(order)
                .log().all()
                .post(createOrdersPath)
                .then()
                .log().all();

    }

    public static ValidatableResponse getIngredients() {

        return given()
                .header("Content-type", "application/json")
                .when()
                .log().all()
                .get(getIngredientsPath)
                .then()
                .log().all();

    }

    public static ValidatableResponse getOrders(String token) {

        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .log().all()
                .get(getOrdersPath)
                .then()
                .log().all();

    }



}
