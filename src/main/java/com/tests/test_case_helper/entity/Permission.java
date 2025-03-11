package com.tests.test_case_helper.entity;

import com.tests.test_case_helper.enums.PermissionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class Permission implements GrantedAuthority {

    public Permission(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission_type")
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;


    @Override
    public String getAuthority() {
        return permissionType.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) && permissionType == that.permissionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permissionType);
    }
}
