package com.repo.registration.common.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RegistrationConfig(String url, @JsonProperty("content_type") String contentType,
                                 @JsonProperty("insecure_ssl") String insecureSsl) {
}
