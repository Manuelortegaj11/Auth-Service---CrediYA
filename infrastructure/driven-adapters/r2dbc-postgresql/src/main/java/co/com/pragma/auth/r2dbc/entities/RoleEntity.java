package co.com.pragma.auth.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table("roles")
public class RoleEntity {
    @Id @Column("id_role") private Long idRole;
    @Column("name") private String name;
    @Column("description") private String description;
}
