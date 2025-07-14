package stellarburgers.pojo;

import lombok.Getter;

import java.util.List;

@Getter
public class Order {

    private final List<String> ingredients;

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
