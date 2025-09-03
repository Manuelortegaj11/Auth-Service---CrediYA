package co.com.pragma.auth.api.dto;

public record CreateRoleResponse(
        Long idRole,
        String name,
        String description
) {}
