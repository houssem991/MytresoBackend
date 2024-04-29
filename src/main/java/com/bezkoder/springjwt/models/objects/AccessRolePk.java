package com.bezkoder.springjwt.models.objects;


import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.Role;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class AccessRolePk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "Role_Id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "Access_Id")
    private Access access;

    public AccessRolePk(Role role, Access access) {
        this.role = role;
        this.access = access;
    }

    public AccessRolePk() {

    }
}
