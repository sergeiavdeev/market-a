package ru.avdeev.market.service.order.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import ru.avdeev.market.dto.ErrorDto;
import ru.avdeev.market.exceptions.ApiException;
import ru.avdeev.market.service.order.router.handlers.OrderHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class Router {

    @Bean
    public RouterFunction<ServerResponse> orderRouter(OrderHandler handler) {

        return route()
                .path("/api/v1", b -> b
                        .GET("", handler::getPageByUserId)
                        .GET("/{id}", handler::getById)
                        .POST(handler::add)
                        .DELETE("/{id}", handler::delete)
                        .filter(apiExceptionHandler())
                ).build();
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> apiExceptionHandler() {

        return (request, next) -> next.handle(request).log()
                .onErrorResume(ApiException.class,
                        e -> ServerResponse
                                .status(e.getStatus())
                                .bodyValue(new ErrorDto(e.getStatus(), e.getMessage()))
                )
                .onErrorResume(ServerWebInputException.class,
                        e -> ServerResponse
                                .status(HttpStatus.BAD_REQUEST)
                                .bodyValue(new ErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage()))
                );
    }
}
