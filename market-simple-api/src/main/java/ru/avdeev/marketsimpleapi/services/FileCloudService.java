package ru.avdeev.marketsimpleapi.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.avdeev.marketsimpleapi.exceptions.FileCloudException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileCloudService {
    @Value("${cloud.dir.product}")
    private String baseDir;

    @Value("${cloud.url}")
    private String url;

    @Value("${cloud.upload}")
    private String upload;

    @Value("${cloud.delete}")
    private String delete;

    private final WebClient webClient;

    public Mono<Boolean> save(String folder, String filename, FilePart filePart) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", filePart);
        MultiValueMap<String, HttpEntity<?>> parts = builder.build();
        String filePath = baseDir + folder;

        return webClient.post()
                .uri(urlBuilder -> urlBuilder
                        .path(url)
                        .pathSegment(upload)
                        .queryParam("folder", filePath)
                        .queryParam("name", filename)
                        .build())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(parts))
                .exchangeToMono(response -> {
                    if (response.statusCode() != HttpStatus.OK)
                        return Mono.error(new FileCloudException("Can't save file into cloud"));
                    log.info("File saved to cloud");
                    return Mono.just(Boolean.TRUE);
                });
    }


    public Mono<Boolean> delete(String folder, String filename) {

        String filePath = baseDir + folder;

        return webClient.post()
                .uri(b -> b
                        .path(url)
                        .pathSegment(delete)
                        .queryParam("folder", filePath)
                        .queryParam("name", filename)
                        .build())
                .exchangeToMono(response -> {
                    if (response.statusCode() != HttpStatus.OK)
                        return Mono.error(new FileCloudException("Can't delete file into cloud"));
                    log.info("File deleted from cloud");
                    return Mono.just(Boolean.TRUE);
                });
    }


    public Mono<Void> deleteFolder(String folder) {
        return webClient.post()
                .uri(b -> b
                        .path(url)
                        .pathSegment(delete)
                        .queryParam("folder", folder)
                        .build())
                .exchangeToMono(response -> {
                    if (response.statusCode() == HttpStatus.OK) {
                        log.info("Directory delete from cloud: {}", folder);
                        return Mono.empty();
                    }
                    log.info("Delete directory failing: {}", folder);
                    return Mono.error(new FileCloudException("Delete directory failing: " + folder));
                });
    }
}
