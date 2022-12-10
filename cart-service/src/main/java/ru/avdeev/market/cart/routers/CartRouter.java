package ru.avdeev.market.cart.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.avdeev.market.cart.exceptions.UnauthorizedException;
import ru.avdeev.market.dto.ErrorDto;
import ru.avdeev.market.exceptions.ApiException;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class CartRouter implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> productRouter(CartHandler handler) {

        return route()
                .path("/api/v1/", b -> b
                        .GET(handler::get)
                        .POST("/add", handler::addItem)
                        .POST("/clear", handler::clear)
                        .POST("/delete", handler::delete)
                        .POST("/change", handler::change)
                        .POST(handler::set)
                        .filter(apiExceptionHandler())
                ).build();
    }

    private ServerRequest setSecurityContext(ServerRequest request) {

        if (request.headers().firstHeader("username") == null
        || request.headers().firstHeader("roles") == null)
            throw new UnauthorizedException();
        return request;
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> apiExceptionHandler() {

        return (request, next) -> next.handle(request).log()
                .onErrorResume(ApiException.class,
                        e -> ServerResponse
                                .status(e.getStatus())
                                .bodyValue(new ErrorDto(e.getStatus(), e.getMessage()))
                );
    }
}
