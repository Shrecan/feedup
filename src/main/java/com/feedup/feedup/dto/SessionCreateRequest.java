package com.feedup.feedup.dto;

import jakarta.validation.constraints.NotBlank;

public record SessionCreateRequest(
        @NotBlank String sessionName
) {
}
