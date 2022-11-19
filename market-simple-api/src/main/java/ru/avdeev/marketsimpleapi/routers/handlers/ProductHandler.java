package ru.avdeev.marketsimpleapi.routers.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.dto.PageResponse;
import ru.avdeev.marketsimpleapi.dto.ProductCreateRequest;
import ru.avdeev.marketsimpleapi.entities.Product;
import ru.avdeev.marketsimpleapi.services.ProductService;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class ProductHandler {

    ProductService productService;

    public Mono<ServerResponse> get(ServerRequest request) {

        return ServerResponse.ok()
                .body(productService.getPage(
                        request.queryParam("page"),
                        request.queryParam("size"),
                        request.queryParam("title"),
                        request.queryParam("minPrice"),
                        request.queryParam("maxPrice"),
                        request.queryParam("sort")
                ), PageResponse.class);

    }

    public Mono<ServerResponse> getById(ServerRequest request) {

        UUID id = UUID.fromString(request.pathVariable("id"));
        return productService.getById(id)
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(ProductCreateRequest.class)
                .flatMap(product -> productService.add(product))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        return request.bodyToMono(Product.class).log()
                .flatMap(product -> productService.update(product))
                .flatMap(product -> ServerResponse.ok().bodyValue(product));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {

        return productService.delete(UUID.fromString(request.pathVariable("id")))
                .then(ServerResponse.ok().build());
    }

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

    public Mono<ServerResponse> fileDelete(ServerRequest request) {
        return ServerResponse.ok()
                .body(productService.fileDelete(UUID.fromString(request.pathVariable("id")), UUID.fromString(request.pathVariable("fileId"))), Void.class);
    }

    @Autowired
    public void init(ProductService ps) {
        this.productService = ps;
    }
}
