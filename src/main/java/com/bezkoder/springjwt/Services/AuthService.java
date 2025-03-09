package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.payload.request.*;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.EnterpriseRepository;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
class AuthService implements IAuthService {
    @Autowired

    UserRepository userRepository;

    @Autowired

    EnterpriseRepository enterpriseRepository;
    @Autowired

    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    private FileStorageService fileStorageService;
    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        try {
            if (userRepository.existsByEmail(loginRequest.getEmail())) {
                Optional<User> optional = userRepository.findByEmail(loginRequest.getEmail());
                User user = optional.get();
                if (user.getStatus().equals(EUser.NotVAlide)) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("veuillez validez votre adresse email pour acceder à votre compte "));
                }
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtUtils.generateJwtToken(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());

                return ResponseEntity.ok(new JwtResponse(jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
            } else return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email or password is invalid"));
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email or password is invalid"));
        }
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setLastname(signUpRequest.getLastname());
        user.setFirstname(signUpRequest.getFirstname());
        user.setEmail(signUpRequest.getEmail());
        user.setStatus(EUser.NotVAlide);
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
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
                        Role userrRole = roleRepository.findByName("ROLE_USER")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userrRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        String request = "http://localhost:4200/pages/validateUser/";
        String subject ="Vérification de votre e-mail";
        String imagePath = "classpath:static/uploads/digid.png";
        String appUrl = request + user.getId();
        String body = " <html> <head> <meta charset='UTF-8'> <title>IVérification de votre e-mail</title> <style>"
                + """
              * {
                       margin: 0;
                       padding: 0;
                       box-sizing: border-box;
                       font-family: Arial, Helvetica, sans-serif;
                     }
                     .container {
                       max-width: 600px;
                       margin: auto;
                       padding: 20px;
                       border: 1px solid #ccc;
                       border-radius: 5px;
                       background-color: #f4f4f4;
                     }
                     h1 {
                       margin-bottom: 20px;
                       text-align: center;
                       color: #444;
                     }
                     img {
                       max-width: 100%;
                       height: auto;
                       margin: auto;
                       margin-bottom: 20px;
                       display: block;\s
                     }
                     p {
                       margin-bottom: 10px;
                       line-height: 1.5;
                       color: #444;
                     }
                     a {
                       color: #fff !important;
                       text-decoration: none;
                     }
                     a:hover {
                       text-decoration: underline;
                     }
                     .imgI {
                       max-width: 100%;
                       height: auto;
                       width: 50%;\s
                       display: block;
                       margin: 10% auto;\s
                     }
                     .cta-btn {
                       display: block;
                       width: 100%;
                       max-width: 200px;
                       margin: 20px auto;
                       padding: 10px;
                       border-radius: 15px;
                       background-color: #8095F2;
                       color: #fff;
                       text-align: center;
                       text-decoration: none;
                     }
                     .signature {
                       margin-top: 20px;
                       text-align: left;
                       font-size: 12px;
                       color: #888;
                     }
                   </style>
             """
                +
                " </head> <body> <div class='container'> <img src='cid:image' class='imgI' width='100%'>"
                +" <h1>Vérification de votre e-mail</h1>"
                +"<p>Merci pour votre inscription à My Treso .Vous recevez cet e-mail pour verifier l'e-mail de votre compte.</p>"+
                " <p>Veuillez cliquer sur le lien ci-dessous ou le copier-coller dans votre navigateur pour terminer le processus:</p>"
                + " <a href='"+appUrl+"' class='cta-btn'>Verification Email</a>" +
                "</div>"+
                "</body>"+
                "</html>";

        emailSenderService.sendEmailWithImage(signUpRequest.getEmail(),subject, body,imagePath);

