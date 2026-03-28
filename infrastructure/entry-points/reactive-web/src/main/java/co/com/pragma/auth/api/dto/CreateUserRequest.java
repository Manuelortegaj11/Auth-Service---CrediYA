package co.com.pragma.auth.api.dto;

import java.math.BigDecimal;

public record CreateUserRequest(
        String name,
        String lastName,
        String email,
        String identityDocument,
        String phoneNumber,
        BigDecimal baseSalary,
        Long idRole
) {}
