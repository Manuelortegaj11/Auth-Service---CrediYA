package co.com.pragma.auth.r2dbc.dto;

import java.math.BigDecimal;

public record UserWithRole(
        Long idUser,
        String name,
        String lastName,
        String email,
        String identityDocument,
        String phoneNumber,
        BigDecimal baseSalary,
        Long idRole,
        String roleName,
        String roleDescription
) {}