        return ResponseEntity.ok(new MessageResponse(user.getId().toString()));
    }

    @Override
    public ResponseEntity<?> registerCollaborator(SignupCollaboratorRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setLastname(signUpRequest.getLastname());
        user.setFirstname(signUpRequest.getFirstname());
        user.setEmail(signUpRequest.getEmail());
        user.setStatus(EUser.Valide);
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        Role role = roleRepository.findById(signUpRequest.getIdrole())
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
roles.add(role);
        user.setRoles(roles);
        user.setEntreprise(enterpriseRepository.findById(signUpRequest.getIdentreprise()).orElseThrow(() -> new RuntimeException("Error: Entreprise is not found.")));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse(user.getId().toString()));
    }

    @Override
    public ResponseEntity<?> InviteCollaborateur(InviteRequest inviteRequest) throws Exception {
        Optional<User> optional = userRepository.findByEmail(inviteRequest.getEmail());
        String request = "http://localhost:4200/pages/registerCollaborator/";
        if (userRepository.existsByEmail(inviteRequest.getEmail())) {
            // userService.passwordoublier(email);
            // Generate random 36-character string token for reset password
            User user = optional.get();
            Entreprise entreprise = enterpriseRepository.findById(user.getEntreprise().getId())
                    .orElseThrow(() -> new RuntimeException("Error: Entreprise is not found."));

            // Save token to database
            String subject ="Invitation à Collaborer dans l'entreprise "+entreprise.getName();
            String imagePath = "classpath:static/uploads/digid.png";
            String appUrl = request + entreprise.getId()+"/"+inviteRequest.getIdrole()+"/"+ EmailEncryptionUtil.encryptEmail(inviteRequest.getEmailCollabrator());
            String body = " <html> <head> <meta charset='UTF-8'> <title>Invitation à Collaborer dans l'entreprise "+entreprise.getName()+"</title> <style>"
                    + """
              * {
                       margin: 0;
                       padding: 0;
                       box-sizing: border-box;
                       font-family: Arial, Helvetica, sans-serif;
                     }
                     .container {
                       max-width: 600px;
                       margin: auto;
                       padding: 20px;
                       border: 1px solid #ccc;
                       border-radius: 5px;
                       background-color: #f4f4f4;
                     }
                     h1 {
                       margin-bottom: 20px;
                       text-align: center;
                       color: #444;
                     }
                     img {
                       max-width: 100%;
                       height: auto;
                       margin: auto;
                       margin-bottom: 20px;
                       display: block;\s
                     }
                     p {
                       margin-bottom: 10px;
                       line-height: 1.5;
                       color: #444;
                     }
                     a {
                       color: #fff !important;
                       text-decoration: none;
                     }
                     a:hover {
                       text-decoration: underline;
                     }
                     .imgI {
                       max-width: 100%;
                       height: auto;
                       width: 50%;\s
                       display: block;
                       margin: 10% auto;\s
                     }
                     .cta-btn {
                       display: block;
                       width: 100%;
                       max-width: 200px;
                       margin: 20px auto;
                       padding: 10px;
                       border-radius: 15px;
                       background-color: #8095F2;
                       color: #fff;
                       text-align: center;
                       text-decoration: none;
                     }
                     .signature {
                       margin-top: 20px;
                       text-align: left;
                       font-size: 12px;
                       color: #888;
                     }
                   </style>
             """
                    +
                    " </head> <body> <div class='container'> <img src='cid:image' class='imgI' width='100%'>"
                    +" <h1>Invitation à Collaborer dans l'entreprise "+entreprise.getName()+"</h1>"
                    +"<p>Vous recevez cet e-mail parce que le propriétaire de l'entreprise "+entreprise.getName()+" qui a pour email "+inviteRequest.getEmail()+" vous a invité à devenir un collaborateur dans son espace del'application MY Treso.</p>"+
                    " <p>Veuillez cliquer sur le lien ci-dessous ou le copier-coller dans votre navigateur pour terminer le processus:</p>"
                    + " <a href='"+appUrl+"' class='cta-btn'>Accepter l'invitation</a>" +
                    "</div>"+
                    "</body>"+
                    "</html>";

            emailSenderService.sendEmailWithImage(inviteRequest.getEmailCollabrator(),subject, body,imagePath);






            return ResponseEntity
                    .ok(new MessageResponse("votre invitation a été envoyer avec succeés !"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("cette adresse email n'est pas valide "));
        }
    }

    @Override
    public ResponseEntity<?> ValidateUser(long iduser) {
        User user = userRepository.findById(iduser).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        if(user.getStatus().equals(EUser.Valide)){
            return ResponseEntity
                    .ok(new MessageResponse("Votre adresse e-mail est déja verifié"));
        }
        user.setStatus(EUser.Valide);
        userRepository.save(user);
        return ResponseEntity
                .ok(new MessageResponse("Votre adresse e-mail est verifié avec succes vous pouvez accéder à votre compte"));
    }

    @Override
    public ResponseEntity<?> passwordoubli(PasswordoublierRequest loginRequest) throws Exception {
        Optional<User> optional = userRepository.findByEmail(loginRequest.getEmail());
        String request = "http://localhost:4200/pages/changepassword/";
        if (userRepository.existsByEmail(loginRequest.getEmail())) {
            // userService.passwordoublier(email);
            // Generate random 36-character string token for reset password
            User user = optional.get();
            user.setResetToken(UUID.randomUUID().toString());
            user.setExpiryDate(30);
            // Save token to database
            userRepository.save(user);
            String subject ="Password Reset Request";
            String imagePath = "classpath:static/uploads/digid.png";
            String appUrl = request + user.getResetToken() + "/" + EmailEncryptionUtil.encryptEmail(user.getEmail()).toString(); 
            String body = " <html> <head> <meta charset='UTF-8'> <title>Reset Password</title> <style>"
                  + """
              * {
                       margin: 0;
                       padding: 0;
                       box-sizing: border-box;
                       font-family: Arial, Helvetica, sans-serif;
                     }
                     .container {
                       max-width: 600px;
                       margin: auto;
                       padding: 20px;
                       border: 1px solid #ccc;
                       border-radius: 5px;
                       background-color: #f4f4f4;
                     }
                     h1 {
                       margin-bottom: 20px;
                       text-align: center;
                       color: #444;
                     }
                     img {
                       max-width: 100%;
                       height: auto;
                       margin: auto;
                       margin-bottom: 20px;
                       display: block;\s
                     }
                     p {
                       margin-bottom: 10px;
                       line-height: 1.5;
                       color: #444;
                     }
                     a {
                       color: #fff !important;
                       text-decoration: none;
                     }
                     a:hover {
                       text-decoration: underline;
                     }
                     .imgI {
                       max-width: 100%;
                       height: auto;
                       width: 50%;\s
                       display: block;
                       margin: 10% auto;\s
                     }
                     .cta-btn {
                       display: block;
                       width: 100%;
                       max-width: 200px;
                       margin: 20px auto;
                       padding: 10px;
                       border-radius: 15px;
                       background-color: #8095F2;
                       color: #fff;
                       text-align: center;
                       text-decoration: none;
                     }
                     .signature {
                       margin-top: 20px;
                       text-align: left;
                       font-size: 12px;
                       color: #888;
                     }
                   </style>
             """
                    +
                    " </head> <body> <div class='container'> <img src='cid:image' class='imgI' width='100%'>"
                    +" <h1>Réinitialiser le mot de passe</h1>"
                    +"<p>Vous recevez cet e-mail parce que vous (ou quelqu'un d'autre) avez demandé une réinitialisation de votre mot de passe pour votre compte.</p>"+
                    " <p>Veuillez cliquer sur le lien ci-dessous ou le copier-coller dans votre navigateur pour terminer le processus:</p>"
                    + " <a href='"+appUrl+"' class='cta-btn'>Réinitialiser le mot de passe</a>" + "<p>Si vous n'avez pas fait cette demande, veuillez ignorer cet e-mail et votre mot de passe restera inchangé.</p>"+
                    "</div>"+
                    "</body>"+
                    "</html>";

            emailSenderService.sendEmailWithImage(loginRequest.getEmail(),subject, body,imagePath);






            return ResponseEntity
                    .ok(new MessageResponse("Nous vous avons envoyé par email le lien de réinitialisation du mot de passe !"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("cette adresse email n'est pas valide "));
        }
    }

    @Override
    public ResponseEntity<?> restpass(RestRequest restRequest) throws Exception {
        Optional<User> optional = userRepository.findByEmail(restRequest.getEmail());
        User user = optional.get();
        System.out.println(restRequest.getPassword().length());
        if (restRequest.getPassword().length() < 6) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("la taille doit être comprise entre 6 et 40"));

        } else if (user.getResetToken() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Impossible de trouver le jeton de réinitialisation du mot de passe"));

        } else if (user.isExpired()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Le jeton a expiré, veuillez demander une nouvelle réinitialisation de mot de passe."));

        } else {
            user.setPassword(encoder.encode(restRequest.getPassword()));
            userRepository.save(user);
            return ResponseEntity
                    .ok(new MessageResponse("Password changed with success"));
        }
    }
    public String uploadImage(long id,MultipartFile file) {
        String fileName = fileStorageService.storeImageUser(id, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return "le fichier a téléchargé avec succès";
    }
    public ResponseEntity<Resource> downloadFile( String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
