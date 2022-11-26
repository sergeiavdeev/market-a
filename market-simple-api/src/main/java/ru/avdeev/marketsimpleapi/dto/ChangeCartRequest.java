package ru.avdeev.marketsimpleapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChangeCartRequest {

    private UUID productId;
    private int delta;
}
