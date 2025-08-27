package co.com.pragma.auth.model.user;
import co.com.pragma.auth.model.role.Role;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String name;
    private String lastName;
    private String email;
    private String identityDocument;
    private String phoneNumber;
    private Role idRole;
    private Long baseSalary;
}
