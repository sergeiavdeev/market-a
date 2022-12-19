package ru.avdeev.market.service.product.routers.handlers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.avdeev.market.dto.PageDto;
import ru.avdeev.market.dto.ProductDto;
import ru.avdeev.market.service.product.services.ProductService;


import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Product")
public class ProductHandler {

    private final ProductService productService;

    public Mono<ServerResponse> get(ServerRequest request) {

        return ServerResponse.ok()
                .body(productService.getPage(
                        request.queryParam("page"),
                        request.queryParam("size"),
                        request.queryParam("title"),
                        request.queryParam("minPrice"),
                        request.queryParam("maxPrice"),
                        request.queryParam("sort")
                ), PageDto.class);

    }

    public Mono<ServerResponse> getById(ServerRequest request) {

        UUID id = UUID.fromString(request.pathVariable("id"));
        return productService.getById(id)
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(ProductDto.class)
                .flatMap(product -> productService.add(product))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> update(ServerRequest request) {

        return request.bodyToMono(ProductDto.class).log()
                .flatMap(product -> productService.update(product))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> delete(ServerRequest request) {

        return productService.delete(UUID.fromString(request.pathVariable("id")))
                .then(ServerResponse.ok().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> fileUpload(ServerRequest request) {

        return request.multipartData()
                .flatMap(parts -> {
                    FilePart file = (FilePart) parts.toSingleValueMap().get("file");
                    Optional<FormFieldPart> orderPart = Optional.ofNullable((FormFieldPart) parts.toSingleValueMap().get("order"));
                    Optional<FormFieldPart> descrPart = Optional.ofNullable((FormFieldPart) parts.toSingleValueMap().get("descr"));

                    Optional<Integer> order = Optional.of(Integer.parseInt(orderPart.map(FormFieldPart::value).orElse("0")));
                    Optional<String> descr = Optional.of(descrPart.map(FormFieldPart::value).orElse(""));

                    return productService.saveFile(file, request.pathVariable("id"), order, descr);
                })
                .flatMap(fileEntity -> ServerResponse.ok().bodyValue(fileEntity));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> fileDelete(ServerRequest request) {
        return ServerResponse.ok()
                .body(productService.fileDelete(UUID.fromString(request.pathVariable("id")), UUID.fromString(request.pathVariable("fileId"))), Void.class);
    }


}
