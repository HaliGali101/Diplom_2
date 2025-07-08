package stellarBurgers.testSteps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import stellarBurgers.Methods;
import stellarBurgers.pojo.Authorize;
import stellarBurgers.pojo.Order;
import stellarBurgers.pojo.UpdateUser;
import stellarBurgers.pojo.User;
import stellarBurgers.pojo.ingredientsList.IngredientData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Steps {

    @Step("Получить лист ингредиентов")
    public static List<IngredientData> getIngredientListData() {
        return Methods.getIngredients()
                .extract().body().jsonPath().getList("data", IngredientData.class);
    }

    @Step("Выбрать случайные ингредиенты из списка")
    public static List<IngredientData> getRandomIngredients(List<IngredientData> ingredientsList) {
        Random random = new Random();
        int count = random.nextInt(ingredientsList.size());
        System.out.println(count);

        List<IngredientData> listData = new ArrayList<>(ingredientsList);

        Collections.shuffle(listData);
        return listData.stream()
                .limit(count)
                .collect(Collectors.toList());
    }

    @Step("Получить лист идентификаторов ингредиентов")
    public static List<String> getIngredientsIds(List<IngredientData> selectedIngredientList) {
        return selectedIngredientList.stream()
                .map(IngredientData::get_id)
                .collect(Collectors.toList());
    }

    @Step("Создать заказ")
    public static ValidatableResponse createOrder(List<String> testData, String token) {
        Order order = new Order(testData);

        return Methods.postCreateOrder(token, order);
    }

    @Step("Создать тестового пользователя")
    public static ValidatableResponse createTestUser(User user) {
        return Methods.postCreateUser(user);
    }

    @Step("Авторизовать пользователя")
    public static ValidatableResponse authorizeTestUser(String email, String password) {
        Authorize auth = new Authorize(email, password);
        return Methods.postLoginUser(auth);
    }

    @Step("Удалить тестового пользователя")
    public static ValidatableResponse deleteTestUser(User user) {
        String accessToken = Methods.postLoginUser(new Authorize(user.getEmail(), user.getPassword()))
                .extract().path("accessToken").toString();
        return Methods.deleteUser(accessToken);
    }

    @Step("Обновить данные о пользователе")
    public static ValidatableResponse updateUserData(String token, UpdateUser updateUserdata) {
        return Methods.patchUpdateUser(token, updateUserdata);
    }

    @Step("Получить заказы пользователя")
    public static ValidatableResponse getOrders(String token) {
        return Methods.getOrders(token);
    }
}
