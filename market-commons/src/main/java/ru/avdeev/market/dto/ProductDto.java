package ru.avdeev.market.dto;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductDto {

    private UUID id;
    private String title;
    private BigDecimal price;
    private List<FileDto> files;

    public ProductDto() {
    }

    public ProductDto(UUID id, String title, BigDecimal price, List<FileDto> files) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.files = files;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setFiles(List<FileDto> files) {
        this.files = files;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<FileDto> getFiles() {
        return files;
    }
}
