package co.com.pragma.auth.api.dto;

public record CreateUserResponse(
        Long idUser,
        String name,
        String email
) {}
