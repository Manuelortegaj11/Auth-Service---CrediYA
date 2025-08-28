package co.com.pragma.auth.model.user;
import co.com.pragma.auth.model.role.Role;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
//import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String identityDocument;
    private String phoneNumber;
    private Role idRole;
    private BigDecimal baseSalary;
    /*
    private String address;
    private String password;
    private LocalDate birthDate;
    */
}
