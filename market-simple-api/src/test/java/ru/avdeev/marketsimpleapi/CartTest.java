package ru.avdeev.marketsimpleapi;

import org.junit.jupiter.api.*;
import ru.avdeev.marketsimpleapi.entities.Product;
import ru.avdeev.marketsimpleapi.model.Cart;
import ru.avdeev.marketsimpleapi.model.CartItem;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartTest {

    private final static Cart cart = new Cart();
    private final static UUID id1 = UUID.randomUUID();
    private final static UUID id2 = UUID.randomUUID();

    @Test
    @Order(1)
    public void addTest() {

        cart.add(new Product(id1, "p1", BigDecimal.valueOf(500)));
        cart.add(new Product(id2, "p2", BigDecimal.valueOf(500)));
        cart.add(new Product(id1, "p1", BigDecimal.valueOf(500)));

        Assertions.assertEquals(cart.getTotal(), BigDecimal.valueOf(1500));

        Optional<CartItem> item = cart.getItems().stream()
                .filter(el -> el.getProductId().equals(id1))
                .findFirst();

        Assertions.assertTrue(item.isPresent());
        Assertions.assertEquals(item.get().getQuantity(), 2);
    }

    @Test
    @Order(5)
    public void changeQuantityTest() {

        cart.changeQuantity(id1, 2);

        Assertions.assertEquals(cart.getTotal(), BigDecimal.valueOf(2500));
    }

    @Test
    @Order(10)
    public void deleteTest() {

        cart.delete(id2);

        Assertions.assertEquals(cart.getTotal(), BigDecimal.valueOf(2000));
    }

    @Test
    @Order(100)
    public void clearTest() {
        cart.clear();
        Assertions.assertEquals(cart.getItems().size(), 0);
        Assertions.assertEquals(cart.getTotal(), BigDecimal.ZERO);
    }
}
