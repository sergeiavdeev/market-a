package ru.avdeev.marketsimpleapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.dto.AddProductRequest;
import ru.avdeev.marketsimpleapi.dto.PageResponse;
import ru.avdeev.marketsimpleapi.dto.ProductDto;
import ru.avdeev.marketsimpleapi.entities.FileEntity;
import ru.avdeev.marketsimpleapi.entities.Product;
import ru.avdeev.marketsimpleapi.exceptions.EntityNotFondException;
import ru.avdeev.marketsimpleapi.mappers.ProductMapper;
import ru.avdeev.marketsimpleapi.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final R2dbcEntityTemplate databaseClient;
    private final FileService fileService;
    private final FileCloudService fileCloudService;
    private final ProductMapper mapper;

    @Value("${product.default-page-size}")
    private String defaultPageSize;

    public Mono<PageResponse<ProductDto>> getPage(Optional<String> page, Optional<String> size, Optional<String> title, Optional<String> minPrice, Optional<String> maxPrice, Optional<String> sort) {

        int pageNum = Integer.parseInt(page.orElse("1"));
        int pageSize = Integer.parseInt(size.orElse(defaultPageSize));

        if (pageNum < 1) pageNum = 1;
        if (pageSize < 1) pageSize = 1;

        return getPage(
                PageRequest.of(pageNum - 1, pageSize, sort.isPresent() ? createSortFromString(sort.get()) : Sort.unsorted()),
                creteCriteria(title, minPrice, maxPrice)
        );
    }

    public Mono<ProductDto> getById(UUID id) {
        return repository.findById(id)
                .map(mapper::toProductDto)
                .flatMap(product -> fileService.getByOwnerId(product.getId())
                        .collectList()
                        .flatMap(fileEntities -> {
                            product.setFiles(fileEntities);
                            return Mono.just(product);
                        }))
                .switchIfEmpty(Mono.error(new EntityNotFondException(id, "Product")));
    }

    @Transactional
    public Mono<Product> update(Product product) {
        return getById(product.getId())
                .flatMap(existProduct -> repository.save(product));
    }

    public Mono<Product> add(AddProductRequest addProductRequest) {

        return Mono.just(addProductRequest)
                .map(mapper::toProduct)
                .flatMap(repository::save);
    }

    @Transactional
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id)
                .then(fileService.deleteByOwnerId(id))
                .then(fileCloudService.deleteFolder(id.toString()));
    }

    public Mono<FileEntity> saveFile(FilePart part, String productId, Optional<Integer> order, Optional<String> descr) {

        String fileExtension = getFileExtension(part.filename());
        String newUUID = UUID.randomUUID().toString();
        String fileName = String.format("%s.%s", newUUID, fileExtension);
        String dbFileName = String.format("%s.%s", newUUID, fileExtension);

        return fileCloudService.save(productId, fileName, part)
                .flatMap(success -> fileService.save(new FileEntity(
                        null,
                        UUID.fromString(productId),
                        dbFileName,
                        order.orElse(0), descr.orElse("")))
                )
                .onErrorResume(Mono::error);
    }

    public Mono<Void> fileDelete(UUID productId, UUID fileId) {
        return fileService.getById(fileId)
                .flatMap(fileEntity -> fileCloudService.delete(productId.toString(), fileEntity.getName()))
                .flatMap(success -> fileService.detete(fileId))
                .onErrorResume(Mono::error);
    }

    private Sort createSortFromString(String sortString) {
        Sort sort = Sort.unsorted();
        String[] sortFields = sortString.split(",");
        for (String s : sortFields) {
            sort = sort.and(Sort.by(s));
        }
        return sort;
    }

    private Criteria creteCriteria(Optional<String> title, Optional<String> minPrice, Optional<String> maxPrice) {

        AtomicReference<Double> minPriceFilter = new AtomicReference<>();
        AtomicReference<Double> maxPriceFilter = new AtomicReference<>();

        minPrice.ifPresent(value -> minPriceFilter.set(Double.valueOf(value)));
        maxPrice.ifPresent(value -> maxPriceFilter.set(Double.valueOf(value)));

        Criteria criteria = Criteria.empty();

        if (title.isPresent())
            criteria = criteria.and(Criteria.where("title").like(String.format("%%%s%%", title.get())).ignoreCase(true));
        if (minPriceFilter.get() != null)
            criteria = criteria.and(Criteria.where("price").greaterThanOrEquals(minPriceFilter.get()));
        if (maxPriceFilter.get() != null)
            criteria = criteria.and(Criteria.where("price").lessThanOrEquals(maxPriceFilter.get()));

        return criteria;
    }

    private String getFileExtension(String fileName) {

        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    private Mono<PageResponse<ProductDto>> getPage(Pageable page, Criteria criteria) {

        Query query = Query.query(criteria)
                .sort(page.getSort())
                .limit(page.getPageSize())
                .offset((long) page.getPageSize() * page.getPageNumber());

        return databaseClient.select(Product.class).from("product")
                .matching(query)
                .all()
                .map(mapper::toProductDto)
                .concatMap(product -> fileService.getByOwnerId(product.getId())
                        .collectList()
                        .flatMap(files -> {
                            product.setFiles(files);
                            return Mono.just(product);
                        }))
                .collectList()
                .zipWith(
                        databaseClient.select(Product.class)
                                .from("product")
                                .matching(query).count())
                .map(t -> new PageResponse<>(t.getT1(), t.getT2(), page.getPageNumber(), page.getPageSize()));
    }
}
