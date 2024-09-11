package com.bezkoder.springjwt;

import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.models.objects.AccessRolePk;
import com.bezkoder.springjwt.repository.AccessRepository;
import com.bezkoder.springjwt.repository.AccessRoleRepository;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class DefaultUserConfig {

    @Bean
    public CommandLineRunner createDefaultUser(UserRepository userRepository, RoleRepository roleRepository, AccessRepository accessRepository, AccessRoleRepository accessRoleRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (accessRepository.count() == 0) {
                Access access = new Access();
                access.setIcon("ft-home");
                access.setPath("/dashboard/dashboard1");
                access.setTitle("Dashboard");
                access.setExternalLink(false);
                Access access1 = new Access();
                access1.setIcon("ft-lock");
                access1.setPath("/pages/agents-role");
                access1.setTitle("Gestion des roles");
                access1.setExternalLink(false);
                Access access2 = new Access();
                access2.setIcon("ft-user");
                access2.setPath("/pages/gestion-fournisseurs");
                access2.setTitle("Gestion des Fournisseurs");
                access2.setExternalLink(false);
                Access access3 = new Access();
                access3.setIcon("ft-file-text");
                access3.setPath("/pages/gestion-facture-fournissseurs");
                access3.setTitle("Documents Fournisseurs");
                access3.setExternalLink(false);
                Access access4 = new Access();
                access4.setIcon("ft-file-text");
                access4.setPath("/pages/gestion-reglement-fournissseurs");
                access4.setTitle("Reglements Fournisseurs");
                access4.setExternalLink(false);
                Access access5 = new Access();
                access5.setIcon("ft-file-text");
                access5.setPath("/pages/gestion-Impayes-fournissseurs");
                access5.setTitle("Impayes Fournisseurs");
                access5.setExternalLink(false);
                Access access6 = new Access();
                access6.setIcon("ft-user");
                access6.setPath("/pages/gestion-clients");
                access6.setTitle("Gestion des Clients");
                access6.setExternalLink(false);
                Access access7 = new Access();
                access7.setIcon("ft-file-text");
                access7.setPath("/pages/gestion-facture-Clients");
                access7.setTitle("Documents Clients");
                access7.setExternalLink(false);
                Access access8 = new Access();
                access8.setIcon("ft-file-text");
                access8.setPath("/pages/gestion-reglements-Clients");
                access8.setTitle("Reglements Clients");
                access8.setExternalLink(false);
                Access access9 = new Access();
                access9.setIcon("ft-file-text");
                access9.setPath("/pages/gestion-Imapyes-Clients");
                access9.setTitle("Imapyes Clients");
                access9.setExternalLink(false);
                Access access10 = new Access();
                access10.setIcon("fa fa-money");
                access10.setPath("/pages/Caisse");
                access10.setTitle("Caisse");
                access10.setExternalLink(false);
                Access access11 = new Access();
                access11.setIcon("bi bi-bank");
                access11.setPath("/pages/banque");
                access11.setTitle("Banque");
                access11.setExternalLink(false);
                accessRepository.save(access);
                accessRepository.save(access1);
                accessRepository.save(access2);
                accessRepository.save(access3);
                accessRepository.save(access4);
                accessRepository.save(access5);
                accessRepository.save(access6);
                accessRepository.save(access7);
                accessRepository.save(access8);
                accessRepository.save(access9);
                accessRepository.save(access10);
                accessRepository.save(access11);
            }
            // Vérifier si la table des utilisateurs est vide
            if (userRepository.count() == 0) {
                // Ajouter l'utilisateur par défaut
                User user = new User();
                user.setUsername("admin");
                user.setLastname("admin");
                user.setFirstname("admin");
                user.setEmail("admin@digidco.com");
                user.setPassword(passwordEncoder.encode("123456"));
                user.setStatus(EUser.Valide);
                if(roleRepository.count() == 0){
                    Role role = new Role();
                    role.setName("ROLE_ADMIN");
                    Role role1 = new Role();
                    role1.setName("ROLE_USER");
                    roleRepository.save(role);
                    roleRepository.save(role1);
                }
                Role role = roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                Role role1 = roleRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                user.setRoles(roles);
                userRepository.save(user);
                role.setUser(user);
                role1.setUser(user);
                roleRepository.save(role);
                roleRepository.save(role1);
            }
            if(accessRoleRepository.count() == 0 ){
                Role role = roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                List<Access> accesss = accessRepository.findAll();
                accesss.forEach(val -> {
                    AccessRole accessRole = new AccessRole();
                    accessRole.setId(new AccessRolePk(role, val)); // Set the identifier value
                    accessRole.setRole(role);
                    accessRole.setAccess(val);
                    accessRole.setAjouter(true);
                    accessRole.setModifier(true);
                    accessRole.setConsulter(true);
                    accessRole.setSupprimer(true);
                    accessRoleRepository.save(accessRole);
                });

            }
        };
    }
}

