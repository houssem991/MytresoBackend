package com.bezkoder.springjwt.controllers;
import com.bezkoder.springjwt.Services.EmailEncryptionUtil;
import com.bezkoder.springjwt.Services.IAuthService;
import com.bezkoder.springjwt.payload.request.*;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {


  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  private IAuthService iAuthService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return iAuthService.authenticateUser(loginRequest);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
   return iAuthService.registerUser(signUpRequest);
  }
  @PostMapping("/signupC")
  public ResponseEntity<?> registerCollaborator(@Valid @RequestBody SignupCollaboratorRequest signUpRequest) {
    return iAuthService.registerCollaborator(signUpRequest);
  }
  @PostMapping("/invite")
  public ResponseEntity<?> InviteCollaborateur(@Valid @RequestBody InviteRequest inviteRequest) throws Exception {
        return iAuthService.InviteCollaborateur(inviteRequest);
  }
  @PutMapping("/validate/{id}")
  public ResponseEntity<?> ValidateUser(@PathVariable("id") long id) {
    return iAuthService.ValidateUser(id);
  }
  @PostMapping("/passwordoubli")
  public ResponseEntity<?> passwordoubli(@Valid @RequestBody PasswordoublierRequest loginRequest) throws Exception {
   return iAuthService.passwordoubli(loginRequest);
  }

  @PostMapping("/restpass")
  public ResponseEntity<?> restpass(@RequestBody RestRequest restRequest) throws Exception {
   return iAuthService.restpass(restRequest);
  }
  @PostMapping("/uploadImage/{id}")
  public String uploadImage(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
     return iAuthService.uploadImage(id,file);
  }
  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
     return iAuthService.downloadFile(fileName,request);
  }
  @PostMapping("/decrypt-email/{encryptedEmail}")
  public ResponseEntity<MessageResponse> decryptEmail(@PathVariable("encryptedEmail") String encryptedEmail) {
    try {
      return ResponseEntity.ok(new MessageResponse(EmailEncryptionUtil.decryptEmail(encryptedEmail)));
    } catch (Exception e) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error decrypting email"));
    }
  }
}
