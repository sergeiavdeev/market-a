package ru.avdeev.marketsimpleapi;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.avdeev.marketsimpleapi.config.JwtUtil;
import ru.avdeev.marketsimpleapi.dto.PageResponse;
import ru.avdeev.marketsimpleapi.dto.UserDto;
import ru.avdeev.marketsimpleapi.entities.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    @Autowired
    WebTestClient client;

    @Autowired
    JwtUtil jwtUtil;

    private final String apiUrl = "/api/v1/product";
    private static Product createdProduct;
    private static String token;

    @Test
    @Order(1)
    public void createProduct() {

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");

        UserDto user = new UserDto();
        user.setUsername("admin");
        user.setRoles(roles);

        token = jwtUtil.generateToken(user);

        createdProduct = new Product();
        createdProduct.setTitle("Тест");
        createdProduct.setPrice(BigDecimal.valueOf(600));

        client.post().uri(apiUrl)
                .body(BodyInserters.fromValue(createdProduct))
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class).value(product -> createdProduct.setId(product.getId()));
    }

    @Test
    @Order(5)
    public void testGetPage() {
        client.get().uri(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageResponse.class).value(page -> {
                    assertThat(page.getSize()).isEqualTo(5);
                    assertThat(page.getNumber()).isEqualTo(1);
                });
    }

    @Test
    @Order(5)
    public void testGetById() {
        client.get().uri(uriBuilder -> uriBuilder
                        .path(apiUrl + "/{id}")
                        .build(createdProduct.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class).value(product -> assertThat(product.getId())
                        .isEqualTo(createdProduct.getId()));
    }

    @Test
    @Order(5)
    public void testUpdate() {

        createdProduct.setPrice(BigDecimal.valueOf(700));
        client.put().uri(apiUrl)
                .body(BodyInserters.fromValue(createdProduct))
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Product.class).value(product -> assertThat(product.getPrice())
                        .isEqualTo(BigDecimal.valueOf(700)));
    }

    @Test
    @Order(5)
    public void testUNAUTHORIZED() {

        createdProduct.setPrice(BigDecimal.valueOf(700));
        client.put().uri(apiUrl)
                .body(BodyInserters.fromValue(createdProduct))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Order(10)
    public void deleteProduct() {

        client.delete().uri(uriBuilder -> uriBuilder
                        .path(apiUrl + "/{id}")
                        .build(createdProduct.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .exchange()
                .expectStatus().isOk();
    }
}
