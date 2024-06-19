package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.payload.response.UserResponse;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
class UserService implements IUserService {
    @Autowired

    UserRepository userRepository;
    @Autowired

    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Override
    public List<UserResponse> findall() {

        List<User> users = userRepository.findAll();
        List<UserResponse> us = new ArrayList<>();
        users.forEach(val->{
            UserResponse ur = new UserResponse();
            ur.setId(val.getId());
            ur.setUsername(val.getUsername());
            ur.setImage(val.getImage());
            ur.setPassword(val.getPassword());
            ur.setEmail(val.getEmail());
            ur.setFirstname(val.getFirstname());
            ur.setLastname(val.getLastname());
            us.add(ur);
        });
        return us;

    }

    @Override
    public List<UserResponse> findallUserByEntreprise(@Param("identreprise")int identreprise) {
        List<User> users= userRepository.findUsersByEntrepriseId(identreprise);
        List<UserResponse> us = new ArrayList<>();
        users.forEach(val->{
            UserResponse ur = new UserResponse();
            ur.setId(val.getId());
            ur.setUsername(val.getUsername());
            ur.setImage(val.getImage());
            ur.setPassword(val.getPassword());
            ur.setEmail(val.getEmail());
            ur.setFirstname(val.getFirstname());
            ur.setLastname(val.getLastname());
            ur.setRoles(val.getRoles());
            us.add(ur);
        });
        return us;
    }

    @Override
    public UserResponse findbyIdd(Long id) {
        User val = userRepository.findById(id).get();
        UserResponse u = new UserResponse();
        u.setId(val.getId());
        u.setUsername(val.getUsername());
        u.setImage(val.getImage());
        u.setPassword(val.getPassword());
        u.setEmail(val.getEmail());
        u.setFirstname(val.getFirstname());
        u.setLastname(val.getLastname());
        u.setRoles(val.getRoles());
        if (val.getEntreprise() != null) {
           u.setIdentreprise(val.getEntreprise().getId());
           u.setNameEntreprise(val.getEntreprise().getName());
           u.setLogo(val.getEntreprise().getLogo());
       }


        return u;
    }

    @Override
    public ResponseEntity<?> UpdateUser(SignupRequest signUpRequest, long id) {
        User user= userRepository.findById(id).get();
   /* if(!Objects.equals(user.getEmail(), signUpRequest.getEmail())){
      user.setEmail(signUpRequest.getEmail());
    }*/
        if (!Objects.equals(user.getUsername(), signUpRequest.getUsername())){
            user.setUsername(signUpRequest.getUsername());
        }


        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setFirstname(signUpRequest.getFirstname());
        user.setLastname(  signUpRequest.getLastname());


          /* Set<Clubs> clubs =new HashSet<>();
            signUpRequest.getNameclubs().forEach(c->{
              Clubs club =clubsRepository.findByName(c).get();
              clubs.add(club);

            });
            user.setClubs(clubs);*/





        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;

                    default:
                        Role userRole = roleRepository.findByName("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse(user.getId().toString()));
    }



    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).get();

        userRepository.delete(user);
    }
}
