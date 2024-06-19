package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.payload.request.RoleRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class RoleService implements IRoleService {
    @Autowired

    UserRepository userRepository;
    @Autowired

    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<Role> findall(@Param("identreprise") int identreprise, @Param("idrole") int idrole , @Param("iduser") long iduser) {
        if (identreprise!=0){
       return  roleRepository.findallRoleByEntrepriseId(identreprise,idrole);}
        else return roleRepository.findallRoleByUserId(iduser);
    }

    @Override
    public Role findbyId(Integer id) {
        return roleRepository.findById(id).get();
    }
    @Override
    public Role findbyName(String name) {
        return roleRepository.findByName(name).get();
    }
    @Override
    public ResponseEntity<?> AddRole(RoleRequest roleRequest) {
        if (roleRepository.existsByName(roleRequest.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Role existe deja!"));
        }
        Role role = new Role();
        role.setName(roleRequest.getName());
        role.setUser(userRepository.findById(roleRequest.getIduser()).get());
        roleRepository.save(role);
        return ResponseEntity.ok(new MessageResponse("Le role est ajouté avec succés")) ;
    }

    @Override
    public ResponseEntity<?> UpdateRole(RoleRequest roleRequest, int id) {
        Role role = roleRepository.findById(id).get();
        role.setName(roleRequest.getName());
        roleRepository.save(role);
        return ResponseEntity.ok(new MessageResponse("Le role est modifié avec succés")) ;
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        Optional<Role> role = roleRepository.findById(id);
        Role r = role.get();
        roleRepository.delete(r);
        return ResponseEntity.ok("Le role est supprimé avec succés") ;
    }
}
