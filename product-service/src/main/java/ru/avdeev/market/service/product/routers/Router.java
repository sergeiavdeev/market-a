package ru.avdeev.market.service.product.routers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import ru.avdeev.market.dto.ErrorDto;
import ru.avdeev.market.dto.PageDto;
import ru.avdeev.market.dto.ProductDto;
import ru.avdeev.market.exceptions.ApiException;
import ru.avdeev.market.service.product.routers.handlers.ProductHandler;


import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@EnableWebFlux
public class Router implements WebFluxConfigurer {

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    beanClass = ProductHandler.class,
                    method = RequestMethod.GET,
                    beanMethod = "get",
                    operation = @Operation(operationId = "get", description = "Get pageable product list",
                            responses = {
                                    @ApiResponse(responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = PageDto.class))
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.QUERY, name = "page", schema = @Schema(implementation = Integer.class)),
                                    @Parameter(in = ParameterIn.QUERY, name = "size", schema = @Schema(implementation = Integer.class)),
                                    @Parameter(in = ParameterIn.QUERY, name = "title", schema = @Schema(implementation = String.class)),
                                    @Parameter(in = ParameterIn.QUERY, name = "minPrice", schema = @Schema(implementation = BigDecimal.class)),
                                    @Parameter(in = ParameterIn.QUERY, name = "maxPrice", schema = @Schema(implementation = BigDecimal.class)),
                                    @Parameter(in = ParameterIn.QUERY, name = "sort", schema = @Schema(implementation = String.class))
                            }
                    )
            ),
            @RouterOperation(path = "/api/v1/{id}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    beanClass = ProductHandler.class,
                    method = RequestMethod.GET,
                    beanMethod = "getById",
                    operation = @Operation(operationId = "getById", description = "Get product by Id",
                            responses = {
                                    @ApiResponse(responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", schema = @Schema(implementation = UUID.class))
                            }
                    )
            ),
            @RouterOperation(path = "/api/v1",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    beanClass = ProductHandler.class,
                    method = RequestMethod.POST,
                    beanMethod = "add",
                    operation = @Operation(operationId = "add", description = "Create new product",
                            responses = {
                                    @ApiResponse(responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                                    ),
                                    @ApiResponse(responseCode = "400",
                                            description = "bad request",
                                            content = @Content(schema = @Schema(implementation = ErrorDto.class))
                                    )
                            },
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ProductDto.class))),
                            security = @SecurityRequirement(name = "jwt")
                    )
            ),
            @RouterOperation(path = "/api/v1",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    beanClass = ProductHandler.class,
                    method = RequestMethod.PUT,
                    beanMethod = "update",
                    operation = @Operation(operationId = "update", description = "Update existing product",
                            security = @SecurityRequirement(name = "jwt"),
                            responses = {
                                    @ApiResponse(responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                                    ),
                                    @ApiResponse(responseCode = "404",
                                            description = "not found",
                                            content = @Content(schema = @Schema(implementation = ErrorDto.class))
                                    ),
                                    @ApiResponse(responseCode = "400",
                                            description = "bad request",
                                            content = @Content(schema = @Schema(implementation = ErrorDto.class))
                                    )
                            },
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = ProductDto.class)))
                    )
            ),
            @RouterOperation(path = "/api/v1/{id}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    beanClass = ProductHandler.class,
                    method = RequestMethod.DELETE,
                    beanMethod = "delete",
                    operation = @Operation(operationId = "delete", description = "Delete product",
                            security = @SecurityRequirement(name = "jwt"),
                            responses = {
                                    @ApiResponse(responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema())
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", schema = @Schema(implementation = UUID.class))
                            }
                    )
            ),
            @RouterOperation(path = "/api/v1/{id}/file",
                    produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = "multipart/form-data",
                    beanClass = ProductHandler.class,
                    method = RequestMethod.POST,
                    beanMethod = "fileUpload",
                    operation = @Operation(operationId = "fileUpload", description = "Upload file for existing product",
                            //security = @SecurityRequirement(name = "jwt"),
                            responses = {
                                    @ApiResponse(responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema())
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", schema = @Schema(implementation = UUID.class))
                            },
                            requestBody = @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schemaProperties = {
                                            @SchemaProperty(name = "file", schema = @Schema(type = "string", format = "binary")),
                                            @SchemaProperty(name = "order", schema = @Schema(type = "string", format = "string")),
                                            @SchemaProperty(name = "descr", schema = @Schema(type = "string", format = "string"))
                                    }))
                    )
            ),
            @RouterOperation(path = "/api/v1/{id}/file/{fileId}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    beanClass = ProductHandler.class,
                    method = RequestMethod.DELETE,
                    beanMethod = "fileDelete",
                    operation = @Operation(operationId = "fileDelete", description = "Delete file from product",
                            security = @SecurityRequirement(name = "jwt"),
                            responses = {
                                    @ApiResponse(responseCode = "200",
                                            description = "successful operation",
                                            content = @Content(schema = @Schema())
                                    )
                            },
                            parameters = {
                                    @Parameter(in = ParameterIn.PATH, name = "id", schema = @Schema(implementation = UUID.class)),
                                    @Parameter(in = ParameterIn.PATH, name = "fileId", schema = @Schema(implementation = UUID.class))
                            }
                    )
            )

    })
    public RouterFunction<ServerResponse> productRouter(ProductHandler handler) {

        return route()
                .path("/api/v1", b -> b
                        .GET("", handler::get)
                        .GET("/{id}", handler::getById)
                        .POST("", handler::add)
                        .PUT("", handler::update)
                        .DELETE("/{id}", handler::delete)
                        .POST("/{id}/file", handler::fileUpload)
                        .DELETE("/{id}/file/{fileId}", handler::fileDelete)
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
