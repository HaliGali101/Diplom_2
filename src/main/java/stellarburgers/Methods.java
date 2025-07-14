package stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarburgers.pojo.Authorize;
import stellarburgers.pojo.Order;
import stellarburgers.pojo.UpdateUser;
import stellarburgers.pojo.User;

import static io.restassured.RestAssured.given;

public class Methods {

    private static final String CREATE_USER_PATH = "/api/auth/register";
    private static final String LOGIN_USER_PATH = "/api/auth/login";
    private static final String UPDATE_USER_PATH = "/api/auth/user";
    private static final String CREATE_ORDERS_PATH = "/api/orders";
    private static final String GET_INGREDIENTS_PATH = "/api/ingredients";
    private static final String GET_ORDERS_PATH = "/api/orders";

    @Step("Создать пользователя")
    public static ValidatableResponse postCreateUser(User user) {

        return given()
                .header("Content-type", "application/json")
                .when()
                .body(user)
                .log().all()
                .post(CREATE_USER_PATH)
                .then()
                .log().all();

    }

    @Step("Удалить пользователя")
    public static ValidatableResponse deleteUser(String accessToken) {

        return given()
                .header("Authorization", accessToken)
                .when()
                .log().all()
                .delete(UPDATE_USER_PATH)
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
                .post(LOGIN_USER_PATH)
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
                .patch(UPDATE_USER_PATH)
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
                .post(CREATE_ORDERS_PATH)
                .then()
                .log().all();

    }

    public static ValidatableResponse getIngredients() {

        return given()
                .header("Content-type", "application/json")
                .when()
                .log().all()
                .get(GET_INGREDIENTS_PATH)
                .then()
                .log().all();

    }

    public static ValidatableResponse getOrders(String token) {

        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .log().all()
                .get(GET_ORDERS_PATH)
                .then()
                .log().all();

    }



}
