package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.AccessRole;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.objects.AccessRolePk;
import com.bezkoder.springjwt.payload.request.AccessroleRequest;
import com.bezkoder.springjwt.payload.response.AccessRoleResponse;
import com.bezkoder.springjwt.repository.AccessRepository;
import com.bezkoder.springjwt.repository.AccessRoleRepository;
import com.bezkoder.springjwt.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class AccessService implements IAccessService {
    @Autowired

    AccessRepository accessRepository;
    @Override
    public List<Access> findallAccessByRoleId(@Param("idrole") int idrole) {
       return accessRepository.findAllByRoleId(idrole);
    }

    @Override
    public List<Access> findallByRoleId(@Param("idrole") int idrole) {
        return accessRepository.findAllAccessByRoleId(idrole);
    }
}
