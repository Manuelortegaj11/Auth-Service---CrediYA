package co.com.pragma.auth.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {
    @Id @Column("id_user") private Long idUser;
    @Column("name") private String name;
    @Column("last_name") private String lastName;
    @Column("email") private String email;
    @Column("identity_document") private String identityDocument;
    @Column("phone_number") private String phoneNumber;
    @Column("base_salary") private BigDecimal baseSalary;
    @Column("id_role") private Long idRole;
    @Transient private RoleEntity role;
}
