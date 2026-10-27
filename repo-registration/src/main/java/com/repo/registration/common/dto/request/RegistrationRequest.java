package com.repo.registration.common.dto.request;

import java.util.List;

public record RegistrationRequest(String name, boolean active, List<String> events, RegistrationConfig config) {
}
