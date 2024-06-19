package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.payload.request.RoleRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAccessService {

    List<Access> findallAccessByRoleId(int idrole);
    List<Access> findallByRoleId(int idrole);
}
