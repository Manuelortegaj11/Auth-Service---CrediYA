package co.com.pragma.auth.api.dto;

public record CreateRoleRequest(
        String name,
        String description
) {}
