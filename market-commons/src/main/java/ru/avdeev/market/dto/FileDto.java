package ru.avdeev.market.dto;

import java.util.UUID;

public class FileDto {

    private UUID id;
    private UUID ownerId;
    private String name;
    private Integer order;
    private String descr;

    public FileDto(UUID id, UUID ownerId, String name, Integer order, String descr) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.order = order;
        this.descr = descr;
    }

    public FileDto() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
