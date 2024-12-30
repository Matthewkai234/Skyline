package database;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Permissions;

@SuppressWarnings("ALL")
@Entity
@Table(name = "role_permission")
public class RolePermission implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;

    @Id
    @ManyToOne
    @JoinColumn(name = "permission_id", nullable = false)
    private Permissions permission;


    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }
}
