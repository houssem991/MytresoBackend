package com.bezkoder.springjwt.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessRoleResponse {
    String name_role;
    Integer role_id;
    String nameaccess;
    Integer access_id;
    boolean add;
    boolean modifier;
    boolean delete;
    boolean consulter;
}
