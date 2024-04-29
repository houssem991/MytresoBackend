package com.bezkoder.springjwt.controllers;


import com.bezkoder.springjwt.Services.EmailSenderService;
import com.bezkoder.springjwt.Services.FileStorageService;
import com.bezkoder.springjwt.Services.IAuthService;


import com.bezkoder.springjwt.payload.request.PasswordoublierRequest;
import com.bezkoder.springjwt.payload.request.RestRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.springjwt.payload.request.LoginRequest;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.repository.RoleRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.security.jwt.JwtUtils;
import com.bezkoder.springjwt.security.services.UserDetailsImpl;
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
  EmailSenderService emailSenderService;

  @Autowired
  private FileStorageService fileStorageService;
  @Autowired
  private IAuthService iAuthService;

  @Autowired
  private JavaMailSender javaMailSender;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return iAuthService.authenticateUser(loginRequest);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
   return iAuthService.registerUser(signUpRequest);
  }

 /* @PostMapping("/uploadImage/{id}")
  public String uploadImage(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) {
    String fileName = fileStorageService.storeImageUser(id, file);

    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/downloadFile/")
            .path(fileName)
            .toUriString();

    return "le fichier a téléchargé avec succès";
  }
  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile( @PathVariable String fileName, HttpServletRequest request) {
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
  }*/
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
}
